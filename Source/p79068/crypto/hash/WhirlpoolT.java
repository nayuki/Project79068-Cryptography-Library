package p79068.crypto.hash;

import p79068.util.hash.AbstractHashFunction;


/**
 * The Whirlpool-T hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see AbstractHashFunction
 * @see Whirlpool0
 * @see Whirlpool
 */
public final class WhirlpoolT extends AbstractWhirlpool {
	
	/**
	 * The singleton instance of the Whirlpool-T hash function. {@code name = "Whirlpool-T"}, {@code hashLength = 64}, {@code blockLength = 64};
	 */
	public final static WhirlpoolT FUNCTION = new WhirlpoolT();
	
	
	
	private WhirlpoolT() {
		super("Whirlpool-T");
	}
	
	
	
	@Override
	WhirlpoolParameters getParameters() {
		return PARAMETERS;
	}
	
	
	
	static WhirlpoolParameters PARAMETERS = new WhirlpoolParameters() {
		
		@Override
		public int getRounds() {
			return 10;
		}
		
		// Identical to the one in Whirlpool
		@Override
		public int[] getSbox() {
			return Whirlpool.PARAMETERS.getSbox();
		}
		
		// Identical to the one in Whirlpool-0
		@Override
		public int[] getC() {
			return Whirlpool0.PARAMETERS.getC();
		}
		
		// Identical to the one in Whirlpool-0
		@Override
		public int[] getCInverse() {
			return Whirlpool0.PARAMETERS.getCInverse();
		}
		
	};
	
}