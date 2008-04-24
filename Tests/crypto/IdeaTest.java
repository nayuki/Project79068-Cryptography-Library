package crypto;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.Idea;


public class IdeaTest {
	
	@Test
	public void testIdea() {
		test("00010002000300040005000600070008", "0000000100020003", "11fbed2b01986de5");
	}
	
	
	
	static void test(String key, String plaintext, String ciphertext) {
		test(Debug.hexToBytes(key), Debug.hexToBytes(plaintext), Debug.hexToBytes(ciphertext));
	}
	
	
	static void test(byte[] key, byte[] plaintext, byte[] ciphertext) {
		byte[] originalplaintext = plaintext.clone();
		Cipherer cipherer = Idea.CIPHER.newCipherer(key);
		cipherer.encrypt(plaintext);
		assertArrayEquals(ciphertext, plaintext);
		cipherer.decrypt(plaintext);
		assertArrayEquals(originalplaintext, plaintext);
	}
	
}