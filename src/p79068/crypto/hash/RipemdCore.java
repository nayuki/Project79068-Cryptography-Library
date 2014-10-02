package p79068.crypto.hash;

import java.math.BigInteger;
import java.util.Arrays;

import p79068.Assert;
import p79068.crypto.Zeroizer;
import p79068.hash.HashValue;
import p79068.math.IntegerBitMath;


final class RipemdCore extends BlockHasherCore {
	
	private final int hashLength;  // In bytes. Note that -16 indicates RIPEMD (original)
	
	private int[] state;
	
	
	
	public RipemdCore(int hashLen) {
		hashLength = hashLen;
		
		if (hashLen == -16)  // RIPEMD (original) has same initial state as RIPEMD-128
			hashLen = 16;
		switch (hashLen) {
			case 16 /* RIPEMD-128 */:  state = new int[]{0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476};  break;
			case 20 /* RIPEMD-160 */:  state = new int[]{0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0};  break;
			case 32 /* RIPEMD-256 */:  state = new int[]{0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0x76543210, 0xFEDCBA98, 0x89ABCDEF, 0x01234567};  break;
			case 40 /* RIPEMD-320 */:  state = new int[]{0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0, 0x76543210, 0xFEDCBA98, 0x89ABCDEF, 0x01234567, 0x3C2D1E0F};  break;
			default:  throw new AssertionError();
		}
	}
	
	
	
