package p79068.crypto.hash;

import p79068.util.hash.Hasher;
import p79068.util.hash.HashFunction;


/**
 * The SHA-224 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 * @see Sha256
 */
public class Sha224 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the SHA-224 hash function.
	 */
	public final static Sha224 FUNCTION = new Sha224();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	public Hasher newHasher() {
		return new Sha256Hasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: <code>SHA-224</code>.
	 */
	public String getName() {
		return "SHA-224";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>28</code> bytes (224 bits).
	 */
	public int getHashLength() {
		return 28;
	}
	
	
	/**
	 * Returns the block length of this hash function: <code>64</code> bytes (512 bits).
	 */
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Sha224() {}
	
}