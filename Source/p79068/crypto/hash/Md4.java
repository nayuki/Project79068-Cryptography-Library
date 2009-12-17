package p79068.crypto.hash;

import p79068.util.hash.Hasher;
import p79068.util.hash.HashFunction;


/**
 * The MD4 hash function. It is described in RFC 1320.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public final class Md4 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the MD4 hash function.
	 */
	public final static Md4 FUNCTION = new Md4();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	public Hasher newHasher() {
		return new BlockHasher(this, new FastMd4Hasher());
	}
	
	
	/**
	 * Returns the name of this hash function: <code>MD4</code>.
	 * @return <code>"MD4"</code>
	 */
	public String getName() {
		return "MD4";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>16</code> bytes (128 bits).
	 * @return <code>16</code>
	 */
	public int getHashLength() {
		return 16;
	}
	
	
	/**
	 * Returns the block length of this hash function: <code>64</code> bytes (512 bits).
	 * @return <code>64</code>
	 */
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Md4() {}
	
}