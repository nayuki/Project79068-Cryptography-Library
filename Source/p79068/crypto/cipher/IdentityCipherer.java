package p79068.crypto.cipher;

import p79068.lang.BoundsChecker;


final class IdentityCipherer extends Cipherer {
	
	IdentityCipherer(IdentityCipher cipher, byte[] key) {
		super(cipher, key);
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
	}
	
	
	@Override
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
	}
	
}