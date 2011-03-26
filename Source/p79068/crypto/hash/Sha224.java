package p79068.crypto.hash;

import p79068.util.hash.AbstractHashFunction;
import p79068.util.hash.Hasher;


/**
 * The SHA-224 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see AbstractHashFunction
 * @see Sha256
 */
public final class Sha224 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the SHA-224 hash function. {@code name = "SHA-224"}, {@code hashLength = 28}, {@code blockLength = 64};
	 */
	public final static Sha224 FUNCTION = new Sha224();
	
	
	
	private Sha224() {
		super("SHA-224", 28, 64);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new Sha256Hasher(false));
	}
	
}