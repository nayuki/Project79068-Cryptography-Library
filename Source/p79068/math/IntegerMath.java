package p79068.math;


/**
 * Contains methods for math functions that deal with integers.
 * <p>Instantiability: <em>Not applicable</em></p>
 */
public final class IntegerMath {
	
	// Basic operations
	
	/**
	 * Returns the sum of the specified integers, throwing an exception if the result overflows.
	 * @param x a summand
	 * @param y a summand
	 * @return {@code x} plus {@code y}
	 * @throws ArithmeticOverflowException if the result overflows
	 */
	public static int safeAdd(int x, int y) {
		int z = x + y;
		if (y > 0 && z < x || y < 0 && z > x)
			throw new ArithmeticOverflowException(String.format("%d + %d", x, y));
		else
			return z;
	}
	
	
	/**
	 * Returns the product of the specified integers, throwing an exception if the result overflows.
	 * @param x a multiplicand
	 * @param y a multiplicand
	 * @return {@code x} times {@code y}
	 * @throws ArithmeticOverflowException if the result overflows
	 */
	public static int safeMultiply(int x, int y) {
		long z = (long)x * y;
		if (z >= Integer.MIN_VALUE && z <= Integer.MAX_VALUE)
			return x * y;
		else
			throw new ArithmeticOverflowException(String.format("%d * %d", x, y));
	}
	
	
	/**
	 * Returns the quotient of the specified integers, throwing an exception if the result overflows. The only overflow case is when {@code x} = &minus;2<sup>31</sup> and {@code y} = &minus;1.
	 * @param x the dividend
	 * @param y the divisor
	 * @return {@code x} divided by {@code y}
	 * @throws ArithmeticOverflowException if the result overflows
	 */
	public static int safeDivide(int x, int y) {
		if (x == Integer.MIN_VALUE && y == -1)
			throw new ArithmeticOverflowException(String.format("%d / %d", x, y));
		else
			return x / y;
	}
	
	
	/**
	 * Returns the floor of the quotient of the specified integers.
	 * @param x the dividend
	 * @param y the divisor
	 * @return the floor of {@code x} divided by {@code y}
	 * @throws ArithmeticException if {@code y} is 0
	 * @throws ArithmeticOverflowException if {@code x} = &minus;2<sup>31</sup> and {@code y} = &minus;1
	 */
	public static int divideAndFloor(int x, int y) {
		if (x == Integer.MIN_VALUE && y == -1)  // The one and only overflow case
			throw new ArithmeticOverflowException(String.format("divideAndFloor(%d, %d)", x, y));
		else if ((x >= 0) == (y >= 0))
			return x / y;  // If they have the same sign, then result is positive and already floored
		else {
			int z = x / y;
			if (z * y == x)
				return z;
			else
				return z - 1;
		}
	}
	
	
	/**
	 * Returns {@code x} modulo {@code y}. The result either has the same sign as {@code y} or is zero. Note that this is not exactly the same as the remainder operator ({@code %}) provided by the language.
	 * <p>Sample values:</p>
	 * <ul>
	 *   <li>{@code mod( 4, &nbsp;3) = &nbsp;1}</li>
	 *   <li>{@code mod(-4, &nbsp;3) = &nbsp;2}</li>
	 *   <li>{@code mod( 4, -3) = -2}</li>
	 *   <li>{@code mod(-4, -3) = -1}</li>
	 * </ul>
	 * @param x the integer to reduce
	 * @param y the modulus
	 * @return {@code x} modulo {@code y}
	 * @throws ArithmeticException if {@code y} is 0
	 */
	public static int mod(int x, int y) {
		x %= y;  // x is now in (-abs(y), abs(y))
		if (y > 0 && x < 0 || y < 0 && x > 0)
			x += y;
		return x;
	}
	
	
	
	// Simple functions
	
