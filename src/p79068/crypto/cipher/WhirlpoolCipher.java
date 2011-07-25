package p79068.crypto.cipher;

import p79068.crypto.hash.Whirlpool;


/**
 * The internal block cipher used in the Whirlpool hash function.
 * <p>Instantiability: <em>Singleton</em></p>
 * @see Whirlpool0Cipher
 * @see WhirlpoolTCipher
 */
public final class WhirlpoolCipher extends AbstractCipher implements BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final WhirlpoolCipher CIPHER = new WhirlpoolCipher();
	
	
	
	private WhirlpoolCipher() {
		super("Whirlpool Cipher", 64, 64);
	}
	
	
	
	@Override
	public Cipherer newCiphererUnchecked(byte[] key) {
		return Whirlpool.WHIRLPOOL_FUNCTION.newCipherer(this, key);
	}
	
}
