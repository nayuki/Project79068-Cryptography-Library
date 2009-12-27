package p79068.crypto.cipher;

import p79068.crypto.hash.Whirlpool;


/**
 * The internal block cipher used in the Whirlpool hash function.
 * <p>Instantiability: <em>Singleton</em></p>
 * @see Whirlpool0Cipher
 * @see WhirlpoolTCipher
 */
public final class WhirlpoolCipher extends BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final WhirlpoolCipher CIPHER = new WhirlpoolCipher();
	
	
	
	@Override
	public Cipherer newCipherer(byte[] key) {
		if (key.length != 64)
			throw new IllegalArgumentException();
		return Whirlpool.FUNCTION.newCipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: <samp>Whirlpool Cipher</samp>.
	 * @return <code>"Whirlpool Cipher"</code>
	 */
	@Override
	public String getName() {
		return "Whirlpool Cipher";
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm: <samp>64</samp> bytes (512 bits).
	 * @return <code>64</code>
	 */
	@Override
	public int getKeyLength() {
		return 64;
	}
	
	
	/**
	 * Returns the block length of this cipher algorithm: <samp>64</samp> bytes (512 bits).
	 * @return <code>64</code>
	 */
	@Override
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private WhirlpoolCipher() {}
	
}