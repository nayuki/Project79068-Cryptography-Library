package p79068.crypto.cipher;


/**
 * A stream cipherer, which performs encryption/decryption by XORing with a pseudo-random key stream.
 * <p>Encryption and decryption are the same operation. The block size is actually 1 bit, but this
 * library restricts the block size to be reported in bytes, so the indicated block size is 1 byte.</p>
 * @see StreamCipher
 * @see Cipherer
 * @see Cipher
 */
public interface StreamCipherer extends Cipherer {
	
	/**
	 * Decrypts the specified range of bytes, which is equivalent to encrypting them.
	 * @throws NullPointerException if {@code b} is {@code null}
	 * @throws IndexOutOfBoundsException if {@code off} and {@code len} specify a range outside of array {@code b}'s bounds
	 */
	@Override
	public void decrypt(byte[] b, int off, int len);
	
	
	/**
	 * Skips the specified number of key stream bytes. This is equivalent to encrypting this number
	 * of bytes and discarding the result, but keeping the cipherer's internal state changes.
	 * @param byteCount the number of bytes to skip, which must be non-negative
	 * @throws IllegalArgumentException if {@code byteCount} is negative
	 */
	public void skip(int byteCount);
	
	
	@Override
	public StreamCipher getCipher();
	
}
