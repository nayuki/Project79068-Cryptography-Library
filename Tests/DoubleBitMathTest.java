import static org.junit.Assert.*;
import org.junit.Test;
import p79068.math.DoubleBitMath;
import p79068.util.Random;


public class DoubleBitMathTest {
	
	private static final double POSITIVE_ZERO = 0;
	private static final double NEGATIVE_ZERO = 1 / (-1.0 / 0.0);
	
	
	@Test
	public void getSign() {
		assertEquals(DoubleBitMath.getSign(POSITIVE_ZERO), 0);
		assertEquals(DoubleBitMath.getSign(NEGATIVE_ZERO), 0);
		assertEquals(DoubleBitMath.getSign(+1), +1);
		assertEquals(DoubleBitMath.getSign(-1), -1);
	}
	
	@Test
	public void getExponent() {
		assertEquals(DoubleBitMath.getExponent(0), -1022);
		assertEquals(DoubleBitMath.getExponent(0.5), -1);
		assertEquals(DoubleBitMath.getExponent(1), 0);
		assertEquals(DoubleBitMath.getExponent(2), 1);
		assertEquals(DoubleBitMath.getExponent(3), 1);
		assertEquals(DoubleBitMath.getExponent(4), 2);
		assertEquals(DoubleBitMath.getExponent(Double.MIN_VALUE), -1022);
		assertEquals(DoubleBitMath.getExponent(Double.MAX_VALUE), 1023);
		assertEquals(DoubleBitMath.getExponent(Math.PI), 1);
		assertEquals(DoubleBitMath.getExponent(Math.E), 1);
	}
	
	@Test
	public void getMantissa() {
		assertEquals(DoubleBitMath.getMantissa(0), 0);
		assertEquals(DoubleBitMath.getMantissa(0.5), 0x10000000000000L);
		assertEquals(DoubleBitMath.getMantissa(1), 0x10000000000000L);
		assertEquals(DoubleBitMath.getMantissa(2), 0x10000000000000L);
		assertEquals(DoubleBitMath.getMantissa(3), 0x18000000000000L);
		assertEquals(DoubleBitMath.getMantissa(4), 0x10000000000000L);
		assertEquals(DoubleBitMath.getMantissa(Double.MIN_VALUE), 1);
		assertEquals(DoubleBitMath.getMantissa(Double.MAX_VALUE), 0x1FFFFFFFFFFFFFL);
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
		assertEquals(DoubleBitMath.isSubnormal(0), false);
		assertEquals(DoubleBitMath.isSubnormal(1), false);
		assertEquals(DoubleBitMath.isSubnormal(Double.MIN_VALUE), true);
		assertEquals(DoubleBitMath.isSubnormal(Double.MIN_VALUE * 3), true);
		assertEquals(DoubleBitMath.isSubnormal(Double.MAX_VALUE), false);
		assertEquals(DoubleBitMath.isSubnormal(Double.POSITIVE_INFINITY), false);
		assertEquals(DoubleBitMath.isSubnormal(Math.PI), false);
		assertEquals(DoubleBitMath.isSubnormal(Math.E), false);
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
