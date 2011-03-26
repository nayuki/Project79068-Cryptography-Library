package p79068.crypto.cipher;

import p79068.crypto.hash.Whirlpool;
import p79068.lang.NullChecker;


/**
 * The internal block cipher used in the Whirlpool hash function.
 * <p>Instantiability: <em>Singleton</em></p>
 * @see WhirlpoolTCipher
 * @see WhirlpoolCipher
 */
public final class Whirlpool0Cipher extends BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final Whirlpool0Cipher CIPHER = new Whirlpool0Cipher();
	
	
	
	@Override
	public Cipherer newCipherer(byte[] key) {
		NullChecker.check(key);
		if (key.length != 64)
			throw new IllegalArgumentException();
		return Whirlpool.WHIRLPOOL0_FUNCTION.newCipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: {@code "Whirlpool-0 Cipher"}.
	 * @return {@code "Whirlpool-0 Cipher"}
	 */
	@Override
	public String getName() {
		return "Whirlpool-0 Cipher";
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm: {@code 64} bytes (512 bits).
	 * @return {@code 64}
	 */
	@Override
	public int getKeyLength() {
		return 64;
	}
	
	
	/**
	 * Returns the block length of this cipher algorithm: {@code 64} bytes (512 bits).
	 * @return {@code 64}
	 */
	@Override
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Whirlpool0Cipher() {}
	
}