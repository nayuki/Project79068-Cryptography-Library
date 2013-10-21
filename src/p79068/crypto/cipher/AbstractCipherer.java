package p79068.crypto.cipher;

import p79068.Assert;
import p79068.crypto.Zeroizer;


/**
 * A cipher instance with a key, with some functionality implemented for convenience. Mutable unless otherwise specified. Thread-unsafe unless otherwise specified.
 */
public abstract class AbstractCipherer implements Cipherer {
	
	protected Cipher cipher;
	
	protected byte[] key;
	
	
	
	public AbstractCipherer(Cipher cipher, byte[] key) {
		Assert.assertNotNull(cipher, key);
		if (key.length != cipher.getKeyLength())
			throw new IllegalArgumentException("Key length does not match cipher's key length");
		
		this.cipher = cipher;
		this.key = key.clone();
	}
	
	
	
	/**
	 * Encrypts the specified byte array.
	 */
	public void encrypt(byte[] b) {
		Assert.assertNotNull(b);
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
		Assert.assertNotNull(b);
		decrypt(b, 0, b.length);
	}
	
	
	/**
	 * Decrypts the specified byte array.
	 */
	public abstract void decrypt(byte[] b, int off, int len);
	
	
	/**
	 * Returns the cipher algorithm associated with this cipherer.
	 * @return the cipher algorithm
	 */
	public Cipher getCipher() {
		return cipher;
	}
	
	
	@Override
	public AbstractCipherer clone() {
		try {
			AbstractCipherer result = (AbstractCipherer)super.clone();
			result.key = result.key.clone();
			return result;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
	}
	
	
	public void zeroize() {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		key = Zeroizer.clear(key);
		cipher = null;
	}
	
}