	@Override
	public RipemdCore clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		RipemdCore result = (RipemdCore)super.clone();
		result.state = result.state.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		state = Zeroizer.clear(state);
	}
	
	
	
	@Override
	public void compress(byte[] message, int off, int len) {
		Assert.assertRangeInBounds(message.length, off, len);
		if (len % 64 != 0)
			throw new AssertionError();
		
		int[] schedule = new int[16];
		
		// For each block of 64 bytes
		for (int i = off, end = off + len; i < end; ) {
			
			// Pack bytes into int32s in little endian
			for (int j = 0; j < 16; j++, i += 4) {
				schedule[j] =
					  (message[i + 0] & 0xFF) <<  0
					| (message[i + 1] & 0xFF) <<  8
					| (message[i + 2] & 0xFF) << 16
					| (message[i + 3] & 0xFF) << 24;
			}
			
			if (hashLength == -16) {  // RIPEMD (original)
				int al = state[0], ar = state[0];
				int bl = state[1], br = state[1];
				int cl = state[2], cr = state[2];
				int dl = state[3], dr = state[3];
				for (int j = 0; j < 48; j++) {  // The 48 rounds
					int temp;
					temp = Integer.rotateLeft(al + f0(j, bl, cl, dl) + schedule[R0[j]] + KL[j / 16], S0[j]);
					al = dl;
					dl = cl;
					cl = bl;
					bl = temp;
					temp = Integer.rotateLeft(ar + f0(j, br, cr, dr) + schedule[R0[j]] + KR3[j / 16], S0[j]);
					ar = dr;
					dr = cr;
					cr = br;
					br = temp;
				}
				int temp = state[1] + cl + dr;
				state[1] = state[2] + dl + ar;
				state[2] = state[3] + al + br;
				state[3] = state[0] + bl + cr;
				state[0] = temp;
				
			} else if (hashLength == 16) {  // RIPEMD-128
				int al = state[0], ar = state[0];
				int bl = state[1], br = state[1];
				int cl = state[2], cr = state[2];
				int dl = state[3], dr = state[3];
				for (int j = 0; j < 64; j++) {  // The 64 rounds
					int temp;
					temp = Integer.rotateLeft(al + f(j, bl, cl, dl) + schedule[RL[j]] + KL[j / 16], SL[j]);
					al = dl;
					dl = cl;
					cl = bl;
					bl = temp;
					temp = Integer.rotateLeft(ar + f(63 - j, br, cr, dr) + schedule[RR[j]] + KR4[j / 16], SR[j]);
					ar = dr;
					dr = cr;
					cr = br;
					br = temp;
				}
				int temp = state[1] + cl + dr;
				state[1] = state[2] + dl + ar;
				state[2] = state[3] + al + br;
				state[3] = state[0] + bl + cr;
				state[0] = temp;
				
			} else if (hashLength == 20) {  // RIPEMD-160
				int al = state[0], ar = state[0];
				int bl = state[1], br = state[1];
				int cl = state[2], cr = state[2];
				int dl = state[3], dr = state[3];
				int el = state[4], er = state[4];
				for (int j = 0; j < 80; j++) {  // The 80 rounds
					int temp;
					temp = Integer.rotateLeft(al + f(j, bl, cl, dl) + schedule[RL[j]] + KL[j / 16], SL[j]) + el;
					al = el;
					el = dl;
					dl = Integer.rotateLeft(cl, 10);
					cl = bl;
					bl = temp;
					temp = Integer.rotateLeft(ar + f(79 - j, br, cr, dr) + schedule[RR[j]] + KR5[j / 16], SR[j]) + er;
					ar = er;
					er = dr;
					dr = Integer.rotateLeft(cr, 10);
					cr = br;
					br = temp;
				}
				int temp = state[1] + cl + dr;
				state[1] = state[2] + dl + er;
				state[2] = state[3] + el + ar;
				state[3] = state[4] + al + br;
				state[4] = state[0] + bl + cr;
				state[0] = temp;
				
			} else if (hashLength == 32) {  // RIPEMD-256
				int al = state[0], ar = state[4];
				int bl = state[1], br = state[5];
				int cl = state[2], cr = state[6];
				int dl = state[3], dr = state[7];
				for (int j = 0; j < 64; j++) {  // The 64 rounds
					int temp;
					temp = Integer.rotateLeft(al + f(j, bl, cl, dl) + schedule[RL[j]] + KL[j / 16], SL[j]);
					al = dl;
					dl = cl;
					cl = bl;
					bl = temp;
					temp = Integer.rotateLeft(ar + f(63 - j, br, cr, dr) + schedule[RR[j]] + KR4[j / 16], SR[j]);
					ar = dr;
					dr = cr;
					cr = br;
					br = temp;
					if (j % 16 == 15) {
						switch (j / 16) {
							case 0:  temp = al;  al = ar;  ar = temp;  break;
							case 1:  temp = bl;  bl = br;  br = temp;  break;
							case 2:  temp = cl;  cl = cr;  cr = temp;  break;
							case 3:  temp = dl;  dl = dr;  dr = temp;  break;
						}
					}
				}
				state[0] += al;
				state[1] += bl;
				state[2] += cl;
				state[3] += dl;
				state[4] += ar;
				state[5] += br;
				state[6] += cr;
				state[7] += dr;
				
			} else if (hashLength == 40) {  // RIPEMD-320
				int al = state[0], ar = state[5];
				int bl = state[1], br = state[6];
				int cl = state[2], cr = state[7];
				int dl = state[3], dr = state[8];
				int el = state[4], er = state[9];
				for (int j = 0; j < 80; j++) {  // The 80 rounds
					int temp;
					temp = Integer.rotateLeft(al + f(j, bl, cl, dl) + schedule[RL[j]] + KL[j / 16], SL[j]) + el;
					al = el;
					el = dl;
					dl = Integer.rotateLeft(cl, 10);
					cl = bl;
					bl = temp;
					temp = Integer.rotateLeft(ar + f(79 - j, br, cr, dr) + schedule[RR[j]] + KR5[j / 16], SR[j]) + er;
					ar = er;
					er = dr;
					dr = Integer.rotateLeft(cr, 10);
					cr = br;
					br = temp;
					if (j % 16 == 15) {
						switch (j / 16) {
							case 0:  temp = bl;  bl = br;  br = temp;  break;
							case 1:  temp = dl;  dl = dr;  dr = temp;  break;
							case 2:  temp = al;  al = ar;  ar = temp;  break;
							case 3:  temp = cl;  cl = cr;  cr = temp;  break;
							case 4:  temp = el;  el = er;  er = temp;  break;
						}
					}
				}
				state[0] += al;
				state[1] += bl;
				state[2] += cl;
				state[3] += dl;
				state[4] += el;
				state[5] += ar;
				state[6] += br;
				state[7] += cr;
				state[8] += dr;
				state[9] += er;
				
			} else
				throw new AssertionError();
		}
	}
	
	
	// For RIPEMD (original) only
	private static int f0(int i, int x, int y, int z) {
		if      ( 0 <= i && i < 16) return (x & y) | (~x & z);
		else if (16 <= i && i < 32) return (x & y) | (x & z) | (y & z);
		else if (32 <= i && i < 48) return x ^ y ^ z;
		else throw new AssertionError();
	}
	
	
	// For RIPEMD-{128,160,256,320}
	private static int f(int i, int x, int y, int z) {
		if      ( 0 <= i && i < 16) return x ^ y ^ z;
		else if (16 <= i && i < 32) return (x & y) | (~x & z);
		else if (32 <= i && i < 48) return (x | ~y) ^ z;
		else if (48 <= i && i < 64) return (x & z) | (y & ~z);
		else if (64 <= i && i < 80) return x ^ (y | ~z);  // RIPEMD-128 and RIPEMD-256 skip this case
		else throw new AssertionError();
	}
	
	
	@Override
	public HashValue getHashDestructively(byte[] block, int blockFilled, BigInteger length) {
		block[blockFilled] = (byte)0x80;
		blockFilled++;
		Arrays.fill(block, blockFilled, block.length, (byte)0);
		if (blockFilled + 8 > block.length) {
			compress(block);
			Arrays.fill(block, (byte)0);
		}
		length = length.shiftLeft(3);  // Length is now in bits
		for (int i = 0; i < 8; i++)
			block[block.length - 8 + i] = length.shiftRight(i * 8).byteValue();
		compress(block);
		return new HashValue(IntegerBitMath.toBytesLittleEndian(state));
	}
	
	
	
	// RIPEMD-160 and RIPEMD-320 use all of the numerical constants below.
	// RIPEMD-128 and RIPEMD-256 generally skip the final fifth, except for the handling of KR.
	
	private static final int[] KL  = {0x00000000, 0x5A827999, 0x6ED9EBA1, 0x8F1BBCDC, 0xA953FD4E};  // Round constants for left line
	private static final int[] KR5 = {0x50A28BE6, 0x5C4DD124, 0x6D703EF3, 0x7A6D76E9, 0x00000000};  // Round constants for right line (RIPEMD-160, RIPEMD-320)
	private static final int[] KR4 = {0x50A28BE6, 0x5C4DD124, 0x6D703EF3, 0x00000000};  // Round constants for right line (RIPEMD-128, RIPEMD-256)
	private static final int[] KR3 = {0x50A28BE6, 0x00000000, 0x5C4DD124};  // Round constants for right line (RIPEMD (original))
	
	private static final int[] R0 = {  // Message schedule for RIPEMD (original)
		 0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15,
		 7,  4, 13,  1, 10,  6, 15,  3, 12,  0,  9,  5, 14,  2, 11,  8,
		 3, 10,  2,  4,  9, 15,  8,  1, 14,  7,  0,  6, 11, 13,  5, 12,
	};
	
	private static final int[] RL = {  // Message schedule for left line
		 0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15,
		 7,  4, 13,  1, 10,  6, 15,  3, 12,  0,  9,  5,  2, 14, 11,  8,
		 3, 10, 14,  4,  9, 15,  8,  1,  2,  7,  0,  6, 13, 11,  5, 12,
		 1,  9, 11, 10,  0,  8, 12,  4, 13,  3,  7, 15, 14,  5,  6,  2,
		 4,  0,  5,  9,  7, 12,  2, 10, 14,  1,  3,  8, 11,  6, 15, 13,
	};
	
	private static final int[] RR = {  // Message schedule for right line
		 5, 14,  7,  0,  9,  2, 11,  4, 13,  6, 15,  8,  1, 10,  3, 12,
		 6, 11,  3,  7,  0, 13,  5, 10, 14, 15,  8, 12,  4,  9,  1,  2,
		15,  5,  1,  3,  7, 14,  6,  9, 11,  8, 12,  2, 10,  0,  4, 13,
		 8,  6,  4,  1,  3, 11, 15,  0,  5, 12,  2, 13,  9,  7, 10, 14,
		12, 15, 10,  4,  1,  5,  8,  7,  6,  2, 13, 14,  0,  3,  9, 11,
	};
	
	private static final int[] S0 = {  // Left-rotation for RIPEMD (original)
		11, 14, 15, 12,  5,  8,  7,  9, 11, 13, 14, 15,  6,  7,  9,  8,
		 7,  6,  8, 13, 11,  9,  7, 15,  7, 12, 15,  9,  7, 11, 13, 12,
		11, 13, 14,  7, 14,  9, 13, 15,  6,  8, 13,  6, 12,  5,  7,  5,
	};
	
	private static final int[] SL = {  // Left-rotation for left line
		11, 14, 15, 12,  5,  8,  7,  9, 11, 13, 14, 15,  6,  7,  9,  8,
		 7,  6,  8, 13, 11,  9,  7, 15,  7, 12, 15,  9, 11,  7, 13, 12,
		11, 13,  6,  7, 14,  9, 13, 15, 14,  8, 13,  6,  5, 12,  7,  5,
		11, 12, 14, 15, 14, 15,  9,  8,  9, 14,  5,  6,  8,  6,  5, 12,
		 9, 15,  5, 11,  6,  8, 13, 12,  5, 12, 13, 14, 11,  8,  5,  6,
	};
	
	private static final int[] SR = {  // Left-rotation for right line
		 8,  9,  9, 11, 13, 15, 15,  5,  7,  7,  8, 11, 14, 14, 12,  6,
		 9, 13, 15,  7, 12,  8,  9, 11,  7,  7, 12,  7,  6, 15, 13, 11,
		 9,  7, 15, 11,  8,  6,  6, 14, 12, 13,  5, 14, 13, 13,  7,  5,
		15,  5,  8, 11, 14, 14,  6, 14,  6,  9, 12,  9, 12,  5, 15,  8,
		 8,  5, 12,  9, 12,  5, 14,  6,  8, 13,  6,  5, 15, 13, 11, 11,
	};
	
}
