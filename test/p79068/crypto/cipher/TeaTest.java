package p79068.crypto.cipher;

import org.junit.Test;


public final class TeaTest extends CipherTest {
	
	protected Cipher[] getCiphersToTest() {
		return new Cipher[] {
			Tea.TEA_CIPHER,
			Tea.XTEA_CIPHER,
		};
	}
	
	
	@Test public void testTea() {
		testCipher(Tea.TEA_CIPHER, "00000000800000000000000000000000", "0000000000000000", "9327C49731B08BBE");
		testCipher(Tea.TEA_CIPHER, "80000000000000000000000000000000", "0000000000000000", "9327C49731B08BBE");
		testCipher(Tea.TEA_CIPHER, "80000000000000008000000080000000", "0000000000000000", "9327C49731B08BBE");
		testCipher(Tea.TEA_CIPHER, "00000000800000008000000080000000", "0000000000000000", "9327C49731B08BBE");
	}
	
	
	@Test public void testXtea() {
		testCipher(Tea.XTEA_CIPHER, "00000000800000000000000000000000", "0000000000000000", "4F190CCFC8DEABFC");
		testCipher(Tea.XTEA_CIPHER, "80000000000000000000000000000000", "0000000000000000", "057E8C0550151937");
		testCipher(Tea.XTEA_CIPHER, "80000000000000008000000080000000", "0000000000000000", "31C4E2C6B347B2DE");
		testCipher(Tea.XTEA_CIPHER, "00000000800000008000000080000000", "0000000000000000", "ED69B78566781EF3");
	}
	
}
