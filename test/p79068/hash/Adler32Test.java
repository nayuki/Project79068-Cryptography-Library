package p79068.hash;

import org.junit.Test;
import static p79068.hash.HashUtils.testAscii;
import static p79068.hash.HashUtils.testHex;


public final class Adler32Test {
	
	@Test
	public void testAdler32() {
		testAscii(Adler32.FUNCTION, "", "00000001");
		testAscii(Adler32.FUNCTION, "Mark Adler", "13070394");
		testAscii(Adler32.FUNCTION, "AAAA", "028E0105");
		testAscii(Adler32.FUNCTION, "BBBBBBBB", "09500211");
		testAscii(Adler32.FUNCTION, "CCCCCCCCCCCCCCCC", "23A80431");
		testAscii(Adler32.FUNCTION, "Wikipedia", "11E60398");
		testHex(Adler32.FUNCTION, "00010203", "000E0007");
		testHex(Adler32.FUNCTION, "0001020304050607", "005C001D");
		testHex(Adler32.FUNCTION, "000102030405060708090A0B0C0D0E0F", "02B80079");
	}
	
}
