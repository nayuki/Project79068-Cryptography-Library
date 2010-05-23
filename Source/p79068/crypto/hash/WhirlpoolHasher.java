package p79068.crypto.hash;

import java.util.Arrays;

import p79068.crypto.Zeroizer;
import p79068.lang.BoundsChecker;
import p79068.lang.NullChecker;
import p79068.util.hash.HashValue;


final class WhirlpoolHasher extends BlockHasherCore {
	
	private final byte[] sub;
	private final byte[] subinv;
	private final byte[][] mul;
	private final byte[][] mulinv;
	private final byte[][] rcon;
	
	private byte[] state;
	
	
	
	public WhirlpoolHasher(WhirlpoolParameters params) {
		NullChecker.check(params);
		sub = makeSbox(params.getSbox());
		subinv = makeSboxInverse(params.getSbox());
		mul = makeMultiplicationTable(params.getC());
		mulinv = makeMultiplicationTable(params.getCInverse());
		rcon = makeRoundConstants(params.getRounds(), sub);
		state = new byte[64];
	}
	
	
	
	@Override
	public WhirlpoolHasher clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		WhirlpoolHasher result = (WhirlpoolHasher)super.clone();
		result.state = result.state.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		Zeroizer.clear(state);
		state = null;
	}
	
	
	
	// Uses Miyaguchi-Preneel construction: next state = encrypt(msg: message block, key: state) XOR state XOR message block
	@Override
	public void compress(byte[] message, int off, int len) {
		BoundsChecker.check(message.length, off, len);
		if (len % 64 != 0)
			throw new AssertionError();
		
		// The lifetime of all 3 arrays actually begin and end within each loop iteration.
		// But in this implementation, they are allocated only once, to avoid the allocation and garbage collection overheads.
		byte[] tempmsg = new byte[64];
		byte[] tempstate = new byte[64];
		byte[] temp = new byte[64];
		
		// For each block of 64 bytes
		for (int end = off + len; off < end; off += 64) {
			System.arraycopy(message, off, tempmsg, 0, 64);
			System.arraycopy(state, 0, tempstate, 0, 64);
			encrypt(tempmsg, tempstate, temp);
			for (int i = 0; i < 64; i++)
				state[i] ^= tempmsg[i] ^ message[off + i];
		}
	}
	
	
	@Override
	public HashValue getHashDestructively(byte[] block, int blockLength, long length) {
		block[blockLength] = (byte)0x80;
		Arrays.fill(block, blockLength + 1, block.length, (byte)0);
		if (blockLength + 1 > block.length - 32) {
			compress(block);
			Arrays.fill(block, (byte)0);
		}
		for (int i = 0; i < 8; i++)
			block[block.length - 1 - i] = (byte)((length * 8) >>> (i * 8));  // Whirlpool supports lengths just less than 2^256 bits (2^253 bytes), but this implementation only counts to just less than 2^64 bytes.
		compress(block);
		return new HashValue(state);
	}
	
	
	// High level cipher functions
	
	// The internal block cipher (W). Encrypts the message in place. Overwrites key and temp.
	void encrypt(byte[] message, byte[] key, byte[] temp) {
		addRoundKey(message, key);
		for (int i = 0; i < rcon.length; i++) {  // rcon.length is the number of rounds
			round(key, rcon[i], temp);
			round(message, key, temp);
		}
	}
	
	
	// The internal block cipher inverse (W inverse). Decrypts the message in place. Overwrites key and temp.
	void decrypt(byte[] message, byte[] key, byte[] temp) {
		for (int i = 0; i < rcon.length; i++)
			round(key, rcon[i], temp);
		
		for (int i = rcon.length - 1; i >= 0; i--) {
			roundInverse(message, key, temp);
			roundInverse(key, rcon[i], temp);
		}
		addRoundKey(message, key);
	}
	
	
	// Middle level cipher functions
	
	// The round function (rho). Encrypts block in place. Also overwrites temp. Preserves key.
	private void round(byte[] block, byte[] key, byte[] temp) {
		subBytes(block);
		shiftColumns(block, temp);
		mixRows(temp, block);
		addRoundKey(block, key);
	}
	
	
	// The inverse round function (rho inverse). Decrypts block in place. Also overwrites temp. Preserves key.
	private void roundInverse(byte[] block, byte[] key, byte[] temp) {
		addRoundKey(block, key);
		mixRowsInverse(block, temp);
		shiftColumnsInverse(temp, block);
		subBytesInverse(block);
	}
	
	
	// Low level cipher functions
	
	// The non-linear layer (gamma)
	private void subBytes(byte[] block) {
		for (int i = 0; i < 64; i++)
			block[i] = sub[block[i] & 0xFF];
	}
	
	
	// The cyclical permutation (pi)
	private static void shiftColumns(byte[] blockin, byte[] blockout) {
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++)
				blockout[(i + j) % 8 * 8 + j] = blockin[i * 8 + j];
		}
	}
	
	
	// The linear diffusion layer (theta)
	private void mixRows(byte[] blockin, byte[] blockout) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int sum = 0;
				for (int k = 0; k < 8; k++)
					sum ^= mul[k][blockin[i * 8 + (j + k) % 8] & 0xFF];
				blockout[i * 8 + j] = (byte)sum;
			}
		}
	}
	
	
	// The key addition (sigma) (self-inverting)
	private static void addRoundKey(byte[] block, byte[] key) {
		for (int i = 0; i < 64; i++)
			block[i] ^= key[i];
	}
	
	
	// The inverse non-linear layer (gamma inverse)
	private void subBytesInverse(byte[] block) {
		for (int i = 0; i < 64; i++)
			block[i] = subinv[block[i] & 0xFF];
	}
	
	
	// The inverse cyclical permutation (pi inverse)
	private static void shiftColumnsInverse(byte[] blockin, byte[] blockout) {
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++)
				blockout[i * 8 + j] = blockin[(i + j) % 8 * 8 + j];
		}
	}
	
	
	// The inverse linear diffusion layer (theta inverse)
	private void mixRowsInverse(byte[] blockin, byte[] blockout) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int sum = 0;
				for (int k = 0; k < 8; k++)
					sum ^= mulinv[k][blockin[i << 3 | (j + k) & 7] & 0xFF];
				blockout[i * 8 + j] = (byte)sum;
			}
		}
	}
	
	
	
	private static byte[] makeSbox(int[] sbox) {
		byte[] result = new byte[256];
		for (int i = 0; i < result.length; i++)
			result[i] = (byte)sbox[i];
		return result;
	}
	
	
	private static byte[] makeSboxInverse(int[] sbox) {
		byte[] result = new byte[256];
		for (int i = 0; i < result.length; i++)
			result[sbox[i]] = (byte)i;
		return result;
	}
	
	
	private static byte[][] makeRoundConstants(int rounds, byte[] sub) {
		byte[][] rcon = new byte[rounds][64];
		for (int i = 0; i < rcon.length; i++) {
			for (int j = 0; j < 8; j++)  // The leading 8 bytes (top row) are taken from the S-box
				rcon[i][j] = sub[8 * i + j];
			for (int j = 8; j < 64; j++)  // The remaining 7 rows are zero
				rcon[i][j] = 0;
		}
		return rcon;
	}
	
	
	private static byte[][] makeMultiplicationTable(int[] c) {
		byte[][] mul = new byte[8][256];
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < 256; j++)
				mul[i][j] = (byte)WhirlpoolUtils.multiply(j, c[i]);
		}
		return mul;
	}
	
}