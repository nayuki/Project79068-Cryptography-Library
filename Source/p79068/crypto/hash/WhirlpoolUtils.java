package p79068.crypto.hash;


/**
 * Contains utility methods for performing arithmetic in Whirlpool's field. The field is GF(2<sup>8</sup>)/0x11D.
 */
final class WhirlpoolUtils {
	
	/**
	 * The exponentiation table, where <code>exp[i]</code> is equal to <code>0x02</code> to the power of <code>i</code>, in GF(2<sup>8</sup>)/0x11D. The table is a permutation of [1, 255].
	 * <p>Note that for all i, exp(i + 255) = exp(i). Also, for all i, exp(i) is in [1, 255].</p>
	 */
	private static final int[] exp;
	
	/**
	 * The logarithm table. For all i, log(exp(i)) = i mod 255. Indices 1 through 255 (inclusive) is a permutation of [1, 255]. log(0) is invalid.
	 */
	private static final int[] log;
	
	
	static {
		exp = new int[255];
		log = new int[256];
		initTables();
	}
	
	
	
	/**
	 * Initializes the exponentiation and logarithm tables.
	 */
	private static void initTables() {
		// Initialize exponentiation (exp) table
		int product = 1;
		for (int i = 0; i < exp.length; i++) {
			exp[i] = product;
			product <<= 1;  // Multiply by 0x02, which is a generator
			if ((product & 0x100) != 0)
				product ^= 0x11D;  // Modulo by 0x11D in GF(2)
		}
		
		// Initialize logarithm (log) table
		log[0] = Integer.MIN_VALUE;  // Log of 0 is invalid
		for (int i = 1; i < exp.length; i++)
			log[exp[i]] = i;
	}
	
	
	
	/**
	 * Returns the sum of the specified elements modulo GF(2<sup>8</sup>)/0x11D. The result is in [0, 255].
	 * @param x a summand
	 * @param y a summand
	 * @return the sum of the specified elements, in the field
	 * @throws IllegalArgumentException if <code>x</code> or <code>y</code> is not in [0, 255]
	 */
	public static int add(int x, int y) {
		if ((x & 0xFF) != x || (y & 0xFF) != y)
			throw new IllegalArgumentException("Input out of range");
		else
			return x ^ y;
	}
	
	
	/**
	 * Returns the product of the specified elements modulo GF(2<sup>8</sup>)/0x11D. The result is in [0, 255].
	 * @param x a multiplicand
	 * @param y a multiplicand
	 * @return the product of the specified elements, in the field
	 * @throws IllegalArgumentException if <code>x</code> or <code>y</code> is not in [0, 255]
	 */
	public static int multiply(int x, int y) {
		if ((x & 0xFF) != x || (y & 0xFF) != y)
			throw new IllegalArgumentException("Input out of range");
		else if (x == 0 || y == 0)
			return 0;
		else
			return exp[(log[x] + log[y]) % 255];
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private WhirlpoolUtils() {}
	
}