package p79068.crypto.hash;

import p79068.util.hash.AbstractHashFunction;
import p79068.util.hash.Hasher;


/**
 * The MD4 hash function. It is described in RFC 1320.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see AbstractHashFunction
 */
public final class Md4 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the MD4 hash function. {@code name = "MD4"}, {@code hashLength = 16}, {@code blockLength = 64};
	 */
	public final static Md4 FUNCTION = new Md4();
	
	
	
	private Md4() {
		super("MD4", 16);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new FastMd4Hasher());
	}
	
	
	/**
	 * Returns the block length of this hash function: {@code 64} bytes (512 bits).
	 * @return {@code 64}
	 */
	@Override
	public int getBlockLength() {
		return 64;
	}
	
}