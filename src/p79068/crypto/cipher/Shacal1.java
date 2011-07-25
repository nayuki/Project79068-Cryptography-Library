package p79068.crypto.cipher;

import p79068.crypto.hash.Sha1;
import p79068.util.HashCoder;


/**
 * The SHACAL-1 block cipher, based on the SHA-1 hash function.
 * <p>Key lengths: 0 to 512 bits at multiples of 8 bits. Minimum 128 bits recommended.</p>
 * <p>Block length: 160 bits (20 bytes)</p>
 * @see Shacal2
 */
public final class Shacal1 extends AbstractCipher implements BlockCipher {
	
	private int keyLength;  // In bytes
	
	
	
	public Shacal1(int keyLength) {
		super(String.format("SHACAL-1 (%d-bit)", keyLength), 20, keyLength);
		if (keyLength < 0 || keyLength > 64)
			throw new IllegalArgumentException();
		this.keyLength = keyLength;
	}
	
	
	
	@Override
	public Cipherer newCiphererUnchecked(byte[] key) {
		return Sha1.FUNCTION.newCipherer(this, key);
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
