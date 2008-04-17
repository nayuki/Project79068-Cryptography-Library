package p79068.util.hash;


/**
 * The Adler-32 checksum function. It is described in RFC 1950.
 * <p>Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public class Adler32 extends HashFunction {
	
	/** The singleton instance of the Adler-32 hash function. */
	public final static Adler32 FUNCTION = new Adler32();
	
	
	public Hasher newHasher() {
		return new Adler32Hasher(this);
	}
	
	
	/** Returns the name of this hash function: <samp>Adler-32</samp>. */
	public String getName() {
		return "Adler-32";
	}
	
	/** Returns the length of hash values produced by this hash function: <samp>4</samp> bytes. */
	public int getHashLength() {
		return 4;
	}
	
	
	private Adler32() {}
}