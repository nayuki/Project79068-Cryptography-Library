package p79068.util.hash;

import org.junit.Test;
import p79068.crypto.CryptoUtils;


public class Xor8Test {
	
	@Test
	public void testXor8() {
		CryptoUtils.testWithAsciiMessage(Xor8.FUNCTION, "", "00");
		CryptoUtils.testWithAsciiMessage(Xor8.FUNCTION, "bb", "00");
		CryptoUtils.testWithAsciiMessage(Xor8.FUNCTION, "asdf", "10");
		CryptoUtils.testWithAsciiMessage(Xor8.FUNCTION, "fads", "10");
		CryptoUtils.testWithAsciiMessage(Xor8.FUNCTION, "The", "59");
		CryptoUtils.testWithAsciiMessage(Xor8.FUNCTION, "tEh", "59");
		CryptoUtils.testWithAsciiMessage(Xor8.FUNCTION, "soy", "65");
		CryptoUtils.testWithAsciiMessage(Xor8.FUNCTION, "sorry", "65");
		CryptoUtils.testWithAsciiMessage(Xor8.FUNCTION, "sorrrry", "65");
	}
	
}