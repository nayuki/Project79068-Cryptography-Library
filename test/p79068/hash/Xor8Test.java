package p79068.hash;

import org.junit.Test;
import static p79068.hash.HashUtils.testAscii;


public final class Xor8Test {
	
	@Test
	public void testXor8() {
		testAscii(Xor8.FUNCTION, "", "00");
		testAscii(Xor8.FUNCTION, "A", "41");
		testAscii(Xor8.FUNCTION, "AA", "00");
		testAscii(Xor8.FUNCTION, "asdf", "10");
		testAscii(Xor8.FUNCTION, "fsda", "10");
		testAscii(Xor8.FUNCTION, "The", "59");
		testAscii(Xor8.FUNCTION, "tEh", "59");
		testAscii(Xor8.FUNCTION, "ac", "02");
		testAscii(Xor8.FUNCTION, "abbc", "02");
		testAscii(Xor8.FUNCTION, "abbbbc", "02");
	}
	
}
