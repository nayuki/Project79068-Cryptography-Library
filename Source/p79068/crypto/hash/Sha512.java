package p79068.crypto.hash;

import p79068.util.hash.HashFunction;
import p79068.util.hash.Hasher;


/**
 * The SHA-512 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public final class Sha512 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the SHA-512 hash function.
	 */
	public final static Sha512 FUNCTION = new Sha512();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new Sha512Hasher(true));
	}
	
	
	/**
	 * Returns the name of this hash function: {@code "SHA-512"}.
	 * @return {@code "SHA-512"}
	 */
	@Override
	public String getName() {
		return "SHA-512";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: {@code 64} bytes (512 bits).
	 * @return {@code 64}
	 */
	@Override
	public int getHashLength() {
		return 64;
	}
	
	
	/**
	 * Returns the block length of this hash function: {@code 128} bytes (1024 bits).
	 * @return {@code 128}
	 */
	@Override
	public int getBlockLength() {
		return 128;
	}
	
	
	
	private Sha512() {}
	
}