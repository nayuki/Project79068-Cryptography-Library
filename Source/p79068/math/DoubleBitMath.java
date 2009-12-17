package p79068.math;


/**
 * Contains methods that deal with the bit representation of IEEE-754 double-precision floating-point numbers.
 */
public class DoubleBitMath {
	
	// Raw bit extraction
	
	/**
	 * Returns the 1 sign bit of the bit representation of specified double-precision number. Positive numbers have a sign of 0; negative numbers have a sign of 1. All numbers are valid inputs.
	 * @param x the double-precision number
	 * @return the 1 sign bit
	 */
	public static int getRawSign(double x) {
		return (int)(Double.doubleToRawLongBits(x) >>> 63);
	}
	
	
	/**
	 * Returns the 11 exponent bits of the bit representation of specified double-precision number. All numbers are valid inputs. The result is in the range [0, 2<sup>11</sup>).
	 * @param x the double-precision number
	 * @return the 11 exponent bits
	 */
	public static int getRawExponent(double x) {
		return (int)(Double.doubleToRawLongBits(x) >>> 52) & 0x7FF;
	}
	
	
	/**
	 * Returns the 52 mantissa bits of the bit representation of specified double-precision number number. All numbers are valid inputs. The result is in the range [0, 2<sup>52</sup>).
	 * @param x the double-precision number
	 * @return the 52 mantissa bits
	 */
	public static long getRawMantissa(double x) {
		return Double.doubleToRawLongBits(x) & 0xFFFFFFFFFFFFFL;
	}
	
	
	
	// Managed values
	
	/**
	 * Returns the signum of the specified number. Positive and negative zero both have a signum of 0. For finite numbers, this relation holds: <code>x</code> = <code>getSign(x)</code> × (<code>getMantissa(x)</code> / 2<sup>52</sup>) × 2<sup><code>getExponent(x)</code></sup>. Infinities are valid inputs, but NaN is not a valid input.
	 * @param x the double-precision number
	 * @return the signum of x
	 * @throws IllegalArgumentException if x is NaN
	 */
	public static int getSign(double x) {
		if (x > 0)
			return 1;
		else if (x < 0)
			return -1;
		else if (x == 0)
			return 0;
		else
			throw new IllegalArgumentException("Not a finite floating-point number");
	}
	
	
	/**
	 * Returns the exponent of the specified number. For finite numbers, this relation holds: <code>x</code> = <code>getSign(x)</code> × (<code>getMantissa(x)</code> / 2<sup>52</sup>) × 2<sup><code>getExponent(x)</code></sup>. Zero yields the exponent 0. Infinities and NaN are invalid inputs. The result is in the range [-1022, 1023].
	 * @param x the double-precision number
	 * @return the exponent
	 * @throws IllegalArgumentException if x is infinite or NaN
	 */
	public static int getExponent(double x) {
		int exp = getRawExponent(x);
		if (exp == 2047)
			throw new IllegalArgumentException("Not a finite floating-point number");
		else if (exp > 0)
			return exp - 1023;
		else {  // Subnormal
			if (x == 0)
				return 0;
			else
				return exp - 1022;
		}
	}
	
	
	/**
	 * Returns the mantissa of the specified number. For finite numbers, this relation holds: <code>x</code> = <code>getSign(x)</code> × (<code>getMantissa(x)</code> / 2<sup>52</sup>) × 2<sup><code>getExponent(x)</code></sup>. Infinities and NaN are invalid inputs. The result is in the range [2<sup>52</sup>, 2<sup>53</sup>) for normal numbers and [0, 2<sup>52</sup>) for subnormal numbers.
	 * @param x the double-precision number
	 * @return the mantissa
	 * @throws IllegalArgumentException if x is infinite or NaN
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
	 * Returns the sign of the specified number it is zero. Returns 1 for positive zero and -1 for negative zero. Non-zero inputs are invalid.
	 * @param x the double-precision number
	 * @return the sign of the number if it is zero
	 * @throws IllegalArgumentException if x is not zero
	 */
	public static int getZeroSign(double x) {
		long bits = Double.doubleToRawLongBits(x);
		if ((bits << 1) != 0)
			throw new IllegalArgumentException("Number is not zero");
		return (int)(1 - (bits >>> 63) * 2);
	}
	
	
	
	// Tests
	
	/**
	 * Tests whether the specified number is normal. Zeros, infinities, and NaN are not normal. All numbers are valid inputs.
	 * @param x the double-precision number
	 * @return whether the specified number is normal
	 */
	public static boolean isNormal(double x) {
		int exp = getRawExponent(x);
		return exp >= 1 && exp <= 2046;
	}
	
	
	/**
	 * Tests whether the specified number is subnormal. Zeros, infinities, and NaN are not subnormal. All numbers are valid inputs.
	 * @param x the double-precision number
	 * @return whether the specified number is subnormal
	 */
	public static boolean isSubnormal(double x) {
		if (x == 0)
			return false;
		return getRawExponent(x) == 0;
	}
	
	
	/**
	 * Tests whether the specified number is finite. Infinities and NaN are not finite; all other numbers are. All numbers are valid inputs.
	 * @param x the double-precision number
	 * @return whether the specified number is finite
	 */
	public static boolean isFinite(double x) {
		return !isNaN(x) && !isInfinite(x);
	}
	
	
	/**
	 * Tests whether the specified number is infinite. All numbers are valid inputs.
	 * @param x the double-precision number
	 * @return whether the specified number is infinite
	 */
	public static boolean isInfinite(double x) {
		return x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY;
	}
	
	
	/**
	 * Tests whether the specified number is NaN (not a number). All numbers are valid inputs.
	 * @param x the double-precision number
	 * @return whether the specified number is NaN
	 */
	public static boolean isNaN(double x) {
		return x != x;
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private DoubleBitMath() {}
	
}