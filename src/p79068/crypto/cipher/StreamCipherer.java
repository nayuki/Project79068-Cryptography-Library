package p79068.crypto.cipher;

import p79068.lang.BoundsChecker;


/**
 * A stream cipherer.
 * <p>Encryption and decryption are the same operation. The block size is actually 1 bit, but this library restricts the block size to be reported in bytes, so the indicated block size is 1 byte.</p>
 * @see StreamCipher
 * @see Cipherer
 * @see Cipher
 */
public abstract class StreamCipherer extends Cipherer {
	
	public StreamCipherer(StreamCipher cipher, byte[] key) {
		super(cipher, key);
	}
	
	
	
	/**
	 * @throws NullPointerException if {@code b} is {@code null}
	 * @throws IndexOutOfBoundsException if {@code off} and {@code len} specify a range outside of array {@code b}'s bounds
	 */
	@Override
	public final void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		encrypt(b, off, len);
	}
	
	
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
