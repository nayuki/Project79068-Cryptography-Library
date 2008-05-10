package p79068.crypto.hash;

import p79068.crypto.Zeroizer;
import p79068.util.hash.HashValue;


final class WhirlpoolHasher extends BlockHasher {
	
	private final byte[] sub;
	private final byte[][] mul;
	private final byte[][] rcon;
	
	private byte[] state;
	
	
	
	WhirlpoolHasher(Whirlpool hashFunc, byte[] sub, byte[][] rcon, byte[][] mul) {
		this((BlockHashFunction)hashFunc, sub, rcon, mul);
	}
	
	
	WhirlpoolHasher(Whirlpool0 hashFunc, byte[] sub, byte[][] rcon, byte[][] mul) {
		this((BlockHashFunction)hashFunc, sub, rcon, mul);
	}
	
	
	WhirlpoolHasher(WhirlpoolT hashFunc, byte[] sub, byte[][] rcon, byte[][] mul) {
		this((BlockHashFunction)hashFunc, sub, rcon, mul);
	}
	
	
	// Private constructor. hashFunc must be either Whirlpool0, Whirlpool1, or Whirlpool.
	private WhirlpoolHasher(BlockHashFunction hashFunc, byte[] sub, byte[][] rcon, byte[][] mul) {
		super(hashFunc, 64);
		if (sub == null || rcon == null || mul == null)
			throw new AssertionError();
		this.sub = sub;
		this.rcon = rcon;
		this.mul = mul;
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
		// The lifetime of all 3 arrays actually begin and end within each loop iteration.
		// But in this implementation, they are allocated only once, to avoid the allocation and garbage collection overheads.
		byte[] tempmsg = new byte[64];
		byte[] tempstate = new byte[64];
		byte[] temp = new byte[64];
		
		// For each block of 64 bytes
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
	
	
	// The internal block cipher. Encrypts message in place. Overwrites key and temp.
	private void w(byte[] message, byte[] key, byte[] temp) {
		sigma(message, key);
		for (int i = 0; i < rcon.length; i++) {  // rcon.length is the number of rounds
			rho(key, rcon[i], temp);
			rho(message, key, temp);
		}
	}
	
	
	// The round function. Encrypts block in place. Overwrites temp. Does not overwrite key.
	private void rho(byte[] block, byte[] key, byte[] temp) {
		gamma(sub, block);
		pi(block, temp);
		theta(mul, temp, block);
		sigma(block, key);
	}
	
	
	// The non-linear layer
	private static void gamma(byte[] sub, byte[] block) {
		for (int i = 0; i < 64; i++)
			block[i] = sub[block[i] & 0xFF];
	}
	
	
	// The cyclical permutation
	private static void pi(byte[] blockin, byte[] blockout) {
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++)
				blockout[((i + j) & 7) << 3 | j] = blockin[i << 3 | j];
		}
	}
	
	
	// The linear diffusion layer
	private static void theta(byte[][] mul, byte[] blockin, byte[] blockout) {
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
	private static void sigma(byte[] block, byte[] key) {
		for (int i = 0; i < 64; i++)
			block[i] ^= key[i];
	}
	
}