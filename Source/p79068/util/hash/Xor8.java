package p79068.util.hash;


/**
 * The XOR checksum function. The hash value is the XOR of all the bytes.
 * <p>Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public class Xor8 extends HashFunction {
	
	/**
	 * The singleton instance of the XOR hash function.
	 */
	public final static Xor8 FUNCTION = new Xor8();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new Xor8Hasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: {@code "XOR-8"}.
	 */
	@Override
	public String getName() {
		return "XOR-8";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: {@code 1} byte.
	 */
	@Override
	public int getHashLength() {
		return 1;
	}
	
}