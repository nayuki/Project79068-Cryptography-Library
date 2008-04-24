package util.hash;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import crypto.Debug;
import p79068.util.hash.Sum32;
import p79068.util.hash.HashFunction;


public class Sum32Test {
	
	static String millionAs;
	
	
	static {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 1000000; i++)
			sb.append('A');
		millionAs = sb.toString();
	}
	
	
	
	@Test
	public void basic() {
		test(Sum32.FUNCTION, "", "00000000");
		test(Sum32.FUNCTION, "abcdef", "00000255");
		test(Sum32.FUNCTION, "fbdcea", "00000255");
		test(Sum32.FUNCTION, "555", "0000009F");
		test(Sum32.FUNCTION, "456", "0000009F");
		test(Sum32.FUNCTION, "348", "0000009F");
		test(Sum32.FUNCTION, millionAs, "03DFD240");
	}
	
	
	static void test(HashFunction hashfunc, String message, String hash) {
		byte[] msg = Debug.asciiToBytes(message);
		byte[] hash0 = hashfunc.getHash(msg).toBytes();
		byte[] hash1 = Debug.hexToBytes(hash);
		assertArrayEquals(hash1, hash0);
	}
	
}