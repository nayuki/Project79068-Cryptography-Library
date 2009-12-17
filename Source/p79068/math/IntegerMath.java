package p79068.math;


/**
 * Contains methods for math functions that deal with integers.
 * <p>Instantiability: <em>Not applicable</em></p>
 */
public final class IntegerMath {
	
	// Elementary functions
	
	/**
	 * Returns the square root of the specified integer, rounded down to the nearest integer.
	 * <p>Sample values:</p>
	 * <ul>
	 *  <li><code>sqrt(4) = 2</code></li>
	 *  <li><code>sqrt(5) = 2</code></li>
	 *  <li><code>sqrt(9) = 3</code></li>
	 * </ul>
	 * @throws IllegalArgumentException if <code>x &lt; 0</code>
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
	 * Returns the cube root of the specified integer, rounded down to the nearest integer.
	 * <p>Sample values:</p>
	 * <ul>
	 *  <li><code>cbrt(1) = 1</code></li>
	 *  <li><code>cbrt(5) = 1</code></li>
	 *  <li><code>cbrt(8) = 2</code></li>
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
	
	
	
	// Combinatorics functions
	
	/**
	 * Returns the factorial of the specified integer.
	 * @throws IllegalArgumentException if <code>x &lt; 0</code>
	 * @throws ArithmeticOverflowException if <code>x &gt; 20</code>
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
	 * Returns the number of ways of obtaining an ordered subset of <code>k</code> elements from a set of <code>n</code> (unique) elements. *plagiarism
	 * @param n the size of the set
	 * @param k the size of the subset to take
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
	
	
	
	// Modular arithmetic functions
	
	/**
	 * Returns <code>x</code> modulo <code>y</code>. The result either has the same sign as <code>y</code> or is zero. Note that this is not exactly the same as the remainder operator (<code>%</code>) provided by the language.
	 * <p>Sample values:</p>
	 * <ul>
	 *  <li><code>mod( 4, &nbsp;3) = &nbsp;1</code></li>
	 *  <li><code>mod(-4, &nbsp;3) = &nbsp;2</code></li>
	 *  <li><code>mod( 4, -3) = -2</code></li>
	 *  <li><code>mod(-4, -3) = -1</code></li>
	 * </ul>
	 * @param x the integer to reduce
	 * @param y the modulus
	 * @return <code>x</code> mod <code>y</code>
	 * @throws ArithmeticException if <code>y</code> is zero
	 */
	public static int mod(int x, int y) {
		x %= y;  // x is now in (-abs(y), abs(y))
		if (y > 0 && x < 0 || y < 0 && x > 0)
			x += y;
		return x;
	}
	
	
	/**
	 * Returns the integer <code>y</code> such that <code>(x * y) mod m == 1</code> (when there is no overflow).
	 * @param x the integer to reciprocate
	 * @param m the modulus
	 * @throws IllegalArgumentException if <code>x == 0</code> or if a reciprocal does no exist
	 */
	public static int reciprocalMod(int x, int m) {
		if (x == 0)
			throw new IllegalArgumentException("Division by zero");
		if (m < 2)
			throw new IllegalArgumentException("Reciprocal does not exist");
		int y = x;
		x = m;
		int a = 0, b = 1;
		while (true) {  // Extended Euclidean algorithm
			int z = x % y;
			if (z == 0) {
				if (y == 1)
					return mod(b, m);  // GCD is 1; reciprocal exists
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
	 * Returns <code>x</code> to the power of <code>y</code>, modulo <code>m</code>.
	 * @param x the base of the power
	 * @param y the exponent of the power
	 * @param m the modulus
	 * @return <code>x</code><sup><code>y</code></sup> mod <code>m</code>
	 * @throws IllegalArgumentException if <code>y &lt; 0</code> and <code>x</code> has no reciprocal
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
		while (y != 0) {
			int z = x % y;
			x = y;
			y = z;
		}
		return x;
	}
	
	
	/**
	 * Returns the lowest common multiple (LCM) of the specified integers. If <var>z</var> is the LCM of <var>x</var> and <var>y</var>, then <var>z</var> is the smallest non-zero number such that <var>z</var>/<var>x</var> and <var>z</var>/<var>y</var> are integers.
	 * @throws ArithmeticOverflowException if the result cannot be represented as a 32-bit integer
	 */
	public static int lcm(int x, int y) {
		return x / gcd(x, y) * y;
	}
	
	
	/**
	 * Returns Euler's totient function of the specified integer. This is the number of integers between <code>1</code> (inclusive) and <code>x</code> (inclusive) that are coprime to <code>x</code>. Note that 1 is coprime to all integers, and <code>totient(1) == 1</code>.
	 * @throws IllegalArgumentException if <code>x &lt;= 0</code>
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
	
	
	/**
	 * Tests whether the specified integer is a prime number. Note that 0 and 1 are not prime.
	 * @param x the integer to test
	 * @return <samp>true</samp> if <code>x</code> is a prime number, <samp>false</samp> otherwise
	 * @throws IllegalArgumentException if <code>x &lt; 0</code>
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
	 * @return <samp>true</samp> if <code>x</code> is a composite number, <samp>false</samp> otherwise
	 * @throws IllegalArgumentException if <code>x &lt; 0</code>
	 */
	public static boolean isComposite(int x) {
		if (x == 0 || x == 1)
			return false;
		else
			return !isPrime(x);
	}
	
	
	/**
	 * Returns an array where each element denotes whether its index is a prime number; integers up to <code>n</code> (inclusive) can be tested. That is, <code>result[n]</code> has the value <samp>true</samp> if <code>n</code> is prime.
	 * <p>Internally, the sieve of Eratosthenes is used.</p>
	 * @param n the highest number (inclusive) that can be tested by the returned array
	 * @return an array of length <code>n+1</code> where each element denotes whether its index is prime
	 * @throws IllegalArgumentException if <code>n &lt; 0</code>
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
	
	
	
	// Miscellaneous functions
	
	/**
	 * Returns the integer in the specified range (inclusive) nearest to the specified integer. In other words, if <code>x &lt; min</code> then <code>min</code> is returned; if <code>x &gt; max</code> then <code>max</code> is returned; otherwise <code>x</code> is returned. This function is equivalent to <code>Math.max(Math.min(x, max), min)</code>.
	 * @param x the integer to clamp
	 * @param min the lower limit (inclusive)
	 * @param max the upper limit (inclusive)
	 * @return <code>min</code>, <code>x</code>, or <code>max</code>, whichever is closest to <code>x</code>
	 * @throws IllegalArgumentException if <code>min &gt; max</code>
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
	 * Returns the sign of the specified integer, which is <samp>-1</samp>, <samp>0</samp>, or <samp>1</samp>.
	 */
	public static int sign(int x) {
		return (x >> 31) | ((-x) >>> 31);
	}
	
	
	/**
	 * Returns the floor of the quotient of the specified integers.
	 */
	public static int divideAndFloor(int x, int y) {
		if (x == -2147483648 && y == -1)
			throw new ArithmeticOverflowException(String.format("divideAndFloor(%d, %d)", x, y));
		else if ((x >= 0) == (y >= 0))
			return x / y;  // If they have the same sign
		else {
			int z = x / y;
			if (z * y == x)
				return z;
			else
				return z - 1;
		}
	}
	
	
	/**
	 * Returns the element of the Fibonacci sequence at the specified index.
	 * <p>Sample values:</p>
	 * <ul>
	 *  <li><code>fibonacci(-4) == -3</code></li>
	 *  <li><code>fibonacci(-3) == &nbsp;2</code></li>
	 *  <li><code>fibonacci(-2) == -1</code></li>
	 *  <li><code>fibonacci(-1) == &nbsp;1</code></li>
	 *  <li><code>fibonacci( 0) == &nbsp;0</code></li>
	 *  <li><code>fibonacci( 1) == &nbsp;1</code></li>
	 *  <li><code>fibonacci( 2) == &nbsp;1</code></li>
	 *  <li><code>fibonacci( 3) == &nbsp;2</code></li>
	 *  <li><code>fibonacci( 4) == &nbsp;3</code></li>
	 * </ul>
	 * @throws ArithmeticOverflowException if <code>x &lt; -46</code> or <code>x &gt; 46</code>
	 */
	public static int fibonacci(int x) {
		if (x < -46 || x > 46)
			throw new ArithmeticOverflowException(String.format("fibonacci(%d)", x));
		int a = 1;
		int b = 0;
		int c = 1;
		if (x >= 0) {
			for (; x >= 1; x--) {
				a = b;
				b = c;
				c = a + b;
			}
		} else {
			for (; x < 0; x++) {
				c = b;
				b = a;
				a = c - b;
			}
		}
		return b;
	}
	
	
	/**
	 * Tests whether the specified integer is a power of 2. The powers of 2 are 1, 2, 4, ..., 1073741824.
	 * @param x the integer to test
	 * @return <samp>true</samp> if x is an <code>integral</code> power of 2
	 */
	public static boolean isPowerOf2(int x) {
		return x > 0 && (x & (x - 1)) == 0;
	}
	
	
	/**
	 * Returns the nearest power of 2 that is less than or equal to the specified integer.
	 * @param x the integer to floor to a power of 2
	 * @return a power of 2 less than or equal to <code>x</code>
	 * @throws IllegalArgumentException if <code>x &lt;= 0</code>
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
	 * Returns the nearest power of 2 that is greater than or equal to the specified integer.
	 * @param x the integer to ceiling to a power of 2
	 * @return a power of 2 greater than or equal to <code>x</code>
	 * @throws IllegalArgumentException if <code>x &lt;= 0</code>
	 * @throws ArithmeticOverflowException if <code>x &gt; 1073741824</code>
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
	
	
	/**
	 * Compares two integers without overflowing.
	 * @return <samp>-1</samp> if <code>x &lt; y</code>, <samp>0</samp> if <code>x == y</code>, or <samp>1</samp> if <code>x &gt; y</code>
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
	 * Compares two unsigned integers without overflowing.
	 * @param x an operand, interpreted as an unsigned 32-bit integer
	 * @param y an operand, interpreted as an unsigned 32-bit integer
	 * @return <samp>-1</samp> if <code>x &lt; y</code>, <samp>0</samp> if <code>x == y</code>, or <samp>1</samp> if <code>x &gt; y</code>
	 */
	public static int compareUnsigned(int x, int y) {
		return compare(x ^ (1 << 31), y ^ (1 << 31));  // Flip top bits
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private IntegerMath() {}
	
}