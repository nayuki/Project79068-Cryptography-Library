package crypto.cipher;

import org.junit.Test;
import crypto.CryptoUtils;
import p79068.crypto.cipher.Rijndael;
import p79068.util.Random;


public class AesTest {
	
	@Test
	public void testBasic() {
		CryptoUtils.testCipher(Rijndael.AES128_CIPHER, "2B7E151628AED2A6ABF7158809CF4F3C", "3243F6A8885A308D313198A2E0370734", "3925841D02DC09FBDC118597196A0B32");
		CryptoUtils.testCipher(Rijndael.AES128_CIPHER, "000102030405060708090A0B0C0D0E0F", "00112233445566778899AABBCCDDEEFF", "69C4E0D86A7B0430D8CDB78070B4C55A");
		CryptoUtils.testCipher(Rijndael.AES192_CIPHER, "000102030405060708090A0B0C0D0E0F1011121314151617", "00112233445566778899AABBCCDDEEFF", "DDA97CA4864CDFE06EAF70A0EC0D7191");
		CryptoUtils.testCipher(Rijndael.AES256_CIPHER, "000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F", "00112233445566778899AABBCCDDEEFF", "8EA2B7CA516745BFEAFC49904B496089");
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