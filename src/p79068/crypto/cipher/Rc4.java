package p79068.crypto.cipher;

import p79068.lang.NullChecker;
import p79068.util.HashCoder;


/**
 * The RC4 stream cipher.
 * <p>The value returned by {@code getName()} is in the format {@code "RC4 (<var>n</var>-bit key)"}.</p>
 */
public final class Rc4 extends AbstractCipher implements StreamCipher {
	
	private int keyLength;  // In bytes
	
	
	
	public Rc4(int keyLength) {
		super(String.format("RC4 (%d-bit key)", keyLength * 8), 1, keyLength);
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
	
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Rc4 && keyLength == ((Rc4)obj).keyLength;
	}
	
	
	@Override
	public int hashCode() {
		return HashCoder.newInstance().add(keyLength).getHashCode();
	}
	
}
