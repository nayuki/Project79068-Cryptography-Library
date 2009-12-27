package p79068.crypto.cipher;

import p79068.lang.NullChecker;


/**
 * The TEA (Tiny Encryption Algorithm) block cipher.
 * <p>Key length: 128 bits (16 bytes)</p>
 * <p>Block length: 64 bits (8 bytes)</p>
 * <p>Instantiability: <em>Singleton</em></p>
 * @see Xtea
 */
public final class Tea extends BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final Tea CIPHER = new Tea();
	
	
	
	@Override
	public Cipherer newCipherer(byte[] key) {
		NullChecker.check(key);
		if (key.length != 16)
			throw new IllegalArgumentException();
		return new TeaCipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: <samp>TEA</samp>.
	 * @return <code>TEA</code>
	 */
	@Override
	public String getName() {
		return "TEA";
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm: <samp>16</samp> bytes (128 bits).
	 * @return <code>16</code>
	 */
	@Override
	public int getKeyLength() {
		return 16;
	}
	
	
	/**
	 * Returns the block length of this cipher algorithm: <samp>8</samp> bytes (64 bits).
	 * @return <code>8</code>
	 */
	@Override
	public int getBlockLength() {
		return 8;
	}
	
	
	
	private Tea() {}
	
}