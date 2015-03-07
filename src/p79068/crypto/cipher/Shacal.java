package p79068.crypto.cipher;

import p79068.crypto.hash.Sha;
import p79068.util.HashCoder;


/**
 * The SHACAL-1 and SHACAL-2 block ciphers, based on the SHA-1 and SHA-256 hash functions.
 * <p>Key lengths: 0 to 512 bits at multiples of 8 bits. Minimum 128 bits recommended.</p>
 * <p>Block length: 160 bits (20 bytes) for SHACAL-1, 256 bits (32 bytes) for SHACAL-2.</p>
 * @see Shacal2
 */
public final class Shacal extends AbstractCipher implements BlockCipher {
	
	private boolean shacal1;
	
	private int keyLength;  // In bytes
	
	
	
	public Shacal(boolean shacal1, int keyLength) {
		super(String.format("SHACAL-%d (%d-bit)", shacal1 ? 1 : 2, keyLength * 8), shacal1 ? 20 : 32, keyLength);
		if (keyLength < 0 || keyLength > 64)
			throw new IllegalArgumentException();
		this.shacal1 = shacal1;
		this.keyLength = keyLength;
	}
	
	
	
	@Override
	protected Cipherer newCiphererUnchecked(byte[] key) {
		return Sha.newShacalCipherer(this, key);
	}
	
	
	// True for SHACAL-1, false for SHACAL-2.
	public boolean isShacal1() {
		return shacal1;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Shacal))
			return false;
		else
			return shacal1 == ((Shacal)obj).shacal1 && keyLength == ((Shacal)obj).keyLength;
	}
	
	
	@Override
	public int hashCode() {
		return HashCoder.newInstance().add(keyLength).getHashCode();
	}
	
}
