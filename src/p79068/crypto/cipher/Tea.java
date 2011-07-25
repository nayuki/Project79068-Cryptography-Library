package p79068.crypto.cipher;

import p79068.lang.NullChecker;


/**
 * The TEA (Tiny Encryption Algorithm) and XTEA (Extended TEA) block ciphers.
 * <p>Key length: 128 bits (16 bytes)</p>
 * <p>Block length: 64 bits (8 bytes)</p>
 * <p>Instantiability: <em>Singleton</em></p>
 */
public final class Tea extends BlockCipher {
	
	/**
	 * The TEA cipher algorithm. {@code name = "TEA"}.
	 */
	public static final Tea TEA_CIPHER = new Tea();
	
	
	/**
	 * The XTEA cipher algorithm, a revision of TEA. {@code name = "XTEA"}.
	 */
	public static final Tea XTEA_CIPHER = new Tea();
	
	
	@Override
	public Cipherer newCipherer(byte[] key) {
		NullChecker.check(key);
		if (key.length != 16)
			throw new IllegalArgumentException();
		
		if (this == TEA_CIPHER)
			return new TeaCipherer(this, key);
		else
			return new XteaCipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm.
	 * @return the name of this cipher algorithm
	 */
	@Override
	public String getName() {
		if (this == TEA_CIPHER)
			return "TEA";
		else
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
	
	
	
	private Tea() {}
	
}
