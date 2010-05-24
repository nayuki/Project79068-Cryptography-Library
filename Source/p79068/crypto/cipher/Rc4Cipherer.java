package p79068.crypto.cipher;

import p79068.crypto.Zeroizer;
import p79068.lang.BoundsChecker;


final class Rc4Cipherer extends StreamCipherer {
	
	/**
	 * A permutation of {0, 1, 2, ..., 254, 255}.
	 */
	private int[] s;
	
	/**
	 * The number of key stream bytes generated, modulo 256. Note: number of key stream bytes generated = number of bytes encrypted + number of bytes decrypted + number of bytes skipped.
	 */
	private int i;
	
	/**
	 * A state variable in the range [0, 256).
	 */
	private int j;
	
	
	
	Rc4Cipherer(Rc4 cipher, byte[] key) {
		super(cipher, key);
		
		s = new int[256];
		for (int i = 0; i < 256; i++)
			s[i] = i;
		
		for (int i = 0, j = 0; i < 256; i++) {
			j = (j + s[i] + key[i % key.length]) & 0xFF;
			
			// Swap s[i] and s[j]
			int temp = s[i];
			s[i] = s[j];
			s[j] = temp;
		}
		
		i = 0;
		j = 0;
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		
		for (int end = off + len; off < end; off++) {
			i = (i + 1) & 0xFF;
			j = (j + s[i]) & 0xFF;
			
			// Swap s[i] and s[j]
			int temp = s[i];
			s[i] = s[j];
			s[j] = temp;
			
			b[off] ^= s[(s[i] + temp) & 0xFF];  // XOR data with generated key stream byte
		}
	}
	
	
	@Override
	public void skip(int byteCount) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		if (byteCount < 0)
			throw new IllegalArgumentException("Negative skip");
		
		for (int k = 0; k < byteCount; k++) {
			i = (i + 1) & 0xFF;
			j = (j + s[i]) & 0xFF;
			
			// Swap s[i] and s[j]
			int temp = s[i];
			s[i] = s[j];
			s[j] = temp;
		}
	}
	
	
	@Override
	public Rc4Cipherer clone() {
		Rc4Cipherer result = (Rc4Cipherer)super.clone();
		result.s = result.s.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (cipher == null)
			return;
		i = 0;
		j = 0;
		Zeroizer.clear(s);
		s = null;
		super.zeroize();
	}
	
}