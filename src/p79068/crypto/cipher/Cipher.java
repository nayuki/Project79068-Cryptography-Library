package p79068.crypto.cipher;


/**
 * An abstract cipher.
 * <p>Mutability: <em>Immutable</em></p>
 * @see Cipherer
 */
public abstract class Cipher {
	
	/**
	 * Returns a new cipherer, which is used to encrypt and decrypt byte blocks.
	 */
	public abstract Cipherer newCipherer(byte[] key);
	
	
	/**
	 * Returns the name of this cipher algorithm.
	 */
	public abstract String getName();
	
	
	/**
	 * Returns the key length of this cipher algorithm, in bytes.
	 * @return the key length of this cipher algorithm, in bytes
	 */
	public abstract int getKeyLength();
	
	
	/**
	 * Returns the block length of this cipher algorithm, in bytes. Encryptions and decryptions only act on complete blocks.
	 * @return the block length of this cipher algorithm, in bytes
	 */
	public abstract int getBlockLength();
	
	
	/**
	 * Returns a string representation of this cipher. The format is: {@code "<var>name</var> cipher"}. This is subject to change.
	 */
	@Override
	public String toString() {
		return String.format("%s cipher", getName());
	}
	
}
