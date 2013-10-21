package p79068.crypto;

import java.util.Arrays;


/**
 * A utility class for clearing arrays. It is intended to be used like this:
 * <pre>byte[] b = (...);
 *(... do work using b ...)
 *b = Zeroizer.clear(b);  // Sets b to null</pre>
 */
public final class Zeroizer {
	
	/**
	 * Sets all elements in the specified array to zero, and returns {@code null}.
	 * @param array the array whose contents to clear
	 * @return {@code null}
	 */
	public static byte[] clear(byte[] array) {
		Arrays.fill(array, (byte)0);
		return null;
	}
	
	
	/**
	 * Sets all elements in the specified array to zero, and returns {@code null}.
	 * @param array the array whose contents to clear
	 * @return {@code null}
	 */
	public static short[] clear(short[] array) {
		Arrays.fill(array, (short)0);
		return null;
	}
	
	
	/**
	 * Sets all elements in the specified array to zero, and returns {@code null}.
	 * @param array the array whose contents to clear
	 * @return {@code null}
	 */
	public static int[] clear(int[] array) {
		Arrays.fill(array, 0);
		return null;
	}
	
	
	/**
	 * Sets all elements in the specified array to zero, and returns {@code null}.
	 * @param array the array whose contents to clear
	 * @return {@code null}
	 */
	public static long[] clear(long[] array) {
		Arrays.fill(array, 0);
		return null;
	}
	
	
	/**
	 * Sets all subelements in the specified array to zero, sets the elements to {@code null}, and returns {@code null}.
	 * @param array the array whose contents to clear
	 * @return {@code null}
	 */
	public static byte[][] clear(byte[][] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = clear(array[i]);
		return null;
	}
	
	
	/**
	 * Sets all subelements in the specified array to zero, sets the elements to {@code null}, and returns {@code null}.
	 * @param array the array whose contents to clear
	 * @return {@code null}
	 */
	public static short[][] clear(short[][] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = clear(array[i]);
		return null;
	}
	
	
	/**
	 * Sets all subelements in the specified array to zero, sets the elements to {@code null}, and returns {@code null}.
	 * @param array the array whose contents to clear
	 * @return {@code null}
	 */
	public static int[][] clear(int[][] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = clear(array[i]);
		return null;
	}
	
	
	/**
	 * Sets all subelements in the specified array to zero, sets the elements to {@code null}, and returns {@code null}.
	 * @param array the array whose contents to clear
	 * @return {@code null}
	 */
	public static long[][] clear(long[][] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = clear(array[i]);
		return null;
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private Zeroizer() {}
	
}
