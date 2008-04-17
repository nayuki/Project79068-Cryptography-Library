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
	
	
	
	public Hasher newHasher() {
		return new Sha256Hasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: <samp>SHA-224</samp>.
	 */
	public String getName() {
		return "SHA-224";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <samp>28</samp> bytes (224 bits).
	 */
	public int getHashLength() {
		return 28;
	}
	
	
	/**
	 * Returns the block length of this hash function: <samp>64</samp> bytes (512 bits).
	 */
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Sha224() {}
	
}