package p79068.crypto.hash;

import p79068.util.hash.Hasher;
import p79068.util.hash.HashFunction;


/**
 * The original SHA hash function, also known as SHA-0. It is described in FIPS Publication 180 (1993-05-11).
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 * @see Sha1
 */
public class Sha extends BlockHashFunction {
	
	/**
	 * The singleton instance of the SHA hash function.
	 */
	public final static Sha FUNCTION = new Sha();
	
	
	
	public Hasher newHasher() {
		return new Sha1Hasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: <samp>SHA</samp>.
	 */
	public String getName() {
		return "SHA";
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
	
	
	
	private Sha() {}
	
}