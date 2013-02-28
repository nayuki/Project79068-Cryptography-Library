package p79068.hash;


/**
 * The Sum-32 checksum function. The hash value is the sum of all the bytes (interpreted as unsigned), modulo 2<sup>32</sup>, serialized in big endian.
 * <p>Instantiability: <em>Singleton</em></p>
 */
public final class Sum32 extends AbstractHashFunction {
	
	/**
	 * The singleton instance of the Sum-32 hash function. {@code name = "Sum-32"}, {@code hashLength = 4}.
	 */
	public final static Sum32 FUNCTION = new Sum32();
	
	
	
	private Sum32() {
		super("Sum-32", 4);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new Sum32Hasher(this);
	}
	
}
