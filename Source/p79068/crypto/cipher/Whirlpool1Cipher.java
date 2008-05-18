package p79068.crypto.cipher;


/**
 * The internal block cipher used in the Whirlpool-1 hash function.
 * <p>Instantiability: <em>Singleton</em></p>
 * @see Whirlpool0Cipher
 * @see WhirlpoolCipher
 */
public final class Whirlpool1Cipher extends BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final Whirlpool1Cipher CIPHER = new Whirlpool1Cipher();
	
	
	
	public Cipherer newCipherer(byte[] key) {
		if (key.length != 64)
			throw new IllegalArgumentException();
		return new Whirlpool1Cipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: <samp>Whirlpool-1 Cipher</samp>.
	 */
	public String getName() {
		return "Whirlpool-1 Cipher";
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
	
	
	
	private Whirlpool1Cipher() {}
	
}