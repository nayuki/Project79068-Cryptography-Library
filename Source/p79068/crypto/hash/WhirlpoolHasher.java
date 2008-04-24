package p79068.crypto.hash;

import p79068.crypto.Zeroizer;
import p79068.util.hash.HashValue;


final class WhirlpoolHasher extends BlockHasher {
	
	private byte[] state;
	
	
	
	WhirlpoolHasher(Whirlpool hashFunc) {
		super(hashFunc, 64);
		sub = WHIRLPOOL_SUB;
		mul = WHIRLPOOL_MUL;
		rcon = WHIRLPOOL_RCON;
		state = new byte[64];
	}
	
	
	WhirlpoolHasher(Whirlpool0 hashFunc) {
		super(hashFunc, 64);
		sub = WHIRLPOOL0_SUB;
		mul = WHIRLPOOL0_MUL;
		rcon = WHIRLPOOL0_RCON;
		state = new byte[64];
	}
	
	
	WhirlpoolHasher(Whirlpool1 hashFunc) {
		super(hashFunc, 64);
		sub = WHIRLPOOL1_SUB;
		mul = WHIRLPOOL1_MUL;
		rcon = WHIRLPOOL1_RCON;
		state = new byte[64];
	}
	
	
	
	public WhirlpoolHasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		WhirlpoolHasher result = (WhirlpoolHasher)super.clone();
		result.state = state.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		Zeroizer.clear(state);
		state = null;
		super.zeroize();
	}
	
	
	
	// Uses Miyaguchi-Preneel construction: next state = encrypt(msg: message block, key: state) XOR state XOR message block
	protected void compress(byte[] message, int off, int len) {
		byte[] tempmsg = new byte[64];
		byte[] tempstate = new byte[64];
		byte[] temp = new byte[64];
		for (int end = off + len; off < end; off += 64) {
			System.arraycopy(message, off, tempmsg, 0, 64);
			System.arraycopy(state, 0, tempstate, 0, 64);
			w(tempmsg, tempstate, temp);
			for (int i = 0; i < 64; i++)
				state[i] ^= tempmsg[i] ^ message[off + i];
		}
	}
	
	
	protected HashValue getHashDestructively() {
		block[blockLength] = (byte)0x80;
		for (int i = blockLength + 1; i < block.length; i++)
			block[i] = 0x00;
		if (blockLength + 1 > block.length - 32) {
			compress();
			for (int i = 0; i < block.length; i++)
				block[i] = 0x00;
		}
		for (int i = 0; i < 8; i++)
			block[block.length - 1 - i] = (byte)((length * 8) >>> (i * 8));  // Whirlpool supports lengths just less than 2^256 bits (2^253 bytes), but this implementation only counts to just less than 2^64 bytes.
		compress();
		return createHash(state);
	}
	
	
	
	private byte[] sub;
	private byte[][] mul;
	private byte[][] rcon;
	
	
	// The internal block cipher. Overwrites message and key.
	private void w(byte[] message, byte[] key, byte[] temp) {
		sigma(message, key);
		for (int i = 0; i < 10; i++) {
			rho(key, rcon[i], temp);
			rho(message, key, temp);
		}
	}
	
	
	// The round function. Overwrites block and temp.
	private void rho(byte[] block, byte[] key, byte[] temp) {
		gamma(block);
		pi(block, temp);
		theta(temp, block);
		sigma(block, key);
	}
	
	
	// The non-linear layer
	private void gamma(byte[] block) {
		for (int i = 0; i < 64; i++)
			block[i] = sub[block[i] & 0xFF];
	}
	
	
	// The cyclical permutation
	private void pi(byte[] blockin, byte[] blockout) {
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++)
				blockout[((i + j) & 7) << 3 | j] = blockin[i << 3 | j];
		}
	}
	
	
	// The linear diffusion layer
	private void theta(byte[] blockin, byte[] blockout) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int sum = 0;
				for (int k = 0; k < 8; k++)
					sum ^= mul[k][blockin[i << 3 | (j + k) & 7] & 0xFF];
				blockout[i << 3 | j] = (byte)sum;
			}
		}
	}
	
	
	// The key addition
	private void sigma(byte[] block, byte[] key) {
		for (int i = 0; i < 64; i++)
			block[i] ^= key[i];
	}
	
	
	
	private static final byte[] WHIRLPOOL0_SUB;
	private static final byte[] WHIRLPOOL1_SUB;
	private static final byte[] WHIRLPOOL_SUB;
	private static final byte[][] WHIRLPOOL0_MUL;
	private static final byte[][] WHIRLPOOL1_MUL;
	private static final byte[][] WHIRLPOOL_MUL;
	private static final byte[][] WHIRLPOOL0_RCON;
	private static final byte[][] WHIRLPOOL1_RCON;
	private static final byte[][] WHIRLPOOL_RCON;
	
	private static int[] exp;  // exp[i] = pow(0x02,i) in GF(2^8)/0x11D.
	private static int[] log;  // These are only used in class initialization.
	
	static {
		// Initialize WHIRLPOOL0_SUB
		{
			int[] sub = {0x68, 0xD0, 0xEB, 0x2B, 0x48, 0x9D, 0x6A, 0xE4, 0xE3, 0xA3, 0x56, 0x81, 0x7D, 0xF1, 0x85, 0x9E, 0x2C, 0x8E, 0x78, 0xCA, 0x17, 0xA9, 0x61, 0xD5, 0x5D, 0x0B, 0x8C, 0x3C, 0x77, 0x51, 0x22, 0x42, 0x3F, 0x54, 0x41, 0x80, 0xCC, 0x86, 0xB3, 0x18, 0x2E, 0x57, 0x06, 0x62, 0xF4, 0x36, 0xD1, 0x6B, 0x1B, 0x65, 0x75, 0x10, 0xDA, 0x49, 0x26, 0xF9, 0xCB, 0x66, 0xE7, 0xBA, 0xAE, 0x50, 0x52, 0xAB, 0x05, 0xF0, 0x0D, 0x73, 0x3B, 0x04, 0x20, 0xFE, 0xDD, 0xF5, 0xB4, 0x5F, 0x0A, 0xB5, 0xC0, 0xA0, 0x71, 0xA5, 0x2D, 0x60, 0x72, 0x93, 0x39, 0x08, 0x83, 0x21, 0x5C, 0x87, 0xB1, 0xE0, 0x00, 0xC3, 0x12, 0x91, 0x8A, 0x02, 0x1C, 0xE6, 0x45, 0xC2, 0xC4, 0xFD, 0xBF, 0x44, 0xA1, 0x4C, 0x33, 0xC5, 0x84, 0x23, 0x7C, 0xB0, 0x25, 0x15, 0x35, 0x69, 0xFF, 0x94, 0x4D, 0x70, 0xA2, 0xAF, 0xCD, 0xD6, 0x6C, 0xB7, 0xF8, 0x09, 0xF3, 0x67, 0xA4, 0xEA, 0xEC, 0xB6, 0xD4, 0xD2, 0x14, 0x1E, 0xE1, 0x24, 0x38, 0xC6, 0xDB, 0x4B, 0x7A, 0x3A, 0xDE, 0x5E, 0xDF, 0x95, 0xFC, 0xAA, 0xD7, 0xCE, 0x07, 0x0F, 0x3D, 0x58,
					0x9A, 0x98, 0x9C, 0xF2, 0xA7, 0x11, 0x7E, 0x8B, 0x43, 0x03, 0xE2, 0xDC, 0xE5, 0xB2, 0x4E, 0xC7, 0x6D, 0xE9, 0x27, 0x40, 0xD8, 0x37, 0x92, 0x8F, 0x01, 0x1D, 0x53, 0x3E, 0x59, 0xC1, 0x4F, 0x32, 0x16, 0xFA, 0x74, 0xFB, 0x63, 0x9F, 0x34, 0x1A, 0x2A, 0x5A, 0x8D, 0xC9, 0xCF, 0xF6, 0x90, 0x28, 0x88, 0x9B, 0x31, 0x0E, 0xBD, 0x4A, 0xE8, 0x96, 0xA6, 0x0C, 0xC8, 0x79, 0xBC, 0xBE, 0xEF, 0x6E, 0x46, 0x97, 0x5B, 0xED, 0x19, 0xD9, 0xAC, 0x99, 0xA8, 0x29, 0x64, 0x1F, 0xAD, 0x55, 0x13, 0xBB, 0xF7, 0x6F, 0xB9, 0x47, 0x2F, 0xEE, 0xB8, 0x7B, 0x89, 0x30, 0xD3, 0x7F, 0x76, 0x82};
			WHIRLPOOL0_SUB = new byte[sub.length];
			for (int i = 0; i < sub.length; i++)
				WHIRLPOOL0_SUB[i] = (byte)sub[i];
		}
		
		// Initialize WHIRLPOOL1_SUB, WHIRLPOOL_SUB
		{
			int[] e = {0x1, 0xB, 0x9, 0xC, 0xD, 0x6, 0xF, 0x3, 0xE, 0x8, 0x7, 0x4, 0xA, 0x2, 0x5, 0x0};  // The E mini-box
			int[] r = {0x7, 0xC, 0xB, 0xD, 0xE, 0x4, 0x9, 0xF, 0x6, 0x3, 0x8, 0xA, 0x2, 0x5, 0x1, 0x0};  // The R mini-box
			int[] einv = new int[16];  // The inverse of E
			for (int i = 0; i < e.length; i++)
				einv[e[i]] = i;
			WHIRLPOOL1_SUB = new byte[256];
			WHIRLPOOL_SUB = WHIRLPOOL1_SUB;
			for (int i = 0; i < WHIRLPOOL1_SUB.length; i++) {
				int left = e[i >>> 4];
				int right = einv[i & 0xF];
				int tp = r[left ^ right];
				WHIRLPOOL1_SUB[i] = (byte)(e[left ^ tp] << 4 | einv[right ^ tp]);
			}
		}
		
		// Initialize WHIRLPOOL0_RCON
		WHIRLPOOL0_RCON = new byte[10][64];  // 10 is the number of rounds
		for (int i = 0; i < WHIRLPOOL0_RCON.length; i++) {
			int j = 0;
			for (; j < 8; j++)
				WHIRLPOOL0_RCON[i][j] = WHIRLPOOL0_SUB[8 * i + j];
			for (; j < 64; j++)
				WHIRLPOOL0_RCON[i][j] = 0;
		}
		
		// Initialize WHIRLPOOL1_RCON, WHIRLPOOL_RCON
		WHIRLPOOL1_RCON = new byte[10][64];  // 10 is the number of rounds
		WHIRLPOOL_RCON = WHIRLPOOL1_RCON;
		for (int i = 0; i < WHIRLPOOL1_RCON.length; i++) {
			int j = 0;
			for (; j < 8; j++)
				WHIRLPOOL1_RCON[i][j] = WHIRLPOOL1_SUB[8 * i + j];
			for (; j < 64; j++)
				WHIRLPOOL1_RCON[i][j] = 0;
		}
		
		// Initialize exponential and logarithm tables
		exp = new int[255];
		log = new int[256];
		exp[0] = 1;
		log[0] = Integer.MIN_VALUE;
		log[1] = 0;
		for (int i = 1; i < exp.length; i++) {
			exp[i] = exp[i - 1] << 1;  // Multiply by 0x02
			if ((exp[i] & 0x100) != 0)
				exp[i] ^= 0x11D;  // Modulo by 0x11D in GF(2)
			log[exp[i]] = i;
		}
		
		// Initialize WHIRLPOOL0_MUL, WHIRLPOOL1_MUL
		{
			int[] c = {0x01, 0x05, 0x09, 0x08, 0x05, 0x01, 0x03, 0x01};
			WHIRLPOOL0_MUL = new byte[8][256];
			WHIRLPOOL1_MUL = WHIRLPOOL0_MUL;
			for (int i = 0; i < c.length; i++) {
				for (int j = 0; j < 256; j++)
					WHIRLPOOL0_MUL[i][j] = (byte)multiply(j, c[i]);
			}
		}
		
		// Initialize WHIRLPOOL_MUL
		{
			int[] c = {0x01, 0x09, 0x02, 0x05, 0x08, 0x01, 0x04, 0x01};
			WHIRLPOOL_MUL = new byte[8][256];
			for (int i = 0; i < c.length; i++) {
				for (int j = 0; j < 256; j++)
					WHIRLPOOL_MUL[i][j] = (byte)multiply(j, c[i]);
			}
		}
		
		exp = null;
		log = null;
	}
	
	
	private static int multiply(int x, int y) {
		if (x == 0 || y == 0)
			return 0;
		return exp[(log[x] + log[y]) % 255];
	}
	
}