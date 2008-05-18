package p79068.crypto.hash;

import p79068.util.hash.HashFunction;


/**
 * The Whirlpool-T hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 * @see Whirlpool0
 * @see Whirlpool
 */
public class WhirlpoolT extends AbstractWhirlpool {
	
	/**
	 * The singleton instance of the Whirlpool-1 hash function.
	 */
	public final static WhirlpoolT FUNCTION = new WhirlpoolT();
	
	
	
	/**
	 * Returns the name of this hash function: <code>Whirlpool-T</code>.
	 */
	public String getName() {
		return "Whirlpool-T";
	}
	
	
	private WhirlpoolT() {}
	
	
	
	int getRounds() {
		return ROUNDS;
	}
	
	
	byte[] getSbox() {
		return SUB;
	}
	
	
	int[] getC() {
		return C;
	}
	
	
	
	private static final int ROUNDS = 10;
	
	private static final byte[] SUB;
	
	private static final int[] C;
	
	
	static {
		if (ROUNDS < 1 || ROUNDS > 32)
			throw new AssertionError("Invalid number of rounds");
		SUB = makeSub();
		C = new int[]{0x01, 0x05, 0x09, 0x08, 0x05, 0x01, 0x03, 0x01};  // Identical to the one in Whirlpool-0
	}
	
	
	// Identical to the one in Whirlpool
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