package util.hash;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import crypto.CryptoUtils;
import p79068.util.hash.Adler32;
import p79068.util.hash.HashFunction;


public class Adler32Test {
	
	@Test
	public void basic() {
		test(Adler32.FUNCTION, "", "00000001");
		test(Adler32.FUNCTION, "Mark Adler", "13070394");
		test(Adler32.FUNCTION, "\000\001\002\003", "000E0007");  // Eww, octal
		test(Adler32.FUNCTION, "\000\001\002\003\004\005\006\007", "005C001D");
		test(Adler32.FUNCTION, "\000\001\002\003\004\005\006\007\010\011\012\013\014\015\016\017", "02B80079");
		test(Adler32.FUNCTION, "AAAA", "028E0105");
		test(Adler32.FUNCTION, "BBBBBBBB", "09500211");
		test(Adler32.FUNCTION, "CCCCCCCCCCCCCCCC", "23A80431");
		test(Adler32.FUNCTION, "Wikipedia", "11E60398");
	}
	
	
	static void test(HashFunction hashfunc, String message, String hash) {
		byte[] msg = CryptoUtils.asciiToBytes(message);
		byte[] hash0 = hashfunc.getHash(msg).toBytes();
		byte[] hash1 = CryptoUtils.hexToBytes(hash);
		assertArrayEquals(hash1, hash0);
	}
	
}