package p79068.util.hash;


/**
 * The Sum-32 checksum function. The hash value is the sum of all the bytes (interpreted as unsigned), modulo 2<sup>32</sup>, serialized in big endian.
 * <p>Instantiability: <em>Singleton</em></p>
 * @see AbstractHashFunction
 */
public class Sum32 extends AbstractHashFunction {
	
	/**
	 * The singleton instance of the Sum-32 hash function.
	 */
	public final static Sum32 FUNCTION = new Sum32();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new Sum32Hasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: {@code "Sum-32"}.
	 */
	@Override
	public String getName() {
		return "Sum-32";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: {@code 4} bytes.
	 */
	@Override
	public int getHashLength() {
		return 4;
	}
	
	
	
	private Sum32() {}
	
}