package p79068.crypto.cipher;

import p79068.Assert;


/**
 * A stream cipherer.
 * <p>Encryption and decryption are the same operation. The block size is actually 1 bit, but this library restricts the block size to be reported in bytes, so the indicated block size is 1 byte.</p>
 * @see StreamCipher
 * @see Cipherer
 * @see Cipher
 */
public abstract class AbstractStreamCipherer extends AbstractCipherer implements StreamCipherer {
	
	public AbstractStreamCipherer(StreamCipher cipher, byte[] key) {
		super(cipher, key);
	}
	
	
	
	/**
	 * Decrypts the specified byte array, which is the same as encrypting it.
	 * @throws NullPointerException if {@code b} is {@code null}
	 * @throws IndexOutOfBoundsException if {@code off} and {@code len} specify a range outside of array {@code b}'s bounds
	 */
	@Override
	public final void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
		encrypt(b, off, len);
	}
	
	
	/**
	 * Skips the specified number of key stream bytes.
	 * @param byteCount the number of bytes to skip, which must be non-negative
	 * @throws IllegalArgumentException if {@code byteCount} is negative
	 */
	public void skip(int byteCount) {
		if (byteCount < 0)
			throw new IllegalArgumentException("Negative skip");
		
		byte[] b = new byte[1024];
		while (byteCount > 0) {
			int templen = Math.min(b.length, byteCount);
			encrypt(b, 0, templen);
			byteCount -= templen;
		}
	}
	
	
	@Override
	public StreamCipher getCipher() {
		return (StreamCipher)cipher;
	}
	
}
