package p79068.util.hash;

import org.junit.Test;
import p79068.crypto.CryptoUtils;


public final class Sum32Test {
	
	@Test
	public void testSum32() {
		CryptoUtils.testWithAsciiMessage(Sum32.FUNCTION, "", "00000000");
		CryptoUtils.testWithAsciiMessage(Sum32.FUNCTION, "abcdef", "00000255");
		CryptoUtils.testWithAsciiMessage(Sum32.FUNCTION, "fbdcea", "00000255");
		CryptoUtils.testWithAsciiMessage(Sum32.FUNCTION, "555", "0000009F");
		CryptoUtils.testWithAsciiMessage(Sum32.FUNCTION, "456", "0000009F");
		CryptoUtils.testWithAsciiMessage(Sum32.FUNCTION, "348", "0000009F");
		CryptoUtils.testWithAsciiMessage(Sum32.FUNCTION, getMillionAs(), "03DFD240");
	}
	
	
	
	private static String getMillionAs() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 1000000; i++)
			sb.append('A');
		return sb.toString();
	}
	
}