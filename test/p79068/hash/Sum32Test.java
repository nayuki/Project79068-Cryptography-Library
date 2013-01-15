package p79068.hash;

import java.util.Arrays;
import org.junit.Test;
import static p79068.hash.HashUtils.testAscii;


public final class Sum32Test {
	
	@Test
	public void testSum32() {
		testAscii(Sum32.FUNCTION, "", "00000000");
		testAscii(Sum32.FUNCTION, "1", "00000031");
		testAscii(Sum32.FUNCTION, "abcdef", "00000255");
		testAscii(Sum32.FUNCTION, "fbdcea", "00000255");
		testAscii(Sum32.FUNCTION, "555", "0000009F");
		testAscii(Sum32.FUNCTION, "456", "0000009F");
		testAscii(Sum32.FUNCTION, "348", "0000009F");
		testAscii(Sum32.FUNCTION, getMillionAs(), "03DFD240");
	}
	
	
	
	private static String getMillionAs() {
		char[] result = new char[1000000];
		Arrays.fill(result, 'A');
		return new String(result);
	}
	
}
