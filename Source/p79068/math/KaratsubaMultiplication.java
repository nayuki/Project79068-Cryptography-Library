package p79068.math;

import java.math.BigInteger;


public final class KaratsubaMultiplication {
	
	private static final int CUTOFF = 2048;
	
	static {
		if (CUTOFF < 64)
			throw new AssertionError();
	}
	
	
	public static BigInteger multiply(BigInteger x, BigInteger y) {
		if (x.signum() < 0 && y.signum() < 0) {
			return multiply(x.negate(), y.negate());
		} else if (x.signum() < 0 && y.signum() >= 0) {
			return multiply(x.negate(), y).negate();
		} else if (x.signum() >= 0 && y.signum() < 0) {
			return multiply(x, y.negate()).negate();
			
		} else if (x.bitLength() <= CUTOFF || y.bitLength() <= CUTOFF) {  // Base case
			return x.multiply(y);
			
		} else {  // Main case. x >= 0, y >= 0.
			int n = Math.max(x.bitLength(), y.bitLength());
			int half = (n + 32) / 64 * 32;
			BigInteger mask = BigInteger.ONE.shiftLeft(half).subtract(BigInteger.ONE);
			BigInteger xlow = x.and(mask);
			BigInteger ylow = y.and(mask);
			BigInteger xhigh = x.shiftRight(half);
			BigInteger yhigh = y.shiftRight(half);
			BigInteger a = multiply(xhigh, yhigh);
			BigInteger b = multiply(xlow.add(xhigh), ylow.add(yhigh));
			BigInteger c = multiply(xlow, ylow);
			BigInteger d = b.subtract(a).subtract(c);
			return c.add(d.shiftLeft(half)).add(a.shiftLeft(half * 2));
		}
	}
	
}