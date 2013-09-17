package p79068.hash;

import java.util.Arrays;

import org.junit.Test;


public final class Sum32Test extends HashFunctionTest {
	
	protected HashFunction[] getHashFunctionsToTest() {
		return new HashFunction[] { Sum32.FUNCTION };
	}
	
	
	@Test
	public void testSum32() {
		HashFunction hf = Sum32.FUNCTION;
		testAscii(hf, ""            , "00000000");
		testAscii(hf, "1"           , "00000031");
		testAscii(hf, "abcdef"      , "00000255");
		testAscii(hf, "fbdcea"      , "00000255");
		testAscii(hf, "555"         , "0000009F");
		testAscii(hf, "456"         , "0000009F");
		testAscii(hf, "348"         , "0000009F");
		testAscii(hf, getMillionAs(), "03DFD240");
	}
	
	
	private static String getMillionAs() {
		char[] c = new char[1000000];
		Arrays.fill(c, 'A');
		return new String(c);
	}
	
}
