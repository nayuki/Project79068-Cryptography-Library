package p79068.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import p79068.util.Random;


public class DoubleBitMathTest {
	
	private static final double POSITIVE_ZERO = +0.0;
	private static final double NEGATIVE_ZERO = -0.0;
	
	
	
	@Test
	public void getSign() {
		assertEquals( 0, DoubleBitMath.getSign(POSITIVE_ZERO));
		assertEquals( 0, DoubleBitMath.getSign(NEGATIVE_ZERO));
		assertEquals(+1, DoubleBitMath.getSign(+1));
		assertEquals(-1, DoubleBitMath.getSign(-1));
		assertEquals(+1, DoubleBitMath.getSign(Double.MIN_VALUE));
		assertEquals(+1, DoubleBitMath.getSign(Double.MIN_NORMAL));
		assertEquals(+1, DoubleBitMath.getSign(Double.MAX_VALUE));
		assertEquals(+1, DoubleBitMath.getSign(Double.POSITIVE_INFINITY));
		assertEquals(-1, DoubleBitMath.getSign(Double.NEGATIVE_INFINITY));
	}
	
	
	@Test
	public void getExponent() {
		assertEquals(0, DoubleBitMath.getExponent(0));
		assertEquals(-1, DoubleBitMath.getExponent(0.5));
		assertEquals(0, DoubleBitMath.getExponent(1));
		assertEquals(1, DoubleBitMath.getExponent(2));
		assertEquals(1, DoubleBitMath.getExponent(3));
		assertEquals(2, DoubleBitMath.getExponent(4));
		assertEquals(-1022, DoubleBitMath.getExponent(Double.MIN_VALUE));
		assertEquals(1023, DoubleBitMath.getExponent(Double.MAX_VALUE));
		assertEquals(1, DoubleBitMath.getExponent(Math.PI));
		assertEquals(1, DoubleBitMath.getExponent(Math.E));
	}
	
	
	@Test
	public void getMantissa() {
		assertEquals(0, DoubleBitMath.getMantissa(0));
		assertEquals(0x10000000000000L, DoubleBitMath.getMantissa(0.5));
		assertEquals(0x10000000000000L, DoubleBitMath.getMantissa(1));
		assertEquals(0x10000000000000L, DoubleBitMath.getMantissa(2));
		assertEquals(0x18000000000000L, DoubleBitMath.getMantissa(3));
		assertEquals(0x14000000000000L, DoubleBitMath.getMantissa(5));
		assertEquals(1, DoubleBitMath.getMantissa(Double.MIN_VALUE));
		assertEquals(0x1FFFFFFFFFFFFFL, DoubleBitMath.getMantissa(Double.MAX_VALUE));
	}
	
	
	@Test
	public void sgnManExpRelation() {
		assertTrue(testRelation(POSITIVE_ZERO));
		assertTrue(testRelation(NEGATIVE_ZERO));
		assertTrue(testRelation(+1));
		assertTrue(testRelation(-1));
		assertTrue(testRelation(+Double.MIN_VALUE));
		assertTrue(testRelation(+Double.MAX_VALUE));
		assertTrue(testRelation(-Double.MIN_VALUE));
		assertTrue(testRelation(-Double.MAX_VALUE));
		assertTrue(testRelation(+Math.PI));
		assertTrue(testRelation(+Math.E));
		assertTrue(testRelation(-Math.PI));
		assertTrue(testRelation(-Math.E));
		for (int i = 0; i < 1000; i++)
			assertTrue(testRelation(Random.DEFAULT.randomDouble()));
	}
	
	
	@Test
	public void testGetZeroSign() {
		assertEquals(+1, DoubleBitMath.getZeroSign(POSITIVE_ZERO));
		assertEquals(-1, DoubleBitMath.getZeroSign(NEGATIVE_ZERO));
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetZeroSignInvalidNonZero() {
		DoubleBitMath.getZeroSign(1);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetZeroSignInvalidInfinity() {
		DoubleBitMath.getZeroSign(Double.POSITIVE_INFINITY);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetZeroSignInvalidNaN() {
		DoubleBitMath.getZeroSign(Double.NaN);
	}
	
	
	@Test
	public void isNormal() {
		assertFalse(DoubleBitMath.isNormal(Double.MIN_VALUE));
		assertFalse(DoubleBitMath.isNormal(Double.MIN_VALUE * 5));
		assertFalse(DoubleBitMath.isNormal(0));
		assertTrue(DoubleBitMath.isNormal(1));
		assertTrue(DoubleBitMath.isNormal(Math.PI));
		assertTrue(DoubleBitMath.isNormal(Math.E));
		assertTrue(DoubleBitMath.isNormal(Double.MAX_VALUE));
		assertFalse(DoubleBitMath.isNormal(Double.POSITIVE_INFINITY));
		assertFalse(DoubleBitMath.isNormal(Double.NaN));
	}
	
	
	@Test
	public void isSubnormal() {
		assertTrue(DoubleBitMath.isSubnormal(Double.MIN_VALUE));
		assertTrue(DoubleBitMath.isSubnormal(Double.MIN_VALUE * 3));
		assertFalse(DoubleBitMath.isSubnormal(0));
		assertFalse(DoubleBitMath.isSubnormal(1));
		assertFalse(DoubleBitMath.isSubnormal(Math.PI));
		assertFalse(DoubleBitMath.isSubnormal(Math.E));
		assertFalse(DoubleBitMath.isSubnormal(Double.MAX_VALUE));
		assertFalse(DoubleBitMath.isSubnormal(Double.POSITIVE_INFINITY));
		assertFalse(DoubleBitMath.isSubnormal(Double.NaN));
	}
	
	
	@Test
	public void isFinite() {
		assertTrue(DoubleBitMath.isFinite(Double.MIN_VALUE));
		assertTrue(DoubleBitMath.isFinite(0));
		assertTrue(DoubleBitMath.isFinite(1));
		assertTrue(DoubleBitMath.isFinite(Math.PI));
		assertTrue(DoubleBitMath.isFinite(Double.MAX_VALUE));
		assertFalse(DoubleBitMath.isFinite(Double.POSITIVE_INFINITY));
		assertFalse(DoubleBitMath.isFinite(Double.NEGATIVE_INFINITY));
		assertFalse(DoubleBitMath.isFinite(Double.NaN));
	}
	
	
	@Test
	public void isInfinite() {
		assertFalse(DoubleBitMath.isInfinite(Double.MIN_VALUE));
		assertFalse(DoubleBitMath.isInfinite(0));
		assertFalse(DoubleBitMath.isInfinite(1));
		assertFalse(DoubleBitMath.isInfinite(Math.PI));
		assertFalse(DoubleBitMath.isInfinite(Double.MAX_VALUE));
		assertTrue(DoubleBitMath.isInfinite(Double.POSITIVE_INFINITY));
		assertTrue(DoubleBitMath.isInfinite(Double.NEGATIVE_INFINITY));
		assertFalse(DoubleBitMath.isInfinite(Double.NaN));
	}
	
	
	@Test
	public void isNaN() {
		assertFalse(DoubleBitMath.isNaN(Double.MIN_VALUE));
		assertFalse(DoubleBitMath.isNaN(0));
		assertFalse(DoubleBitMath.isNaN(1));
		assertFalse(DoubleBitMath.isNaN(Math.PI));
		assertFalse(DoubleBitMath.isNaN(Double.MAX_VALUE));
		assertFalse(DoubleBitMath.isNaN(Double.POSITIVE_INFINITY));
		assertFalse(DoubleBitMath.isNaN(Double.NEGATIVE_INFINITY));
		assertTrue(DoubleBitMath.isNaN(Double.NaN));
	}
	
	
	
	private static boolean testRelation(double x) {
		int sgn = DoubleBitMath.getSign(x);
		long man = DoubleBitMath.getMantissa(x);
		int exp = DoubleBitMath.getExponent(x);
		return x == toDouble(sgn, man, exp);
	}
	
	
	private static double toDouble(int signum, long mantissa, int exponent) {
		if (signum < -1 || signum > 1 || mantissa < 0 || mantissa >= (1L << 53) || exponent < -1022 || exponent > 1023)
			throw new IllegalArgumentException();
		return (double)signum * mantissa * Math.pow(2, exponent - 52);
	}
	
}