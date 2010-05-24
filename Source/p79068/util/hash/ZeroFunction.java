package p79068.util.hash;


/**
 * The zero hash function, always returning 8 bits of zeros.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public class ZeroFunction extends HashFunction {
	
	/**
	 * The singleton instance of the zero hash function.
	 */
	public final static ZeroFunction FUNCTION = new ZeroFunction();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new ZeroHasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: {@code "Zero"}.
	 */
	@Override
	public String getName() {
		return "Zero";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: {@code 1} byte.
	 */
	@Override
	public int getHashLength() {
		return 1;
	}
	
	
	
	private ZeroFunction() {}
	
}