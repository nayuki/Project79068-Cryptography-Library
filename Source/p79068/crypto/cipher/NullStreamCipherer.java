package p79068.crypto.cipher;

import p79068.lang.BoundsChecker;


final class NullStreamCipherer extends StreamCipherer {
	
	NullStreamCipherer(NullStreamCipher cipher, byte[] key) {
		super(cipher, key);
	}
	
	
	
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
	}
	
	
	public void zeroize() {
		if (cipher == null)
			return;
		super.zeroize();
	}
	
}