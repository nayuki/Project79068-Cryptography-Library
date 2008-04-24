package util.hash;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import crypto.CryptoUtils;
import p79068.util.hash.Xor8;
import p79068.util.hash.HashFunction;


public class Xor8Test {
	
	@Test
	public void basic() {
		test(Xor8.FUNCTION, "", "00");
		test(Xor8.FUNCTION, "The", "59");
		test(Xor8.FUNCTION, "tEh", "59");
		test(Xor8.FUNCTION, "soy", "65");
		test(Xor8.FUNCTION, "sorry", "65");
		test(Xor8.FUNCTION, "sorrrry", "65");
	}
	
	
	static void test(HashFunction hashfunc, String message, String hash) {
		byte[] msg = CryptoUtils.asciiToBytes(message);
		byte[] hash0 = hashfunc.getHash(msg).toBytes();
		byte[] hash1 = CryptoUtils.hexToBytes(hash);
		assertArrayEquals(hash1, hash0);
	}
	
}