package math;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import p79068.math.Int128;


public class Int128Test {
	
	@Test
	public void testInitSignExtension() {
		assertEquals(new Int128(0L, 0L), new Int128(0L));
		assertEquals(new Int128(0L, 3L), new Int128(3L));
		assertEquals(new Int128(-1L, -1L), new Int128(-1L));
		assertEquals(new Int128(-1L, -5L), new Int128(-5L));
	}
	
	
	@Test
	public void testNot() {
		assertEquals(new Int128(0xFFFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL), new Int128(0x0000000000000000L, 0x0000000000000000L).not());
		assertEquals(new Int128(0xFEDCBA9876543210L, 0x32107654BA98FEDCL), new Int128(0x0123456789ABCDEFL, 0xCDEF89AB45670123L).not());
	}
	
	
	@Test
	public void testAnd() {
		//assertEquals(new Int128(0x048C000001014126L, 0x0000048C1014926L), new Int128(0xFFFF000001234567L, 0x0000FFFF89ABCDEFL).and(new Int128(0x048C048C31415926L, 0x048C048C31415926L)));
	}
	
	
	@Test
	public void testToString() {
		assertEquals("0", new Int128(0x0000000000000000L, 0x0000000000000000L).toString());
		assertEquals("24", new Int128(0x0000000000000000L, 0x0000000000000018L).toString());
		assertEquals("18446744073709551616", new Int128(0x0000000000000001L, 0x0000000000000000L).toString());
		assertEquals("-1", new Int128(0xFFFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL).toString());
	}
	
}