package p79068.crypto.cipher;

import p79068.lang.*;


final class Rc4Cipherer extends StreamCipherer {
	
	private int[] s;  // A permutation of {0, 1, 2, ..., 254, 255}
	private int i, j;
	
	
	
	Rc4Cipherer(Rc4 cipher, byte[] key) {
		super(cipher, key);
		s = new int[256];
		for (int i = 0; i < 256; i++)
			s[i] = i;
		for (int i = 0, j = 0; i < 256; i++) {
			j = (j + s[i] + key[i % key.length]) & 0xFF;
			int tp = s[i];
			s[i] = s[j];
			s[j] = tp;
		}
		i = 0;
		j = 0;
	}
	
	
	
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		for (len += off; off < len; off++) {
			i = (i + 1) & 0xFF;
			j = (j + s[i]) & 0xFF;
			int tp = s[i];
			s[i] = s[j];
			s[j] = tp;
			b[off] ^= s[(s[i] + tp) & 0xFF];
		}
	}
	
	
	public void skip(int byteCount) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		for (int k = 0; k < byteCount; k++) {
			i = (i + 1) & 0xFF;
			j = (j + s[i]) & 0xFF;
			int tp = s[i];
			s[i] = s[j];
			s[j] = tp;
		}
	}
	
	
	public void zeroize() {
		if (cipher == null)
			return;
		i = 0;
		j = 0;
		for (int i = 0; i < 256; i++) s[i] = 0;
		s = null;
		super.zeroize();
	}
	
}