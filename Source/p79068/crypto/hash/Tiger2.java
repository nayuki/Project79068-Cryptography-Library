package p79068.crypto.hash;

import p79068.util.hash.HashFunction;
import p79068.util.hash.Hasher;


/**
 * The Tiger2 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 * @see Tiger2
 */
public final class Tiger2 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the Tiger2 hash function.
	 */
	public final static Tiger2 FUNCTION = new Tiger2();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new TigerHasher(true));
	}
	
	
	/**
	 * Returns the name of this hash function: {@code Tiger2}.
	 * @return {@code "Tiger2"}
	 */
	@Override
	public String getName() {
		return "Tiger2";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: {@code 24} bytes (192 bits).
	 * @return {@code 24}
	 */
	@Override
	public int getHashLength() {
		return 24;
	}
	
	
	/**
	 * Returns the block length of this hash function: {@code 64} bytes (512 bits).
	 * @return {@code 64}
	 */
	@Override
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Tiger2() {}
	
}