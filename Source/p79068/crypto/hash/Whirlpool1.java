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
		return new WhirlpoolHasher(this);
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
	
}