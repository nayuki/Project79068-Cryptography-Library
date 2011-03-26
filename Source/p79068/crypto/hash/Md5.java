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
	 * The singleton instance of the MD5 hash function.
	 */
	public final static Md5 FUNCTION = new Md5();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new Md5Hasher());
	}
	
	
	/**
	 * Returns the name of this hash function: {@code "MD5"}.
	 * @return {@code "MD5"}
	 */
	@Override
	public String getName() {
		return "MD5";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: {@code 16} bytes (128 bits).
	 * @return {@code 16}
	 */
	@Override
	public int getHashLength() {
		return 16;
	}
	
	
	/**
	 * Returns the block length of this hash function: {@code 64} bytes (512 bits).
	 * @return {@code 64}
	 */
	@Override
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Md5() {}
	
}