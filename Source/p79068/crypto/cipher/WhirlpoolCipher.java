package p79068.crypto.cipher;


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
	
	
	
	public Cipherer newCipherer(byte[] key) {
		if (key.length != 64)
			throw new IllegalArgumentException();
		return new WhirlpoolCipherer(this, SUB, SUB_INV, MUL, MUL_INV, RCON, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: <samp>Whirlpool Cipher</samp>.
	 * @return <code>"Whirlpool Cipher"</code>
	 */
	public String getName() {
		return "Whirlpool Cipher";
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm: <samp>64</samp> bytes (512 bits).
	 * @return <code>64</code>
	 */
	public int getKeyLength() {
		return 64;
	}
	
	
	/**
	 * Returns the block length of this cipher algorithm: <samp>64</samp> bytes (512 bits).
	 * @return <code>64</code>
	 */
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private WhirlpoolCipher() {}
	
	
	
	private static final int ROUNDS = 10;
	
	private static final byte[] SUB;
	private static final byte[] SUB_INV;
	
	private static final byte[][] MUL;
	private static final byte[][] MUL_INV;
	
	private static final byte[][] RCON;
	
	
	static {
		if (ROUNDS < 1 || ROUNDS > 32)
			throw new AssertionError("Invalid number of rounds");
		
		SUB = makeSub();
		SUB_INV = WhirlpoolUtils.makeInverseSbox(SUB);
		
		RCON = WhirlpoolUtils.makeRoundConstants(ROUNDS, SUB);
		
		int[] c    = {0x01, 0x09, 0x02, 0x05, 0x08, 0x01, 0x04, 0x01};
		int[] cinv = {0x04, 0x3E, 0xCB, 0xC2, 0xC2, 0xA4, 0x0E, 0xAF};
		MUL     = WhirlpoolUtils.makeMultiplicationTable(c);
		MUL_INV = WhirlpoolUtils.makeMultiplicationTable(cinv);
	}
	
	
	// Identical to the one in Whirlpool-T
	private static byte[] makeSub() {
		byte[] sub = new byte[256];
		
		int[] e = {0x1, 0xB, 0x9, 0xC, 0xD, 0x6, 0xF, 0x3, 0xE, 0x8, 0x7, 0x4, 0xA, 0x2, 0x5, 0x0};  // The E mini-box
		int[] r = {0x7, 0xC, 0xB, 0xD, 0xE, 0x4, 0x9, 0xF, 0x6, 0x3, 0x8, 0xA, 0x2, 0x5, 0x1, 0x0};  // The R mini-box
		
		int[] einv = new int[16];  // The inverse of E
		for (int i = 0; i < e.length; i++)
			einv[e[i]] = i;
		
		for (int i = 0; i < sub.length; i++) {
			int left = e[i >>> 4];
			int right = einv[i & 0xF];
			int temp = r[left ^ right];
			sub[i] = (byte)(e[left ^ temp] << 4 | einv[right ^ temp]);
		}
		
		return sub;
	}
	
}