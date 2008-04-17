package p79068.crypto.hash;

import p79068.util.hash.HashFunction;
import p79068.util.hash.Hasher;


/**
 * The SHA-1 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 * Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public class Sha1 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the SHA-1 hash function.
	 */
	public final static Sha1 FUNCTION = new Sha1();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	public Hasher newHasher() {
		return new FastSha1Hasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: <samp>SHA-1</samp>.
	 */
	public String getName() {
		return "SHA-1";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <samp>20</samp> bytes (160 bits).
	 */
	public int getHashLength() {
		return 20;
	}
	
	
	/**
	 * Returns the block length of this hash function: <samp>64</samp> bytes (512 bits).
	 */
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Sha1() {}
	
}