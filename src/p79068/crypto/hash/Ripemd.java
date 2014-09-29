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
