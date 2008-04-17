package p79068.util.hash;


/**
 * The null hash function, always returning 8 bits of zeros.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public class NullFunction extends HashFunction {
	
	/**
	 * The singleton instance of the null hash function.
	 */
	public final static NullFunction FUNCTION = new NullFunction();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	public Hasher newHasher() {
		return new NullHasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: <code>Null</code>.
	 */
	public String getName() {
		return "Null";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>1</code> byte.
	 */
	public int getHashLength() {
		return 1;
	}
	
	
	
	private NullFunction() {}
	
}