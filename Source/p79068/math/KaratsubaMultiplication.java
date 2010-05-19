package p79068.math;

import java.math.BigInteger;
import p79068.lang.NullChecker;


public final class KaratsubaMultiplication {
	
	// Requirement: CUTOFF >= 64, or else there will be infinite recursion.
	private static final int CUTOFF = 2048;
	
	
	
	public static BigInteger multiply(BigInteger x, BigInteger y) {
		NullChecker.check(x, y);
		if (x.signum() < 0 && y.signum() < 0) {
			return privateMultiply(x.negate(), y.negate());
		} else if (x.signum() < 0 && y.signum() >= 0) {
			return privateMultiply(x.negate(), y).negate();
		} else if (x.signum() >= 0 && y.signum() < 0) {
			return privateMultiply(x, y.negate()).negate();
		} else {  // Main case. x >= 0, y >= 0.
			return privateMultiply(x, y);
		}
	}
	
	
	// Assumes x >= 0 and y >= 0, so that it does not need to be repeatedly checked.
	private static BigInteger privateMultiply(BigInteger x, BigInteger y) {
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
			BigInteger a = privateMultiply(xhigh, yhigh);
			BigInteger b = privateMultiply(xlow.add(xhigh), ylow.add(yhigh));
			BigInteger c = privateMultiply(xlow, ylow);
			BigInteger d = b.subtract(a).subtract(c);
			return c.add(d.shiftLeft(half)).add(a.shiftLeft(half * 2));
		}
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private KaratsubaMultiplication() {}
	
}