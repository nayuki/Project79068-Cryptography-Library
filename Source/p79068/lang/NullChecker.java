package p79068.lang;


/**
 * Provides a convenience method for checking for {@code null} and throwing {@code NullPointerException}s.
 * <p>Instantiability: <em>Not applicable</em></p>
 */
public final class NullChecker {
	
	/**
	 * Throws a {@code NullPointerException} if the specified object is {@code null}; otherwise returns normally.
	 * @throws NullPointerException if the object is {@code null}
	 */
	public static void check(Object obj) {
		if (obj == null)
			throw new NullPointerException("Non-null object expected");
	}
	
	
	public static void check(Object... obj) {
		for (Object o : obj) {
			if (o == null)
				throw new NullPointerException("Non-null object expected");
		}
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private NullChecker() {}
	
}