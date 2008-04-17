package p79068.crypto.hash;

import p79068.util.hash.Hasher;
import p79068.util.hash.HashFunction;


/**
 * The MD4 hash function. It is described in RFC 1320.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public class Md4 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the MD4 hash function.
	 */
	public final static Md4 FUNCTION = new Md4();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	public Hasher newHasher() {
		return new FastMd4Hasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: <samp>MD4</samp>.
	 */
	public String getName() {
		return "MD4";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <samp>16</samp> bytes (128 bits).
	 */
	public int getHashLength() {
		return 16;
	}
	
	
	/**
	 * Returns the block length of this hash function: <samp>64</samp> bytes (512 bits).
	 */
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Md4() {}
	
}