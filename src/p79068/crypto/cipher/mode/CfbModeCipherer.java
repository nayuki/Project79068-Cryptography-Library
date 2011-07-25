package p79068.crypto.cipher.mode;

import p79068.crypto.Zeroizer;
import p79068.crypto.cipher.AbstractCipherer;
import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.Cipherer;
import p79068.lang.BoundsChecker;


final class CfbModeCipherer extends AbstractCipherer {
	
	private Cipherer cipherer;
	private int blockLength;
	
	private byte[] prevCiphertext;
	
	
	
	CfbModeCipherer(CfbModeCipher cipher, byte[] initVector, BlockCipher blockCipher, byte[] cipherKey) {
		super(cipher, initVector);
		blockLength = blockCipher.getBlockLength();
		if (initVector.length != blockLength)
			throw new AssertionError();
		cipherer = blockCipher.newCipherer(cipherKey);
		prevCiphertext = initVector.clone();
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % blockLength != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		for (int end = off + len; off < end; off += blockLength) {
			cipherer.encrypt(prevCiphertext);
			for (int i = 0; i < blockLength; i++)
				b[off + i] ^= prevCiphertext[i];
			System.arraycopy(b, off, prevCiphertext, 0, blockLength);
		}
	}
	
	
	@Override
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % blockLength != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		for (int end = off + len; off < end; off += blockLength) {
			cipherer.encrypt(prevCiphertext);
			for (int i = 0; i < blockLength; i++) {
				byte temp = b[off + i];
				b[off + i] ^= prevCiphertext[i];
				prevCiphertext[i] = temp;
			}
		}
	}
	
	
	@Override
	public CfbModeCipherer clone() {
		CfbModeCipherer result = (CfbModeCipherer)super.clone();
		result.prevCiphertext = result.prevCiphertext.clone();
		return result;
	}
	
	
	@Override
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
