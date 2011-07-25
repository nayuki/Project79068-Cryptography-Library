package p79068.crypto.cipher;

import p79068.crypto.Zeroizable;


/**
 * A cipher instance.
 * <p>Mutability: <em>Mutable</em>, unless otherwise specified<br>
 * Thread safety: <em>Unsafe</em>, unless otherwise specified<br>
 * Instantiability: Via {@code Cipher instance.newCipherer()}</p>
 * @see Cipher
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
	 */
	public Cipher getCipher();
	
	
	public Cipherer clone();
	
	
	public void zeroize();
	
}
