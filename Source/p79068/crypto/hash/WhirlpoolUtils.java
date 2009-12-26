package p79068.crypto.hash;


final class WhirlpoolUtils {
	
	/**
	 * The exponential table. <code>exp[i]</code> is equal to <code>0x02</code> to the power of <code>i</code>, in GF(2^8)/0x11D. This table is a permutation of [1, 255].
	 * <p>Note that exp(i + 255) = exp(i), and that exp(i) is in [1, 255].</p>
	 */
	private static final int[] exp;
	
	/**
	 * The logarithm table. <code>log[exp[i]] == i</code>. <code>log[0]</code> is invalid.
	 */
	private static final int[] log;
	
	
	static {
		exp = new int[255];
		log = new int[256];
		initTables();
	}
	
	
	private static void initTables() {
		// Initialize exponentiation table
		int product = 1;
		for (int i = 0; i < exp.length; i++) {
			exp[i] = product;
			product <<= 1;  // Multiply by 0x02, which is a generator
			if ((product & 0x100) != 0)
				product ^= 0x11D;  // Modulo by 0x11D in GF(2)
		}
		
		// Initialize logarithm table
		log[0] = Integer.MIN_VALUE;  // Log of 0 is invalid
		for (int i = 1; i < exp.length; i++)
			log[exp[i]] = i;
	}
	
	
	
	public static int add(int x, int y) {
		if ((x & 0xFF) != x || (y & 0xFF) != y)
			throw new IllegalArgumentException("Input out of range");
		else
			return x ^ y;
	}
	
	
	public static int multiply(int x, int y) {
		if ((x & 0xFF) != x || (y & 0xFF) != y)
			throw new IllegalArgumentException("Input out of range");
		else if (x == 0 || y == 0)
			return 0;
		else
			return exp[(log[x] + log[y]) % 255];
	}
	
}