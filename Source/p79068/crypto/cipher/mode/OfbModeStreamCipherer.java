package p79068.crypto.cipher.mode;

import p79068.crypto.Zeroizer;
import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.StreamCipherer;
import p79068.lang.BoundsChecker;


final class OfbModeStreamCipherer extends StreamCipherer {
	
	private Cipherer cipherer;
	private int blockLength;
	
	private byte[] keyStream;  // The current key stream block
	private int keyStreamIndex;
	
	
	
	OfbModeStreamCipherer(OfbModeStreamCipher cipher, byte[] initVector, BlockCipher blockCipher, byte[] cipherKey) {
		super(cipher, initVector);
		blockLength = blockCipher.getBlockLength();
		if (initVector.length != blockLength)
			throw new AssertionError();
		cipherer = blockCipher.newCipherer(cipherKey);
		keyStream = initVector.clone();
		keyStreamIndex = 0;
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		
		for (int i = off, end = off + len; i < end; i++) {
			b[i] ^= keyStream[keyStreamIndex];
			keyStreamIndex++;
			if (keyStreamIndex == blockLength) {
				cipherer.encrypt(keyStream);
				keyStreamIndex = 0;
			}
		}
	}
	
	
	@Override
	public void skip(int byteCount) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		if (byteCount < 0)
			throw new IllegalArgumentException("Negative skip");
		
		// Try to finish the current block
		if (keyStreamIndex > 0) {
			int temp = Math.min(blockLength - keyStreamIndex, byteCount);  // 0 <= temp < blockLength
			keyStreamIndex += temp;
			byteCount -= temp;
			if (keyStreamIndex == blockLength) {
				cipherer.encrypt(keyStream);
				keyStreamIndex = 0;
			}
		}
		
		// Skip one block at a time
		for (; byteCount >= blockLength; byteCount -= blockLength)
			cipherer.encrypt(keyStream);
		
		// The residual, which is less than a block
		keyStreamIndex += byteCount;  // 0 <= byteCount < blockLength
	}
	
	
	@Override
	public OfbModeStreamCipherer clone() {
		OfbModeStreamCipherer result = (OfbModeStreamCipherer)super.clone();
		result.keyStream = result.keyStream.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		keyStreamIndex = 0;
		Zeroizer.clear(keyStream);
		keyStream = null;
		cipherer.zeroize();
		cipherer = null;
		super.zeroize();
	}
	
}