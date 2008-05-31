package p79068.crypto.cipher.mode;

import p79068.crypto.Zeroizer;
import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.Cipherer;
import p79068.lang.BoundsChecker;


final class CbcModeCipherer extends Cipherer {
	
	private Cipherer cipherer;
	private int blockLength;
	
	private byte[] prevCiphertext;
	
	
	
	CbcModeCipherer(CbcModeCipher cipher, byte[] initVector, BlockCipher blockCipher, byte[] cipherKey) {
		super(cipher, initVector);
		blockLength = blockCipher.getBlockLength();
		if (initVector.length != blockLength)
			throw new AssertionError();
		cipherer = blockCipher.newCipherer(cipherKey);
		prevCiphertext = initVector.clone();
	}
	
	
	
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % blockLength != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		for (int end = off + len; off < end; off += blockLength) {
			for (int i = 0; i < blockLength; i++)
				b[off + i] ^= prevCiphertext[i];
			cipherer.encrypt(b, off, blockLength);
			System.arraycopy(b, off, prevCiphertext, 0, blockLength);
		}
	}
	
	
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % blockLength != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		byte[] ciphertext = new byte[blockLength];
		for (int end = off + len; off < end; off += blockLength) {
			System.arraycopy(b, off, ciphertext, 0, blockLength);
			cipherer.decrypt(b, off, blockLength);
			for (int i = 0; i < blockLength; i++)
				b[off + i] ^= prevCiphertext[i];
			System.arraycopy(ciphertext, 0, prevCiphertext, 0, blockLength);
		}
	}
	
	
	public void zeroize() {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Zeroizer.clear(prevCiphertext);
		prevCiphertext = null;
		cipherer.zeroize();
		cipherer = null;
		super.zeroize();
	}
	
}