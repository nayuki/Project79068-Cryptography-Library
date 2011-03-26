package p79068.crypto.hash;

import p79068.util.hash.AbstractHashFunction;
import p79068.util.hash.Hasher;


/**
 * The Tiger2 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see AbstractHashFunction
 * @see Tiger2
 */
public final class Tiger2 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the Tiger2 hash function. {@code name = "Tiger2"}, {@code hashLength = 24}, {@code blockLength = 64};
	 */
	public final static Tiger2 FUNCTION = new Tiger2();
	
	
	
	private Tiger2() {
		super("Tiger2", 24, 64);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new TigerHasher(true));
	}
	
}