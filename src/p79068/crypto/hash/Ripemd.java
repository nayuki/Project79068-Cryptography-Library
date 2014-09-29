package p79068.crypto.hash;

import p79068.hash.Hasher;


/**
 * The RIPEMD-<var>n</var> hash function family.
 */
public final class Ripemd extends AbstractBlockHashFunction {
	
	/**
	 * The RIPEMD-128 hash function. {@code name = "RIPEMD-128"}, {@code hashLength = 16}, {@code blockLength = 64}.
	 */
	public final static Ripemd RIPEMD128_FUNCTION = new Ripemd("RIPEMD-128", 16);
	
	/**
	 * The RIPEMD-160 hash function. {@code name = "RIPEMD-160"}, {@code hashLength = 20}, {@code blockLength = 64}.
	 */
	public final static Ripemd RIPEMD160_FUNCTION = new Ripemd("RIPEMD-160", 20);
	
	/**
	 * The RIPEMD-256 hash function. {@code name = "RIPEMD-256"}, {@code hashLength = 32}, {@code blockLength = 64}.
	 */
	public final static Ripemd RIPEMD256_FUNCTION = new Ripemd("RIPEMD-256", 32);
	
	/**
	 * The RIPEMD-320 hash function. {@code name = "RIPEMD-320"}, {@code hashLength = 40}, {@code blockLength = 64}.
	 */
	public final static Ripemd RIPEMD320_FUNCTION = new Ripemd("RIPEMD-320", 40);
	
	
	
	private Ripemd(String name, int hashLen) {
		super(name, hashLen, 64);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new RipemdCore(getHashLength()));
	}
	
}
