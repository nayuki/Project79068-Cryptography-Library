package crypto.cipher;

import org.junit.Test;
import crypto.CryptoUtils;
import p79068.crypto.cipher.Rijndael;
import p79068.util.Random;


public class AesTest {
	
	@Test
	public void testBasic() {
		CryptoUtils.testCipher(Rijndael.AES128_CIPHER, "2b7e151628aed2a6abf7158809cf4f3c", "3243f6a8885a308d313198a2e0370734", "3925841d02dc09fbdc118597196a0b32");
		CryptoUtils.testCipher(Rijndael.AES128_CIPHER, "000102030405060708090a0b0c0d0e0f", "00112233445566778899aabbccddeeff", "69c4e0d86a7b0430d8cdb78070b4c55a");
		CryptoUtils.testCipher(Rijndael.AES192_CIPHER, "000102030405060708090a0b0c0d0e0f1011121314151617", "00112233445566778899aabbccddeeff", "dda97ca4864cdfe06eaf70a0ec0d7191");
		CryptoUtils.testCipher(Rijndael.AES256_CIPHER, "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f", "00112233445566778899aabbccddeeff", "8ea2b7ca516745bfeafc49904b496089");
	}
	
	
	@Test
	public void testAesInvertibilityRandomly() {
		for (int i = 0; i < 1000; i++) {
			int keylength = (1 + Random.DEFAULT.randomInt(16)) * 4;  // Random key length from 32 bits to 512 bits, at multiples of 32 bits
			int blocklength = 16;
			CryptoUtils.testCipherInvertibility(new Rijndael(blocklength, keylength), blocklength);
		}
	}
	
	
	@Test
	public void testRijndaelInvertibilityRandomly() {
		for (int i = 0; i < 1000; i++) {
			int keylength = (1 + Random.DEFAULT.randomInt(16)) * 4;  // Random key length from 32 bits to 512 bits, at multiples of 32 bits
			int blocklength = (5 + Random.DEFAULT.randomInt(4)) * 4;  // Random block length from 160 bits to 256 bits, at multiples of 32 bits. Other than 128 bits (AES), these are the only other block sizes allowed by Rijndael.
			CryptoUtils.testCipherInvertibility(new Rijndael(blocklength, keylength), blocklength);
		}
	}
	
}