package p79068.crypto.cipher;

import p79068.lang.NullChecker;


/**
 * The IDEA (International Data Encryption Algorithm) block cipher. This cipher is patented.
 * <p>Instantiability: <em>Singleton</em></p>
 */
public final class Idea extends BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final Idea CIPHER = new Idea();
	
	
	
	@Override
	public Cipherer newCipherer(byte[] key) {
		NullChecker.check(key);
		if (key.length != 16)
			throw new IllegalArgumentException();
		return new IdeaCipherer(this, key);
	}
	
	
	
	/**
	 * Returns the name of this cipher algorithm: {@code "IDEA"}.
	 * @return {@code IDEA}
	 */
	@Override
	public String getName() {
		return "IDEA";
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
	
	
	
	private Idea() {}
	
}
