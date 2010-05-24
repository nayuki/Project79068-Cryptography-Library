package p79068.crypto.hash;

import p79068.util.hash.HashFunction;
import p79068.util.hash.Hasher;


/**
 * The original SHA hash function, also known as SHA-0. It is described in FIPS Publication 180 (1993-05-11).
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 * @see Sha1
 */
public final class Sha extends BlockHashFunction {
	
	/**
	 * The singleton instance of the SHA hash function.
	 */
	public final static Sha FUNCTION = new Sha();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new Sha1Hasher(false));
	}
	
	
	/**
	 * Returns the name of this hash function: {@code "SHA"}.
	 * @return {@code "SHA"}
	 */
	@Override
	public String getName() {
		return "SHA";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: {@code 20} bytes (160 bits).
	 * @return {@code 20}
	 */
	@Override
	public int getHashLength() {
		return 20;
	}
	
	
	/**
	 * Returns the block length of this hash function: {@code 64} bytes (512 bits).
	 * @return {@code 64}
	 */
	@Override
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Sha() {}
	
}