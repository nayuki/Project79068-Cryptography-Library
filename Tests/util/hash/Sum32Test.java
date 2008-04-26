package util.hash;

import org.junit.Test;
import crypto.CryptoUtils;
import p79068.util.hash.Sum32;


public class Sum32Test {
	
	static String millionAs;
	
	
	static {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 1000000; i++)
			sb.append('A');
		millionAs = sb.toString();
	}
	
	
	
	@Test
	public void basic() {
		CryptoUtils.testWithAsciiMessage(Sum32.FUNCTION, "", "00000000");
		CryptoUtils.testWithAsciiMessage(Sum32.FUNCTION, "abcdef", "00000255");
		CryptoUtils.testWithAsciiMessage(Sum32.FUNCTION, "fbdcea", "00000255");
		CryptoUtils.testWithAsciiMessage(Sum32.FUNCTION, "555", "0000009F");
		CryptoUtils.testWithAsciiMessage(Sum32.FUNCTION, "456", "0000009F");
		CryptoUtils.testWithAsciiMessage(Sum32.FUNCTION, "348", "0000009F");
		CryptoUtils.testWithAsciiMessage(Sum32.FUNCTION, millionAs, "03DFD240");
	}
	
}