package p79068.crypto.cipher;


/**
 * A cipher algorithm. The cipher algorithm should be stateless, except possible for zeroization.
 * A cipher is given a key to create a {@link Cipherer}, which is used to encrypt/decrypt messages.
 * <p>Example usage:</p>
 * <pre>byte[] key = (...);
 *byte[] msg = (...);
 *Cipher cp = Rijndael.AES128_CIPHER;
 *Cipherer c = cp.newCipherer(key);
 *c.encrypt(msg);</pre>
 * @see Cipherer
 */
public interface Cipher {
	
	/**
	 * Returns a new cipherer of this algorithm with the specified key,
	 * which is used to encrypt and decrypt byte blocks.
	 * @param key the key to be used
	 * @return a new cipherer of this algorithm with the specified key
	 */
	public Cipherer newCipherer(byte[] key);
	
	
	/**
	 * Returns the name of this cipher algorithm.
	 * The value must not change for the lifetime of the cipher object.
	 * @return the name of this cipher algorithm
	 */
	public String getName();
	
	
	/**
	 * Returns the key length of this cipher algorithm, in bytes.
	 * <p>The value must not change for the lifetime of the cipher object, and must be a positive number.</p>
	 * @return the key length of this cipher algorithm, in bytes
	 */
	public int getKeyLength();
	
	
	/**
	 * Returns the block length of this cipher algorithm, in bytes.
	 * Encryptions and decryptions only act on complete blocks.
	 * <p>The value must not change for the lifetime of the cipher object,
	 * and must be a positive number.</p>
	 * @return the block length of this cipher algorithm, in bytes
	 */
	public int getBlockLength();
	
	
	/**
	 * Returns a string representation of this cipher. In general, unless otherwise documented,
	 * you should assume that the format of the string is not guaranteed to be fixed.
	 * This is unlike {@link Cipher#getName()}, which has definite semantics.
	 * @return a string representation of this cipher
	 */
	@Override
	public String toString();
	
}
