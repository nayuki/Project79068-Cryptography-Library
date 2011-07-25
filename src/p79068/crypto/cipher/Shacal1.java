package p79068.crypto.cipher;

import p79068.crypto.hash.Sha1;
import p79068.lang.NullChecker;
import p79068.util.HashCoder;


/**
 * The SHACAL-1 block cipher, based on the SHA-1 hash function.
 * <p>Key lengths: 0 to 512 bits at multiples of 8 bits. Minimum 128 bits recommended.</p>
 * <p>Block length: 160 bits (20 bytes)</p>
 * @see Shacal2
 */
public final class Shacal1 extends BlockCipher {
	
	private int keyLength;  // In bytes
	
	
	
	public Shacal1(int keyLength) {
		if (keyLength < 0 || keyLength > 64)
			throw new IllegalArgumentException();
		this.keyLength = keyLength;
	}
	
	
	
	@Override
	public Cipherer newCipherer(byte[] key) {
		NullChecker.check(key);
		if (key.length != keyLength)
			throw new IllegalArgumentException();
		return Sha1.FUNCTION.newCipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: {@code "SHACAL-1 (<var>n</var>-bit key)"}.
	 */
	@Override
	public String getName() {
		return String.format("SHACAL-1 (%d-bit key)", keyLength * 8);
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm.
	 */
	@Override
	public int getKeyLength() {
		return keyLength;
	}
	
	
	/**
	 * Returns the block length of this cipher algorithm: {@code 20} bytes (160 bits).
	 * @return {@code 20}
	 */
	@Override
	public int getBlockLength() {
		return 20;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Shacal1 && keyLength == ((Shacal1)obj).keyLength;
	}
	
	
	@Override
	public int hashCode() {
		return HashCoder.newInstance().add(keyLength).getHashCode();
	}
	
}
