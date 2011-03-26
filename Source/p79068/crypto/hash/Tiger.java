package p79068.crypto.hash;

import p79068.util.hash.Hasher;


/**
 * The Tiger hash function family.
 */
public final class Tiger extends AbstractBlockHashFunction {
	
	/**
	 * The Tiger hash function. {@code name = "Tiger"}, {@code hashLength = 24}, {@code blockLength = 64}.
	 */
	public final static Tiger TIGER_FUNCTION = new Tiger("Tiger");
	
	
	/**
	 * The Tiger2 hash function. {@code name = "Tiger2"}, {@code hashLength = 24}, {@code blockLength = 64}.
	 */
	public final static Tiger TIGER2_FUNCTION = new Tiger("Tiger2");
	
	
	
	private Tiger(String name) {
		super(name, 24, 64);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		if      (this == TIGER_FUNCTION ) return new BlockHasher(this, new TigerHasher(false));
		else if (this == TIGER2_FUNCTION) return new BlockHasher(this, new TigerHasher(true));
		else throw new AssertionError();
	}
	
}