package p79068.crypto;


public final class Zeroizer {
	
	/**
	 * Sets all elements in the specified array to zero.
	 * @param array the array whose contents to clear
	 */
	public static void clear(byte[] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = 0;
	}
	
	
	/**
	 * Sets all elements in the specified array to zero.
	 * @param array the array whose contents to clear
	 */
	public static void clear(short[] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = 0;
	}
	
	
	/**
	 * Sets all elements in the specified array to zero.
	 * @param array the array whose contents to clear
	 */
	public static void clear(int[] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = 0;
	}
	
	
	/**
	 * Sets all elements in the specified array to zero.
	 * @param array the array whose contents to clear
	 */
	public static void clear(long[] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = 0;
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private Zeroizer() {}
	
}