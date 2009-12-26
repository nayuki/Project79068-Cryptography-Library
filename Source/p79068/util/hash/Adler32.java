package p79068.util.hash;


/**
 * The Adler-32 checksum function. It is described in RFC 1950.
 * <p>Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public class Adler32 extends HashFunction {
	
	/**
	 * The singleton instance of the Adler-32 hash function.
	 */
	public final static Adler32 FUNCTION = new Adler32();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new Adler32Hasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: <code>Adler-32</code>.
	 */
	@Override
	public String getName() {
		return "Adler-32";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>4</code> bytes.
	 */
	@Override
	public int getHashLength() {
		return 4;
	}
	
	
	
	private Adler32() {}
	
}