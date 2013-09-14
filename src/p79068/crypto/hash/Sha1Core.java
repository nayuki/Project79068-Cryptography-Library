package p79068.crypto.hash;

import java.math.BigInteger;
import java.util.Arrays;

import p79068.Assert;
import p79068.crypto.Zeroizer;
import p79068.hash.HashValue;
import p79068.math.IntegerBitMath;


class Sha1Core extends BlockHasherCore {
	
	private int[] state;
	
	private final boolean sha1Mode;
	
	
	
	public Sha1Core(boolean sha1Mode) {
		state = new int[]{0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0};
		this.sha1Mode = sha1Mode;
	}
	
	
	
	@Override
	public Sha1Core clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		Sha1Core result = (Sha1Core)super.clone();
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
		
		int[] schedule = new int[80];
		
		// For each block of 64 bytes
		for (int end = off + len; off < end;) {
			
			// Pack bytes into int32s in big endian
			for (int i = 0; i < 16; i++, off += 4) {
				schedule[i] =
					  (message[off + 0] & 0xFF) << 24
					| (message[off + 1] & 0xFF) << 16
					| (message[off + 2] & 0xFF) <<  8
					| (message[off + 3] & 0xFF) <<  0;
			}
			
			// Expand the schedule
			for (int i = 16; i < 80; i++) {
				int temp = schedule[i - 3] ^ schedule[i - 8] ^ schedule[i - 14] ^ schedule[i - 16];
				if (sha1Mode)  // This is the only difference between SHA and SHA-1
					temp = Integer.rotateLeft(temp, 1);
				schedule[i] = temp;
			}
			
			int[] tempState = state.clone();
			encrypt(schedule, tempState);
			
			state[0] += tempState[0];
			state[1] += tempState[1];
			state[2] += tempState[2];
			state[3] += tempState[3];
			state[4] += tempState[4];
		}
	}
	
	
	private static final int[] k = {0x5A827999, 0x6ED9EBA1, 0x8F1BBCDC, 0xCA62C1D6};
	
	/* 
	 * Each round performs a transform of this form:
	 *  a' = e + f(a,b,c,d)
	 *  b' = a
	 *  c' = b ROTLEFT 30
	 *  d' = c
	 *  e' = d
	 * The primed variables represent the output.
	 * The actual implementation is an in-place version of this description.
	 */
	static void encrypt(int[] keySchedule, int[] message) {
		int a = message[0];
		int b = message[1];
		int c = message[2];
		int d = message[3];
		int e = message[4];
		
		// The 80 rounds
		for (int i = 0; i < 80; i++) {
			int temp = Integer.rotateLeft(a, 5) + f(i, b, c, d) + e + k[i / 20] + keySchedule[i];
			e = d;
			d = c;
			c = Integer.rotateLeft(b, 30);
			b = a;
			a = temp;
		}
		
		message[0] = a;
		message[1] = b;
		message[2] = c;
		message[3] = d;
		message[4] = e;
	}
	
	
	/* 
	 * Each round performs a transform of this form:
	 *  a = b'
	 *  b = c' ROTLEFT 2
	 *  c = d'
	 *  d = e'
	 *  e = a' - f(a,b,c,d)
	 * The primed variables represent the input.
	 * Therefore, equivalently: e = a' - f(b', c' ROTLEFT 2, d', e')
	 * The actual implementation is an in-place version of this description.
	 */
	static void decrypt(int[] keySchedule, int[] message) {
		int a = message[0];
		int b = message[1];
		int c = message[2];
		int d = message[3];
		int e = message[4];
		
		// The 80 rounds
		for (int i = 79; i >= 0; i--) {
			int temp = a;
			a = b;
			b = Integer.rotateLeft(c, 2);
			c = d;
			d = e;
			e = temp - (Integer.rotateLeft(a, 5) + f(i, b, c, d) + k[i / 20] + keySchedule[i]);
		}
		
		message[0] = a;
		message[1] = b;
		message[2] = c;
		message[3] = d;
		message[4] = e;
	}
	
	
	
	private static int f(int i, int x, int y, int z) {
		if      ( 0 <= i && i < 20) return (x & y) | (~x & z);           // Choose. Can be optimized to z ^ (x & (y ^ z)).
		else if (20 <= i && i < 40) return x ^ y ^ z;                    // Parity
		else if (40 <= i && i < 60) return (x & y) ^ (x & z) ^ (y & z);  // Majority. Can be optimized to (x & (y | z)) | (y & z).
		else if (60 <= i && i < 80) return x ^ y ^ z;                    // Parity
		else throw new AssertionError();
	}
	
	
	@Override
	public HashValue getHashDestructively(byte[] block, int blockLength, BigInteger length) {
		block[blockLength] = (byte)0x80;
		blockLength++;
		Arrays.fill(block, blockLength, block.length, (byte)0);
		if (blockLength + 8 > block.length) {
			compress(block);
			Arrays.fill(block, (byte)0);
		}
		length = length.shiftLeft(3);  // Length is now in bits
		for (int i = 0; i < 8; i++)
			block[block.length - 1 - i] = length.shiftRight(i * 8).byteValue();
		compress(block);
		return new HashValue(IntegerBitMath.toBytesBigEndian(state));
	}
	
}
