package p79068.crypto.cipher;

import p79068.crypto.hash.Whirlpool;


/**
 * The internal block ciphers used in the Whirlpool hash functions.
 */
public final class WhirlpoolCipher extends AbstractCipher implements BlockCipher {
	
	/**
	 * The internal block cipher used in the Whirlpool hash function (preferred).
	 */
	public static final WhirlpoolCipher WHIRLPOOL_CIPHER = new WhirlpoolCipher("Whirlpool Cipher");
	
	/**
	 * The internal block cipher used in the Whirlpool-T hash function.
	 */
	public static final WhirlpoolCipher WHIRLPOOL_T_CIPHER = new WhirlpoolCipher("Whirlpool-T Cipher");
	
	/**
	 * The internal block cipher used in the Whirlpool-0 hash function.
	 */
	public static final WhirlpoolCipher WHIRLPOOL0_CIPHER = new WhirlpoolCipher("Whirlpool-0 Cipher");
	
	
	
	private WhirlpoolCipher(String name) {
		super(name, 64, 64);
	}
	
	
	
	@Override
	protected Cipherer newCiphererUnchecked(byte[] key) {
		if      (this == WHIRLPOOL_CIPHER  ) return Whirlpool.WHIRLPOOL_FUNCTION  .newCipherer(this, key);
		else if (this == WHIRLPOOL_T_CIPHER) return Whirlpool.WHIRLPOOL_T_FUNCTION.newCipherer(this, key);
		else if (this == WHIRLPOOL0_CIPHER ) return Whirlpool.WHIRLPOOL0_FUNCTION .newCipherer(this, key);
		else throw new AssertionError();
	}
	
}
