package util.hash;

import org.junit.Test;
import crypto.CryptoUtils;
import p79068.util.hash.Adler32;


public class Adler32Test {
	
	@Test
	public void basic() {
		CryptoUtils.testWithAsciiMessage(Adler32.FUNCTION, "", "00000001");
		CryptoUtils.testWithAsciiMessage(Adler32.FUNCTION, "Mark Adler", "13070394");
		CryptoUtils.testWithAsciiMessage(Adler32.FUNCTION, "\000\001\002\003", "000E0007");  // Eww, octal
		CryptoUtils.testWithAsciiMessage(Adler32.FUNCTION, "\000\001\002\003\004\005\006\007", "005C001D");
		CryptoUtils.testWithAsciiMessage(Adler32.FUNCTION, "\000\001\002\003\004\005\006\007\010\011\012\013\014\015\016\017", "02B80079");
		CryptoUtils.testWithAsciiMessage(Adler32.FUNCTION, "AAAA", "028E0105");
		CryptoUtils.testWithAsciiMessage(Adler32.FUNCTION, "BBBBBBBB", "09500211");
		CryptoUtils.testWithAsciiMessage(Adler32.FUNCTION, "CCCCCCCCCCCCCCCC", "23A80431");
		CryptoUtils.testWithAsciiMessage(Adler32.FUNCTION, "Wikipedia", "11E60398");
	}
	
}