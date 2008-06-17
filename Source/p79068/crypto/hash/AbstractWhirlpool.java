package p79068.crypto.hash;

import p79068.util.hash.Hasher;


abstract class AbstractWhirlpool extends BlockHashFunction {
	
	protected AbstractWhirlpool() {}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	public Hasher newHasher() {
		return new FastWhirlpoolHasher(this);
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>64</code> bytes (512 bits).
	 * @return <code>64</code>
	 */
	public final int getHashLength() {
		return 64;
	}
	
	
	/**
	 * Returns the block length of this hash function: <code>64</code> bytes (512 bits).
	 * @return <code>64</code>
	 */
	public final int getBlockLength() {
		return 64;
	}
	
	
	
	abstract int getRounds();
	
	
	abstract byte[] getSbox();
	
	
	abstract int[] getC();
	
}