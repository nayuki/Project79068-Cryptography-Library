package p79068.hash;


/**
 * The zero hash function, always returning 8 bits of zeros.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 */
public final class ZeroFunction extends AbstractHashFunction {
	
	/**
	 * The singleton instance of the zero hash function. {@code name = "Zero"}, {@code hashLength = 1}.
	 */
	public final static ZeroFunction FUNCTION = new ZeroFunction();
	
	
	
	private ZeroFunction() {
		super("Zero", 1);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new ZeroHasher(this);
	}
	
}
