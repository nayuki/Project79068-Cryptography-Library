import org.junit.Test;
import static org.junit.Assert.*;
import p79068.crypto.cipher.Cipher;
import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.Tea;
import p79068.crypto.cipher.Xtea;


public class TeaTest {
	
	@Test
	public void basic() {
		test(Tea.CIPHER, "00000000800000000000000000000000", "0000000000000000", "9327c49731b08bbe");
		test(Tea.CIPHER, "80000000000000000000000000000000", "0000000000000000", "9327c49731b08bbe");
		test(Tea.CIPHER, "80000000000000008000000080000000", "0000000000000000", "9327c49731b08bbe");
		test(Tea.CIPHER, "00000000800000008000000080000000", "0000000000000000", "9327c49731b08bbe");
		
		test(Xtea.CIPHER, "00000000800000000000000000000000", "0000000000000000", "4f190ccfc8deabfc");
		test(Xtea.CIPHER, "80000000000000000000000000000000", "0000000000000000", "057e8c0550151937");
		test(Xtea.CIPHER, "80000000000000008000000080000000", "0000000000000000", "31c4e2c6b347b2de");
		test(Xtea.CIPHER, "00000000800000008000000080000000", "0000000000000000", "ed69b78566781ef3");
	}
	
	
	static void test(Cipher cipher, String key, String plaintext, String ciphertext) {
		test(cipher, Debug.hexToBytes(key), Debug.hexToBytes(plaintext), Debug.hexToBytes(ciphertext));
	}
	
	static void test(Cipher cipher, byte[] key, byte[] plaintext, byte[] ciphertext) {
		byte[] originalplaintext = plaintext.clone();
		Cipherer cipherer = cipher.newCipherer(key);
		cipherer.encrypt(plaintext);
		assertTrue(Debug.areEqual(plaintext, ciphertext));
		cipherer.decrypt(plaintext);
		assertTrue(Debug.areEqual(plaintext, originalplaintext));
	}
}