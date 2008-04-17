package p79068.crypto.cipher;


/**
The SHACAL-2 block cipher, based on the SHA-256 hash function.
<p>Key lengths: 0 to 512 bits at multiples of 8 bits. Minimum 128 bits recommended.</p>
<p>Block length: 256 bits (32 bytes)</p>
@see Shacal1
*/
public final class Shacal2 extends BlockCipher {
	
	private int keyLength; // In bytes
	

	public Shacal2(int keyLength) {
		if (keyLength < 0 || keyLength > 64)
			throw new IllegalArgumentException();
		this.keyLength = keyLength;
	}
	
	
	public Cipherer newCipherer(byte[] key) {
		if (key.length != keyLength)
			throw new IllegalArgumentException();
		return new Shacal2Cipherer(this, key);
	}
	
	
	/** Returns the name of this cipher algorithm: <samp>SHACAL-2 (<var>n</var>-bit key)</samp>. */
	public String getName() {
		return String.format("SHACAL-2 (%d-bit key)", keyLength * 8);
	}
	
	/** Returns the key length of this cipher algorithm. */
	public int getKeyLength() {
		return keyLength;
	}
	
	/** Returns the block length of this cipher algorithm: <samp>32</samp> bytes (256 bits). */
	public int getBlockLength() {
		return 32;
	}
	
	
	public boolean equals(Object obj) {
		return obj instanceof Shacal2 && keyLength == ((Shacal2)obj).keyLength;
	}
	
	public int hashCode() {
		return keyLength;
	}
}