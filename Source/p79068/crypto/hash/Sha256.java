package p79068.crypto.hash;

import p79068.util.hash.Hasher;
import p79068.util.hash.HashFunction;


/**
 * The SHA-256 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public class Sha256 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the SHA-256 hash function.
	 */
	public final static Sha256 FUNCTION = new Sha256();
	
	
	
	public Hasher newHasher() {
		return new Sha256Hasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: <samp>SHA-256</samp>.
	 */
	public String getName() {
		return "SHA-256";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <samp>32</samp> bytes (256 bits).
	 */
	public int getHashLength() {
		return 32;
	}
	
	
	/**
	 * Returns the block length of this hash function: <samp>64</samp> bytes (512 bits).
	 */
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Sha256() {}
	
}