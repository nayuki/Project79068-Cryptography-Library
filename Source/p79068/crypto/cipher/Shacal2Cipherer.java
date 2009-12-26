package p79068.crypto.cipher;

import java.util.Arrays;
import p79068.crypto.Zeroizer;
import p79068.lang.*;


final class Shacal2Cipherer extends Cipherer {
	
	private int[] keySchedule;
	
	
	
	Shacal2Cipherer(Shacal2 cipher, byte[] key) {
		super(cipher, key);
		keySchedule = new int[64];
		setKey(key);
	}
	
	
	
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
	public void encrypt(byte[] B, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(B.length, off, len);
		if (len % 32 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		// For each block of 32 bytes
		for (int end = off + len; off < end; off += 32) {
			
			// Pack bytes into int32s in big endian
			int a = B[off +  0] << 24 | (B[off +  1] & 0xFF) << 16 | (B[off +  2] & 0xFF) << 8 | (B[off +  3] & 0xFF);
			int b = B[off +  4] << 24 | (B[off +  5] & 0xFF) << 16 | (B[off +  6] & 0xFF) << 8 | (B[off +  7] & 0xFF);
			int c = B[off +  8] << 24 | (B[off +  9] & 0xFF) << 16 | (B[off + 10] & 0xFF) << 8 | (B[off + 11] & 0xFF);
			int d = B[off + 12] << 24 | (B[off + 13] & 0xFF) << 16 | (B[off + 14] & 0xFF) << 8 | (B[off + 15] & 0xFF);
			int e = B[off + 16] << 24 | (B[off + 17] & 0xFF) << 16 | (B[off + 18] & 0xFF) << 8 | (B[off + 19] & 0xFF);
			int f = B[off + 20] << 24 | (B[off + 21] & 0xFF) << 16 | (B[off + 22] & 0xFF) << 8 | (B[off + 23] & 0xFF);
			int g = B[off + 24] << 24 | (B[off + 25] & 0xFF) << 16 | (B[off + 26] & 0xFF) << 8 | (B[off + 27] & 0xFF);
			int h = B[off + 28] << 24 | (B[off + 29] & 0xFF) << 16 | (B[off + 30] & 0xFF) << 8 | (B[off + 31] & 0xFF);
			
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
			
			// Unpack int32s into bytes in big endian
			B[off +  0] = (byte)(a >>> 24);
			B[off +  1] = (byte)(a >>> 16);
			B[off +  2] = (byte)(a >>>  8);
			B[off +  3] = (byte)(a >>>  0);
			B[off +  4] = (byte)(b >>> 24);
			B[off +  5] = (byte)(b >>> 16);
			B[off +  6] = (byte)(b >>>  8);
			B[off +  7] = (byte)(b >>>  0);
			B[off +  8] = (byte)(c >>> 24);
			B[off +  9] = (byte)(c >>> 16);
			B[off + 10] = (byte)(c >>>  8);
			B[off + 11] = (byte)(c >>>  0);
			B[off + 12] = (byte)(d >>> 24);
			B[off + 13] = (byte)(d >>> 16);
			B[off + 14] = (byte)(d >>>  8);
			B[off + 15] = (byte)(d >>>  0);
			B[off + 16] = (byte)(e >>> 24);
			B[off + 17] = (byte)(e >>> 16);
			B[off + 18] = (byte)(e >>>  8);
			B[off + 19] = (byte)(e >>>  0);
			B[off + 20] = (byte)(f >>> 24);
			B[off + 21] = (byte)(f >>> 16);
			B[off + 22] = (byte)(f >>>  8);
			B[off + 23] = (byte)(f >>>  0);
			B[off + 24] = (byte)(g >>> 24);
			B[off + 25] = (byte)(g >>> 16);
			B[off + 26] = (byte)(g >>>  8);
			B[off + 27] = (byte)(g >>>  0);
			B[off + 28] = (byte)(h >>> 24);
			B[off + 29] = (byte)(h >>> 16);
			B[off + 30] = (byte)(h >>>  8);
			B[off + 31] = (byte)(h >>>  0);
		}
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
	public void decrypt(byte[] B, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(B.length, off, len);
		if (len % 32 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		// For each block of 32 bytes
		for (int end = off + len; off < end; off += 32) {
			
			// Pack bytes into int32s in big endian
			int a = B[off +  0] << 24 | (B[off +  1] & 0xFF) << 16 | (B[off +  2] & 0xFF) << 8 | (B[off +  3] & 0xFF);
			int b = B[off +  4] << 24 | (B[off +  5] & 0xFF) << 16 | (B[off +  6] & 0xFF) << 8 | (B[off +  7] & 0xFF);
			int c = B[off +  8] << 24 | (B[off +  9] & 0xFF) << 16 | (B[off + 10] & 0xFF) << 8 | (B[off + 11] & 0xFF);
			int d = B[off + 12] << 24 | (B[off + 13] & 0xFF) << 16 | (B[off + 14] & 0xFF) << 8 | (B[off + 15] & 0xFF);
			int e = B[off + 16] << 24 | (B[off + 17] & 0xFF) << 16 | (B[off + 18] & 0xFF) << 8 | (B[off + 19] & 0xFF);
			int f = B[off + 20] << 24 | (B[off + 21] & 0xFF) << 16 | (B[off + 22] & 0xFF) << 8 | (B[off + 23] & 0xFF);
			int g = B[off + 24] << 24 | (B[off + 25] & 0xFF) << 16 | (B[off + 26] & 0xFF) << 8 | (B[off + 27] & 0xFF);
			int h = B[off + 28] << 24 | (B[off + 29] & 0xFF) << 16 | (B[off + 30] & 0xFF) << 8 | (B[off + 31] & 0xFF);
			
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
			
			// Unpack int32s into bytes in big endian
			B[off +  0] = (byte)(a >>> 24);
			B[off +  1] = (byte)(a >>> 16);
			B[off +  2] = (byte)(a >>>  8);
			B[off +  3] = (byte)(a >>>  0);
			B[off +  4] = (byte)(b >>> 24);
			B[off +  5] = (byte)(b >>> 16);
			B[off +  6] = (byte)(b >>>  8);
			B[off +  7] = (byte)(b >>>  0);
			B[off +  8] = (byte)(c >>> 24);
			B[off +  9] = (byte)(c >>> 16);
			B[off + 10] = (byte)(c >>>  8);
			B[off + 11] = (byte)(c >>>  0);
			B[off + 12] = (byte)(d >>> 24);
			B[off + 13] = (byte)(d >>> 16);
			B[off + 14] = (byte)(d >>>  8);
			B[off + 15] = (byte)(d >>>  0);
			B[off + 16] = (byte)(e >>> 24);
			B[off + 17] = (byte)(e >>> 16);
			B[off + 18] = (byte)(e >>>  8);
			B[off + 19] = (byte)(e >>>  0);
			B[off + 20] = (byte)(f >>> 24);
			B[off + 21] = (byte)(f >>> 16);
			B[off + 22] = (byte)(f >>>  8);
			B[off + 23] = (byte)(f >>>  0);
			B[off + 24] = (byte)(g >>> 24);
			B[off + 25] = (byte)(g >>> 16);
			B[off + 26] = (byte)(g >>>  8);
			B[off + 27] = (byte)(g >>>  0);
			B[off + 28] = (byte)(h >>> 24);
			B[off + 29] = (byte)(h >>> 16);
			B[off + 30] = (byte)(h >>>  8);
			B[off + 31] = (byte)(h >>>  0);
		}
	}
	
	
	public Shacal2Cipherer clone() {
		Shacal2Cipherer result = (Shacal2Cipherer)super.clone();
		result.keySchedule = result.keySchedule.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (cipher == null)
			return;
		Zeroizer.clear(keySchedule);
		keySchedule = null;
		super.zeroize();
	}
	
	
	
	private void setKey(byte[] key) {
		key = Arrays.copyOf(key, 64);  // Truncates or zero-pads the key to 64 bytes
		
		// Pack bytes into int32s in big endian
		for (int i = 0; i < 16; i++) {
			keySchedule[i] = (key[i * 4 + 0] & 0xFF) << 24
			               | (key[i * 4 + 1] & 0xFF) << 16
			               | (key[i * 4 + 2] & 0xFF) <<  8
			               | (key[i * 4 + 3] & 0xFF) <<  0;
		}
		
		// Expand the key schedule
		for (int i = 16; i < 64; i++) {
			int s0 = (keySchedule[i-15] << 25 | keySchedule[i-15] >>> 7) ^ (keySchedule[i-15] << 14 | keySchedule[i-15] >>> 18) ^ (keySchedule[i-15] >>> 3);
			int s1 = (keySchedule[i-2] << 15 | keySchedule[i-2] >>> 17) ^ (keySchedule[i-2] << 13 | keySchedule[i-2] >>> 19) ^ (keySchedule[i-2] >>> 10);
			keySchedule[i] = keySchedule[i - 16] + keySchedule[i - 7] + s0 + s1;
		}
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
	
}