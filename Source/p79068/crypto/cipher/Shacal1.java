package p79068.crypto.cipher;


/** 
The SHACAL-1 block cipher, based on the SHA-1 hash function.
<p>Key lengths: 0 to 512 bits at multiples of 8 bits. Minimum 128 bits recommended.</p>
<p>Block length: 160 bits (20 bytes)</p>
@see Shacal2
*/
public final class Shacal1 extends BlockCipher {
	
	private int keyLength; // In bytes
	

	public Shacal1(int keyLength) {
		if (keyLength < 0 || keyLength > 64)
			throw new IllegalArgumentException();
		this.keyLength = keyLength;
	}
	
	
	public Cipherer newCipherer(byte[] key) {
		if (key.length != keyLength)
			throw new IllegalArgumentException();
		return new Shacal1Cipherer(this, key);
	}
	
	
	/** Returns the name of this cipher algorithm: SHACAL-1 (<var>n</var>-bit key). */
	public String getName() {
		return String.format("SHACAL-1 (%d-bit key)", keyLength * 8);
	}
	
	/** Returns the key length of this cipher algorithm. */
	public int getKeyLength() {
		return keyLength;
	}
	
	/** Returns the block length of this cipher algorithm: <samp>20</samp> bytes (160 bits). */
	public int getBlockLength() {
		return 20;
	}
	
	
	public boolean equals(Object obj) {
		return obj instanceof Shacal1 && keyLength == ((Shacal1)obj).keyLength;
	}
	
	public int hashCode() {
		return keyLength;
	}
}