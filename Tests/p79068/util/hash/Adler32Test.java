package p79068.util.hash;

import org.junit.Test;
import p79068.crypto.hash.HashUtils;


public final class Adler32Test {
	
	@Test
	public void testAdler32() {
		HashUtils.testAscii(Adler32.FUNCTION, "", "00000001");
		HashUtils.testAscii(Adler32.FUNCTION, "Mark Adler", "13070394");
		HashUtils.testAscii(Adler32.FUNCTION, "AAAA", "028E0105");
		HashUtils.testAscii(Adler32.FUNCTION, "BBBBBBBB", "09500211");
		HashUtils.testAscii(Adler32.FUNCTION, "CCCCCCCCCCCCCCCC", "23A80431");
		HashUtils.testAscii(Adler32.FUNCTION, "Wikipedia", "11E60398");
		HashUtils.testHex(Adler32.FUNCTION, "00010203", "000E0007");
		HashUtils.testHex(Adler32.FUNCTION, "0001020304050607", "005C001D");
		HashUtils.testHex(Adler32.FUNCTION, "000102030405060708090A0B0C0D0E0F", "02B80079");
	}
	
}