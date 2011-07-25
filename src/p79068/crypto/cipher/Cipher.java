package p79068.crypto.cipher;


/**
 * A cipher algorithm.
 * @see Cipherer
 */
public interface Cipher {
	
	/**
	 * Returns a new cipherer, which is used to encrypt and decrypt byte blocks.
	 */
	public Cipherer newCipherer(byte[] key);
	
	
	/**
	 * Returns the name of this cipher algorithm.
	 */
	public String getName();
	
	
	/**
	 * Returns the key length of this cipher algorithm, in bytes.
	 * @return the key length of this cipher algorithm, in bytes
	 */
	public int getKeyLength();
	
	
	/**
	 * Returns the block length of this cipher algorithm, in bytes. Encryptions and decryptions only act on complete blocks.
	 * @return the block length of this cipher algorithm, in bytes
	 */
	public int getBlockLength();
	
	
	/**
	 * Returns a string representation of this cipher.
	 */
	@Override
	public String toString();
	
}
