package p79068.crypto.hash;

import p79068.util.hash.HashFunction;
import p79068.util.hash.Hasher;


/**
 * The SHA-224 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 * @see Sha256
 */
public final class Sha224 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the SHA-224 hash function.
	 */
	public final static Sha224 FUNCTION = new Sha224();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new Sha256Hasher(false));
	}
	
	
	/**
	 * Returns the name of this hash function: <code>SHA-224</code>.
	 * @return <code>"SHA-224"</code>
	 */
	@Override
	public String getName() {
		return "SHA-224";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>28</code> bytes (224 bits).
	 * @return <code>28</code>
	 */
	@Override
	public int getHashLength() {
		return 28;
	}
	
	
	/**
	 * Returns the block length of this hash function: <code>64</code> bytes (512 bits).
	 * @return <code>64</code>
	 */
	@Override
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Sha224() {}
	
}