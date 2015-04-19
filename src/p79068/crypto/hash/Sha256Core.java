package p79068.crypto.hash;

import static java.lang.Integer.rotateRight;
import java.math.BigInteger;
import java.util.Arrays;
import p79068.Assert;
import p79068.crypto.Zeroizer;
import p79068.hash.HashValue;
import p79068.math.IntegerBitMath;


class Sha256Core extends BlockHasherCore {
	
	private final boolean sha256Mode;
	
	protected int[] state;
	
	
	
	public Sha256Core(boolean sha256Mode) {
		this.sha256Mode = sha256Mode;
		if (sha256Mode) {
			state = new int[] {
				0x6A09E667, 0xBB67AE85, 0x3C6EF372, 0xA54FF53A,
				0x510E527F, 0x9B05688C, 0x1F83D9AB, 0x5BE0CD19,
			};
		} else {
			state = new int[] {
				0xC1059ED8, 0x367CD507, 0x3070DD17, 0xF70E5939,
				0xFFC00B31, 0x68581511, 0x64F98FA7, 0xBEFA4FA4,
			};
		}
	}
	
	
	
	@Override
	public Sha256Core clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		Sha256Core result = (Sha256Core)super.clone();
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
		
		int[] schedule = new int[64];
		
		// For each block of 64 bytes
		for (int i = off, end = off + len; i < end; ) {
			
			// Pack bytes into int32s in big endian
			for (int j = 0; j < 16; j++, i += 4) {
				schedule[j] =
					  (message[i + 0] & 0xFF) << 24
					| (message[i + 1] & 0xFF) << 16
					| (message[i + 2] & 0xFF) <<  8
					| (message[i + 3] & 0xFF) <<  0;
			}
			
			// Expand the schedule
			for (int j = 16; j < 64; j++)
				schedule[j] = schedule[j-16] + schedule[j-7] + smallSigma0(schedule[j-15]) + smallSigma1(schedule[j-2]);
			
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
	public HashValue getHashDestructively(byte[] block, int blockFilled, BigInteger length) {
		if (length.bitLength() > 61)  // SHA-224, SHA-256 only support messages less than 2^64 bits long
			throw new IllegalStateException("Message too long");
		
		block[blockFilled] = (byte)0x80;
		blockFilled++;
		Arrays.fill(block, blockFilled, block.length, (byte)0);
		if (blockFilled + 8 > block.length) {
			compress(block);
			Arrays.fill(block, (byte)0);
		}
		length = length.shiftLeft(3);  // Length is now in bits
		for (int i = 0; i < 8; i++)
			block[block.length - 1 - i] = length.shiftRight(i * 8).byteValue();
		compress(block);
		if (!sha256Mode)  // If SHA-224, truncate the state
			state = Arrays.copyOf(state, 7);
		return new HashValue(IntegerBitMath.toBytesBigEndian(state));
	}
	
	
	/* 
	 * Each round performs a transform of this form:
	 *   a' = h + Ch(e,f,g) + Maj(a,b,c)
	 *   b' = a
	 *   c' = b
	 *   d' = c
	 *   e' = d + h + Ch(e,f,g)
	 *   f' = e
	 *   g' = f
	 *   h' = g
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
			int t1 = h + bigSigma1(e) + choose(e, f, g) + K[i] + keySchedule[i];
			int t2 = bigSigma0(a) + majority(a, b, c);
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
	 *   a = b'
	 *   b = c'
	 *   c = d'
	 *   d = e' - (h + Ch(e,f,g))
	 *   e = f'
	 *   f = g'
	 *   g = h'
	 *   h = a' - (Ch(e,f,g) + Maj(a,b,c))
	 * The primed variables represent the input.
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
			int t2 = bigSigma1(e) + choose(e, f, g) + K[i] + keySchedule[i];
			int t3 = bigSigma0(a) + majority(a, b, c);
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
	
	
	static int smallSigma0(int x) { return rotateRight(x,  7) ^ rotateRight(x, 18) ^ (x >>>  3); }
	static int smallSigma1(int x) { return rotateRight(x, 17) ^ rotateRight(x, 19) ^ (x >>> 10); }
	private static int bigSigma0  (int x) { return rotateRight(x,  2) ^ rotateRight(x, 13) ^ rotateRight(x, 22); }
	private static int bigSigma1  (int x) { return rotateRight(x,  6) ^ rotateRight(x, 11) ^ rotateRight(x, 25); }
	private static int choose  (int x, int y, int z) { return (x & y) ^ (~x & z);          }  // Can be optimized to z ^ (x & (y ^ z))
	private static int majority(int x, int y, int z) { return (x & y) ^ (x & z) ^ (y & z); }  // Can be optimized to (x & (y | z)) | (y & z)
	
	
	protected static final int[] K = {
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
		0x90BEFFFA, 0xA4506CEB, 0xBEF9A3F7, 0xC67178F2,
	};
	
}
