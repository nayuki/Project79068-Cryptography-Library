package math;

import static org.junit.Assert.*;
import org.junit.Test;
import p79068.math.DoubleBitMath;
import p79068.util.Random;


public class DoubleBitMathTest {
	
	private static final double POSITIVE_ZERO = 0;
	private static final double NEGATIVE_ZERO = 1 / (-1.0 / 0.0);
	
	
	
	@Test
	public void getSign() {
		assertEquals( 0, DoubleBitMath.getSign(POSITIVE_ZERO));
		assertEquals( 0, DoubleBitMath.getSign(NEGATIVE_ZERO));
		assertEquals(+1, DoubleBitMath.getSign(+1));
		assertEquals(-1, DoubleBitMath.getSign(-1));
	}
	
	
	@Test
	public void getExponent() {
		assertEquals(-1022, DoubleBitMath.getExponent(0));
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
		assertEquals(0x10000000000000L, DoubleBitMath.getMantissa(4));
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
	public void isSubnormal() {
		assertFalse(DoubleBitMath.isSubnormal(0));
		assertFalse(DoubleBitMath.isSubnormal(1));
		assertTrue(DoubleBitMath.isSubnormal(Double.MIN_VALUE));
		assertTrue(DoubleBitMath.isSubnormal(Double.MIN_VALUE * 3));
		assertFalse(DoubleBitMath.isSubnormal(Double.MAX_VALUE));
		assertFalse(DoubleBitMath.isSubnormal(Double.POSITIVE_INFINITY));
		assertFalse(DoubleBitMath.isSubnormal(Math.PI));
		assertFalse(DoubleBitMath.isSubnormal(Math.E));
	}
	
	
	
	private static boolean testRelation(double x) {
		int sgn = DoubleBitMath.getSign(x);
		long man = DoubleBitMath.getMantissa(x);
		int exp = DoubleBitMath.getExponent(x);
		return x == toDouble(sgn, man, exp);
	}
	
	
	private static double toDouble(int signum, long mantissa, int exponent) {
		return (double)signum * mantissa * Math.pow(2, exponent - 52);
	}
	
}