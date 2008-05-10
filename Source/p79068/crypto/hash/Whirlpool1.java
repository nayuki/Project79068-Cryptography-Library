package p79068.crypto.hash;

import p79068.util.hash.Hasher;
import p79068.util.hash.HashFunction;


/**
 * The Whirlpool-1 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 * @see Whirlpool0
 * @see Whirlpool
 */
public class Whirlpool1 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the Whirlpool-1 hash function.
	 */
	public final static Whirlpool1 FUNCTION = new Whirlpool1();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	public Hasher newHasher() {
		return new WhirlpoolHasher(this, SUB, RCON, MUL);
	}
	
	
	/**
	 * Returns the name of this hash function: <code>Whirlpool-1</code>.
	 */
	public String getName() {
		return "Whirlpool-1";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>64</code> bytes (512 bits).
	 */
	public int getHashLength() {
		return 64;
	}
	
	
	/**
	 * Returns the block length of this hash function: <code>64</code> bytes (512 bits).
	 */
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Whirlpool1() {}
	
	
	
	private static final int ROUNDS = 10;
	
	private static final byte[] SUB;
	
	private static final byte[][] MUL;
	
	private static final byte[][] RCON;
	
	
	static {
		if (ROUNDS < 1 || ROUNDS > 32)
			throw new AssertionError("Invalid number of rounds");
		SUB = makeSub();
		RCON = WhirlpoolUtils.makeRoundConstants(ROUNDS, SUB);
		int[] c = {0x01, 0x05, 0x09, 0x08, 0x05, 0x01, 0x03, 0x01};  // Identical to the one in Whirlpool-0
		MUL = WhirlpoolUtils.makeMultiplicationTable(c);
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