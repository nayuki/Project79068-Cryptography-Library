package p79068.hash;

import org.junit.Test;


public final class Adler32Test extends HashFunctionTest {
	
	protected HashFunction[] getHashFunctionsToTest() {
		return new HashFunction[] { Adler32.FUNCTION };
	}
	
	
	@Test
	public void testAdler32() {
		HashFunction hf = Adler32.FUNCTION;
		testAscii(hf, ""                , "00000001");
		testAscii(hf, "Mark Adler"      , "13070394");
		testAscii(hf, "AAAA"            , "028E0105");
		testAscii(hf, "BBBBBBBB"        , "09500211");
		testAscii(hf, "CCCCCCCCCCCCCCCC", "23A80431");
		testAscii(hf, "Wikipedia"       , "11E60398");
		testHex(hf, "00010203"                        , "000E0007");
		testHex(hf, "0001020304050607"                , "005C001D");
		testHex(hf, "000102030405060708090A0B0C0D0E0F", "02B80079");
	}
	
}
