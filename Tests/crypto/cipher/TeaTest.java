package crypto.cipher;

import org.junit.Test;
import crypto.CryptoUtils;
import p79068.crypto.cipher.Tea;
import p79068.crypto.cipher.Xtea;


public class TeaTest {
	
	@Test
	public void testTea() {
		CryptoUtils.testCipher(Tea.CIPHER, "00000000800000000000000000000000", "0000000000000000", "9327c49731b08bbe");
		CryptoUtils.testCipher(Tea.CIPHER, "80000000000000000000000000000000", "0000000000000000", "9327c49731b08bbe");
		CryptoUtils.testCipher(Tea.CIPHER, "80000000000000008000000080000000", "0000000000000000", "9327c49731b08bbe");
		CryptoUtils.testCipher(Tea.CIPHER, "00000000800000008000000080000000", "0000000000000000", "9327c49731b08bbe");
	}
	
	
	@Test
	public void testXtea() {
		CryptoUtils.testCipher(Xtea.CIPHER, "00000000800000000000000000000000", "0000000000000000", "4f190ccfc8deabfc");
		CryptoUtils.testCipher(Xtea.CIPHER, "80000000000000000000000000000000", "0000000000000000", "057e8c0550151937");
		CryptoUtils.testCipher(Xtea.CIPHER, "80000000000000008000000080000000", "0000000000000000", "31c4e2c6b347b2de");
		CryptoUtils.testCipher(Xtea.CIPHER, "00000000800000008000000080000000", "0000000000000000", "ed69b78566781ef3");
	}
	
}