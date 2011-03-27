package p79068.math;

import java.math.BigInteger;
import p79068.lang.NullChecker;


/**
 * Utility class for multiplying BigIntegers quickly, using the Karatsuba multiplication algorithm.
 */
public final class KaratsubaMultiplication {
	
	/**
	 * Returns {@code x * y}, the product of the specified integers. This gives the same result as {@code x.multiply(y)} but should be faster.
	 * @param x a multiplicand
	 * @param y a multiplicand
	 * @return {@code x} times {@code} y
	 * @throws NullPointerException if {@code x} or {@code y} is {@code null}
	 */
	public static BigInteger multiply(BigInteger x, BigInteger y) {
		NullChecker.check(x, y);
		if (x.signum() >= 0 && y.signum() >= 0) {
			return recursiveMultiply(x, y);
		} else if (x.signum() < 0 && y.signum() >= 0) {
			return recursiveMultiply(x.negate(), y).negate();
		} else if (x.signum() >= 0 && y.signum() < 0) {
			return recursiveMultiply(x, y.negate()).negate();
		} else {  // x < 0 && y < 0
			return recursiveMultiply(x.negate(), y.negate());
		}
	}
	
	
	// Requirement: CUTOFF >= 64, or else there will be infinite recursion.
	private static final int CUTOFF = 2048;
	
	
	// Requires x >= 0 and y >= 0
	private static BigInteger recursiveMultiply(BigInteger x, BigInteger y) {
		if (x.bitLength() <= CUTOFF || y.bitLength() <= CUTOFF) {  // Base case
			return x.multiply(y);
			
		} else {
			int n = Math.max(x.bitLength(), y.bitLength());
			int half = (n + 32) / 64 * 32;
			BigInteger mask = BigInteger.ONE.shiftLeft(half).subtract(BigInteger.ONE);
			BigInteger xlow = x.and(mask);
			BigInteger ylow = y.and(mask);
			BigInteger xhigh = x.shiftRight(half);
			BigInteger yhigh = y.shiftRight(half);
			
			BigInteger a = recursiveMultiply(xhigh, yhigh);
			BigInteger b = recursiveMultiply(xlow.add(xhigh), ylow.add(yhigh));
			BigInteger c = recursiveMultiply(xlow, ylow);
			BigInteger d = b.subtract(a).subtract(c);
			return c.add(d.shiftLeft(half)).add(a.shiftLeft(half * 2));
		}
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private KaratsubaMultiplication() {}
	
}