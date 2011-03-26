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
	 * The singleton instance of the SHA-384 hash function. {@code name = "SHA-384"}, {@code hashLength = 48}, {@code blockLength = 128};
	 */
	public final static Sha384 FUNCTION = new Sha384();
	
	
	
	private Sha384() {
		super("SHA-384", 48, 128);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new Sha512Hasher(false));
	}
	
}