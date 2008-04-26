package util.hash;

import org.junit.Test;
import crypto.CryptoUtils;
import p79068.util.hash.Xor8;


public class Xor8Test {
	
	@Test
	public void basic() {
		CryptoUtils.testWithAsciiMessage(Xor8.FUNCTION, "", "00");
		CryptoUtils.testWithAsciiMessage(Xor8.FUNCTION, "The", "59");
		CryptoUtils.testWithAsciiMessage(Xor8.FUNCTION, "tEh", "59");
		CryptoUtils.testWithAsciiMessage(Xor8.FUNCTION, "soy", "65");
		CryptoUtils.testWithAsciiMessage(Xor8.FUNCTION, "sorry", "65");
		CryptoUtils.testWithAsciiMessage(Xor8.FUNCTION, "sorrrry", "65");
	}
	
}