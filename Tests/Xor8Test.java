import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import crypto.Debug;
import p79068.util.hash.Xor;
import p79068.util.hash.HashFunction;


public class Xor8Test {
	
	@Test
	public void basic() {
		test(Xor.FUNCTION, "", "00");
		test(Xor.FUNCTION, "The", "59");
		test(Xor.FUNCTION, "tEh", "59");
		test(Xor.FUNCTION, "soy", "65");
		test(Xor.FUNCTION, "sorry", "65");
		test(Xor.FUNCTION, "sorrrry", "65");
	}
	
	
	static void test(HashFunction hashfunc, String message, String hash) {
		byte[] msg = Debug.asciiToBytes(message);
		byte[] hash0 = hashfunc.getHash(msg).toBytes();
		byte[] hash1 = Debug.hexToBytes(hash);
		assertArrayEquals(hash1, hash0);
	}
	
}