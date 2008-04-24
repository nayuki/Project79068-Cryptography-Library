package crypto.cipher;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import crypto.CryptoUtils;
import p79068.crypto.cipher.Cipher;
import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.Tea;
import p79068.crypto.cipher.Xtea;


public class TeaTest {
	
	@Test
	public void testTea() {
		test(Tea.CIPHER, "00000000800000000000000000000000", "0000000000000000", "9327c49731b08bbe");
		test(Tea.CIPHER, "80000000000000000000000000000000", "0000000000000000", "9327c49731b08bbe");
		test(Tea.CIPHER, "80000000000000008000000080000000", "0000000000000000", "9327c49731b08bbe");
		test(Tea.CIPHER, "00000000800000008000000080000000", "0000000000000000", "9327c49731b08bbe");
	}
	
	
	@Test
	public void testXtea() {
		test(Xtea.CIPHER, "00000000800000000000000000000000", "0000000000000000", "4f190ccfc8deabfc");
		test(Xtea.CIPHER, "80000000000000000000000000000000", "0000000000000000", "057e8c0550151937");
		test(Xtea.CIPHER, "80000000000000008000000080000000", "0000000000000000", "31c4e2c6b347b2de");
		test(Xtea.CIPHER, "00000000800000008000000080000000", "0000000000000000", "ed69b78566781ef3");
	}
	
	
	
	private static void test(Cipher cipher, String key, String plaintext, String ciphertext) {
		test(cipher, CryptoUtils.hexToBytes(key), CryptoUtils.hexToBytes(plaintext), CryptoUtils.hexToBytes(ciphertext));
	}
	
	
	private static void test(Cipher cipher, byte[] key, byte[] plaintext, byte[] ciphertext) {
		byte[] originalplaintext = plaintext.clone();
		Cipherer cipherer = cipher.newCipherer(key);
		cipherer.encrypt(plaintext);
		assertArrayEquals(ciphertext, plaintext);
		cipherer.decrypt(plaintext);
		assertArrayEquals(originalplaintext, plaintext);
	}
	
}