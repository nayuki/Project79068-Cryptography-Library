package p79068.math;


/**
 * Contains methods for math functions that deal with long integers.
 * <p>Instantiability: <em>Not applicable</em></p>
 * @see IntegerMath
 */
public final class LongMath {
	
	// Basic operations
	
	/**
	 * Returns the sum of the specified integers, throwing an exception if the result overflows.
	 * @param x a summand
	 * @param y a summand
	 * @return <code>x</code> plus <code>y</code>
	 * @throws ArithmeticOverflowException if the result overflows
	 */
	public static long checkedAdd(long x, long y) {
		long z = x + y;
		if (y > 0 && z < x || y < 0 && z > x)
			throw new ArithmeticOverflowException(String.format("%d + %d", x, y));
		else
			return z;
	}
	
	
	/**
	 * Returns the product of the specified integers, throwing an exception if the result overflows.
	 * @param x a multiplicand
	 * @param y a multiplicand
	 * @return <code>x</code> times <code>y</code>
	 * @throws ArithmeticOverflowException if the result overflows
	 */
	public static long checkedMultiply(long x, long y) {
		Int128 z = new Int128(x).multiply(new Int128(y));
		if (z.high == z.low >> 63)  // Equivalent to z >= Long.MIN_VALUE && z <= Long.MAX_VALUE
			return z.low;
		else
			throw new ArithmeticOverflowException(String.format("%d * %d", x, y));
	}
	
	
	/**
	 * Returns the quotient of the specified integers, throwing an exception if the result overflows. The only overflow case is when <code>x</code> = &minus;2<sup>31</sup> and <code>y</code> = &minus;1.
	 * @param x the dividend
	 * @param y the divisor
	 * @return <code>x</code> divided by <code>y</code>
	 * @throws ArithmeticOverflowException if the result overflows
	 */
	public static long checkedDivide(long x, long y) {
		if (x == Long.MIN_VALUE && y == -1)
			throw new ArithmeticOverflowException(String.format("%d / %d", x, y));
		else
			return x / y;
	}
	
	
	/**
	 * Returns the floor of the quotient of the specified integers.
	 * @param x the dividend
	 * @param y the divisor
	 * @return the floor of <code>x</code> divided by <code>y</code>
	 * @throws ArithmeticException if <code>y</code> is 0
	 * @throws ArithmeticOverflowException if <code>x</code> = &minus;2<sup>63</sup> and <code>y</code> = &minus;1
	 */
	public static long divideAndFloor(long x, long y) {
		if (x == Long.MIN_VALUE && y == -1)  // The one and only overflow case
			throw new ArithmeticOverflowException(String.format("divideAndFloor(%d, %d)", x, y));
		else if ((x >= 0) == (y >= 0))  // If both have the same sign, then result is positive and already floored
			return x / y;
		else {
			long z = x / y;
			if (z * y == x)
				return z;
			else
				return z - 1;
		}
	}
	
	
	/**
	 * Returns <code>x</code> modulo <code>y</code>. The result either has the same sign as <code>y</code> or is zero. Note that this is not exactly the same as the remainder operator (<code>%</code>) provided by the language.
	 * @param x the integer to reduce
	 * @param y the modulus
	 * @return <code>x</code> modulo <code>y</code>
	 * @throws ArithmeticException if <code>y</code> is 0
	 */
	public static long mod(long x, long y) {
		x %= y;  // x is now in (-abs(y), abs(y))
		if (y > 0 && x < 0 || y < 0 && x > 0)
			x += y;
		return x;
	}
	
	
	
	// Simple functions
	
	/**
	 * Returns the sign of the specified integer, which is <samp>-1</samp>, <samp>0</samp>, or <samp>1</samp>.
	 * @param x the integer to whose sign will be computed
	 */
	public static int sign(long x) {
		return (int)(x >> 63) | (int)((-x) >>> 63);
	}
	
	
	/**
	 * Compares two integers without overflowing.
	 * @return <samp>-1</samp> if <code>x &lt; y</code>, <samp>0</samp> if <code>x == y</code>, or <samp>1</samp> if <code>x &gt; y</code>
	 */
	public static int compare(long x, long y) {
		if (x < y)
			return -1;
		else if (x > y)
			return 1;
		else
			return 0;
	}
	
	
	/**
	 * Compares two unsigned integers without overflowing.
	 * @param x an operand, interpreted as an unsigned 64-bit integer
	 * @param y an operand, interpreted as an unsigned 64-bit integer
	 * @return <samp>-1</samp> if <code>x &lt; y</code>, <samp>0</samp> if <code>x == y</code>, or <samp>1</samp> if <code>x &gt; y</code>
	 */
	public static int compareUnsigned(long x, long y) {
		return compare(x ^ (1L << 63), y ^ (1L << 63));  // Flip top bits
	}
	
	
	/**
	 * Returns the integer in the specified range (inclusive) nearest to the specified integer. In other words, if <code>x &lt; min</code> then <code>min</code> is returned; if <code>x &gt; max</code> then <code>max</code> is returned; otherwise <code>x</code> is returned. This function is equivalent to <code>Math.max(Math.min(x, max), min)</code>.
	 * @param x the integer to clamp
	 * @param min the lower limit (inclusive)
	 * @param max the upper limit (inclusive)
	 * @return <code>min</code>, <code>x</code>, or <code>max</code>, whichever is closest to <code>x</code>
	 * @throws IllegalArgumentException if <code>min &gt; max</code>
	 */
	public static long clamp(long x, long min, long max) {
		if (min > max)
			throw new IllegalArgumentException("Minimum greater than maximum");
		else if (x < min)
			return min;
		else if (x > max)
			return max;
		else
			return x;
	}
	
	
	/**
	 * Tests whether the specified integer is a power of 2. The powers of 2 are 1, 2, 4, ..., 4611686018427387904.
	 * <p>Note that Long.MIN_VALUE (-9223372036854775808) is not a power of 2 because it is negative.</p>
	 * 
	 * @param x the integer to test
	 * @return <samp>true</samp> if x is positive and is a power of 2
	 */
	public static boolean isPowerOf2(long x) {
		return x > 0 && (x & (x - 1)) == 0;
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private LongMath() {}
	
}