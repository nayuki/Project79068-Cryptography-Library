package p79068.crypto.cipher;

import p79068.crypto.Zeroizer;
import p79068.lang.*;


final class Shacal1Cipherer extends Cipherer {
	
	private int[] keySchedule;
	
	
	
	Shacal1Cipherer(Shacal1 cipher, byte[] key) {
		super(cipher, key);
		keySchedule = new int[80];
		setKey(key);
	}
	
	
	
	private static final int[] k = { 0x5A827999, 0x6ED9EBA1, 0x8F1BBCDC, 0xCA62C1D6 };
	
	
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
	public void encrypt(byte[] B, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(B.length, off, len);
		if (len % 20 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		// For each block of 20 bytes
		for (int end = off + len; off < end; off += 20) {
			
			// Pack bytes into int32s in big endian
			int a = B[off +  0] << 24 | (B[off +  1] & 0xFF) << 16 | (B[off +  2] & 0xFF) << 8 | (B[off +  3] & 0xFF);
			int b = B[off +  4] << 24 | (B[off +  5] & 0xFF) << 16 | (B[off +  6] & 0xFF) << 8 | (B[off +  7] & 0xFF);
			int c = B[off +  8] << 24 | (B[off +  9] & 0xFF) << 16 | (B[off + 10] & 0xFF) << 8 | (B[off + 11] & 0xFF);
			int d = B[off + 12] << 24 | (B[off + 13] & 0xFF) << 16 | (B[off + 14] & 0xFF) << 8 | (B[off + 15] & 0xFF);
			int e = B[off + 16] << 24 | (B[off + 17] & 0xFF) << 16 | (B[off + 18] & 0xFF) << 8 | (B[off + 19] & 0xFF);
			
			// The 80 rounds
			for (int i = 0; i < 80; i++) {
				int f;
				if (0 <= i && i < 20)
					f = d ^ (b & (c ^ d));
				else if (20 <= i && i < 40)
					f = b ^ c ^ d;
				else if (40 <= i && i < 60)
					f = (b & (c | d)) | (c & d);
				else if (60 <= i && i < 80)
					f = b ^ c ^ d;
				else
					throw new AssertionError();
				
				int temp = (a << 5 | a >>> 27) + f + e + k[i / 20] + keySchedule[i];
				e = d;
				d = c;
				c = b << 30 | b >>> 2;
				b = a;
				a = temp;
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
		}
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
	public void decrypt(byte[] B, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(B.length, off, len);
		if (len % 20 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		// For each block of 20 bytes
		for (int end = off + len; off < end; off += 20) {
			
			// Pack bytes into int32s in big endian
			int a = B[off +  0] << 24 | (B[off +  1] & 0xFF) << 16 | (B[off +  2] & 0xFF) << 8 | (B[off +  3] & 0xFF);
			int b = B[off +  4] << 24 | (B[off +  5] & 0xFF) << 16 | (B[off +  6] & 0xFF) << 8 | (B[off +  7] & 0xFF);
			int c = B[off +  8] << 24 | (B[off +  9] & 0xFF) << 16 | (B[off + 10] & 0xFF) << 8 | (B[off + 11] & 0xFF);
			int d = B[off + 12] << 24 | (B[off + 13] & 0xFF) << 16 | (B[off + 14] & 0xFF) << 8 | (B[off + 15] & 0xFF);
			int e = B[off + 16] << 24 | (B[off + 17] & 0xFF) << 16 | (B[off + 18] & 0xFF) << 8 | (B[off + 19] & 0xFF);
			
			// The 80 rounds
			for (int i = 79; i >= 0; i--) {
				int temp = a;
				a = b;
				b = c << 2 | c >>> 30;
				c = d;
				d = e;
				
				int f;
				if (0 <= i && i < 20)
					f = d ^ (b & (c ^ d));
				else if (20 <= i && i < 40)
					f = b ^ c ^ d;
				else if (40 <= i && i < 60)
					f = (b & (c | d)) | (c & d);
				else if (60 <= i && i < 80)
					f = b ^ c ^ d;
				else
					throw new AssertionError();
				
				e = temp - ((a << 5 | a >>> 27) + f + k[i / 20] + keySchedule[i]);
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
		}
	}
	
	
	public Shacal1Cipherer clone() {
		Shacal1Cipherer result = (Shacal1Cipherer)super.clone();
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
		key = ensureKeyLength(key);

		// Pack bytes into int32s in big endian
		for (int i = 0; i < 16; i++) {
			keySchedule[i] = (key[i * 4 + 0] & 0xFF) << 24
			               | (key[i * 4 + 1] & 0xFF) << 16
			               | (key[i * 4 + 2] & 0xFF) <<  8
			               | (key[i * 4 + 3] & 0xFF) <<  0;
		}
		
		// Expand the key schedule
		for (int i = 16; i < 80; i++) {
			int temp = keySchedule[i - 3] ^ keySchedule[i - 8] ^ keySchedule[i - 14] ^ keySchedule[i - 16];
			keySchedule[i] = temp << 1 | temp >>> 31;
		}
	}
	
	
	// If the key is shorter than 64 bytes, then zeros are appended. If the key is longer, then it is truncated.
	private static byte[] ensureKeyLength(byte[] key) {
		byte[] result = new byte[64];
		System.arraycopy(key, 0, result, 0, Math.min(key.length, result.length));
		return result;
	}
	
}