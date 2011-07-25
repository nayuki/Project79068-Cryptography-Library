package p79068.crypto.cipher;

import p79068.lang.NullChecker;
import p79068.util.HashCoder;


/**
 * The RC4 stream cipher.
 */
public final class Rc4 extends StreamCipher {
	
	private int keyLength;  // In bytes
	
	
	
	public Rc4(int keyLength) {
		if (keyLength <= 0 || keyLength > 256)
			throw new IllegalArgumentException();
		this.keyLength = keyLength;
	}
	
	
	
	@Override
	public StreamCipherer newCipherer(byte[] key) {
		NullChecker.check(key);
		if (key.length != keyLength)
			throw new IllegalArgumentException();
		return new Rc4Cipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: {@code "RC4 (<var>n</var>-bit key)"}.
	 */
	@Override
	public String getName() {
		return String.format("RC4 (%d-bit key)", keyLength * 8);
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm.
	 */
	@Override
	public int getKeyLength() {
		return keyLength;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Rc4 && keyLength == ((Rc4)obj).keyLength;
	}
	
	
	@Override
	public int hashCode() {
		return HashCoder.newInstance().add(keyLength).getHashCode();
	}
	
}
