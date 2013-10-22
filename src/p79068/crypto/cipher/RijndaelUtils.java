package p79068.crypto.cipher;


final class RijndaelUtils {
	
	private static final byte[] SBOX;
	private static final byte[] SBOX_INVERSE;
	
	static {
		// Calculate exponentiation table
		int[] exp = new int[255];
		int prod = 1;
		for (int i = 0; i < exp.length; i++) {
			exp[i] = prod;
			prod ^= prod << 1;  // Multiply by 0x03, which is a generator
			prod ^= (prod >>> 8) * 0x11B;  // Modulo by 0x11B in GF(2)
		}
		
		// Calculate reciprocals
		int[] rec = new int[256];
		rec[0] = 0;
		rec[1] = 1;
		for (int i = 1; i < 255; i++)
			rec[exp[i]] = exp[255 - i];
		
		// Initialize the S-box and inverse
		SBOX = new byte[256];
		SBOX_INVERSE = new byte[256];
		for (int i = 0; i < 256; i++) {
			int t = rec[i];
			int s = (t ^ (t << 4 | t >>> 4) ^ (t << 3 | t >>> 5) ^ (t << 2 | t >>> 6) ^ (t << 1 | t >>> 7) ^ 0x63) & 0xFF;
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
			throw new IllegalArgumentException();
		int z = 0;
		for (; y != 0; y >>>= 1) {
			z ^= (y & 1) * x;
			x = (x << 1) ^ ((x >>> 7) * 0x11B);
		}
		return z;
	}
	
	
	// In the FIPS 197 specification, this function is named KeyExpansion.
	public static int[] expandKey(byte[] key, int nb) {
		int nk = key.length / 4;
		int round = Math.max(nk, nb) + 6;
		int[] w = new int[(round + 1) * nb];  // Key schedule
		for (int i = 0; i < nk; i++)
			w[i] = (key[i*4 + 0] & 0xFF) << 24 | (key[i*4 + 1] & 0xFF) << 16 | (key[i*4 + 2] & 0xFF) << 8 | (key[i*4 + 3] & 0xFF) << 0;
		for (int i = nk, rcon = 1; i < w.length; i++) {  // rcon = 2^(i/nk) mod 0x11B
			int tp = w[i - 1];
			if (i % nk == 0) {
				tp = subInt32Bytes(Integer.rotateLeft(tp, 8)) ^ (rcon << 24);
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
	
	
	
	/**
	 * Not instantiable.
	 */
	private RijndaelUtils() {}
	
}
