package p79068.crypto.cipher;


final class RijndaelUtils {
	
	private static final byte[] SBOX;
	private static final byte[] SBOX_INVERSE;
	
	private static final int[] exp;
	private static final int[] log;
	
	
	
	static {
		// Initialize exponentiation table
		exp = new int[255];
		int product = 1;
		for (int i = 0; i < exp.length; i++) {
			exp[i] = product;
			product ^= product << 1;  // Multiply by 0x03, which is a generator
			if ((product & 0x100) != 0)
				product ^= 0x11B;  // Modulo by 0x11B in GF(2)
		}
		
		// Initialize logarithm (log) table
		log = new int[256];
		log[0] = Integer.MIN_VALUE;  // Log of 0 is invalid
		for (int i = 1; i < exp.length; i++)
			log[exp[i]] = i;
		
		// Initialize the S-box and inverse
		SBOX = new byte[256];
		SBOX_INVERSE = new byte[256];
		for (int i = 0; i < 256; i++) {
			int tp = reciprocal(i);
			int s = (tp ^ (tp << 4 | tp >>> 4) ^ (tp << 3 | tp >>> 5) ^ (tp << 2 | tp >>> 6) ^ (tp << 1 | tp >>> 7) ^ 0x63) & 0xFF;
			SBOX[i] = (byte)s;
			SBOX_INVERSE[s] = (byte)i;
		}
	}
	
	
	public static byte[] getSbox() {
		return SBOX.clone();
	}
	
	
	public static byte[] getSboxInverse() {
		return SBOX_INVERSE.clone();
	}
	
	
	public static int multiply(int x, int y) {
		if ((x & 0xFF) != x || (y & 0xFF) != y)
			throw new IllegalArgumentException("Input out of range");
		if (x == 0 || y == 0)
			return 0;
		else
			return exp[(log[x] + log[y]) % 255];
	}
	
	
	public static int reciprocal(int x) {
		if ((x & 0xFF) != x)
			throw new IllegalArgumentException("Input out of range");
		if (x == 0)
			return 0;
		else
			return exp[(255 - log[x]) % 255];
	}
	
	
	// In the FIPS 197 specification, this function is named KeyExpansion.
	public static int[] expandKey(byte[] key, int nb) {
		int nk = key.length / 4;
		int round = Math.max(nk, nb) + 6;
		int[] w = new int[(round + 1) * nb];  // Key schedule
		for (int i = 0; i < nk; i++)
			w[i] = toInt32(key, i * 4);
		for (int i = nk, rcon = 1; i < w.length; i++) {  // rcon = 2^(i/nk) mod 0x11B
			int tp = w[i - 1];
			if (i % nk == 0) {
				tp = subInt32Bytes(tp << 8 | tp >>> 24) ^ (rcon << 24);
				rcon = multiply(rcon, 0x02);
			} else if (nk > 6 && i % nk == 4)
				tp = subInt32Bytes(tp);
			w[i] = w[i - nk] ^ tp;
		}
		return w;
	}
	
	
	// Applies the S-box to each byte in the 32-bit integer.
	private static int subInt32Bytes(int x) {
		return (SBOX[x >>> 24 & 0xFF] & 0xFF) << 24
		     | (SBOX[x >>> 16 & 0xFF] & 0xFF) << 16
		     | (SBOX[x >>>  8 & 0xFF] & 0xFF) <<  8
		     | (SBOX[x >>>  0 & 0xFF] & 0xFF) <<  0;
	}
	
	
	// Converts 4 consecutive bytes into an int32 in big endian.
	private static int toInt32(byte[] b, int off) {
		return   (b[off + 0] & 0xFF) << 24
		       | (b[off + 1] & 0xFF) << 16
		       | (b[off + 2] & 0xFF) <<  8
		       | (b[off + 3] & 0xFF) <<  0;
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private RijndaelUtils() {}
	
}
