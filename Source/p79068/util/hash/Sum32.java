package p79068.util.hash;


/**
 * The Sum-32 checksum function. The hash value is the sum of all the bytes (interpreted as unsigned), modulo 2<sup>32</sup>, serialized in big endian.
 * <p>Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public class Sum32 extends HashFunction {
	
	/**
	 * The singleton instance of the Sum-32 hash function.
	 */
	public final static Sum32 FUNCTION = new Sum32();
	
	
	
	public Hasher newHasher() {
		return new Sum32Hasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: <samp>Sum-32</samp>.
	 */
	public String getName() {
		return "Sum-32";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <samp>4</samp> bytes.
	 */
	public int getHashLength() {
		return 4;
	}
	
	
	
	private Sum32() {}
	
}