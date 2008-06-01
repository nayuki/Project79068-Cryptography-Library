package p79068.crypto.hash;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import p79068.crypto.Zeroizer;
import p79068.lang.BoundsChecker;
import p79068.math.LongBitMath;
import p79068.util.hash.HashValue;


final class FastWhirlpoolHasher extends BlockHasher {
	
	private final long[][] rcon;
	
	private final long[][] mul;
	
	
	private long[] state;
	
	
	
	FastWhirlpoolHasher(AbstractWhirlpool hashFunc) {
		super(hashFunc);
		
		if (!tablesByFunction.containsKey(hashFunc)) {
			mul = makeMultiplicationTable(hashFunc.getSbox(), hashFunc.getC());
			rcon = makeRoundConstants(hashFunc.getRounds(), hashFunc.getSbox());
			tablesByFunction.put(hashFunc, new Tables(mul, rcon));
		} else {
			Tables tables = tablesByFunction.get(hashFunc);
			mul = tables.multiply;
			rcon = tables.roundConstants;
		}
		
		state = new long[8];
	}
	
	
	
	public FastWhirlpoolHasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		FastWhirlpoolHasher result = (FastWhirlpoolHasher)super.clone();
		result.state = result.state.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		Zeroizer.clear(state);
		state = null;
		super.zeroize();
	}
	
	
	
	protected void compress(byte[] message, int off, int len) {
		BoundsChecker.check(message.length, off, len);
		if (len % 64 != 0)
			throw new AssertionError();
		
		long[] block = new long[8];
		long[] tempblock = new long[8];
		long[] tempstate = new long[8];
		long[] temp = new long[8];
		
		// For each block of 64 bytes
		for (int end = off + len; off < end;) {
			
			// Pack bytes into int64s in big endian
			for (int i = 0; i < 8; i++, off += 8) {
				block[i] = (message[off + 0] & 0xFFL) << 56
				         | (message[off + 1] & 0xFFL) << 48
				         | (message[off + 2] & 0xFFL) << 40
				         | (message[off + 3] & 0xFFL) << 32
				         | (message[off + 4] & 0xFFL) << 24
				         | (message[off + 5] & 0xFFL) << 16
				         | (message[off + 6] & 0xFFL) <<  8
				         | (message[off + 7] & 0xFFL) <<  0;
				tempblock[i] = block[i];
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
			block[block.length - 1 - i] = (byte)((length * 8) >>> (i * 8));  // Whirlpool supports lengths just less than 2^256 bits (2^253 bytes), but this implementation only counts to just less than 2^64 bytes.
		compress();
		return createHash(LongBitMath.toBytesBigEndian(state));
	}
	
	
	
	// The internal block cipher. Encrypts block in place. Overwrites key and temp.
	private void w(long[] block, long[] key, long[] temp) {
		// Sigma
		for (int i = 0; i < 8; i++)
			block[i] ^= key[i];
		
		// Do the rounds
		for (int i = 0; i < rcon.length; i++) {
			rho(key, rcon[i], temp);
			rho(block, key, temp);
		}
	}
	
	
	// The round function. Encrypts block in place. Overwrites block and temp.
	private void rho(long[] block, long[] key, long[] temp) {
		// Clear temp
		for (int i = 0; i < 8; i++)
			temp[i] = 0;
		
		// Do the combined gamma, pi, and theta
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				temp[(i + j) & 7] ^= mul[j][(int)(block[i] >>> ((j ^ 7) << 3)) & 0xFF];
		}
		
		// Sigma
		for (int i = 0; i < 8; i++)
			block[i] = temp[i] ^ key[i];
	}
	
	
	
	private static Map<AbstractWhirlpool,Tables> tablesByFunction;
	
	static {
		tablesByFunction = new HashMap<AbstractWhirlpool,Tables>();
		tablesByFunction = Collections.synchronizedMap(tablesByFunction);
	}
	
	
	
	private static class Tables {
		
		public final long[][] multiply;
		
		public final long[][] roundConstants;
		
		
		
		public Tables(long[][] multiply, long[][] roundConstants) {
			this.multiply = multiply;
			this.roundConstants = roundConstants;
		}
		
	}
	
	
	
	private static long[][] makeMultiplicationTable(byte[] sub, int[] c) {
		c = pseudoReverse(c);
		long[][] result = new long[8][256];
		for (int i = 0; i < 256; i++) {
			long vector = 0;
			for (int j = 0; j < 8; j++)
				vector |= (long)WhirlpoolUtils.multiply(sub[i] & 0xFF, c[j]) << ((7 - j) * 8);
			for (int j = 0; j < 8; j++)
				result[j][i] = LongBitMath.rotateRight(vector, j * 8);
		}
		return result;
	}
	
	
	private static long[][] makeRoundConstants(int rounds, byte[] sub) {
		long[][] result = new long[rounds][8];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < 8; j++)
				result[i][0] |= (long)(sub[8 * i + j] & 0xFF) << ((7 - j) * 8);
			for (int j = 1; j < 8; j++)
				result[i][j] = 0;
		}
		return result;
	}
	
	
	private static int[] pseudoReverse(int[] array) {
		int[] result = new int[array.length];
		result[0] = array[0];
		for (int i = 1; i < array.length; i++)
			result[result.length - i] = array[i];
		return result;
	}
	
}