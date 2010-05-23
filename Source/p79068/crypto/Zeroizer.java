package p79068.crypto;

import java.util.Arrays;


public final class Zeroizer {
	
	/**
	 * Sets all elements in the specified array to zero.
	 * @param array the array whose contents to clear
	 */
	public static void clear(byte[] array) {
		Arrays.fill(array, (byte)0);
	}
	
	
	/**
	 * Sets all elements in the specified array to zero.
	 * @param array the array whose contents to clear
	 */
	public static void clear(short[] array) {
		Arrays.fill(array, (short)0);
	}
	
	
	/**
	 * Sets all elements in the specified array to zero.
	 * @param array the array whose contents to clear
	 */
	public static void clear(int[] array) {
		Arrays.fill(array, 0);
	}
	
	
	/**
	 * Sets all elements in the specified array to zero.
	 * @param array the array whose contents to clear
	 */
	public static void clear(long[] array) {
		Arrays.fill(array, 0);
	}
	
	
	public static void clear(byte[][] array) {
		for (int i = 0; i < array.length; i++) {
			clear(array[i]);
			array[i] = null;
		}
	}
	
	
	public static void clear(short[][] array) {
		for (int i = 0; i < array.length; i++) {
			clear(array[i]);
			array[i] = null;
		}
	}
	
	
	public static void clear(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			clear(array[i]);
			array[i] = null;
		}
	}
	
	
	public static void clear(long[][] array) {
		for (int i = 0; i < array.length; i++) {
			clear(array[i]);
			array[i] = null;
		}
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private Zeroizer() {}
	
}