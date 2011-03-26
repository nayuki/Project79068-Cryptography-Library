package p79068.crypto.hash;

import p79068.util.hash.AbstractHashFunction;
import p79068.util.hash.Hasher;


/**
 * The MD4 hash function. It is described in RFC 1320.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 */
public final class Md4 extends AbstractBlockHashFunction {
	
	/**
	 * The singleton instance of the MD4 hash function. {@code name = "MD4"}, {@code hashLength = 16}, {@code blockLength = 64};
	 */
	public final static Md4 FUNCTION = new Md4();
	
	
	
	private Md4() {
		super("MD4", 16, 64);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new FastMd4Hasher());
	}
	
}