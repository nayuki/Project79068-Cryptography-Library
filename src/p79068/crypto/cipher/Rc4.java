package p79068.crypto.cipher;

import p79068.util.HashCoder;


/**
 * The RC4 stream cipher.
 * <p>The value returned by {@code getName()} is in the format {@code "RC4 (<var>n</var>-bit)"}.</p>
 */
public final class Rc4 extends AbstractStreamCipher {
	
	private int keyLength;  // In bytes
	
	
	
	public Rc4(int keyLength) {
		super(String.format("RC4 (%d-bit)", keyLength * 8), keyLength);
		if (keyLength <= 0 || keyLength > 256)
			throw new IllegalArgumentException();
		this.keyLength = keyLength;
	}
	
	
	
	@Override
	protected StreamCipherer newCiphererUnchecked(byte[] key) {
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
