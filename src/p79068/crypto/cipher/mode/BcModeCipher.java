package p79068.crypto.cipher.mode;

import p79068.crypto.Zeroizable;
import p79068.crypto.Zeroizer;
import p79068.crypto.cipher.AbstractCipher;
import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.Cipherer;


/**
 * A cipher using a block cipher in BC (block chaining) mode.
 * <p>Encryption algorithm:</p>
 * <p><code>prevCiphersXored[-1] = initializationVector<br>
 * prevCiphersXored[i] = prevCiphersXored[i-1] XOR ciphertext[i]<br>
 * ciphertext[i] = encrypt(plaintext[i] XOR prevCiphersXored[i-1])</code></p>
 * <p>Decryption algorithm:</p>
 * <p><code>prevCiphersXored[-1] = initializationVector<br>
 * prevCiphersXored[i] = prevCiphersXored[i-1] XOR ciphertext[i]<br>
 * plaintext[i] = decrypt(ciphertext[i]) XOR prevCiphersXored[i-1]</code></p>
 */
public final class BcModeCipher extends AbstractCipher implements Zeroizable {
	
	private BlockCipher blockCipher;
	private byte[] key;
	
	
	
	public BcModeCipher(BlockCipher cipher, byte[] key) {
		super(cipher.getName() + "-BC", cipher.getBlockLength(), cipher.getBlockLength());
		if (key.length != cipher.getKeyLength())
			throw new IllegalArgumentException();
		blockCipher = cipher;
		this.key = key.clone();
	}
	
	
	
	@Override
	public Cipherer newCiphererUnchecked(byte[] initVector) {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		return new BcModeCipherer(this, initVector, blockCipher, key);
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
		return obj instanceof BcModeCipher && blockCipher.equals(((BcModeCipher)obj).blockCipher);
	}
	
}
