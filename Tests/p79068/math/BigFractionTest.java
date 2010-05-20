package p79068.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public final class BigFractionTest {
	
	@Test
	public void testEquals() {
		assertEquals(BigFraction.ZERO, new BigFraction(0, 2));
		assertEquals(BigFraction.ZERO, new BigFraction(0, -1));
		assertEquals(BigFraction.ONE, new BigFraction(5, 5));
		assertEquals(BigFraction.ONE, new BigFraction(-8, -8));
		assertEquals(new BigFraction(3, 2), new BigFraction(9, 6));
		assertEquals(new BigFraction(-5, 7), new BigFraction(5, -7));
	}
	
	
	@Test
	public void testAdd() {
		assertEquals(BigFraction.ZERO, BigFraction.ZERO.add(BigFraction.ZERO));
		assertEquals(new BigFraction(2), BigFraction.ONE.add(BigFraction.ONE));
		assertEquals(new BigFraction(5, 6), new BigFraction(1, 2).add(new BigFraction(1, 3)));
		assertEquals(new BigFraction(-49, 45), new BigFraction(-19, 18).add(new BigFraction(-1, 30)));
	}
	
	
	@Test
	public void testMultiply() {
		assertEquals(BigFraction.ZERO, BigFraction.ZERO.multiply(BigFraction.ONE));
		assertEquals(BigFraction.ONE, BigFraction.ONE.multiply(BigFraction.ONE));
		assertEquals(new BigFraction(15), new BigFraction(3).multiply(new BigFraction(5)));
		assertEquals(new BigFraction(35, 6), new BigFraction(5, 3).multiply(new BigFraction(7, 2)));
		assertEquals(new BigFraction(-4, 3), new BigFraction(-1, 18).multiply(new BigFraction(24, 1)));
	}
	
	
	@Test(expected=ArithmeticException.class)
	public void testDivideInvalid() {
		BigFraction.ONE.divide(BigFraction.ZERO);
	}
	
	
	@Test
	public void testNegate() {
		assertEquals(BigFraction.ZERO, BigFraction.ZERO.negate());
		assertEquals(new BigFraction(-1), BigFraction.ONE.negate());
		assertEquals(new BigFraction(2), new BigFraction(-2).negate());
		assertEquals(new BigFraction(-3, 5), new BigFraction(3, 5).negate());
	}
	
	
	@Test(expected=ArithmeticException.class)
	public void testReciprocalInvalid() {
		BigFraction.ZERO.reciprocal();
	}
	
	
	@Test
	public void testCompareTo() {
		assertTrue(new BigFraction(0).compareTo(new BigFraction(0)) == 0);
		assertTrue(new BigFraction(1, 2).compareTo(new BigFraction(1, 2)) == 0);
		assertTrue(new BigFraction(4, 4).compareTo(new BigFraction(1, 1)) == 0);
		assertTrue(new BigFraction(2, 3).compareTo(new BigFraction(3, 2)) < 0);
		assertTrue(new BigFraction(5, 2).compareTo(new BigFraction(1, 5)) > 0);
		assertTrue(new BigFraction(-1, 2).compareTo(new BigFraction(1, 2)) < 0);
		assertTrue(new BigFraction(-4, 3).compareTo(new BigFraction(1, 2)) < 0);
		assertTrue(new BigFraction(-2, 3).compareTo(new BigFraction(-7, 5)) > 0);
	}
	
}