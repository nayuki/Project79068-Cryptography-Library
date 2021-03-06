package p79068.crypto.cipher.mode;

import p79068.Assert;
import p79068.crypto.Zeroizer;
import p79068.crypto.cipher.AbstractCipherer;
import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.Cipherer;


final class IgeModeCipherer extends AbstractCipherer {
	
	private Cipherer cipherer;
	private int blockLength;
	
	private byte[] prevPlaintext;
	private byte[] prevCiphertext;
	
	
	
	IgeModeCipherer(IgeModeCipher cipher, byte[] initVector, BlockCipher blockCipher, byte[] cipherKey) {
		super(cipher, initVector);
		blockLength = blockCipher.getBlockLength();
		if (initVector.length != blockLength)
			throw new AssertionError();
		cipherer = blockCipher.newCipherer(cipherKey);
		prevPlaintext = new byte[blockLength];
		prevCiphertext = initVector.clone();
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
		if (len % blockLength != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		byte[] plaintext = new byte[blockLength];
		for (int end = off + len; off < end; off += blockLength) {
			System.arraycopy(b, off, plaintext, 0, blockLength);
			for (int i = 0; i < blockLength; i++)
				b[off + i] ^= prevCiphertext[i];
			cipherer.encrypt(b, off, blockLength);
			for (int i = 0; i < blockLength; i++) {
				b[off + i] ^= prevPlaintext[i];
				prevCiphertext[i] = b[off + i];
			}
			System.arraycopy(plaintext, 0, prevPlaintext, 0, blockLength);
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
			for (int i = 0; i < blockLength; i++)
				b[off + i] ^= prevPlaintext[i];
			cipherer.decrypt(b, off, blockLength);
			for (int i = 0; i < blockLength; i++)
				prevPlaintext[i] = b[off + i] ^= prevCiphertext[i];
			System.arraycopy(ciphertext, 0, prevCiphertext, 0, blockLength);
		}
	}
	
	
	@Override
	public IgeModeCipherer clone() {
		IgeModeCipherer result = (IgeModeCipherer)super.clone();
		result.prevPlaintext = result.prevPlaintext.clone();
		result.prevCiphertext = result.prevCiphertext.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		prevCiphertext = Zeroizer.clear(prevCiphertext);
		prevPlaintext = Zeroizer.clear(prevPlaintext);
		cipherer.zeroize();
		cipherer = null;
		super.zeroize();
	}
	
}
