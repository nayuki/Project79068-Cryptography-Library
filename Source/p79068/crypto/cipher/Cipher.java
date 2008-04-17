package p79068.crypto.cipher;


/**
 * An abstract cipher.
 * <p>Mutability: <em>Immutable</em></p>
 * @see Cipherer
 */
public abstract class Cipher {
	
	protected Cipher() {}
	
	
	
	/**
	 * Returns a new cipherer, which is used to encrypt and decrypt byte blocks.
	 */
	public abstract Cipherer newCipherer(byte[] key);
	
	
	/**
	 * Returns the name of this cipher algorithm.
	 */
	public abstract String getName();
	
	
	/**
	 * Returns the key length of this cipher algorithm.
	 */
	public abstract int getKeyLength();
	
	
	/**
	 * Returns the block length of this cipher algorithm. Encryptions and decryptions only act on complete blocks.
	 */
	public abstract int getBlockLength();
	
	
	/**
	 * Returns a string representation of this cipher. The format is: <code>"<var>name</var> cipher"</code>. This is subject to change.
	 */
	public String toString() {
		return String.format("%s cipher", getName());
	}
	
}