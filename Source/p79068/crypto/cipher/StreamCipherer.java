package p79068.crypto.cipher;

import p79068.lang.BoundsChecker;
import p79068.util.Random;


/**
 * A stream cipherer.
 * <p>Encryption and decryption are the same operation. The block size is actually 1 bit, but this library restricts the block size to be reported in bytes, so the indicated block size is 1 byte.</p>
 * @see StreamCipher
 * @see Cipherer
 * @see Cipher
 */
public abstract class StreamCipherer extends Cipherer {
	
	protected StreamCipherer(StreamCipher cipher, byte[] key) {
		super(cipher, key);
	}
	
	
	
	/**
	 * @throws NullPointerException if <code>b</code> is <code>null</code>
	 * @throws IndexOutOfBoundsException if <code>off</code> and <code>len</code> specify a range outside of array <code>b</code>'s bounds
	 */
	public final void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		encrypt(b, off, len);
	}
	
	
	public void skip(int byteCount) {
		if (byteCount < 0)
			throw new IllegalArgumentException();
		byte[] b = new byte[1024];
		while (byteCount > 0) {
			int templen = Math.min(b.length, byteCount);
			encrypt(b, 0, templen);
			byteCount -= templen;
		}
	}
	
	
	public StreamCipher getCipher() {
		return (StreamCipher)cipher;
	}
	
	
	/**
	 * Returns this stream cipher as a (cryptographically secure) pseudorandom number generator.
	 */
	public Random asRandom() {
		return new StreamCiphererRandom(this);
	}
	
}