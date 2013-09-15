package p79068.hash;

import org.junit.Test;


public final class Xor8Test extends HashFunctionTest {
	
	protected HashFunction[] getHashFunctionsToTest() {
		return new HashFunction[] { Xor8.FUNCTION };
	}
	
	
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
