package p79068.crypto.hash;

import p79068.util.hash.AbstractHashFunction;
import p79068.util.hash.Hasher;


/**
 * The SHA-512 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see AbstractHashFunction
 */
public final class Sha512 extends AbstractBlockHashFunction {
	
	/**
	 * The singleton instance of the SHA-512 hash function. {@code name = "SHA-512"}, {@code hashLength = 64}, {@code blockLength = 128};
	 */
	public final static Sha512 FUNCTION = new Sha512();
	
	
	
	private Sha512() {
		super("SHA-512", 64, 128);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new Sha512Hasher(true));
	}
	
}