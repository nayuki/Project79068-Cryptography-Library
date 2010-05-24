package p79068.crypto.cipher;


/**
 * The XTEA (Extended TEA) block cipher, a revision of TEA.
 * <p>Key length: 128 bits (16 bytes)</p>
 * <p>Block length: 64 bits (8 bytes)</p>
 * <p>Instantiability: <em>Singleton</em></p>
 * @see Tea
 */
public final class Xtea extends BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final Xtea CIPHER = new Xtea();
	
	
	
	@Override
	public Cipherer newCipherer(byte[] key) {
		if (key.length != 16)
			throw new IllegalArgumentException();
		return new XteaCipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: {@code "XTEA"}.
	 * @return {@code XTEA}
	 */
	@Override
	public String getName() {
		return "XTEA";
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm: {@code 16} bytes (128 bits).
	 * @return {@code 16}
	 */
	@Override
	public int getKeyLength() {
		return 16;
	}
	
	
	/**
	 * Returns the block length of this cipher algorithm: {@code 8} bytes (64 bits).
	 * @return {@code 8}
	 */
	@Override
	public int getBlockLength() {
		return 8;
	}
	
	
	
	private Xtea() {}
	
}