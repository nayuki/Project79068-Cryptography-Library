package p79068.crypto.cipher.mode;

import p79068.Assert;
import p79068.crypto.Zeroizer;
import p79068.crypto.cipher.AbstractCipherer;
import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.Cipherer;


final class BcModeCipherer extends AbstractCipherer {
	
	private Cipherer cipherer;
	private int blockLength;
	
	private byte[] prevCiphertextsXored;
	
	
	
	BcModeCipherer(BcModeCipher cipher, byte[] initVector, BlockCipher blockCipher, byte[] cipherKey) {
		super(cipher, initVector);
		blockLength = blockCipher.getBlockLength();
		if (initVector.length != blockLength)
			throw new AssertionError();
		cipherer = blockCipher.newCipherer(cipherKey);
		prevCiphertextsXored = initVector.clone();
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
		if (len % blockLength != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		for (int end = off + len; off < end; off += blockLength) {
			for (int i = 0; i < blockLength; i++)
				b[off + i] ^= prevCiphertextsXored[i];
			cipherer.encrypt(b, off, blockLength);
			for (int i = 0; i < blockLength; i++)
				prevCiphertextsXored[i] ^= b[off + i];
		}
	}
	
	
	@Override
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
		if (len % blockLength != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		byte[] ciphertext = new byte[blockLength];
		for (int end = off + len; off < end; off += blockLength) {
			System.arraycopy(b, off, ciphertext, 0, blockLength);
			cipherer.decrypt(b, off, blockLength);
			for (int i = 0; i < blockLength; i++) {
				b[off + i] ^= prevCiphertextsXored[i];
				prevCiphertextsXored[i] ^= ciphertext[i];
			}
		}
	}
	
	
	@Override
	public BcModeCipherer clone() {
		BcModeCipherer result = (BcModeCipherer)super.clone();
		result.prevCiphertextsXored = result.prevCiphertextsXored.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Zeroizer.clear(prevCiphertextsXored);
		prevCiphertextsXored = null;
		cipherer.zeroize();
		cipherer = null;
		super.zeroize();
	}
	
}
