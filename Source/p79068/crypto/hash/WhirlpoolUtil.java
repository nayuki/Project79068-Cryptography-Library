package p79068.crypto.hash;


final class WhirlpoolUtil {
	
	/**
	 * The exponential table. <code>exp[i]</code> is equal to <code>0x02</code> to the power of <code>i</code>, in GF(2^8)/0x11D.
	 */
	private static final int[] exp;
	
	/**
	 * The logarithm table.
	 */
	private static final int[] log;
	
	
	static {
		exp = new int[255];
		log = new int[256];
		initExpLogTables();
	}
	
	
	private static void initExpLogTables() {
		exp[0] = 1;
		log[0] = Integer.MIN_VALUE;
		log[1] = 0;
		for (int i = 1; i < exp.length; i++) {
			exp[i] = exp[i - 1] << 1;  // Multiply by 0x02, which is a generator
			if ((exp[i] & 0x100) != 0)
				exp[i] ^= 0x11D;  // Modulo by 0x11D in GF(2)
			log[exp[i]] = i;
		}
	}
	
	
	
	public static int add(int x, int y) {
		if ((x & ~0xFF) != 0 || (y & ~0xFF) != 0)
			throw new IllegalArgumentException("Input out of range");
		else
			return x ^ y;
	}
	
	
	public static int multiply(int x, int y) {
		if ((x & ~0xFF) != 0 || (y & ~0xFF) != 0)
			throw new IllegalArgumentException("Input out of range");
		else if (x == 0 || y == 0)
			return 0;
		else
			return exp[(log[x] + log[y]) % 255];
	}
	
}