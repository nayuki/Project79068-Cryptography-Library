package p79068.lang;


/**
 * Provides convenience methods for checking bounds and throwing IndexOutOfBoundsExceptions.
 * <p>Instantiability: <em>Not applicable</em></p>
 */
public final class BoundsChecker {
	
	/**
	 * Throws an IndexOutOfBoundsException if the specified index is outside the specified bounds; otherwise does nothing.
	 * <p>An index is out of bounds if:</p>
	 * <ul><li><code>accessindex &lt; 0</code></li>
	 *  <li><code>accessindex &ge; arraylength</code></li></ul>
	 */
	public static void check(int arrayLength, int accessIndex) {
		if (accessIndex < 0 || accessIndex >= arrayLength)
			throw new IndexOutOfBoundsException(String.format("Bounds = [%d,%d), access index = %d", 0, arrayLength, accessIndex));
	}
	
	/**
	 * Throws an IndexOutOfBoundsException if the specified range is outside the specified bounds; otherwise does nothing.
	 * <p>An index is out of bounds if:</p>
	 * <ul>
	 *  <li><code>accessoff &lt; 0</code></li>
	 *  <li><code>accessoff &gt; arraylength</code></li>
	 *  <li><code>accesslen &lt; 0</code></li>
	 *  <li><code>accessoff+accesslen &gt; arraylength</code></li>
	 * </ul>
	 */
	public static void check(int arrayLength, int accessOff, int accessLen) {
		if (accessOff < 0 || accessOff > arrayLength || accessLen < 0 || accessOff + accessLen > arrayLength)
			throw new IndexOutOfBoundsException(String.format("Bounds = [%d,%d), access range = [%d,%d)", 0, arrayLength, accessOff, accessOff + accessLen));
	}
	
	
	private BoundsChecker() {}
	
}