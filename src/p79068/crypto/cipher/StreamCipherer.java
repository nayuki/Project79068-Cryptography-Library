package p79068.crypto.cipher;


/**
 * A stream cipherer.
 * <p>Encryption and decryption are the same operation. The block size is actually 1 bit, but this library restricts the block size to be reported in bytes, so the indicated block size is 1 byte.</p>
 * @see StreamCipher
 * @see Cipherer
 * @see Cipher
 */
public interface StreamCipherer extends Cipherer {
	
	/**
	 * @throws NullPointerException if {@code b} is {@code null}
	 * @throws IndexOutOfBoundsException if {@code off} and {@code len} specify a range outside of array {@code b}'s bounds
	 */
	@Override
	public void decrypt(byte[] b, int off, int len);
	
	
	public void skip(int byteCount);
	
	
	@Override
	public StreamCipher getCipher();
	
}