	/**
	 * Returns the sign of the specified integer, which is {@code -1}, {@code 0}, or {@code 1}. Also called sgn and signum.
	 * @param x the integer to whose sign will be computed
	 */
	public static int sign(int x) {
		return (x >> 31) | ((-x) >>> 31);
	}
	
	
	/**
	 * Compares the specified integers.
	 * @return {@code -1} if {@code x &lt; y}, {@code 0} if {@code x == y}, or {@code 1} if {@code x &gt; y}
	 */
	public static int compare(int x, int y) {
		if (x < y)
			return -1;
		else if (x > y)
			return 1;
		else
			return 0;
	}
	
	
	/**
	 * Compares the specified unsigned integers.
	 * @param x an operand, interpreted as an unsigned 32-bit integer
	 * @param y an operand, interpreted as an unsigned 32-bit integer
	 * @return {@code -1} if {@code x &lt; y}, {@code 0} if {@code x == y}, or {@code 1} if {@code x &gt; y}
	 */
	public static int compareUnsigned(int x, int y) {
		return compare(x ^ (1 << 31), y ^ (1 << 31));  // Flip top bits
	}
	
	
	/**
	 * Returns the integer in the specified range (inclusive) nearest to the specified integer. In other words, if {@code x &lt; min} then {@code min} is returned; if {@code x &gt; max} then {@code max} is returned; otherwise {@code x} is returned. This function is equivalent to {@code Math.max(Math.min(x, max), min)}.
	 * @param x the integer to clamp
	 * @param min the lower limit (inclusive)
	 * @param max the upper limit (inclusive)
	 * @return {@code min}, {@code x}, or {@code max}, whichever is closest to {@code x}
	 * @throws IllegalArgumentException if {@code min &gt; max}
	 */
	public static int clamp(int x, int min, int max) {
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
	 * Tests whether the specified number is an integral power of 2. The powers of 2 are 1, 2, 4, ..., 1073741824. Zero and negative numbers are not powers of 2.
	 * @param x the integer to test
	 * @return {@code true} if and only if {@code x} is an integral power of 2
	 */
	public static boolean isPowerOf2(int x) {
		return x > 0 && (x & (x - 1)) == 0;
	}
	
	
	
	// Elementary functions
	
	/**
	 * Returns the floor of the square root of the specified number.
	 * <p>Sample values:</p>
	 * <ul>
	 *  <li>{@code sqrt(4) = 2}</li>
	 *  <li>{@code sqrt(5) = 2}</li>
	 *  <li>{@code sqrt(9) = 3}</li>
	 * </ul>
	 * @throws IllegalArgumentException if {@code x &lt; 0}
	 */
	public static int sqrt(int x) {
		if (x < 0)
			throw new IllegalArgumentException("Square root of negative number");
		int y = 0;
		for (int i = 32768; i >= 1; i /= 2) {
			y += i;
			if (y > 46340 || y * y > x)
				y -= i;
		}
		return y;
	}
	
	
	/**
	 * Returns the cube root of the specified number, rounded down towards zero.
	 * <p>Sample values:</p>
	 * <ul>
	 *  <li>{@code cbrt(1) = 1}</li>
	 *  <li>{@code cbrt(5) = 1}</li>
	 *  <li>{@code cbrt(8) = 2}</li>
	 * </ul>
	 */
	public static int cbrt(int x) {
		if (x == -2147483648)
			return -1290;
		if (x < 0)
			return -cbrt(-x);
		int y = 0;
		for (int i = 1024; i >= 1; i /= 2) {
			y += i;
			if (y > 1290 || y * y * y > x)
				y -= i;
		}
		return y;
	}
	
	
	/**
	 * Returns the floor of the base 2 logarithm of the specified number. The result is in the range [0, 30].
	 * @param x the integer to log and floor
	 * @return the floor of the base 2 logarithm of the number
	 * @throws IllegalArgumentException if {@code x} &le; 0
	 */
	public static int log2Floor(int x) {
		if (x <= 0)
			throw new IllegalArgumentException("Argument must be positive");
		x |= x >>>  1;
		x |= x >>>  2;
		x |= x >>>  4;
		x |= x >>>  8;
		x |= x >>> 16;
		return IntegerBitMath.countOnes(x) - 1;
	}
	
	
	/**
	 * Returns the ceiling of the base 2 logarithm of the specified number. The result is in the range [0, 31].
	 * @param x the integer to log and ceiling
	 * @return the ceiling of the base 2 logarithm of the number
	 * @throws IllegalArgumentException if {@code x} &le; 0
	 */
	public static int log2Ceiling(int x) {
		if (x <= 0)
			throw new IllegalArgumentException("Argument must be positive");
		x--;
		x |= x >>>  1;
		x |= x >>>  2;
		x |= x >>>  4;
		x |= x >>>  8;
		x |= x >>> 16;
		return IntegerBitMath.countOnes(x);
	}
	
	
	/**
	 * Returns the nearest power of 2 that is less than or equal to the specified number.
	 * @param x the integer to floor to a power of 2
	 * @return a power of 2 less than or equal to {@code x}
	 * @throws IllegalArgumentException if {@code x &lt;= 0}
	 */
	public static int floorToPowerOf2(int x) {
		if (x <= 0)
			throw new IllegalArgumentException("Non-positive argument");
		x |= x >>>  1;
		x |= x >>>  2;
		x |= x >>>  4;
		x |= x >>>  8;
		x |= x >>> 16;
		return x ^ (x >>> 1);
	}
	
	
	/**
	 * Returns the nearest power of 2 that is greater than or equal to the specified number.
	 * @param x the integer to ceiling to a power of 2
	 * @return a power of 2 greater than or equal to {@code x}
	 * @throws IllegalArgumentException if {@code x &lt;= 0}
	 * @throws ArithmeticOverflowException if {@code x &gt; 1073741824}
	 */
	public static int ceilingToPowerOf2(int x) {
		if (x <= 0)
			throw new IllegalArgumentException("Non-positive argument");
		if (x > 1073741824)
			throw new ArithmeticOverflowException(String.format("ceilingToPowerOf2(%d)", x));
		x--;
		x |= x >>>  1;
		x |= x >>>  2;
		x |= x >>>  4;
		x |= x >>>  8;
		x |= x >>> 16;
		return x + 1;
	}
	
	
	
	// Modular arithmetic functions
	
	/**
	 * Returns the integer {@code y} such that {@code x} {@code y} mod m = 1 (assuming the calculation does not overflow). {@code y} exists if and only if {@code gcd(x, m)} = 1. If {@code y} exists, then {@code y} is in the range [0, {@code m}).
	 * @param x the integer to reciprocate
	 * @param m the modulus
	 * @throws ArithmeticException if {@code x} = 0
	 * @throws IllegalArgumentException if {@code m} &lt; 1
	 * @throws IllegalArgumentException if a reciprocal does no exist (because {@code gcd(x, m)} &ne; 1)
	 */
	public static int reciprocalMod(int x, int m) {
		if (x == 0)
			throw new ArithmeticException("Division by zero");
		if (m < 1)
			throw new IllegalArgumentException("Invalid modulus");
		int y = x;
		x = m;
		int a = 0;
		int b = 1;
		while (true) {  // Extended Euclidean algorithm
			int z = x % y;
			if (z == 0) {
				if (y == 1)  // GCD is 1; reciprocal exists
					return mod(b, m);
				else
					throw new IllegalArgumentException("Reciprocal does not exist");
			}
			int c = a - x / y * b;
			x = y;
			y = z;
			a = b;
			b = c;
		}
	}
	
	
	/**
	 * Returns {@code x} to the power of {@code y}, modulo {@code m}.
	 * @param x the base of the power
	 * @param y the exponent of the power
	 * @param m the modulus
	 * @return {@code x}<sup>{@code y}</sup> mod {@code m}
	 * @throws IllegalArgumentException if {@code y} &lt; 0
	 * @throws IllegalArgumentException if {@code x} has no reciprocal modulo {@code m}
	 */
	public static int powMod(int x, int y, int m) {
		if (y < 0)
			return powMod(reciprocalMod(x, m), -y, m);
		int z = 1;
		for (; y != 0; y >>>= 1, x = x * x % m) {
			if ((y & 1) != 0)
				z = z * x % m;
		}
		return z;
	}
	
	
	
	// Number theory functions
	
	/**
	 * Returns the greatest common divisor (GCD) of the specified integers. If <var>z</var> is the GCD of <var>x</var> and <var>y</var>, then <var>z</var> is the largest number such that <var>x</var>/<var>z</var> and <var>y</var>/<var>z</var> are integers.
	 */
	public static int gcd(int x, int y) {
		if (x == Integer.MIN_VALUE && y == Integer.MIN_VALUE)
			throw new ArithmeticOverflowException(String.format("gcd(%d, %d)", x, y));
		else {
			if (x == Integer.MIN_VALUE)
				x /= 2;
			else if (y == Integer.MIN_VALUE)
				y /= 2;
			if (x < 0)
				x = -x;
			if (y < 0)
				y = -y;
			while (y != 0) {
				int z = x % y;
				x = y;
				y = z;
			}
			return x;
		}
	}
	
	
	/**
	 * Returns the least common multiple (LCM) of the specified integers. If <var>z</var> is the LCM of <var>x</var> and <var>y</var>, then <var>z</var> is the smallest non-zero number such that <var>z</var>/<var>x</var> and <var>z</var>/<var>y</var> are integers.
	 * @throws ArithmeticOverflowException if the result overflows
	 */
	public static int lcm(int x, int y) {
		try {
			return safeMultiply(x / gcd(x, y), y);
		} catch (ArithmeticOverflowException e) {
			throw new ArithmeticOverflowException(String.format("lcm(%d, %d)", x, y));
		}
	}
	
	
	/**
	 * Returns Euler's totient function of the specified integer. This is the number of integers between {@code 1} (inclusive) and {@code x} (inclusive) that are coprime to {@code x}. Note that 1 is coprime to all integers, and {@code totient(1) == 1}.
	 * @throws IllegalArgumentException if {@code x} &le; 0
	 */
	public static int totient(int x) {
		if (x <= 0)
			throw new IllegalArgumentException("Totient of non-positive integer");
		int p = 1;
		for (int i = 2, end = sqrt(x); i <= end; i++) {  // Trial division
			if (x % i == 0) {  // Found a factor
				p *= i - 1;
				x /= i;
				while (x % i == 0) {
					p *= i;
					x /= i;
				}
				end = sqrt(x);
			}
		}
		if (x != 1)
			p *= x - 1;
		return p;
	}
	
	
	
	// Primality functions
	
	/**
	 * Tests whether the specified integer is a prime number. Note that 0 and 1 are not prime.
	 * @param x the integer to test
	 * @return {@code true} if {@code x} is a prime number, {@code false} otherwise
	 * @throws IllegalArgumentException if {@code x &lt; 0}
	 */
	public static boolean isPrime(int x) {
		if (x < 0)
			throw new IllegalArgumentException("Undefined for negative integers");
		else if (x == 0 || x == 1)
			return false;
		else if (x == 2)
			return true;
		else if (x % 2 == 0)
			return false;
		else {
			for (int i = 3, end = sqrt(x); i <= end; i += 2) {  // Trial division
				if (x % i == 0)
					return false;
			}
			return true;
		}
	}
	
	
	/**
	 * Tests whether the specified integer is a composite number. Note that 0 and 1 are not composite.
	 * @param x the integer to test
	 * @return {@code true} if {@code x} is a composite number, {@code false} otherwise
	 * @throws IllegalArgumentException if {@code x &lt; 0}
	 */
	public static boolean isComposite(int x) {
		if (x == 0 || x == 1)
			return false;
		else
			return !isPrime(x);
	}
	
	
	/**
	 * Returns an array where each element denotes whether its index is a prime number; integers up to {@code n} (inclusive) can be tested. That is, {@code result[n]} has the value {@code true} if {@code n} is prime.
	 * <p>Internally, the sieve of Eratosthenes is used.</p>
	 * @param n the highest number (inclusive) that can be tested by the returned array
	 * @return an array of length {@code n+1} where each element denotes whether its index is prime
	 * @throws IllegalArgumentException if {@code n &lt; 0}
	 */
	public static boolean[] listPrimality(int n) {
		if (n < 0)
			throw new IllegalArgumentException();
		boolean[] prime = new boolean[n + 1];
		if (n >= 2)
			prime[2] = true;
		for (int i = 3; i <= n; i += 2)
			prime[i] = true;
		for (int i = 3, end = sqrt(n); i <= end; i += 2) {
			if (prime[i]) {
				for (int j = i * 3; j <= n; j += i << 1)
					prime[j] = false;
			}
		}
		return prime;
	}
	
	
	
	// Combinatorics functions
	
	/**
	 * Returns the factorial of the specified integer.
	 * @throws IllegalArgumentException if {@code x} &lt; 0
	 * @throws ArithmeticOverflowException if {@code x} &gt; 20
	 */
	public static int factorial(int x) {
		if (x < 0)
			throw new IllegalArgumentException("Factorial of negative integer");
		if (x > 12)
			throw new ArithmeticOverflowException(String.format("factorial(%d)", x));
		int p = 1;
		for (; x >= 2; x--)
			p *= x;
		return p;
	}
	
	
	/**
	 * Returns the number of ways of obtaining an ordered subset of {@code k} elements from a set of {@code n} (unique) elements. *plagiarism
	 * @param n the size of the set
	 * @param k the size of the subset to take
	 * @throws ArithmeticOverflowException if the result overflows
	 */
	public static int permutation(int n, int k) {
		int p = 1;
		for (; k >= 1; n--, k--) {
			if (Integer.MAX_VALUE / p < n)
				throw new ArithmeticOverflowException(String.format("permutation(%d, %d)", n, k));
			p *= n;
		}
		return p;
	}
	
	
	
	// Miscellaneous functions
	
	/**
	 * Returns the element of the Fibonacci sequence at the specified index.
	 * <p>Sample values:</p>
	 * <ul>
	 *  <li>{@code fibonacci(-4) == -3}</li>
	 *  <li>{@code fibonacci(-3) == &nbsp;2}</li>
	 *  <li>{@code fibonacci(-2) == -1}</li>
	 *  <li>{@code fibonacci(-1) == &nbsp;1}</li>
	 *  <li>{@code fibonacci( 0) == &nbsp;0}</li>
	 *  <li>{@code fibonacci( 1) == &nbsp;1}</li>
	 *  <li>{@code fibonacci( 2) == &nbsp;1}</li>
	 *  <li>{@code fibonacci( 3) == &nbsp;2}</li>
	 *  <li>{@code fibonacci( 4) == &nbsp;3}</li>
	 * </ul>
	 * @throws ArithmeticOverflowException if the result overflows (because {@code x} &lt; &minus;46 or {@code x} &gt; 46)
	 */
	public static int fibonacci(int x) {
		if (x < -46 || x > 46)
			throw new ArithmeticOverflowException(String.format("fibonacci(%d)", x));
		if (x >= 0) {
			int a = 0;
			int b = 1;
			for (int i = 0; i < x; i++) {
				int c = a + b;
				a = b;
				b = c;
			}
			return a;
		} else {
			if (x % 2 == 0)
				return -fibonacci(-x);
			else
				return fibonacci(-x);
		}
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private IntegerMath() {}
	
}