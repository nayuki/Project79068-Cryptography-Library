package p79068.crypto.cipher;


/** The RC4 stream cipher. */
public final class Rc4 extends StreamCipher {
	
	private int keyLength; // In bytes
	

	public Rc4(int keyLength) {
		if (keyLength <= 0 || keyLength > 256)
			throw new IllegalArgumentException();
		this.keyLength = keyLength;
	}
	
	
	public StreamCipherer newCipherer(byte[] key) {
		if (key.length != keyLength)
			throw new IllegalArgumentException();
		return new Rc4Cipherer(this, key);
	}
	
	
	/** Returns the name of this cipher algorithm: <samp>RC4 (<var>n</var>-bit key)</samp>. */
	public String getName() {
		return String.format("RC4 (%d-bit key)", keyLength * 8);
	}
	
	/** Returns the key length of this cipher algorithm. */
	public int getKeyLength() {
		return keyLength;
	}
	
	
	public boolean equals(Object obj) {
		return obj instanceof Rc4 && keyLength == ((Rc4)obj).keyLength;
	}
	
	public int hashCode() {
		return keyLength;
	}
}