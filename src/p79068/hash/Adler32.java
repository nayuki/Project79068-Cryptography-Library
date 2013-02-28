package p79068.hash;


/**
 * The Adler-32 checksum function. It is described in RFC 1950.
 * <p>Instantiability: <em>Singleton</em></p>
 */
public final class Adler32 extends AbstractHashFunction {
	
	/**
	 * The singleton instance of the Adler-32 hash function. {@code name = "Adler-32"}, {@code hashLength = 4}.
	 */
	public final static Adler32 FUNCTION = new Adler32();
	
	
	
	private Adler32() {
		super("Adler-32", 4);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new Adler32Hasher(this);
	}
	
}
