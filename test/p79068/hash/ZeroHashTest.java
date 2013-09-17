package p79068.hash;

import org.junit.Test;


public final class ZeroHashTest extends HashFunctionTest {
	
	protected HashFunction[] getHashFunctionsToTest() {
		return new HashFunction[] { ZeroHash.FUNCTION };
	}
	
	
	@Test
	public void testZeroHash() {
		HashFunction hf = ZeroHash.FUNCTION;
		testHex(hf, ""          , "00");
		testHex(hf, "00"        , "00");
		testHex(hf, "01"        , "00");
		testHex(hf, "38"        , "00");
		testHex(hf, "FF"        , "00");
		testHex(hf, "0000"      , "00");
		testHex(hf, "0123"      , "00");
		testHex(hf, "0000000000", "00");
		testHex(hf, "010003C000", "00");
	}
	
}
