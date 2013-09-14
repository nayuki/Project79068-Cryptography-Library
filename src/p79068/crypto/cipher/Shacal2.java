package p79068.crypto.cipher;

import p79068.crypto.hash.Sha256;
import p79068.util.HashCoder;


/**
 * The SHACAL-2 block cipher, based on the SHA-256 hash function.
 * <p>Key lengths: 0 to 512 bits at multiples of 8 bits. Minimum 128 bits recommended.</p>
 * <p>Block length: 256 bits (32 bytes)</p>
 * @see Shacal1
 */
public final class Shacal2 extends AbstractCipher implements BlockCipher {
	
	private int keyLength;  // In bytes
	
	
	
	public Shacal2(int keyLength) {
		super(String.format("SHACAL-2 (%d-bit)", keyLength), 32, keyLength);
		if (keyLength < 0 || keyLength > 64)
			throw new IllegalArgumentException();
		this.keyLength = keyLength;
	}
	
	
	
	@Override
	protected Cipherer newCiphererUnchecked(byte[] key) {
		return Sha256.FUNCTION.newCipherer(this, key);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Shacal2 && keyLength == ((Shacal2)obj).keyLength;
	}
	
	
	@Override
	public int hashCode() {
		return HashCoder.newInstance().add(keyLength).getHashCode();
	}
	
}
