package p79068.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class IntegerMathTest {
	
	@Test
	public void testSqrt() {
		assertEquals(0, IntegerMath.sqrt(0));
		assertEquals(1, IntegerMath.sqrt(1));
		assertEquals(1, IntegerMath.sqrt(2));
		assertEquals(1, IntegerMath.sqrt(3));
		assertEquals(2, IntegerMath.sqrt(4));
		assertEquals(2, IntegerMath.sqrt(6));
		assertEquals(3, IntegerMath.sqrt(9));
		assertEquals(3, IntegerMath.sqrt(15));
		assertEquals(46339, IntegerMath.sqrt(2147395599));
		assertEquals(46340, IntegerMath.sqrt(2147395600));
		assertEquals(46340, IntegerMath.sqrt(2147483647));
	}
	
	
	@Test
	public void testCbrt() {
		assertEquals(0, IntegerMath.cbrt(0));
		assertEquals(1, IntegerMath.cbrt(1));
		assertEquals(1, IntegerMath.cbrt(2));
		assertEquals(1, IntegerMath.cbrt(3));
		assertEquals(2, IntegerMath.cbrt(8));
		assertEquals(2, IntegerMath.cbrt(9));
		assertEquals(2, IntegerMath.cbrt(16));
		assertEquals(3, IntegerMath.cbrt(27));
		assertEquals(3, IntegerMath.cbrt(28));
		assertEquals(1289, IntegerMath.cbrt(2146688999));
		assertEquals(1290, IntegerMath.cbrt(2146689000));
		assertEquals(1290, IntegerMath.cbrt(2147483647));
		
		assertEquals(-1, IntegerMath.cbrt(-1));
		assertEquals(-1, IntegerMath.cbrt(-4));
		assertEquals(-1, IntegerMath.cbrt(-7));
		assertEquals(-2, IntegerMath.cbrt(-8));
		assertEquals(-2, IntegerMath.cbrt(-13));
		assertEquals(-2, IntegerMath.cbrt(-24));
		assertEquals(-3, IntegerMath.cbrt(-27));
		assertEquals(-3, IntegerMath.cbrt(-50));
		assertEquals(-1289, IntegerMath.cbrt(-2146688999));
		assertEquals(-1290, IntegerMath.cbrt(-2146689000));
		assertEquals(-1290, IntegerMath.cbrt(-2147483648));
	}
	
	
	@Test
	public void testIsPrime() {
		assertFalse(IntegerMath.isPrime( 0));
		assertFalse(IntegerMath.isPrime( 1));
		assertTrue(IntegerMath.isPrime( 2));
		assertTrue(IntegerMath.isPrime( 3));
		assertFalse(IntegerMath.isPrime( 4));
		assertTrue(IntegerMath.isPrime( 5));
		assertFalse(IntegerMath.isPrime( 6));
		assertTrue(IntegerMath.isPrime( 7));
		assertFalse(IntegerMath.isPrime( 8));
		assertFalse(IntegerMath.isPrime( 9));
		assertFalse(IntegerMath.isPrime(10));
		assertTrue(IntegerMath.isPrime(11));
		assertFalse(IntegerMath.isPrime(12));
		assertTrue(IntegerMath.isPrime(13));
	}
	
	
	@Test
	public void testIsComposite() {
		assertFalse(IntegerMath.isComposite( 0));
		assertFalse(IntegerMath.isComposite( 1));
		assertFalse(IntegerMath.isComposite( 2));
		assertFalse(IntegerMath.isComposite( 3));
		assertTrue(IntegerMath.isComposite( 4));
		assertFalse(IntegerMath.isComposite( 5));
		assertTrue(IntegerMath.isComposite( 6));
		assertFalse(IntegerMath.isComposite( 7));
		assertTrue(IntegerMath.isComposite( 8));
		assertTrue(IntegerMath.isComposite( 9));
		assertTrue(IntegerMath.isComposite(10));
		assertFalse(IntegerMath.isComposite(11));
		assertTrue(IntegerMath.isComposite(12));
		assertFalse(IntegerMath.isComposite(13));
	}
	
	
	@Test
	public void testClamp() {
		assertEquals(5, IntegerMath.clamp(5, 0, 10));
		assertEquals(10, IntegerMath.clamp(12, 0, 10));
		assertEquals(0, IntegerMath.clamp(-7, 0, 10));
	}
	
	
	@Test
	public void testSign() {
		assertEquals( 0, IntegerMath.sign(0));
		
		assertEquals(+1, IntegerMath.sign(1));
		assertEquals(+1, IntegerMath.sign(2));
		assertEquals(+1, IntegerMath.sign(3));
		assertEquals(+1, IntegerMath.sign(700));
		assertEquals(+1, IntegerMath.sign(Integer.MAX_VALUE - 1));
		assertEquals(+1, IntegerMath.sign(Integer.MAX_VALUE));
		
		assertEquals(-1, IntegerMath.sign(-1));
		assertEquals(-1, IntegerMath.sign(-2));
		assertEquals(-1, IntegerMath.sign(-3));
		assertEquals(-1, IntegerMath.sign(-50));
		assertEquals(-1, IntegerMath.sign(Integer.MIN_VALUE + 1));
		assertEquals(-1, IntegerMath.sign(Integer.MIN_VALUE));
	}
	
	
	@Test
	public void testIsPowerOf2() {
		assertTrue(IntegerMath.isPowerOf2(1));
		assertTrue(IntegerMath.isPowerOf2(2));
		assertTrue(IntegerMath.isPowerOf2(4));
		assertTrue(IntegerMath.isPowerOf2(8));
		assertTrue(IntegerMath.isPowerOf2(536870912));
		assertTrue(IntegerMath.isPowerOf2(1073741824));
		
		assertFalse(IntegerMath.isPowerOf2(0));
		assertFalse(IntegerMath.isPowerOf2(3));
		assertFalse(IntegerMath.isPowerOf2(5));
		assertFalse(IntegerMath.isPowerOf2(6));
		assertFalse(IntegerMath.isPowerOf2(7));
		assertFalse(IntegerMath.isPowerOf2(1073741823));
		assertFalse(IntegerMath.isPowerOf2(1073741825));
		assertFalse(IntegerMath.isPowerOf2(2147483647));
		
		assertFalse(IntegerMath.isPowerOf2(-1));
		assertFalse(IntegerMath.isPowerOf2(-2));
		assertFalse(IntegerMath.isPowerOf2(-3));
		assertFalse(IntegerMath.isPowerOf2(-4));
		assertFalse(IntegerMath.isPowerOf2(-2147483648));
	}
	
	
	@Test
	public void testFloorToPowerOf2() {
		assertEquals(1, IntegerMath.floorToPowerOf2(1));
		assertEquals(2, IntegerMath.floorToPowerOf2(2));
		assertEquals(2, IntegerMath.floorToPowerOf2(3));
		assertEquals(4, IntegerMath.floorToPowerOf2(4));
		assertEquals(4, IntegerMath.floorToPowerOf2(5));
		assertEquals(4, IntegerMath.floorToPowerOf2(6));
		assertEquals(4, IntegerMath.floorToPowerOf2(7));
		assertEquals(8, IntegerMath.floorToPowerOf2(8));
		assertEquals(536870912, IntegerMath.floorToPowerOf2(1073741823));
		assertEquals(1073741824, IntegerMath.floorToPowerOf2(1073741824));
		assertEquals(1073741824, IntegerMath.floorToPowerOf2(2147483647));
	}
	
	
	@Test
	public void testCeilingToPowerOf2() {
		assertEquals(1, IntegerMath.ceilingToPowerOf2(1));
		assertEquals(2, IntegerMath.ceilingToPowerOf2(2));
		assertEquals(4, IntegerMath.ceilingToPowerOf2(3));
		assertEquals(4, IntegerMath.ceilingToPowerOf2(4));
		assertEquals(8, IntegerMath.ceilingToPowerOf2(5));
		assertEquals(8, IntegerMath.ceilingToPowerOf2(6));
		assertEquals(8, IntegerMath.ceilingToPowerOf2(7));
		assertEquals(8, IntegerMath.ceilingToPowerOf2(8));
		assertEquals(1073741824, IntegerMath.ceilingToPowerOf2(1073741823));
		assertEquals(1073741824, IntegerMath.ceilingToPowerOf2(1073741824));
	}
	
	
	@Test
	public void testCompareUnsigned() {
		assertTrue(IntegerMath.compareUnsigned(13, 72) < 0);
		assertTrue(IntegerMath.compareUnsigned(0xFFFFFFFF, 0x00000000) > 0);
		assertTrue(IntegerMath.compareUnsigned(0xFFFFFFFF, 0x80000000) > 0);
		assertTrue(IntegerMath.compareUnsigned(0x00000000, 0x80000000) < 0);
	}
	
}