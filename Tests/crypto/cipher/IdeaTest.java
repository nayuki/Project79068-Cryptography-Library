package crypto.cipher;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import crypto.CryptoUtils;
import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.Idea;


public class IdeaTest {
	
	@Test
	public void testIdea() {
		test("00010002000300040005000600070008", "0000000100020003", "11fbed2b01986de5");
	}
	
	
	
	private static void test(String key, String plaintext, String ciphertext) {
		test(CryptoUtils.hexToBytes(key), CryptoUtils.hexToBytes(plaintext), CryptoUtils.hexToBytes(ciphertext));
	}
	
	
	private static void test(byte[] key, byte[] plaintext, byte[] ciphertext) {
		byte[] originalplaintext = plaintext.clone();
		Cipherer cipherer = Idea.CIPHER.newCipherer(key);
		cipherer.encrypt(plaintext);
		assertArrayEquals(ciphertext, plaintext);
		cipherer.decrypt(plaintext);
		assertArrayEquals(originalplaintext, plaintext);
	}
	
}