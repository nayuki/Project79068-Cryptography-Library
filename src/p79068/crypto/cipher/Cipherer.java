package p79068.crypto.cipher;

import p79068.crypto.Zeroizable;


/**
 * A cipher instance with a key. Mutable unless otherwise specified. Thread-unsafe unless otherwise specified.
 * @see Cipher#newCipherer(byte[])
 */
public interface Cipherer extends Cloneable, Zeroizable {
	
	/**
	 * Encrypts the specified byte array.
	 */
	public void encrypt(byte[] b);
	
	
	/**
	 * Encrypts the specified byte array.
	 */
	public abstract void encrypt(byte[] b, int off, int len);
	
	
	/**
	 * Decrypts the specified byte array.
	 */
	public void decrypt(byte[] b);
	
	
	/**
	 * Decrypts the specified byte array.
	 */
	public abstract void decrypt(byte[] b, int off, int len);
	
	
	/**
	 * Returns the cipher algorithm associated with this cipherer.
	 * @return the cipher algorithm
	 */
	public Cipher getCipher();
	
	
	public Cipherer clone();
	
	
	public void zeroize();
	
}
