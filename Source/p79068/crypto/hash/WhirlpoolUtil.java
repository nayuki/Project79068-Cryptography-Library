package p79068.crypto.hash;


final class WhirlpoolUtil {
	
	public static int multiply(int x, int y) {
		if (x == 0 || y == 0)
			return 0;
		return exp[(log[x] + log[y]) % 255];
	}
	
	
	
	/**
	 * The exponential table. <code>exp[i]</code> is equal to <code>0x02</code> to the power of <code>i</code>, in GF(2^8)/0x11D.
	 */
	private static int[] exp;
	
	/**
	 * The logarithm table.
	 */
	private static int[] log;
	
	
	static {
		initExpLogTables();
	}
	
	
	private static void initExpLogTables() {
		exp = new int[255];
		log = new int[256];
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
	
	
}