package p79068.crypto.cipher.mode;

import p79068.crypto.Zeroizable;
import p79068.crypto.Zeroizer;
import p79068.crypto.cipher.AbstractStreamCipher;
import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.StreamCipherer;


/**
 * A stream cipher using a block cipher in OFB (output feedback) mode.
 * <p>Encryption algorithm:</p>
 * <p><code>keyStream[-1] = initializationVector<br>
 * keyStream[i] = encrypt(keyStream[i-1])<br>
 * ciphertext[i] = keyStream[i] XOR plaintext[i]</code></p>
 * <p>Decryption algorithm:</p>
 * <p><code>keyStream[-1] = initializationVector<br>
 * keyStream[i] = encrypt(keyStream[i-1])<br>
 * plaintext[i] = keyStream[i] XOR ciphertext[i]</code></p>
 */
public final class OfbModeStreamCipher extends AbstractStreamCipher implements Zeroizable {
	
	private BlockCipher blockCipher;
	private byte[] key;
	
	
	
	public OfbModeStreamCipher(BlockCipher cipher, byte[] key) {
		super(cipher.getName() + "-OFB", cipher.getBlockLength());
		if (key.length != cipher.getKeyLength())
			throw new IllegalArgumentException();
		blockCipher = cipher;
		this.key = key.clone();
	}
	
	
	
	@Override
	protected StreamCipherer newCiphererUnchecked(byte[] initVector) {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		return new OfbModeStreamCipherer(this, initVector, blockCipher, key);
	}
	
	
	public void zeroize() {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		key = Zeroizer.clear(key);
		if (blockCipher instanceof Zeroizable)
			((Zeroizable)blockCipher).zeroize();
		blockCipher = null;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof OfbModeStreamCipher && blockCipher.equals(((OfbModeStreamCipher)obj).blockCipher);
	}
	
	
	@Override
	public int hashCode() {
		return blockCipher.hashCode();
	}
	
}
