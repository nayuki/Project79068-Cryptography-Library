package p79068.crypto.hash;

import p79068.util.hash.AbstractHashFunction;
import p79068.util.hash.Hasher;


/**
 * The original SHA hash function, also known as SHA-0. It is described in FIPS Publication 180 (1993-05-11).
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see Sha1
 */
public final class Sha extends AbstractBlockHashFunction {
	
	/**
	 * The singleton instance of the SHA hash function. {@code name = "SHA"}, {@code hashLength = 20}, {@code blockLength = 64};
	 */
	public final static Sha FUNCTION = new Sha();
	
	
	
	private Sha() {
		super("SHA", 20, 64);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new Sha1Hasher(false));
	}
	
}