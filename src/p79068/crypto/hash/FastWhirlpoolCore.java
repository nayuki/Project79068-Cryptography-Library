package p79068.crypto.hash;

import java.math.BigInteger;
import java.util.Arrays;
import p79068.Assert;
import p79068.crypto.Zeroizer;
import p79068.hash.HashValue;
import p79068.math.LongBitMath;


final class FastWhirlpoolCore extends BlockHasherCore {
	
	// Constants
	private final long[][] rcon;
	private final int[] subInv;
	private final long[] mul;
	private final long[][] mulInv;
	
	// Variable (reference doesn't change, but elements do)
	private long[] state;
	
	
	
	public FastWhirlpoolCore(int rounds, int[] sbox, int[] c, int[] cInv) {
		subInv = invertSbox(sbox);
		mul = makeMultiplicationTable(sbox, c);
		mulInv = makeInverseMultiplicationTable(cInv);
		rcon = makeRoundConstants(rounds, sbox);
		state = new long[8];
	}
	
	
	
	@Override
	public FastWhirlpoolCore clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		FastWhirlpoolCore result = (FastWhirlpoolCore)super.clone();
		result.state = result.state.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		state = Zeroizer.clear(state);
	}
	
	
	
	@Override
	public void compress(byte[] message, int off, int len) {
		Assert.assertRangeInBounds(message.length, off, len);
		if (len % 64 != 0)
			throw new AssertionError();
		
		// For the following arrays, each element is overwritten in each outer loop iteration
		long[] block = new long[8];
		long[] tempblock = new long[8];
		long[] tempstate = new long[8];
		
		// For each block of 64 bytes
		for (int i = off, end = off + len; i < end; ) {
			
			// Pack bytes into int64 in big endian
			for (int j = 0; j < 8; j++, i += 8) {
				block[j] = (message[i + 0] & 0xFFL) << 56
				         | (message[i + 1] & 0xFFL) << 48
				         | (message[i + 2] & 0xFFL) << 40
				         | (message[i + 3] & 0xFFL) << 32
				         | (message[i + 4] & 0xFFL) << 24
				         | (message[i + 5] & 0xFFL) << 16
				         | (message[i + 6] & 0xFFL) <<  8
				         | (message[i + 7] & 0xFFL) <<  0;
				tempblock[j] = block[j];
			}
			
			System.arraycopy(state, 0, tempstate, 0, 8);
			encrypt(tempblock, tempstate);
			for (int j = 0; j < 8; j++)
				state[j] ^= tempblock[j] ^ block[j];
		}
	}
	
	
	@Override
	public HashValue getHashDestructively(byte[] block, int blockFilled, BigInteger length) {
		block[blockFilled] = (byte)0x80;
		blockFilled++;
		Arrays.fill(block, blockFilled, block.length, (byte)0);
		if (blockFilled + 32 > block.length) {
			compress(block);
			Arrays.fill(block, (byte)0);
		}
		length = length.shiftLeft(3);  // Length is now in bits
		for (int i = 0; i < 8; i++)
			block[block.length - 1 - i] = length.shiftRight(i * 8).byteValue();
		compress(block);
		return new HashValue(LongBitMath.toBytesBigEndian(state));
	}
	
	
	
	// The internal block cipher (W). Encrypts the message in place. Overwrites key and temp.
	void encrypt(long[] message, long[] key) {
		// Sigma
		for (int i = 0; i < 8; i++)
			message[i] ^= key[i];
		
		// Do the rounds
		for (int i = 0; i < rcon.length; i++) {
			round(key, rcon[i]);
			round(message, key);
		}
	}
	
	
	// The round function (rho). Encrypts the message in place.
	private void round(long[] msg, long[] key) {
		final int M = 0xFF;  // Byte mask
		long m0 = msg[0], m1 = msg[1], m2 = msg[2], m3 = msg[3], m4 = msg[4], m5 = msg[5], m6 = msg[6], m7 = msg[7];
		msg[0] = mul[(int)(m0 >>> 56)] ^ mul[0x100 | (int)(m7 >>> 48) & M] ^ mul[0x200 | (int)(m6 >>> 40) & M] ^ mul[0x300 | (int)(m5 >>> 32) & M] ^ mul[0x400 | (int)m4 >>> 24] ^ mul[0x500 | (int)m3 >>> 16 & M] ^ mul[0x600 | (int)m2 >>> 8 & M] ^ mul[0x700 | (int)m1 & M] ^ key[0];
		msg[1] = mul[(int)(m1 >>> 56)] ^ mul[0x100 | (int)(m0 >>> 48) & M] ^ mul[0x200 | (int)(m7 >>> 40) & M] ^ mul[0x300 | (int)(m6 >>> 32) & M] ^ mul[0x400 | (int)m5 >>> 24] ^ mul[0x500 | (int)m4 >>> 16 & M] ^ mul[0x600 | (int)m3 >>> 8 & M] ^ mul[0x700 | (int)m2 & M] ^ key[1];
		msg[2] = mul[(int)(m2 >>> 56)] ^ mul[0x100 | (int)(m1 >>> 48) & M] ^ mul[0x200 | (int)(m0 >>> 40) & M] ^ mul[0x300 | (int)(m7 >>> 32) & M] ^ mul[0x400 | (int)m6 >>> 24] ^ mul[0x500 | (int)m5 >>> 16 & M] ^ mul[0x600 | (int)m4 >>> 8 & M] ^ mul[0x700 | (int)m3 & M] ^ key[2];
		msg[3] = mul[(int)(m3 >>> 56)] ^ mul[0x100 | (int)(m2 >>> 48) & M] ^ mul[0x200 | (int)(m1 >>> 40) & M] ^ mul[0x300 | (int)(m0 >>> 32) & M] ^ mul[0x400 | (int)m7 >>> 24] ^ mul[0x500 | (int)m6 >>> 16 & M] ^ mul[0x600 | (int)m5 >>> 8 & M] ^ mul[0x700 | (int)m4 & M] ^ key[3];
		msg[4] = mul[(int)(m4 >>> 56)] ^ mul[0x100 | (int)(m3 >>> 48) & M] ^ mul[0x200 | (int)(m2 >>> 40) & M] ^ mul[0x300 | (int)(m1 >>> 32) & M] ^ mul[0x400 | (int)m0 >>> 24] ^ mul[0x500 | (int)m7 >>> 16 & M] ^ mul[0x600 | (int)m6 >>> 8 & M] ^ mul[0x700 | (int)m5 & M] ^ key[4];
		msg[5] = mul[(int)(m5 >>> 56)] ^ mul[0x100 | (int)(m4 >>> 48) & M] ^ mul[0x200 | (int)(m3 >>> 40) & M] ^ mul[0x300 | (int)(m2 >>> 32) & M] ^ mul[0x400 | (int)m1 >>> 24] ^ mul[0x500 | (int)m0 >>> 16 & M] ^ mul[0x600 | (int)m7 >>> 8 & M] ^ mul[0x700 | (int)m6 & M] ^ key[5];
		msg[6] = mul[(int)(m6 >>> 56)] ^ mul[0x100 | (int)(m5 >>> 48) & M] ^ mul[0x200 | (int)(m4 >>> 40) & M] ^ mul[0x300 | (int)(m3 >>> 32) & M] ^ mul[0x400 | (int)m2 >>> 24] ^ mul[0x500 | (int)m1 >>> 16 & M] ^ mul[0x600 | (int)m0 >>> 8 & M] ^ mul[0x700 | (int)m7 & M] ^ key[6];
		msg[7] = mul[(int)(m7 >>> 56)] ^ mul[0x100 | (int)(m6 >>> 48) & M] ^ mul[0x200 | (int)(m5 >>> 40) & M] ^ mul[0x300 | (int)(m4 >>> 32) & M] ^ mul[0x400 | (int)m3 >>> 24] ^ mul[0x500 | (int)m2 >>> 16 & M] ^ mul[0x600 | (int)m1 >>> 8 & M] ^ mul[0x700 | (int)m0 & M] ^ key[7];
	}
	
	
	// The internal block cipher inverse (W inverse). Decrypts the message in place. Overwrites key and temp.
	void decrypt(long[] message, long[] key, long[] temp) {
		// Make key schedule
		long[][] keySch = new long[rcon.length + 1][8];
		System.arraycopy(key, 0, keySch[0], 0, 8);
		for (int i = 0; i < rcon.length; i++) {
			round(key, rcon[i]);
			System.arraycopy(key, 0, keySch[i + 1], 0, 8);
		}
		
		// Do the rounds
		for (int i = keySch.length - 1; i >= 1; i--)
			roundInverse(message, keySch[i], temp);
		
		// Sigma
		for (int i = 0; i < 8; i++)
			message[i] ^= keySch[0][i];
	}
	
	
	// The inverse round function (rho inverse). Decrypts the message in place. Also overwrites temp. Preserves key.
	void roundInverse(long[] message, long[] key, long[] temp) {
		// Sigma
		for (int i = 0; i < 8; i++)
			temp[i] = message[i] ^ key[i];
		
		
		// Inverse theta
		for (int i = 0; i < 8; i++) {
			message[i] = 0;
			for (int j = 0; j < 8; j++)
				message[i] ^= mulInv[j][(int)(temp[i] >>> ((j ^ 7) << 3)) & 0xFF];
		}
		
		// Do the combined inverse gamma and inverse pi
		for (int i = 0; i < 8; i++) {
			temp[i] = 0;
			for (int j = 0; j < 8; j++) {
				int shift = (j ^ 7) << 3;
				temp[i] |= (long)subInv[(int)(message[(i + j) & 7] >>> shift) & 0xFF] << shift;
			}
		}
		
		System.arraycopy(temp, 0, message, 0, 8);
	}
	
	
	
	private static long[] makeMultiplicationTable(int[] sub, int[] c) {
		c = pseudoReverse(c);
		long[] result = new long[256 * 8];
		for (int i = 0; i < 256; i++) {
			long vector = 0;
			for (int j = 0; j < 8; j++)
				vector |= multiply(sub[i], c[j]) << ((7 - j) * 8);
			for (int j = 0; j < 8; j++)
				result[j * 256 + i] = Long.rotateRight(vector, j * 8);
		}
		return result;
	}
	
	
	private static long[][] makeInverseMultiplicationTable(int[] cInv) {
		cInv = pseudoReverse(cInv);
		long[][] result = new long[8][256];
		for (int i = 0; i < 256; i++) {
			long vector = 0;
			for (int j = 0; j < 8; j++)
				vector |= multiply(i, cInv[j]) << ((7 - j) * 8);
			for (int j = 0; j < 8; j++)
				result[j][i] = Long.rotateRight(vector, j * 8);
		}
		return result;
	}
	
	
	private static long[][] makeRoundConstants(int rounds, int[] sub) {
		long[][] result = new long[rounds][8];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < 8; j++)
				result[i][0] |= (long)sub[8 * i + j] << ((7 - j) * 8);
			for (int j = 1; j < 8; j++)
				result[i][j] = 0;
		}
		return result;
	}
	
	
	private static int[] invertSbox(int[] sub) {
		int[] subInv = new int[256];
		for (int i = 0; i < sub.length; i++)
			subInv[sub[i]] = i;
		return subInv;
	}
	
	
	private static int[] pseudoReverse(int[] array) {
		int[] result = new int[array.length];
		result[0] = array[0];
		for (int i = 1; i < array.length; i++)
			result[result.length - i] = array[i];
		return result;
	}
	
	
	private static long multiply(int x, int y) {
		if ((x & 0xFF) != x || (y & 0xFF) != y)
			throw new IllegalArgumentException();
		int z = 0;
		for (; y != 0; y >>>= 1) {
			z ^= (y & 1) * x;
			x = (x << 1) ^ ((x >>> 7) * 0x11D);
		}
		return z;
	}
	
}
