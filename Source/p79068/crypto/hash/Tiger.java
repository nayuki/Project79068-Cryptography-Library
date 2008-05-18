package p79068.crypto.hash;

import p79068.util.hash.Hasher;
import p79068.util.hash.HashFunction;


/**
 * The Tiger hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public final class Tiger extends BlockHashFunction {
	
	/**
	 * The singleton instance of the Tiger hash function.
	 */
	public final static Tiger FUNCTION = new Tiger();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	public Hasher newHasher() {
		return new TigerHasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: <code>Tiger</code>.
	 */
	public String getName() {
		return "Tiger";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>24</code> bytes (192 bits).
	 */
	public int getHashLength() {
		return 24;
	}
	
	
	/**
	 * Returns the block length of this hash function: <code>64</code> bytes (512 bits).
	 */
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Tiger() {}
	
}