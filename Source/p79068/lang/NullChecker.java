package p79068.lang;


/**
Provides a convenience method for checking for <code>null</code> and throwing NullPointerExceptions.
<p>Instantiability: <em>Not applicable</em></p>
*/
public final class NullChecker {
	
	/** Throws a NullPointerException if the specified object is null; otherwise does nothing. */
	public static void check(Object obj) {
		if (obj == null)
			throw new NullPointerException();
	}
	
	
	private NullChecker() {}
}