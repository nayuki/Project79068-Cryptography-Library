package p79068.lang;


/**
 * Provides a convenience method for checking for <code>null</code> and throwing <code>NullPointerException</code>s.
 * <p>Instantiability: <em>Not applicable</em></p>
 */
public final class NullChecker {
	
	/**
	 * Throws a <code>NullPointerException</code> if the specified object is <code>null</code>; otherwise returns normally.
	 * @throws NullPointerException if the object is <code>null</code>
	 */
	public static void check(Object obj) {
		if (obj == null)
			throw new NullPointerException();
	}
	
	
	/**
	 * Not instantiable.
	 */
	private NullChecker() {}
	
}