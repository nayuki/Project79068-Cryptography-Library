package p79068.crypto.hash;

import p79068.util.hash.HashFunction;
import p79068.util.hash.Hasher;


/**
 * The Tiger2 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 * @see Tiger2
 */
public final class Tiger2 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the Tiger2 hash function.
	 */
	public final static Tiger2 FUNCTION = new Tiger2();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	public Hasher newHasher() {
		return new BlockHasher(this, new TigerHasher(true));
	}
	
	
	/**
	 * Returns the name of this hash function: <code>Tiger2</code>.
	 * @return <code>"Tiger2"</code>
	 */
	public String getName() {
		return "Tiger2";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>24</code> bytes (192 bits).
	 * @return <code>24</code>
	 */
	public int getHashLength() {
		return 24;
	}
	
	
	/**
	 * Returns the block length of this hash function: <code>64</code> bytes (512 bits).
	 * @return <code>64</code>
	 */
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Tiger2() {}
	
}