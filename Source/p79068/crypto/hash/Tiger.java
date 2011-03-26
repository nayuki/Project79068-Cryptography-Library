package p79068.crypto.hash;

import p79068.util.hash.AbstractHashFunction;
import p79068.util.hash.Hasher;


/**
 * The Tiger hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see AbstractHashFunction
 */
public final class Tiger extends BlockHashFunction {
	
	/**
	 * The singleton instance of the Tiger hash function. {@code name = "Tiger"}, {@code hashLength = 24}, {@code blockLength = 64};
	 */
	public final static Tiger FUNCTION = new Tiger();
	
	
	
	private Tiger() {
		super("Tiger", 24);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new TigerHasher(false));
	}
	
	
	/**
	 * Returns the block length of this hash function: {@code 64} bytes (512 bits).
	 * @return {@code 64}
	 */
	@Override
	public int getBlockLength() {
		return 64;
	}
	
}