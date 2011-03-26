package p79068.crypto.cipher;

import p79068.crypto.hash.AbstractWhirlpool;
import p79068.lang.NullChecker;


/**
 * The internal block cipher used in the Whirlpool-1 hash function.
 * <p>Instantiability: <em>Singleton</em></p>
 * @see Whirlpool0Cipher
 * @see WhirlpoolCipher
 */
public final class WhirlpoolTCipher extends BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final WhirlpoolTCipher CIPHER = new WhirlpoolTCipher();
	
	
	
	@Override
	public Cipherer newCipherer(byte[] key) {
		NullChecker.check(key);
		if (key.length != 64)
			throw new IllegalArgumentException();
		return AbstractWhirlpool.WHIRLPOOL_T_FUNCTION.newCipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: {@code "Whirlpool-T Cipher"}.
	 * @return {@code "Whirlpool-T Cipher"}
	 */
	@Override
	public String getName() {
		return "Whirlpool-T Cipher";
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
	
	
	
	private WhirlpoolTCipher() {}
	
}