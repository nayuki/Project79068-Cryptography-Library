package p79068.crypto.hash;

import p79068.util.hash.AbstractHashFunction;
import p79068.util.hash.AbstractHasher;


/**
 * The Tiger hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 */
public final class Tiger extends AbstractBlockHashFunction {
	
	/**
	 * The singleton instance of the Tiger hash function. {@code name = "Tiger"}, {@code hashLength = 24}, {@code blockLength = 64};
	 */
	public final static Tiger FUNCTION = new Tiger();
	
	
	
	private Tiger() {
		super("Tiger", 24, 64);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public AbstractHasher newHasher() {
		return new BlockHasher(this, new TigerHasher(false));
	}
	
}