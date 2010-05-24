package p79068.util;

import p79068.lang.BoundsChecker;
import p79068.math.IntegerMath;


/**
 * Generates a stream of random numbers. True random number generators can be implemented, but generally, implementers of this class are pseudorandom number generators (PRNGs). PRNGs are deterministic and can reproduce the same sequence when given the same seed.
 * <p>Mutability: <em>Mutable</em><br>
 * Thread safety: <em>Unsafe</em>, unless otherwise specified</p>
 * <p>One usage example:</p>
 * <p>{@code int i = Random.DEFAULT.randomInt(10);  // Returns a number from 0 to 9 (inclusive) }</p>
 */
public abstract class Random {
	
	/**
	 * A default, thread-safe instance provided for convenience.
	 */
	public static final Random DEFAULT = new SynchronizedRandom(new MersenneTwister());
	
	
	/**
	 * Multiplying a 24-bit integer with this constant yields a {@code float} in [0, 1). This value is chosen so that all the mantissa bits in the {@code float} may be non-zero when the magnitude is in [0.5, 1).
	 */
	protected static final float floatScaler = 1.0F / (1 << 24);
	
	
	/**
	 * Multiplying a 53-bit integer with this constant yields a {@code double} in [0, 1). This value is chosen so that all the mantissa bits in the {@code double} may be non-zero when the magnitude is in [0.5, 1).
	 */
	protected static final double doubleScaler = 1.0D / (1L << 53);
	
	
	
	/**
	 * Returns a new random number generator instance from a default algorithm.
	 */
	public static Random newInstance() {
		return new MersenneTwister();
	}
	
	
	
	private double nextGaussian;
	
	private boolean hasNextGaussian;
	
	
	
	public Random() {
		nextGaussian = Double.NaN;
		hasNextGaussian = false;
	}
	
	
	
	/**
	 * Returns a random, uniformly distributed {@code boolean} value.
	 * @return <samp>true</samp> or <samp>false</samp>, each with equal probability
	 */
	public boolean randomBoolean() {
		return (randomInt() & 1) != 0;
	}
	
	
	/**
	 * Returns a random, uniformly distributed {@code int} value.
	 * @return a value in the range of {@code int}, each with equal probability
	 */
	public abstract int randomInt();
	
	
	/**
	 * Returns a random, uniformly distributed integer between 0 (inclusive) and {@code n} (exclusive). {@code n} must be positive.
	 * @return an integer in the range [0, {@code n}), each with equal probability
	 * @throws IllegalArgumentException if {@code n} &le; 0
	 */
	public int randomInt(int n) {
		if (n <= 0)
			throw new IllegalArgumentException();
		if (IntegerMath.isPowerOf2(n))
			return randomInt() & (n - 1);
		else {  // Unbiased
			int random;
			int result;
			do {
				random = randomInt() & 0x7FFFFFFF;
				result = random % n;
			} while (random - result + (n - 1) < 0);
			return result;
		}
	}
	
	
	/**
	 * Returns a random, uniformly distributed {@code long} value.
	 * @return a value in the range of {@code long}, each with equal probability
	 */
	public long randomLong() {
		return (long)randomInt() << 32 | randomInt() & 0xFFFFFFFFL;
	}
	
	
	/**
	 * Returns a random {@code float} value uniformly distributed between 0.0 (inclusive) and 1.0 (exclusive). The granularity is unspecified.
	 * @return a {@code float} in the range [0, 1), each with equal probability
	 */
	public float randomFloat() {
		return (randomInt() & 0xFFFFFF) * floatScaler;
	}
	
	
	/**
	 * Returns a random {@code double} value uniformly distributed between 0.0 (inclusive) and 1.0 (exclusive). The granularity is unspecified.
	 * @return a {@code double} in the range [0, 1), each with equal probability
	 */
	public double randomDouble() {
		return ((randomInt() & 0xFFFFFFFFL) << 21 | randomInt() & 0x1FFFFFL) * doubleScaler;
	}
	
	
	/**
	 * Places random, uniformly distributed {@code byte} values into the specified array.
	 */
	public void randomBytes(byte[] b) {
		randomBytes(b, 0, b.length);
	}
	
	
	/**
	 * Places random, uniformly distributed {@code byte} values into the specified array.
	 */
	public void randomBytes(byte[] b, int off, int len) {
		BoundsChecker.check(b.length, off, len);
		int end = off + len;
		
		// Fill until off is a multiple of 4, if necessary (fewer than 4 iterations)
		for (int rand = randomInt(); off % 4 != 0; off++, rand >>>= 8)
			b[off] = (byte)rand;
		
		// Fill efficiently, 4 bytes at a time
		for (int end4 = end / 4 * 4; off < end4; off += 4) {
			int rand = randomInt();
			b[off | 0] = (byte)(rand >>> 24);
			b[off | 1] = (byte)(rand >>> 16);
			b[off | 2] = (byte)(rand >>>  8);
			b[off | 3] = (byte)(rand >>>  0);
		}
		
		// Fill the last few bytes (fewer than 4 iterations)
		for (int rand = randomInt(); off < end; off++, rand >>>= 8)
			b[off] = (byte)rand;
	}
	
	
	/**
	 * Returns a random {@code double} with a Gaussian (<q>normal</q>) distribution of mean 0.0 and standard deviation 1.0.
	 * <p>To obtain a Gaussian-distributed value with mean {@code m} and standard deviation {@code s}, use this expression: {@code randomGaussian()*s + m}</p>
	 * <p>Note that the probability of producing a number outside of [&minus;10, 10] is 10<sup>&minus;23</sup>; the probability of producing a number outside of [&minus;15, 15] is 10<sup>&minus;50</sup> (i.e., effectively impossible). (Assuming that the underlying random number generator is unbiased.)</p>
	 * @return a {@code double} with a Gaussian distribution of mean 0.0 and standard deviation 1.0
	 */
	public double randomGaussian() {  // Uses the Box-Muller transform
		if (!hasNextGaussian) {
			double x, y;
			double magsqr;
			// Use rejection sampling to pick a point uniformly distributed in the unit circle
			do {
				x = randomDouble() * 2 - 1;
				y = randomDouble() * 2 - 1;
				magsqr = x * x + y * y;
			} while (magsqr >= 1 || magsqr == 0);
			double temp = Math.sqrt(-2 * Math.log(magsqr) / magsqr);
			nextGaussian = y * temp;
			hasNextGaussian = true;
			return x * temp;
		} else {
			hasNextGaussian = false;
			return nextGaussian;
		}
	}
	
	
	/**
	 * Returns a random {@code double} with an exponential distribution of mean 1.
	 * <p>To obtain a exponentially distributed value with mean {@code lambda}, use this expression: {@code randomExponential() / lambda}</p>
	 * @return a {@code double} with an exponential distribution of mean 1.
	 */
	public double randomExponential() {
		return -Math.log(randomDouble());
	}
	
	
	/**
	 * Returns this random number generator wrapped as a {@code java.util.Random} instance.
	 * <p>Actions performed on the returned object will affect this object, and vice versa.</p>
	 */
	public java.util.Random asJavaRandom() {
		return new JavaRandomAdapter(this);
	}
	
}