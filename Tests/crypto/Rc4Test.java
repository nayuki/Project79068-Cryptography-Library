package crypto;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.Rc4;


public class Rc4Test {
	
	@Test
	public void basic() {
		test("4B6579", "506C61696E74657874", "BBF316E8D940AF0AD3");
		test("57696B69", "7065646961", "1021BF0420");
		test("536563726574", "41747461636B206174206461776E", "45A01F645FC35B383552544B9BF5");
	}
	
	
	
	static void test(String key, String plaintext, String ciphertext) {
		test(Debug.hexToBytes(key), Debug.hexToBytes(plaintext), Debug.hexToBytes(ciphertext));
	}
	
	
	static void test(byte[] key, byte[] plaintext, byte[] ciphertext) {
		Cipherer cipherer = new Rc4(key.length).newCipherer(key);
		cipherer.encrypt(plaintext, 0, plaintext.length);
		assertArrayEquals(ciphertext, plaintext);
	}
	
}