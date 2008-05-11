package p79068.crypto.cipher;


final class RijndaelUtils {
	
	static final byte[] sub;
	static final byte[] subinv;
	
	private static final int[] exp;
	private static final int[] log;
	
	
	
	static {
		exp = new int[255];
		log = new int[256];
		initExpLogTables();
		sub = new byte[256];
		subinv = new byte[256];
		initSBoxes();
	}
	
	
	private static void initExpLogTables() {
		exp[0] = 1;
		log[0] = Integer.MIN_VALUE;
		log[1] = 0;
		for (int i = 1; i < exp.length; i++) {
			exp[i] = (exp[i - 1] << 1) ^ (exp[i - 1]);  // Multiply by 0x03, which is a generator
			if (exp[i] >= 0x100)
				exp[i] ^= 0x11B;  // Modulo by 0x11B in GF(2)
			log[exp[i]] = i;
		}
	}
	
	
	private static void initSBoxes() {
		for (int i = 0; i < 256; i++) {
			int temp = reciprocal(i);
			sub[i] = (byte)(temp ^ (temp << 4 | temp >>> 4) ^ (temp << 3 | temp >>> 5) ^ (temp << 2 | temp >>> 6) ^ (temp << 1 | temp >>> 7) ^ 0x63);
			subinv[sub[i] & 0xFF] = (byte)i;
		}
	}
	
	
	
	public static int multiply(int x, int y) {
		if ((x & ~0xFF) != 0 || (y & ~0xFF) != 0)
			throw new IllegalArgumentException("Input out of range");
		else if (x == 0 || y == 0)
			return 0;
		else
			return exp[(log[x] + log[y]) % 255];
	}
	
	
	public static int reciprocal(int x) {
		if ((x & ~0xFF) != 0)
			throw new IllegalArgumentException("Input out of range");
		else if (x != 0)
			return exp[(255 - log[x]) % 255];
		else
			return 0;
	}
	
	
	public static byte[] getSBox() {
		return sub.clone();
	}
	
	
	public static byte[] getSBoxInverse() {
		return subinv.clone();
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
	
	
	private static int subInt32Bytes(int x) {  // Apply S-box to each byte in the 32-bit integer.
		return sub[x >>> 24] << 24 | (sub[x >>> 16 & 0xFF] & 0xFF) << 16 | (sub[x >>> 8 & 0xFF] & 0xFF) << 8 | (sub[x & 0xFF] & 0xFF);
	}
	
	
	private static int toInt32(byte[] b, int off) {
		return b[off] << 24 | (b[off + 1] & 0xFF) << 16 | (b[off + 2] & 0xFF) << 8 | (b[off + 3] & 0xFF);
	}
	
}