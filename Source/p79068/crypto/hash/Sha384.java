package p79068.crypto.hash;

import p79068.util.hash.AbstractHashFunction;
import p79068.util.hash.Hasher;


/**
 * The SHA-384 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see AbstractHashFunction
 * @see Sha512
 */
public final class Sha384 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the SHA-384 hash function.
	 */
	public final static Sha384 FUNCTION = new Sha384();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new Sha512Hasher(false));
	}
	
	
	/**
	 * Returns the name of this hash function: {@code "SHA-384"}.
	 * @return {@code "SHA-384"}
	 */
	@Override
	public String getName() {
		return "SHA-384";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: {@code 48} bytes (384 bits).
	 * @return {@code 48}
	 */
	@Override
	public int getHashLength() {
		return 48;
	}
	
	
	/**
	 * Returns the block length of this hash function: {@code 128} bytes (1024 bits).
	 * @return {@code 128}
	 */
	@Override
	public int getBlockLength() {
		return 128;
	}
	
	
	
	private Sha384() {}
	
}