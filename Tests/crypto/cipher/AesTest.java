package crypto;

import org.junit.Test;
import static org.junit.Assert.*;
import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.Rijndael;
import p79068.util.Random;


public class AesTest {
	
	@Test
	public void basic() {
		test("2b7e151628aed2a6abf7158809cf4f3c", "3243f6a8885a308d313198a2e0370734", "3925841d02dc09fbdc118597196a0b32");
		test("000102030405060708090a0b0c0d0e0f", "00112233445566778899aabbccddeeff", "69c4e0d86a7b0430d8cdb78070b4c55a");
		test("000102030405060708090a0b0c0d0e0f1011121314151617", "00112233445566778899aabbccddeeff", "dda97ca4864cdfe06eaf70a0ec0d7191");
		test("000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f", "00112233445566778899aabbccddeeff", "8ea2b7ca516745bfeafc49904b496089");
	}
	
	
	@Test
	public void random() {
		for (int i = 0; i < 1000; i++) {
			byte[] key = new byte[(1 + Random.DEFAULT.randomInt(10)) * 4];
			byte[] plaintext = new byte[16 * 256];
			Random.DEFAULT.randomBytes(key);
			Random.DEFAULT.randomBytes(plaintext);
			test(key, plaintext, null);
		}
	}
	
	
	
	static void test(String key, String plaintext, String ciphertext) {
		test(Debug.hexToBytes(key), Debug.hexToBytes(plaintext), Debug.hexToBytes(ciphertext));
	}
	
	
	static void test(byte[] key, byte[] plaintext, byte[] ciphertext) {
		Cipherer cipherer = new Rijndael(key.length, 16).newCipherer(key);
		byte[] originalplaintext = plaintext.clone();
		cipherer.encrypt(plaintext);
		if (ciphertext != null)
			assertArrayEquals(ciphertext, plaintext);
		cipherer.decrypt(plaintext);
		assertArrayEquals(originalplaintext, plaintext);
	}
	
}