package p79068.crypto.cipher;


/**
 * The internal block cipher used in the Whirlpool hash function.
 * <p>Instantiability: <em>Singleton</em></p>
 * @see Whirlpool0Cipher
 * @see Whirlpool1Cipher
 */
public final class WhirlpoolCipher extends BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final WhirlpoolCipher CIPHER = new WhirlpoolCipher();
	
	
	
	public Cipherer newCipherer(byte[] key) {
		if (key.length != 64)
			throw new IllegalArgumentException();
		return new WhirlpoolCipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: <samp>Whirlpool Cipher</samp>.
	 */
	public String getName() {
		return "Whirlpool Cipher";
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm: <samp>64</samp> bytes (512 bits).
	 */
	public int getKeyLength() {
		return 64;
	}
	
	
	/**
	 * Returns the block length of this cipher algorithm: <samp>64</samp> bytes (512 bits).
	 */
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private WhirlpoolCipher() {}
	
}