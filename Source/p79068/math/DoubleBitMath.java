package p79068.math;


/**
 * Contains methods that deal with the bit representation of IEEE-754 double-precision floating-point numbers.
 */
public class DoubleBitMath {
	
	/**
	 * Returns the 1 sign bit of the specified number. Positive numbers have a sign of 0; negative numbers have a sign of 1.
	 * @param x the double-precision number
	 * @return the 1 sign bit
	 */
	public static int getRawSign(double x) {
		return (int)(Double.doubleToRawLongBits(x) >>> 63);
	}
	
	/**
	 * Returns the 11 exponent bits of the specified number.
	 * @param x the double-precision number
	 * @return the 11 exponent bits
	 */
	public static int getRawExponent(double x) {
		return (int)(Double.doubleToRawLongBits(x) >>> 52) & 0x7FF;
	}
	
	/**
	 * Returns the 52 mantissa bits of the specified number.
	 * @param x the double-precision number
	 * @return the 52 mantissa bits
	 */
	public static long getRawMantissa(double x) {
		return Double.doubleToRawLongBits(x) & 0xFFFFFFFFFFFFFL;
	}
	
	
	/**
	 * Returns the signum of the specified number. Positive and negative zero both have a signum of 0. For finite numbers, this relation holds: <code>x</code> = <code>getSign(x)</code> × (<code>getMantissa(x)</code> / 2<sup>52</sup>) × 2<sup><code>getExponent(x)</code></sup>.
	 * @param x the double-precision number
	 * @return the signum
	 */
	public static int getSign(double x) {
		if (x < 0)
			return -1;
		else if (x > 0)
			return 1;
		else if (x == 0)
			return 0;
		else
			throw new IllegalArgumentException("Not a finite floating-point number");
	}
	
	/**
	 * Returns the exponent of the specified number. For finite numbers, this relation holds: <code>x</code> = <code>getSign(x)</code> × (<code>getMantissa(x)</code> / 2<sup>52</sup>) × 2<sup><code>getExponent(x)</code></sup>.
	 * @param x the double-precision number
	 * @return the exponent
	 */
	public static int getExponent(double x) {
		int exp = getRawExponent(x);
		if (exp == 2047)
			throw new IllegalArgumentException("Not a finite floating-point number");
		else if (exp == 0)
			return exp - 1022; // Subnormal
		else
			return exp - 1023;
	}
	
	/**
	 * Returns the mantissa of the specified number. For finite numbers, this relation holds: <code>x</code> = <code>getSign(x)</code> × (<code>getMantissa(x)</code> / 2<sup>52</sup>) × 2<sup><code>getExponent(x)</code></sup>.
	 * @param x the double-precision number
	 * @return the mantissa
	 */
	public static long getMantissa(double x) {
		if (!isFinite(x))
			throw new IllegalArgumentException("Not a finite floating-point number");
		long man = getRawMantissa(x);
		if (!isSubnormal(x) && x != 0)
			return man | 0x10000000000000L;
		else
			return man;
	}
	
	/**
	 * Tests whether the specified number is subnormal. Zero is neither normal nor subnormal.
	 * @param x the double-precision number
	 * @return whether the specified number is subnormal
	 */
	public static boolean isSubnormal(double x) {
		if (x == 0)
			return false;
		return getRawExponent(x) == 0;
	}
	
	public static boolean isFinite(double x) {
		return !Double.isNaN(x) && !Double.isInfinite(x);
	}
	
}