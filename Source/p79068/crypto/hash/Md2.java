package p79068.crypto.hash;

import p79068.util.hash.HashFunction;
import p79068.util.hash.Hasher;


/**
 * The MD2 hash function. It is described in RFC 1319.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public final class Md2 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the MD2 hash function.
	 */
	public final static Md2 FUNCTION = new Md2();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new Md2Hasher());
	}
	
	
	/**
	 * Returns the name of this hash function: <code>MD2</code>.
	 * @return <code>"MD2"</code>
	 */
	@Override
	public String getName() {
		return "MD2";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>16</code> bytes (128 bits).
	 * @return <code>16</code>
	 */
	@Override
	public int getHashLength() {
		return 16;
	}
	
	
	/**
	 * Returns the block length of this hash function: <code>64</code> bytes (512 bits).
	 * @return <code>64</code>
	 */
	@Override
	public int getBlockLength() {
		return 16;
	}
	
	
	
	private Md2() {}
	
}