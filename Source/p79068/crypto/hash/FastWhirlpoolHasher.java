package p79068.crypto.hash;

import p79068.math.LongBitMath;
import p79068.util.hash.HashValue;


final class FastWhirlpoolHasher extends BlockHasher {
	
	private long[] state;
	
	
	
	FastWhirlpoolHasher(Whirlpool hashFunc) {
		super(hashFunc, 64);
		state = new long[8];
	}
	
	
	
	public FastWhirlpoolHasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		FastWhirlpoolHasher result = (FastWhirlpoolHasher)super.clone();
		result.state = state.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		for (int i = 0; i < state.length; i++)
			state[i] = 0;
		state = null;
		super.zeroize();
	}
	
	
	
	protected void compress(byte[] message, int off, int len) {
		long[] block = new long[8];
		long[] tempblock = new long[8];
		long[] tempstate = new long[8];
		long[] temp = new long[8];
		for (len += off; off < len;) {
			for (int i = 0; i < 8; i++, off += 8) {
				block[i] = tempblock[i] = (message[off + 0] & 0xFFL) << 56 | (message[off + 1] & 0xFFL) << 48 | (message[off + 2] & 0xFFL) << 40 | (message[off + 3] & 0xFFL) << 32 | (message[off + 4] & 0xFFL) << 24 | (message[off + 5] & 0xFFL) << 16 | (message[off + 6] & 0xFFL) << 8 | (message[off + 7] & 0xFFL) << 0;
			}
			System.arraycopy(state, 0, tempstate, 0, 8);
			w(tempblock, tempstate, temp);
			for (int i = 0; i < 8; i++)
				state[i] ^= tempblock[i] ^ block[i];
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
			block[block.length - 1 - i] = (byte)((length * 8) >>> (i * 8)); // Whirlpool supports lengths just less than 2^256 bits (2^253 bytes), but this implementation only counts to just less than 2^64 bytes.
		compress();
		return createHash(LongBitMath.toBytesBigEndian(state));
	}
	
	
	
	private static final int ROUNDS = 10;
	
	private static long[][] rcon;
	
	private static long[][] mul;
	
	
	private static void w(long[] block, long[] key, long[] temp) { // The internal block cipher. Overwrites block, key, and temp.
		for (int i = 0; i < 8; i++)
			block[i] ^= key[i]; // Sigma
		for (int i = 0; i < rcon.length; i++) {
			rho(key, rcon[i], temp);
			rho(block, key, temp);
		}
	}
	
	
	private static void rho(long[] block, long[] key, long[] temp) { // The round function. Overwrites block and temp.
		for (int i = 0; i < 8; i++)
			temp[i] = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				temp[(i + j) & 7] ^= mul[j][(int)(block[i] >>> ((j ^ 7) << 3)) & 0xFF];
		}
		for (int i = 0; i < 8; i++)
			block[i] = temp[i] ^ key[i];
	} // Sigma
	
	
	
	private static int[] sub; // These are only used in class initialization.
	
	
	static {
		initSBox();
		initMultiplyTable(new int[]{0x01, 0x01, 0x04, 0x01, 0x08, 0x05, 0x02, 0x09});
		initRoundConstant();
		sub = null;
	}
	
	
	private static void initSBox() {
		int[] e = {0x1, 0xB, 0x9, 0xC, 0xD, 0x6, 0xF, 0x3, 0xE, 0x8, 0x7, 0x4, 0xA, 0x2, 0x5, 0x0}; // The E mini-box
		int[] r = {0x7, 0xC, 0xB, 0xD, 0xE, 0x4, 0x9, 0xF, 0x6, 0x3, 0x8, 0xA, 0x2, 0x5, 0x1, 0x0}; // The R mini-box
		int[] einv = new int[16]; // The inverse of E
		for (int i = 0; i < e.length; i++)
			einv[e[i]] = i;
		sub = new int[256];
		for (int i = 0; i < sub.length; i++) {
			int left = e[i >>> 4];
			int right = einv[i & 0xF];
			int temp = r[left ^ right];
			sub[i] = e[left ^ temp] << 4 | einv[right ^ temp];
		}
	}
	
	
	private static void initMultiplyTable(int[] c) {
		mul = new long[8][256];
		for (int i = 0; i < 256; i++) {
			long vector = 0;
			for (int j = 0; j < 8; j++)
				vector |= (long)WhirlpoolUtil.multiply(sub[i], c[j]) << ((7 - j) * 8);
			for (int j = 0; j < 8; j++)
				mul[j][i] = LongBitMath.rotateRight(vector, j * 8);
		}
	}
	
	
	private static void initRoundConstant() {
		rcon = new long[ROUNDS][8];
		for (int i = 0; i < rcon.length; i++) {
			for (int j = 0; j < 8; j++)
				rcon[i][0] |= (long)sub[8 * i + j] << ((7 - j) * 8);
			for (int j = 1; j < 8; j++)
				rcon[i][j] = 0;
		}
	}
	
}