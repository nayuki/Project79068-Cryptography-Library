package p79068.hash;

import org.junit.Test;


public final class Xor8Test extends HashFunctionTest {
	
	protected HashFunction[] getHashFunctionsToTest() {
		return new HashFunction[] { Xor8.FUNCTION };
	}
	
	
	@Test
	public void testXor8() {
		HashFunction hf = Xor8.FUNCTION;
		testAscii(hf, ""      , "00");
		testAscii(hf, "A"     , "41");
		testAscii(hf, "AA"    , "00");
		testAscii(hf, "asdf"  , "10");
		testAscii(hf, "fsda"  , "10");
		testAscii(hf, "The"   , "59");
		testAscii(hf, "tEh"   , "59");
		testAscii(hf, "ac"    , "02");
		testAscii(hf, "abbc"  , "02");
		testAscii(hf, "abbbbc", "02");
	}
	
}
