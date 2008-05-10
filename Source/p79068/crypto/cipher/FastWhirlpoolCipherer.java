/*
Each int64 represents a row of state.
*/


package p79068.crypto.cipher;

import p79068.crypto.Zeroizer;
import p79068.lang.*;


final class FastWhirlpoolCipherer extends Cipherer {
	
	private long[][] rcon;
	private long[][] mul;
	
	private long[][] keySchedule;
	
	
	
	FastWhirlpoolCipherer(WhirlpoolCipher cipher, byte[] key) {
		super(cipher, key);
		rcon = RCON;
		mul = MUL;
		setKey(key);
	}
	
	
	
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if ((len & 0x3F) != 0)
			throw new IllegalArgumentException("Invalid block length");
		long[] tempmsg = new long[8];
		long[] temp = new long[8];
		for (len += off; off < len; off += 64) {
			toInt64sBigEndian(b, off, tempmsg);
			w(tempmsg, temp);
			toBytesBigEndian(tempmsg, b, off);
		}
	}
	
	
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if ((len & 0x3F) != 0)
			throw new IllegalArgumentException("Invalid block length");
		long[] tempmsg = new long[8];
		long[] temp = new long[8];
		for (len += off; off < len; off += 64) {
			toInt64sBigEndian(b, off, tempmsg);
			//   wInverse(tempmsg,temp);
			toBytesBigEndian(tempmsg, b, off);
		}
		throw new RuntimeException("Not implemented yet");
	}
	
	
	public void zeroize() {
		if (cipher == null)
			return;
		for (int i = 0; i < keySchedule.length; i++) {
			Zeroizer.clear(keySchedule[i]);
			keySchedule[i] = null;
		}
		keySchedule = null;
		super.zeroize();
	}
	
	
	private void setKey(byte[] key) {
		if (key.length != 64)
			throw new AssertionError();
		keySchedule = new long[rcon.length + 1][];
		keySchedule[0] = new long[8];
		toInt64sBigEndian(key, 0, keySchedule[0]);
		long[] temp = new long[64];
		for (int i = 1; i < keySchedule.length; i++) {
			keySchedule[i] = keySchedule[i - 1].clone();
			rho(keySchedule[i], rcon[i - 1], temp);
		}
	}
	
	
	private void w(long[] block, long[] temp) { // The internal block cipher. Overwrites block and temp.
		for (int i = 0; i < 8; i++)
			block[i] ^= keySchedule[0][i]; // Sigma
		for (int i = 0; i < rcon.length; i++)
			rho(block, keySchedule[i], temp);
	}
	
	private void rho(long[] block, long[] key, long[] temp) { // The round function. Overwrites block and temp.
		for (int i = 0; i < 8; i++)
			temp[i] = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				temp[(i + j) & 7] ^= mul[j][(int)(block[i] >>> ((j ^ 7) << 3)) & 0xFF];
		}
		for (int i = 0; i < 8; i++)
			block[i] = temp[i] ^ key[i];
	} // Sigma
	

	private static void toInt64sBigEndian(byte[] in, int off, long[] out) {
		for (int i = 0; i < out.length; i++, off += 8) {
			out[i] = (in[off + 0] & 0xFFL) << 56 | (in[off + 1] & 0xFFL) << 48 | (in[off + 2] & 0xFFL) << 40 | (in[off + 3] & 0xFFL) << 32 | (in[off + 4] & 0xFFL) << 24 | (in[off + 5] & 0xFFL) << 16 | (in[off + 6] & 0xFFL) << 8 | (in[off + 7] & 0xFFL) << 0;
		}
	}
	
	private static void toBytesBigEndian(long[] in, byte[] out, int off) {
		for (int i = 0; i < in.length; i++, off += 8) {
			out[off + 0] = (byte)(in[i] >>> 56);
			out[off + 1] = (byte)(in[i] >>> 48);
			out[off + 2] = (byte)(in[i] >>> 40);
			out[off + 3] = (byte)(in[i] >>> 32);
			out[off + 4] = (byte)(in[i] >>> 24);
			out[off + 5] = (byte)(in[i] >>> 16);
			out[off + 6] = (byte)(in[i] >>> 8);
			out[off + 7] = (byte)(in[i] >>> 0);
		}
	}
	
	
	
	private static final int ROUNDS = 10;
	
	private static int[] exp; // exp[i] = pow(0x02,i) in GF(2^8)/0x11D.
	private static int[] log; // These are only used in class initialization.
	
	private static int[] SUB;
	private static long[][] MUL;
	private static long[][] RCON;
	
	
	static {
		initExpLogTables();
		initSBox();
		initMultiplyTable(new int[]{0x01, 0x01, 0x04, 0x01, 0x08, 0x05, 0x02, 0x09});
		initRoundConstant();
	}
	
	
	private static void initExpLogTables() {
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
	}
	
	
	private static void initSBox() {
		int[] e = {0x1, 0xB, 0x9, 0xC, 0xD, 0x6, 0xF, 0x3, 0xE, 0x8, 0x7, 0x4, 0xA, 0x2, 0x5, 0x0};  // The E mini-box
		int[] r = {0x7, 0xC, 0xB, 0xD, 0xE, 0x4, 0x9, 0xF, 0x6, 0x3, 0x8, 0xA, 0x2, 0x5, 0x1, 0x0};  // The R mini-box
		int[] einv = new int[16]; // The inverse of E
		for (int i = 0; i < e.length; i++)
			einv[e[i]] = i;
		SUB = new int[256];
		for (int i = 0; i < SUB.length; i++) {
			int left = e[i >>> 4];
			int right = einv[i & 0xF];
			int temp = r[left ^ right];
			SUB[i] = e[left ^ temp] << 4 | einv[right ^ temp];
		}
	}
	
	
	private static void initMultiplyTable(int[] c) {
		MUL = new long[8][256];
		for (int i = 0; i < 256; i++) {
			long vector = 0;
			for (int j = 0; j < 8; j++)
				vector |= (long)multiply(SUB[i], c[j]) << ((7 - j) * 8);
			for (int j = 0; j < 8; j++)
				MUL[j][i] = rotateRight(vector, j * 8);
		}
	}
	
	
	private static void initRoundConstant() {
		RCON = new long[ROUNDS][8];
		for (int i = 0; i < RCON.length; i++) {
			for (int j = 0; j < 8; j++)
				RCON[i][0] |= (long)SUB[8 * i + j] << ((7 - j) * 8);
			for (int j = 1; j < 8; j++)
				RCON[i][j] = 0;
		}
	}
	
	
	private static int multiply(int x, int y) {
		if (x == 0 || y == 0)
			return 0;
		return exp[(log[x] + log[y]) % 255];
	}
	
	
	private static long rotateRight(long x, int rotate) {
		return x << (64 - rotate) | x >>> rotate;
	}
	
}