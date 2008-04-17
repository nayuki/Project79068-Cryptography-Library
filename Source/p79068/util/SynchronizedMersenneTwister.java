package p79068.util;


/**
 * The Mersenne Twister pseudorandom number generator, thread-safe version.
 * <p>Mutability: <em>Mutable</em><br>
 * Thread safety: <em>Safe</em></p>
 * @see MersenneTwister
 * @see Random
 */
public final class SynchronizedMersenneTwister extends Random {
	
	private int[] state;
	private int index;
	
	private Object lock = new Object();
	
	
	public SynchronizedMersenneTwister() {
		this(toInt32s(new long[]{System.currentTimeMillis(), System.nanoTime()}));
	}
	
	public SynchronizedMersenneTwister(int seed) {
		setSeed(seed);
	}
	
	public SynchronizedMersenneTwister(long seed) {
		this(toInt32s(new long[]{seed}));
	}
	
	public SynchronizedMersenneTwister(int[] seed) {
		setSeed(seed);
	}
	
	
	public int randomInt() {
		int x;
		synchronized (lock) {
			if (index == 624)
				nextState();
			x = state[index];
			index++;
		}
		x ^= x >>> 11;
		x ^= (x << 7) & 0x9D2C5680;
		x ^= (x << 15) & 0xEFC60000;
		return x ^ (x >>> 18);
	}
	
	
	private void setSeed(int seed) {
		if (state == null)
			state = new int[624];
		for (index = 0; index < 624; index++) {
			state[index] = seed;
			seed = 1812433253 * (seed ^ (seed >>> 30)) + index + 1;
		}
	}
	
	private void setSeed(int[] seed) {
		setSeed(19650218);
		int i = 1;
		for (int j = 0, k = 0; k < Math.max(624, seed.length); k++) {
			state[i] = (state[i] ^ ((state[i - 1] ^ (state[i - 1] >>> 30)) * 1664525)) + seed[j] + j;
			i++;
			j++;
			if (i == 624) {
				state[0] = state[623];
				i = 1;
			}
			if (j >= seed.length)
				j = 0;
		}
		for (int k = 0; k < 623; k++) {
			state[i] = (state[i] ^ ((state[i - 1] ^ (state[i - 1] >>> 30)) * 1566083941)) - i;
			i++;
			if (i == 624) {
				state[0] = state[623];
				i = 1;
			}
		}
		state[0] = 0x80000000;
	}
	
	
	private void nextState() {
		int k = 0;
		for (; k < 227; k++) {
			int y = (state[k] & 0x80000000) | (state[k + 1] & 0x7FFFFFFF);
			state[k] = state[k + 397] ^ (y >>> 1) ^ ((y & 1) * 0x9908B0DF);
		}
		for (; k < 623; k++) {
			int y = (state[k] & 0x80000000) | (state[k + 1] & 0x7FFFFFFF);
			state[k] = state[k - 227] ^ (y >>> 1) ^ ((y & 1) * 0x9908B0DF);
		}
		int y = (state[623] & 0x80000000) | (state[0] & 0x7FFFFFFF);
		state[623] = state[396] ^ (y >>> 1) ^ ((y & 1) * 0x9908B0DF);
		index = 0;
	}
	
	
	private static int[] toInt32s(long[] in) {
		int[] out = new int[in.length * 2];
		for (int i = 0; i < in.length; i++) {
			out[i * 2 + 0] = (int)(in[i] >>> 32);
			out[i * 2 + 1] = (int)(in[i] >>> 0);
		}
		return out;
	}
	
}