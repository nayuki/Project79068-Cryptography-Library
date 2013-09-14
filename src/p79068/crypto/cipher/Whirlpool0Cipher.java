package p79068.crypto.cipher;

import p79068.crypto.hash.Whirlpool;


/**
 * The internal block cipher used in the Whirlpool hash function.
 * <p>Instantiability: <em>Singleton</em></p>
 * @see WhirlpoolTCipher
 * @see WhirlpoolCipher
 */
public final class Whirlpool0Cipher extends AbstractCipher implements BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final Whirlpool0Cipher CIPHER = new Whirlpool0Cipher();
	
	
	
	private Whirlpool0Cipher() {
		super("Whirlpool-0 Cipher", 64, 64);
	}
	
	
	
	@Override
	protected Cipherer newCiphererUnchecked(byte[] key) {
		return Whirlpool.WHIRLPOOL0_FUNCTION.newCipherer(this, key);
	}
	
}
