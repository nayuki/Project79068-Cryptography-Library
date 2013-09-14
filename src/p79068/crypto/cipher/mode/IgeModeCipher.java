package p79068.crypto.cipher.mode;

import p79068.crypto.Zeroizable;
import p79068.crypto.Zeroizer;
import p79068.crypto.cipher.AbstractCipher;
import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.Cipherer;


/**
 * A cipher using a block cipher in IGE (infinite garble extension) mode.
 * <p>Encryption algorithm:</p>
 * <p><code>plaintext[-1] = zeros<br>
 * ciphertext[-1] = initializationVector<br>
 * ciphertext[i] = encrypt(plaintext[i] XOR ciphertext[i-1]) XOR plaintext[i-1]</code></p>
 * <p>Decryption algorithm:</p>
 * <p><code>plaintext[-1] = zeros<br>
 * ciphertext[-1] = initializationVector<br>
 * plaintext[i] = decrypt(ciphertext[i] XOR plaintext[i-1]) XOR ciphertext[i-1]</code></p>
 */
public final class IgeModeCipher extends AbstractCipher implements Zeroizable {
	
	private BlockCipher blockCipher;
	private byte[] key;
	
	
	
	public IgeModeCipher(BlockCipher cipher, byte[] key) {
		super(cipher.getName() + "-IGE", cipher.getBlockLength(), cipher.getBlockLength());
		if (key.length != cipher.getKeyLength())
			throw new IllegalArgumentException();
		blockCipher = cipher;
		this.key = key.clone();
	}
	
	
	
	@Override
	protected Cipherer newCiphererUnchecked(byte[] initVector) {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		return new IgeModeCipherer(this, initVector, blockCipher, key);
	}
	
	
	public void zeroize() {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		Zeroizer.clear(key);
		key = null;
		if (blockCipher instanceof Zeroizable)
			((Zeroizable)blockCipher).zeroize();
		blockCipher = null;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof IgeModeCipher && blockCipher.equals(((IgeModeCipher)obj).blockCipher);
	}
	
	
	@Override
	public int hashCode() {
		return blockCipher.hashCode();
	}
	
}
