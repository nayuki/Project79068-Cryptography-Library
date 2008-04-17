package p79068.crypto.hash;

import p79068.util.hash.Hasher;
import p79068.util.hash.HashFunction;


/**
 * The MD2 hash function. It is described in RFC 1319.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public class Md2 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the MD2 hash function.
	 */
	public final static Md2 FUNCTION = new Md2();
	
	
	
	public Hasher newHasher() {
		return new Md2Hasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: <samp>MD2</samp>.
	 */
	public String getName() {
		return "MD2";
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
		return 16;
	}
	
	
	
	private Md2() {}
	
}