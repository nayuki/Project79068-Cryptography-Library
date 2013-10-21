package p79068.crypto.cipher.mode;

import p79068.crypto.Zeroizable;
import p79068.crypto.Zeroizer;
import p79068.crypto.cipher.AbstractCipher;
import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.Cipherer;


/**
 * A cipher using a block cipher in CBC (cipher block chaining) mode.
 * <p>Encryption algorithm:</p>
 * <p><code>ciphertext[-1] = initializationVector<br>
 * ciphertext[i] = encrypt(plaintext[i] XOR ciphertext[i-1])</code></p>
 * <p>Decryption algorithm:</p>
 * <p><code>ciphertext[-1] = initializationVector<br>
 * plaintext[i] = decrypt(ciphertext[i]) XOR ciphertext[i-1]</code></p>
 */
public final class CbcModeCipher extends AbstractCipher implements Zeroizable {
	
	private BlockCipher blockCipher;
	private byte[] key;
	
	
	
	public CbcModeCipher(BlockCipher cipher, byte[] key) {
		super(cipher.getName() + "-CBC", cipher.getBlockLength(), cipher.getBlockLength());
		if (key.length != cipher.getKeyLength())
			throw new IllegalArgumentException();
		blockCipher = cipher;
		this.key = key.clone();
	}
	
	
	
	@Override
	protected Cipherer newCiphererUnchecked(byte[] initVector) {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		return new CbcModeCipherer(this, initVector, blockCipher, key);
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
		return obj instanceof CbcModeCipher && blockCipher.equals(((CbcModeCipher)obj).blockCipher);
	}
	
	
	@Override
	public int hashCode() {
		return blockCipher.hashCode();
	}
	
}
