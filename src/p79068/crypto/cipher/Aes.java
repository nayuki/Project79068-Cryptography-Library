package p79068.crypto.cipher;

import p79068.util.HashCoder;


/**
 * The AES (Advanced Encryption Standard) (Rijndael) block cipher.
 * <p>Key lengths: Any positive multiple of 32 bits (4 bytes)</p>
 * <p>Block length: 128 bits (16 bytes).</p>
 * <p>The value returned by {@code getName()} is in the format {@code "AES (<var>n</var>-bit)"}.</p>
 */
public final class Aes extends AbstractCipher implements BlockCipher {
	
	/**
	 * The AES cipher algorithm with 128-bit key size.
	 */
	public static final Aes AES128_CIPHER = new Aes(16);
	
	/**
	 * The AES cipher algorithm with 192-bit key size.
	 */
	public static final Aes AES192_CIPHER = new Aes(24);
	
	/**
	 * The AES cipher algorithm with 256-bit key size.
	 */
	public static final Aes AES256_CIPHER = new Aes(32);
	
	
	
	/**
	 * The key length, in bytes.
	 */
	private int keyLength;
	
	
	
	public Aes(int keyLength) {
		super(String.format("AES (%d-bit)", keyLength * 8), 16, keyLength);
		if (keyLength < 4 || keyLength % 4 != 0)
			throw new IllegalArgumentException("Invalid key length");
		this.keyLength = keyLength;
	}
	
	
	
	@Override
	public Cipherer newCiphererUnchecked(byte[] key) {
		return new FastAesCipherer(this, key);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Aes))
			return false;
		Aes cipher = (Aes)obj;
		return keyLength == cipher.keyLength;
	}
	
	
	@Override
	public int hashCode() {
		HashCoder h = HashCoder.newInstance();
		h.add(keyLength);
		return h.getHashCode();
	}
	
}
