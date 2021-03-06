package p79068.crypto.cipher;

import p79068.util.HashCoder;


/**
 * The Rijndael/AES (Advanced Encryption Standard) block cipher.
 * <p>Key lengths: Any positive multiple of 32 bits (4 bytes)</p>
 * <p>Block lengths: 128, 160, 192, 224, and 256 bits. While Rijndael can use all of these, AES is limited to 128 bits (16 bytes).</p>
 */
public final class Rijndael extends AbstractCipher implements BlockCipher {
	
	/**
	 * The AES cipher algorithm with 128-bit key size.
	 */
	public static final Rijndael AES128_CIPHER = new Rijndael(16, 16);
	
	/**
	 * The AES cipher algorithm with 192-bit key size.
	 */
	public static final Rijndael AES192_CIPHER = new Rijndael(16, 24);
	
	/**
	 * The AES cipher algorithm with 256-bit key size.
	 */
	public static final Rijndael AES256_CIPHER = new Rijndael(16, 32);
	
	
	
	/**
	 * The block length, in bytes.
	 */
	private int blockLength;
	
	/**
	 * The key length, in bytes.
	 */
	private int keyLength;
	
	
	
	public Rijndael(int blockLength, int keyLength) {
		super(String.format("Rijndael (%d-bit key, %d-bit block)", keyLength * 8, blockLength * 8), blockLength, keyLength);
		if (keyLength < 4 || keyLength % 4 != 0)
			throw new IllegalArgumentException("Invalid key length");
		if (blockLength < 16 || blockLength > 32 || blockLength % 4 != 0)
			throw new IllegalArgumentException("Invalid block length");
		this.keyLength = keyLength;
		this.blockLength = blockLength;
	}
	
	
	
	@Override
	protected Cipherer newCiphererUnchecked(byte[] key) {
		if (blockLength == 16)
			return new FastAesCipherer(this, key);
		else
			return new RijndaelCipherer(this, key);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Rijndael))
			return false;
		Rijndael cipher = (Rijndael)obj;
		return keyLength == cipher.keyLength && blockLength == cipher.blockLength;
	}
	
	
	@Override
	public int hashCode() {
		HashCoder h = HashCoder.newInstance();
		h.add(keyLength);
		h.add(blockLength);
		return h.getHashCode();
	}
	
}
