package p79068.crypto.cipher;

import p79068.Assert;


final class IdentityCipherer extends AbstractCipherer {
	
	IdentityCipherer(IdentityCipher cipher, byte[] key) {
		super(cipher, key);
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
	}
	
	
	@Override
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
	}
	
}
