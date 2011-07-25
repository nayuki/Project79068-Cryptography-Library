package p79068.crypto;

import java.util.Arrays;


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
	
	
	public static byte[][] clear(byte[][] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = clear(array[i]);
		return null;
	}
	
	
	public static short[][] clear(short[][] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = clear(array[i]);
		return null;
	}
	
	
	public static int[][] clear(int[][] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = clear(array[i]);
		return null;
	}
	
	
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
