package p79068.hash;

import java.util.Arrays;
import org.junit.Test;


public final class SimpleHashTest extends HashFunctionTest {
	
	protected HashFunction[] getHashFunctionsToTest() {
		return new HashFunction[] {
			Adler32.FUNCTION,
			Sum32.FUNCTION,
			Xor8.FUNCTION,
			new ZeroHash(1),
			new ZeroHash(16),
		};
	}
	
	
	@Test public void testAdler32() {
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
	
	
	@Test public void testSum32() {
		HashFunction hf = Sum32.FUNCTION;
		testAscii(hf, ""            , "00000000");
		testAscii(hf, "1"           , "00000031");
		testAscii(hf, "abcdef"      , "00000255");
		testAscii(hf, "fbdcea"      , "00000255");
		testAscii(hf, "555"         , "0000009F");
		testAscii(hf, "456"         , "0000009F");
		testAscii(hf, "348"         , "0000009F");
		testAscii(hf, getMillionAs(), "03DFD240");
	}
	
	
	@Test public void testXor8() {
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
	
	
	@Test public void testZeroHash() {
		HashFunction hf = new ZeroHash(1);
		testHex(hf, ""          , "00");
		testHex(hf, "00"        , "00");
		testHex(hf, "01"        , "00");
		testHex(hf, "38"        , "00");
		testHex(hf, "FF"        , "00");
		testHex(hf, "0000"      , "00");
		testHex(hf, "0123"      , "00");
		testHex(hf, "0000000000", "00");
		testHex(hf, "010003C000", "00");
		testHex(new ZeroHash(2), "", "0000");
		testHex(new ZeroHash(3), "", "000000");
	}
	
	
	private static String getMillionAs() {
		char[] c = new char[1000000];
		Arrays.fill(c, 'A');
		return new String(c);
	}
	
}
