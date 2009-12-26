package p79068.crypto.hash;

import java.util.Arrays;
import p79068.crypto.Zeroizer;
import p79068.lang.BoundsChecker;
import p79068.math.IntegerBitMath;
import p79068.util.hash.HashValue;


final class Sha256Hasher extends BlockHasherCore {
	
	private boolean sha256Mode;
	
	private int[] state;
	
	
	
	public Sha256Hasher(boolean sha256Mode) {
		this.sha256Mode = sha256Mode;
		if (sha256Mode) {
			state = new int[] {
				0x6A09E667, 0xBB67AE85, 0x3C6EF372, 0xA54FF53A,
				0x510E527F, 0x9B05688C, 0x1F83D9AB, 0x5BE0CD19
			};
		} else {
			state = new int[] {
				0xC1059ED8, 0x367CD507, 0x3070DD17, 0xF70E5939,
				0xFFC00B31, 0x68581511, 0x64F98FA7, 0xBEFA4FA4
			};
		}
	}
	
	
	
	@Override
	public Sha256Hasher clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		Sha256Hasher result = (Sha256Hasher)super.clone();
		result.state = result.state.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		Zeroizer.clear(state);
		state = null;
	}
	
	
	
	@Override
	public void compress(byte[] message, int off, int len) {
		BoundsChecker.check(message.length, off, len);
		if (len % 64 != 0)
			throw new AssertionError();
		
		int[] schedule = new int[64];
		
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
			for (int i = 16; i < 64; i++) {
				int s0 = (schedule[i-15] << 25 | schedule[i-15] >>> 7) ^ (schedule[i-15] << 14 | schedule[i-15] >>> 18) ^ (schedule[i-15] >>> 3);
				int s1 = (schedule[i-2] << 15 | schedule[i-2] >>> 17) ^ (schedule[i-2] << 13 | schedule[i-2] >>> 19) ^ (schedule[i-2] >>> 10);
				schedule[i] = schedule[i - 16] + schedule[i - 7] + s0 + s1;
			}
			
			int[] tempState = state.clone();
			encrypt(schedule, tempState);
			
			state[0] += tempState[0];
			state[1] += tempState[1];
			state[2] += tempState[2];
			state[3] += tempState[3];
			state[4] += tempState[4];
			state[5] += tempState[5];
			state[6] += tempState[6];
			state[7] += tempState[7];
		}
	}
	
	
	@Override
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
			block[block.length - 1 - i] = (byte)((length * 8) >>> (i * 8));
		compress(block);
		if (!sha256Mode)  // If SHA-224, truncate the state
			state = Arrays.copyOf(state, 7);
		return new HashValue(IntegerBitMath.toBytesBigEndian(state));
	}
	
	
	
	private static final int[] k = {
		0x428A2F98, 0x71374491, 0xB5C0FBCF, 0xE9B5DBA5,
		0x3956C25B, 0x59F111F1, 0x923F82A4, 0xAB1C5ED5,
		0xD807AA98, 0x12835B01, 0x243185BE, 0x550C7DC3,
		0x72BE5D74, 0x80DEB1FE, 0x9BDC06A7, 0xC19BF174,
		0xE49B69C1, 0xEFBE4786, 0x0FC19DC6, 0x240CA1CC,
		0x2DE92C6F, 0x4A7484AA, 0x5CB0A9DC, 0x76F988DA,
		0x983E5152, 0xA831C66D, 0xB00327C8, 0xBF597FC7,
		0xC6E00BF3, 0xD5A79147, 0x06CA6351, 0x14292967,
		0x27B70A85, 0x2E1B2138, 0x4D2C6DFC, 0x53380D13,
		0x650A7354, 0x766A0ABB, 0x81C2C92E, 0x92722C85,
		0xA2BFE8A1, 0xA81A664B, 0xC24B8B70, 0xC76C51A3,
		0xD192E819, 0xD6990624, 0xF40E3585, 0x106AA070,
		0x19A4C116, 0x1E376C08, 0x2748774C, 0x34B0BCB5,
		0x391C0CB3, 0x4ED8AA4A, 0x5B9CCA4F, 0x682E6FF3,
		0x748F82EE, 0x78A5636F, 0x84C87814, 0x8CC70208,
		0x90BEFFFA, 0xA4506CEB, 0xBEF9A3F7, 0xC67178F2
	};
	
	
	/*
	 * Each round performs a transform of this form:
	 *  a' = h + F(e,f,g) + G(a,b,c)
	 *  b' = a
	 *  c' = b
	 *  d' = c
	 *  e' = d + h + F(e,f,g)
	 *  f' = e
	 *  g' = f
	 *  h' = g
	 * The primed variables represent the output.
	 * The actual implementation is an in-place version of this description.
	 */
	static void encrypt(int[] keySchedule, int[] message) {
		int a = message[0];
		int b = message[1];
		int c = message[2];
		int d = message[3];
		int e = message[4];
		int f = message[5];
		int g = message[6];
		int h = message[7];
		
		// The 64 rounds
		for (int i = 0; i < 64; i++) {
			int s0 = (a << 30 | a >>> 2) ^ (a << 19 | a >>> 13) ^ (a << 10 | a >>> 22);
			int s1 = (e << 26 | e >>> 6) ^ (e << 21 | e >>> 11) ^ (e << 7 | e >>> 25);
			int maj = (a & (b | c)) | (b & c);
			int ch = g ^ (e & (f ^ g));
			int t1 = h + s1 + ch + k[i] + keySchedule[i];
			int t2 = s0 + maj;
			h = g;
			g = f;
			f = e;
			e = d + t1;
			d = c;
			c = b;
			b = a;
			a = t1 + t2;
		}
		
		message[0] = a;
		message[1] = b;
		message[2] = c;
		message[3] = d;
		message[4] = e;
		message[5] = f;
		message[6] = g;
		message[7] = h;
	}
	
	
	/*
	 * Each round performs a transform of this form:
	 *  a = b'
	 *  b = c'
	 *  c = d'
	 *  d = e' - (h + F(e,f,g))
	 *  e = f'
	 *  f = g'
	 *  g = h'
	 *  h = a' - (F(e,f,g) + G(a,b,c))
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
		int f = message[5];
		int g = message[6];
		int h = message[7];
		
		// The 64 rounds
		for (int i = 63; i >= 0; i--) {
			int t0 = a;
			int t1 = e;
			a = b;
			b = c;
			c = d;
			e = f;
			f = g;
			g = h;
			int s0 = (a << 30 | a >>> 2) ^ (a << 19 | a >>> 13) ^ (a << 10 | a >>> 22);
			int s1 = (e << 26 | e >>> 6) ^ (e << 21 | e >>> 11) ^ (e << 7 | e >>> 25);
			int maj = (a & (b | c)) | (b & c);
			int ch = g ^ (e & (f ^ g));
			int t2 = s1 + ch + k[i] + keySchedule[i];
			int t3 = s0 + maj;
			h = t0 - (t2 + t3);
			d = t1 - (h + t2);
		}
		
		message[0] = a;
		message[1] = b;
		message[2] = c;
		message[3] = d;
		message[4] = e;
		message[5] = f;
		message[6] = g;
		message[7] = h;
	}
	
}