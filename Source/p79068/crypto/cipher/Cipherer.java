package p79068.crypto.cipher;

import p79068.crypto.Zeroizable;
import p79068.crypto.Zeroizer;


/**
 * A cipher instance.
 * <p>Mutability: <em>Mutable</em>, unless otherwise specified<br>
 * Thread safety: <em>Unsafe</em>, unless otherwise specified<br>
 * Instantiability: Via <code>Cipher instance.newCipherer()</code></p>
 * @see Cipher
 */
public abstract class Cipherer implements Zeroizable {
	
	protected Cipher cipher;
	protected byte[] key;
	
	
	
	protected Cipherer(Cipher cipher, byte[] key) {
		if (key.length != cipher.getKeyLength())
			throw new IllegalArgumentException();
		this.cipher = cipher;
		this.key = key.clone();
	}
	
	
	
	/**
	 * Encrypts the specified byte array.
	 */
	public void encrypt(byte[] b) {
		encrypt(b, 0, b.length);
	}
	
	
	/**
	 * Encrypts the specified byte array.
	 */
	public abstract void encrypt(byte[] b, int off, int len);
	
	
	/**
	 * Decrypts the specified byte array.
	 */
	public void decrypt(byte[] b) {
		decrypt(b, 0, b.length);
	}
	
	
	/**
	 * Decrypts the specified byte array.
	 */
	public abstract void decrypt(byte[] b, int off, int len);
	
	
	/**
	 * Returns the cipher algorithm associated with this cipherer.
	 */
	public Cipher getCipher() {
		return cipher;
	}
	
	
	public void zeroize() {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Zeroizer.clear(key);
		key = null;
		cipher = null;
	}
	
}