package p79068.crypto.hash;

import static p79068.math.IntegerBitMath.rotateLeft;
import p79068.lang.BoundsChecker;


final class Sha1Core extends AbstractSha1Core {
	
	public Sha1Core() {
		super();
	}
	
	
	
	@Override
	public void compress(byte[] message, int off, int len) {
		BoundsChecker.check(message.length, off, len);
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
			int temp = rotateLeft(a, 5) + f(i, b, c, d) + e + k[i / 20] + keySchedule[i];
			e = d;
			d = c;
			c = rotateLeft(b, 30);
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
			b = rotateLeft(c, 2);
			c = d;
			d = e;
			e = temp - (rotateLeft(a, 5) + f(i, b, c, d) + k[i / 20] + keySchedule[i]);
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
	
}
