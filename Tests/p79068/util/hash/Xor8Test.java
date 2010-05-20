package p79068.util.hash;

import org.junit.Test;
import p79068.crypto.hash.HashUtils;


public final class Xor8Test {
	
	@Test
	public void testXor8() {
		HashUtils.testWithAsciiMessage(Xor8.FUNCTION, "", "00");
		HashUtils.testWithAsciiMessage(Xor8.FUNCTION, "bb", "00");
		HashUtils.testWithAsciiMessage(Xor8.FUNCTION, "asdf", "10");
		HashUtils.testWithAsciiMessage(Xor8.FUNCTION, "fads", "10");
		HashUtils.testWithAsciiMessage(Xor8.FUNCTION, "The", "59");
		HashUtils.testWithAsciiMessage(Xor8.FUNCTION, "tEh", "59");
		HashUtils.testWithAsciiMessage(Xor8.FUNCTION, "soy", "65");
		HashUtils.testWithAsciiMessage(Xor8.FUNCTION, "sorry", "65");
		HashUtils.testWithAsciiMessage(Xor8.FUNCTION, "sorrrry", "65");
	}
	
}