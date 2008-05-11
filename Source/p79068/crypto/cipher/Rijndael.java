package p79068.crypto.cipher;


/**
 * The Rijndael/AES (Advanced Encryption Standard) block cipher.
 * <p>Key lengths: Any positive multiple of 32 bits (4 bytes)</p>
 * <p>Block lengths: 128, 160, 192, 224, and 256 bits. While Rijndael can use all of these, AES is limited to 128 bits (16 bytes).</p>
 */
public final class Rijndael extends BlockCipher {
	
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
	
	
	
	private int keyLength;
	private int blockLength;
	
	
	
	public Rijndael(int blockLength, int keyLength) {
		if (keyLength < 4 || keyLength % 4 != 0)
			throw new IllegalArgumentException("Invalid key length");
		if (blockLength < 16 || blockLength > 32 || blockLength % 4 != 0)
			throw new IllegalArgumentException("Invalid block length");
		this.keyLength = keyLength;
		this.blockLength = blockLength;
	}
	
	
	
	public Cipherer newCipherer(byte[] key) {
		if (key.length != keyLength)
			throw new IllegalArgumentException();
		if (blockLength == 16)
			return new FastAesCipherer(this, key);
		else
			return new RijndaelCipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: <samp>Rijndael (<var>n</var>-bit key, <var>m</var>-bit block)</samp>.
	 */
	public String getName() {
		return String.format("Rijndael (%d-bit key, %d-bit block)", keyLength * 8, blockLength * 8);
	}
	
	/**
	 * Returns the key length of this cipher algorithm.
	 */
	public int getKeyLength() {
		return keyLength;
	}
	
	/**
	 * Returns the block length of this cipher algorithm.
	 */
	public int getBlockLength() {
		return blockLength;
	}
	
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Rijndael))
			return false;
		Rijndael cipher = (Rijndael)obj;
		return keyLength == cipher.keyLength && blockLength == cipher.blockLength;
	}
	
	
	public int hashCode() {
		return keyLength << 16 | blockLength;
	}
	
}