package p79068.crypto.cipher;


/**
 * An abstract block cipher in ECB (electronic codebook) mode.
 * Each block must be encrypted independently of other blocks,
 * and the internal state of the cipher must not change.
 * (Therefore a stream cipher is not a block cipher.)
 * <p>Mutability: <em>Immutable</em>, except for being Zeroizable</p>
 */
public interface BlockCipher extends Cipher {
	
	/**
	 * Returns a new cipherer, which is used to encrypt and decrypt byte blocks.
	 * The cipherer returned must be immutable except for being zeroizable.
	 * In particular, its encryption and decryption must be pure functions.
	 * Thus encrypting (or decrypting) the same block of byte values
	 * must produce the same result each time.
	 * @param key the key to be used
	 * @return a new cipherer of this algorithm with the specified key
	 */
	@Override
	public Cipherer newCipherer(byte[] key);
	
	
	/**
	 * Returns the block length of this cipher algorithm, in bytes.
	 * Encryption and decryption only act on complete blocks.
	 * For a block cipher, this value is typically greater than 1;
	 * common values are 8 and 16 (respectively 64 and 128 bits).
	 * <p>This value must not change for the lifetime of
	 * the cipher object, and must be a positive number.</p>
	 * @return the block length of this cipher algorithm, in bytes
	 */
	@Override
	public int getBlockLength();
	
}
