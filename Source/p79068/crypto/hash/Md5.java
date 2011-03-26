package p79068.crypto.hash;

import p79068.util.hash.AbstractHashFunction;
import p79068.util.hash.Hasher;


/**
 * The MD5 hash function. It is described in RFC 1321.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see AbstractHashFunction
 */
public final class Md5 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the MD5 hash function. {@code name = "MD5"}, {@code hashLength = 16}, {@code blockLength = 64};
	 */
	public final static Md5 FUNCTION = new Md5();
	
	
	
	private Md5() {
		super("MD5", 16);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new Md5Hasher());
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