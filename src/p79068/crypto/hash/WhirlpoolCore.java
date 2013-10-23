package p79068.crypto.hash;

import java.math.BigInteger;
import java.util.Arrays;

import p79068.Assert;
import p79068.crypto.Zeroizer;
import p79068.hash.HashValue;


final class WhirlpoolCore extends BlockHasherCore {
	
	// Constants
	private final byte[] sbox;
	private final byte[] sboxInverse;
	private final int[] c;
	private final int[] cInverse;
	private final byte[][] rcon;
	
	// Variable (reference doesn't change, but elements do)
	private byte[] state;
	
	
	
	public WhirlpoolCore(int rounds, int[] sbox, int[] c, int[] cInv) {
		this.sbox = new byte[256];
		sboxInverse = new byte[256];
		for (int i = 0; i < sbox.length; i++) {
			this.sbox[i] = (byte)sbox[i];
			sboxInverse[sbox[i]] = (byte)i;
		}
		
		this.c = c;
		this.cInverse = cInv;
		
		rcon = new byte[rounds][64];
		for (int i = 0; i < rcon.length; i++) {
			for (int j = 0; j < 8; j++)  // The leading 8 bytes (top row) are taken from the S-box
				rcon[i][j] = this.sbox[8 * i + j];
			for (int j = 8; j < 64; j++)  // The remaining 7 rows are zero
				rcon[i][j] = 0;
		}
		
		state = new byte[64];
	}
	
	
	
	@Override
	public WhirlpoolCore clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		WhirlpoolCore result = (WhirlpoolCore)super.clone();
		result.state = result.state.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		state = Zeroizer.clear(state);
	}
	
	
	
	// Uses Miyaguchi-Preneel construction: next state = encrypt(msg: message block, key: state) XOR state XOR message block
	@Override
	public void compress(byte[] message, int off, int len) {
		Assert.assertRangeInBounds(message.length, off, len);
		if (len % 64 != 0)
			throw new AssertionError();
		
		// The lifetime of all 3 arrays actually begin and end within each loop iteration.
		// But in this implementation, they are allocated only once, to avoid the allocation and garbage collection overheads.
		byte[] tempmsg = new byte[64];
		byte[] tempstate = new byte[64];
		byte[] temp = new byte[64];
		
		// For each block of 64 bytes
		for (int i = off, end = off + len; i < end; i += 64) {
			System.arraycopy(message, i, tempmsg, 0, 64);
			System.arraycopy(state, 0, tempstate, 0, 64);
			encrypt(tempmsg, tempstate, temp);
			for (int j = 0; j < 64; j++)
				state[j] ^= tempmsg[j] ^ message[i + j];
		}
	}
	
	
	@Override
	public HashValue getHashDestructively(byte[] block, int blockFilled, BigInteger length) {
		block[blockFilled] = (byte)0x80;
		blockFilled++;
		Arrays.fill(block, blockFilled, block.length, (byte)0);
		if (blockFilled > block.length - 32) {
			compress(block);
			Arrays.fill(block, (byte)0);
		}
		length = length.shiftLeft(3);  // Length is now in bits
		for (int i = 0; i < 32; i++)
			block[block.length - 1 - i] = length.shiftRight(i * 8).byteValue();
		compress(block);
		return new HashValue(state);
	}
	
	
	/* High-level functions of the Whirlpool internal cipher */
	
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
	
	
	/* Mid-level cipher functions */
	
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
	
	
	/* Low-level cipher functions */
	
	// The non-linear layer (gamma)
	private void subBytes(byte[] block) {
		for (int i = 0; i < 64; i++)
			block[i] = sbox[block[i] & 0xFF];
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
					sum ^= multiply(c[k], blockin[i * 8 + (j + k) % 8] & 0xFF);
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
			block[i] = sboxInverse[block[i] & 0xFF];
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
					sum ^= multiply(cInverse[k], blockin[i << 3 | (j + k) & 7] & 0xFF);
				blockout[i * 8 + j] = (byte)sum;
			}
		}
	}
	
	
	private static byte multiply(int x, int y) {
		if ((x & 0xFF) != x || (y & 0xFF) != y)
			throw new IllegalArgumentException();
		byte z = 0;
		for (; y != 0; y >>>= 1) {
			z ^= (y & 1) * x;
			x = (x << 1) ^ ((x >>> 7) * 0x11D);
		}
		return z;
	}
	
}
