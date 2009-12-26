package p79068.crypto.hash;

import p79068.util.hash.HashFunction;


/**
 * The Whirlpool hash function (version 2).
 * <p>Revisions of the Whirlpool function:</p>
 * <ul>
 *  <li>Version 0: The original Whirlpool algorithm (Whirlpool-0)</li>
 *  <li>Version 1: S-box changed (Whirlpool-T)</li>
 *  <li>Version 2: Linear mixing changed (current version, just Whirlpool)</li>
 * </ul>
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 * @see Whirlpool0
 * @see WhirlpoolT
 */
public final class Whirlpool extends AbstractWhirlpool {
	
	/**
	 * The singleton instance of the Whirlpool hash function.
	 */
	public final static Whirlpool FUNCTION = new Whirlpool();
	
	
	
	/**
	 * Returns the name of this hash function: <code>Whirlpool</code>.
	 * @return <code>"Whirlpool"</code>
	 */
	public String getName() {
		return "Whirlpool";
	}
	
	
	
	WhirlpoolParameters getParameters() {
		return PARAMETERS;
	}
	
	
	
	private Whirlpool() {}
	
	
	
	static WhirlpoolParameters PARAMETERS = new WhirlpoolParameters() {
		
		public int getRounds() {
			return 10;
		}
		
		public int[] getSbox() {
			int[] sub = new int[256];
			
			int[] e = {0x1, 0xB, 0x9, 0xC, 0xD, 0x6, 0xF, 0x3, 0xE, 0x8, 0x7, 0x4, 0xA, 0x2, 0x5, 0x0};  // The E mini-box
			int[] r = {0x7, 0xC, 0xB, 0xD, 0xE, 0x4, 0x9, 0xF, 0x6, 0x3, 0x8, 0xA, 0x2, 0x5, 0x1, 0x0};  // The R mini-box
			
			int[] einv = new int[16];  // The inverse of E
			for (int i = 0; i < e.length; i++)
				einv[e[i]] = i;
			
			for (int i = 0; i < sub.length; i++) {
				int left = e[i >>> 4];
				int right = einv[i & 0xF];
				int temp = r[left ^ right];
				sub[i] = (e[left ^ temp] << 4 | einv[right ^ temp]);
			}
			
			return sub;
		}
		
		public int[] getC() {
			return new int[]{0x01, 0x09, 0x02, 0x05, 0x08, 0x01, 0x04, 0x01};
		}
		
	};
	
}