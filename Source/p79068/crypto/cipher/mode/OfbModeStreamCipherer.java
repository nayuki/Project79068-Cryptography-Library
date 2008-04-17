package p79068.crypto.cipher.mode;

import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.StreamCipherer;
import p79068.lang.BoundsChecker;


final class OfbModeStreamCipherer extends StreamCipherer {
	
	private Cipherer cipherer;
	private int blockLength;
	
	private byte[] keyStream; // The current key stream block
	private int keyStreamOff;
	
	
	OfbModeStreamCipherer(OfbModeStreamCipher cipher, byte[] initVector, BlockCipher blockCipher, byte[] cipherKey) {
		super(cipher, initVector);
		blockLength = blockCipher.getBlockLength();
		if (initVector.length != blockLength)
			throw new AssertionError();
		cipherer = blockCipher.newCipherer(cipherKey);
		keyStream = initVector.clone();
		keyStreamOff = 0;
	}
	
	
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		for (len += off; off < len; off++) {
			b[off] ^= keyStream[keyStreamOff];
			keyStreamOff++;
			if (keyStreamOff == blockLength) {
				cipherer.encrypt(keyStream);
				keyStreamOff = 0;
			}
		}
	}
	
	public void skip(int byteCount) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		if (keyStreamOff > 0) {
			int templen = Math.min(blockLength - keyStreamOff, byteCount);
			keyStreamOff += templen;
			byteCount -= templen;
			if (keyStreamOff == blockLength) {
				cipherer.encrypt(keyStream);
				keyStreamOff = 0;
			}
		}
		for (; byteCount >= blockLength; byteCount -= blockLength)
			cipherer.encrypt(keyStream);
		keyStreamOff += byteCount;
	} // 0 <= byteCount < blockLength
	

	public void zeroize() {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		keyStreamOff = 0;
		for (int i = 0; i < blockLength; i++)
			keyStream[i] = 0;
		keyStream = null;
		cipherer.zeroize();
		cipherer = null;
		super.zeroize();
	}
}