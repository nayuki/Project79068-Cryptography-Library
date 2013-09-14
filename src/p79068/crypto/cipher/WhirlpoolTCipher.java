package p79068.crypto.cipher;

import p79068.crypto.hash.Whirlpool;


/**
 * The internal block cipher used in the Whirlpool-1 hash function.
 * <p>Instantiability: <em>Singleton</em></p>
 * @see Whirlpool0Cipher
 * @see WhirlpoolCipher
 */
public final class WhirlpoolTCipher extends AbstractCipher implements BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final WhirlpoolTCipher CIPHER = new WhirlpoolTCipher();
	
	
	
	private WhirlpoolTCipher() {
		super("Whirlpool-T Cipher", 64, 64);
	}
	
	
	
	@Override
	protected Cipherer newCiphererUnchecked(byte[] key) {
		return Whirlpool.WHIRLPOOL_T_FUNCTION.newCipherer(this, key);
	}
	
}
