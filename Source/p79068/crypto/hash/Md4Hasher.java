package p79068.crypto.hash;

import p79068.crypto.Zeroizer;
import p79068.lang.BoundsChecker;
import p79068.math.IntegerBitMath;
import p79068.util.hash.HashValue;


final class Md4Hasher extends BlockHasherCore {
	
	private int[] state;
	
	
	
	public Md4Hasher() {
		state = new int[]{0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476};
	}
	
	
	
	public Md4Hasher clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		Md4Hasher result = (Md4Hasher)super.clone();
		result.state = result.state.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		Zeroizer.clear(state);
		state = null;
	}
	
	
	
	private static final int[] s = {
		 3,  7, 11, 19,
		 3,  5,  9, 13,
		 3,  9, 11, 15
	};
	
	
	private static final int[] k = {
		 0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15,
		 0,  4,  8, 12,  1,  5,  9, 13,  2,  6, 10, 14,  3,  7, 11, 15,
		 0,  8,  4, 12,  2, 10,  6, 14,  1,  9,  5, 13,  3, 11,  7, 15
	};
	
	
	private static final int[] addCon = {  // Additive constant
		0x00000000, 0x5A827999, 0x6ED9EBA1
	};
	
	
	
	public void compress(byte[] message, int off, int len) {
		BoundsChecker.check(message.length, off, len);
		if (len % 64 != 0)
			throw new AssertionError();
		
		int[] schedule = new int[16];
		int a = state[0];
		int b = state[1];
		int c = state[2];
		int d = state[3];
		
		// For each block of 64 bytes
		for (int end = off + len; off < end;) {
			
			// Pack bytes into int32s in little endian
			for (int i = 0; i < 16; i++, off += 4) {
				schedule[i] =
					  (message[off + 0] & 0xFF) <<  0
					| (message[off + 1] & 0xFF) <<  8
					| (message[off + 2] & 0xFF) << 16
					| (message[off + 3] & 0xFF) << 24;
			}
			
			// The 48 rounds
			for (int i = 0; i < 48; i++) {
				int f;
				if (0 <= i && i < 16)
					f = d ^ (b & (c ^ d));  // Same as (b & c) | (~b & d)
				else if (16 <= i && i < 32)
					f = (b & c) | (d & (b | c));
				else if (32 <= i && i < 48)
					f = b ^ c ^ d;
				else
					throw new AssertionError();
				
				int temp = a + f + schedule[k[i / 16 * 16 + i % 16]] + addCon[i / 16];
				int rot = s[i / 16 * 4 + i % 4];
				temp = temp << rot | temp >>> (32 - rot);  // Left rotation
				a = d;
				d = c;
				c = b;
				b = temp;
			}
			
			state[0] += a;
			state[1] += b;
			state[2] += c;
			state[3] += d;
			a = state[0];
			b = state[1];
			c = state[2];
			d = state[3];
		}
	}
	
	
	public HashValue getHashDestructively(byte[] block, int blockLength, long length) {
		block[blockLength] = (byte)0x80;
		for (int i = blockLength + 1; i < block.length; i++)
			block[i] = 0x00;
		if (blockLength + 1 > block.length - 8) {
			compress(block);
			for (int i = 0; i < block.length; i++)
				block[i] = 0x00;
		}
		for (int i = 0; i < 8; i++)
			block[block.length - 8 + i] = (byte)((length * 8) >>> (i * 8));
		compress(block);
		return new HashValue(IntegerBitMath.toBytesLittleEndian(state));
	}
	
}