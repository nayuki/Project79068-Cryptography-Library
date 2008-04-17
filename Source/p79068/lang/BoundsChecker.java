package p79068.lang;


/**
 * Provides convenience methods for checking bounds and throwing <code>IndexOutOfBoundsException</code>s.
 * <p>Instantiability: <em>Not applicable</em></p>
 */
public final class BoundsChecker {
	
	/**
	 * Throws an <code>IndexOutOfBoundsException</code> if the specified index is outside the specified bounds; otherwise returns normally.
	 * <p>An index is out of bounds if at least one of these is true:</p>
	 * <ul>
	 *  <li><code>accessIndex &lt; 0</code></li>
	 *  <li><code>accessIndex &ge; arrayLength</code></li>
	 * </ul>
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	public static void check(int arrayLength, int accessIndex) {
		if (accessIndex < 0 || accessIndex >= arrayLength)
			throw new IndexOutOfBoundsException(String.format("Bounds = [%d,%d), access index = %d", 0, arrayLength, accessIndex));
	}
	
	
	/**
	 * Throws an <code>IndexOutOfBoundsException</code> if the specified range is outside the specified bounds; otherwise returns normally.
	 * <p>An index is out of bounds if at least one of these is true:</p>
	 * <ul>
	 *  <li><code>accessOffset &lt; 0</code></li>
	 *  <li><code>accessOffset &gt; arrayLength</code></li>
	 *  <li><code>accessLength &lt; 0</code></li>
	 *  <li><code>accessOffset+accessLength &gt; arrayLength</code></li>
	 * </ul>
	 * @throws IndexOutOfBoundsException if the range is out of bounds
	 */
	public static void check(int arrayLength, int accessOffset, int accessLength) {
		if (accessOffset < 0 || accessOffset > arrayLength || accessLength < 0 || accessOffset + accessLength > arrayLength)
			throw new IndexOutOfBoundsException(String.format("Bounds = [%d,%d), access range = [%d,%d)", 0, arrayLength, accessOffset, accessOffset + accessLength));
	}
	
	
	/**
	 * Not instantiable.
	 */
	private BoundsChecker() {}
	
}