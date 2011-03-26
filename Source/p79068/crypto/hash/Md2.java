package p79068.crypto.hash;

import p79068.util.hash.AbstractHashFunction;
import p79068.util.hash.Hasher;


/**
 * The MD2 hash function. It is described in RFC 1319.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 */
public final class Md2 extends AbstractBlockHashFunction {
	
	/**
	 * The singleton instance of the MD2 hash function. {@code name = "MD2"}, {@code hashLength = 16}, {@code blockLength = 16};
	 */
	public final static Md2 FUNCTION = new Md2();
	
	
	
	private Md2() {
		super("MD2", 16, 16);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new Md2Hasher());
	}
	
}