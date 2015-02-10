package p79068.hash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import p79068.math.IntegerMath;
import p79068.util.random.Random;


public final class HashValueTest {
	
	@Test
	public void testNewHex() {
		assertEquals(hv(), new HashValue(""));
		assertEquals(hv(0), new HashValue("00"));
		assertEquals(hv(-1), new HashValue("ff"));
		assertEquals(hv(-6, -50), new HashValue("FacE"));
		assertEquals(hv(49, 65, 89, 38), new HashValue("31415926"));
	}
	
	
	@Test
	public void testNewHexInvalid() {
		String[] cases = {
			"0",
			"ag",
			"-0",
			"+1",
			"abc",
			"[]",
			"@`",
			"12345&78",
		};
		for (String s : cases) {
			try {
				new HashValue(s);
				fail();
			} catch (NumberFormatException e) {}  // Pass
		}
	}
	
	
	@Test
	public void testHexStringRoundTrip() {
		for (int i = 0; i < 1000; i++) {
			byte[] b = new byte[rand.uniformInt(100)];
			rand.uniformBytes(b);
			HashValue h = new HashValue(b);
			assertEquals(h, new HashValue(h.toHexString()));
		}
	}
	
	
	@Test
	public void testEquals() {
		assertEquals(hv(), hv());
		assertEquals(hv(0), hv(0));
		assertEquals(hv(-1), hv(-1));
		assertEquals(hv(2, 1), hv(2, 1));
		assertFalse(hv().equals(hv(1)));
		assertFalse(hv(1).equals(hv(5)));
		assertFalse(hv(4, 3).equals(hv(4, 2)));
		assertFalse(hv(1, 2).equals(hv(2, 1)));
		assertFalse(hv(0, 0).equals(hv(0, 0, 0)));
	}
	
	
	@Test
	public void testEqualsRandomly() {
		for (int i = 0; i < 10000; i++) {
			byte[][] pair = randomByteArrayPair();
			assertTrue(new HashValue(pair[0]).equals(new HashValue(pair[1])) == Arrays.equals(pair[0], pair[1]));
		}
	}
	
	
	@Test
	public void testCompareTo() {
		assertTrue(hv().compareTo(hv()) == 0);
		assertTrue(hv(0).compareTo(hv(0)) == 0);
		assertTrue(hv(-1).compareTo(hv(-1)) == 0);
		assertTrue(hv(2, 1).compareTo(hv(2, 1)) == 0);
		assertTrue(hv().compareTo(hv(1)) < 0);
		assertTrue(hv(1).compareTo(hv()) > 0);
		assertTrue(hv(1).compareTo(hv(5)) < 0);
		assertTrue(hv(5).compareTo(hv(1)) > 0);
		assertTrue(hv(1).compareTo(hv(-1)) < 0);
		assertTrue(hv(-1).compareTo(hv(1)) > 0);
		assertTrue(hv(4, 3).compareTo(hv(4, 2)) > 0);
		assertTrue(hv(1, 2).compareTo(hv(2, 1)) < 0);
		assertTrue(hv(0, 0).compareTo(hv(0, 0, 0)) < 0);
	}
	
	
	@Test
	public void testCompareToRandomly() {
		for (int i = 0; i < 100000; i++) {
			byte[][] pair = randomByteArrayPair();
			byte[] first = pair[0];
			byte[] second = pair[1];
			
			int comp = 0;
			for (int j = 0; j < first.length && j < second.length; j++) {
				if (first[j] != second[j]) {
					comp = (first[j] & 0xFF) - (second[j] & 0xFF);
					break;
				}
			}
			if (comp == 0 && first.length != second.length)
				comp = first.length < second.length ? -1 : 1;
			
			assertEquals(IntegerMath.sign(comp), IntegerMath.sign(new HashValue(first).compareTo(new HashValue(second))));
			assertTrue((comp == 0) == Arrays.equals(first, second));
		}
	}
	
	
	
	private static Random rand = Random.DEFAULT;
	
	private static byte[][] randomByteArrayPair() {
		byte[] first = new byte[rand.uniformInt(100)];
		rand.uniformBytes(first);
		
		byte[] second;
		int mode = rand.uniformInt(4);
		if (mode == 0) {  // Same
			second = first.clone();
			
		} else if (mode == 1) {  // Single difference
			second = first.clone();
			if (first.length > 0)
				second[rand.uniformInt(second.length)] += rand.uniformInt(255) + 1;
			
		} else if (mode == 2) {  // Same prefix, usually different length
			second = Arrays.copyOf(first, rand.uniformInt(100));
			if (second.length > first.length)
				rand.uniformBytes(second, first.length, second.length - first.length);
			
		} else if (mode == 3) {  // Completely independent
			second = new byte[rand.uniformInt(100)];
			rand.uniformBytes(second);
			
		} else
			throw new AssertionError();
		
		return new byte[][]{first, second};
	}
	
	
	private static HashValue hv(int... bytes) {
		byte[] result = new byte[bytes.length];
		for (int i = 0; i < bytes.length; i++)
			result[i] = (byte)bytes[i];
		return new HashValue(result);
	}
	
}
