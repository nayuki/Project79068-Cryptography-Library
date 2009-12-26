package p79068.crypto.hash;


abstract class WhirlpoolParameters {
	
	/**
	 * Returns the number of rounds. Must be in the range [1, 32].
	 * @return the number of rounds
	 */
	public abstract int getRounds();
	
	
	public abstract int[] getSbox();
	
	
	public abstract int[] getC();
	
	
	public abstract int[] getCInverse();
	
}