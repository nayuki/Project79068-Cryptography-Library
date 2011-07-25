package p79068.crypto.cipher;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import p79068.crypto.CryptoUtils;
import p79068.crypto.cipher.mode.CbcModeCipher;
import p79068.random.Random;


public final class AesTest {
	
	@Test
	public void testBasic() {
		CryptoUtils.testCipher(Aes.AES128_CIPHER, "2B7E151628AED2A6ABF7158809CF4F3C", "3243F6A8885A308D313198A2E0370734", "3925841D02DC09FBDC118597196A0B32");
		CryptoUtils.testCipher(Aes.AES128_CIPHER, "000102030405060708090A0B0C0D0E0F", "00112233445566778899AABBCCDDEEFF", "69C4E0D86A7B0430D8CDB78070B4C55A");
		CryptoUtils.testCipher(Aes.AES192_CIPHER, "000102030405060708090A0B0C0D0E0F1011121314151617", "00112233445566778899AABBCCDDEEFF", "DDA97CA4864CDFE06EAF70A0EC0D7191");
		CryptoUtils.testCipher(Aes.AES256_CIPHER, "000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F", "00112233445566778899AABBCCDDEEFF", "8EA2B7CA516745BFEAFC49904B496089");
	}
	
	
	@Test
	public void testAesInvertibilityRandomly() {
		for (int i = 0; i < 1000; i++) {
			int keylength = (1 + Random.DEFAULT.uniformInt(16)) * 4;  // Random key length from 32 bits to 512 bits, at multiples of 32 bits
			CryptoUtils.testCipherInvertibility(new Aes(keylength), 16);
		}
	}
	
	
	@Test
	public void testRijndaelInvertibilityRandomly() {
		for (int i = 0; i < 1000; i++) {
			int keylength = (1 + Random.DEFAULT.uniformInt(16)) * 4;  // Random key length from 32 bits to 512 bits, at multiples of 32 bits
			CryptoUtils.testCipherInvertibility(new Aes(keylength), 16);
		}
	}
	
	
	@Test
	public void testAesCbcMode() {
		byte[] key = CryptoUtils.hexToBytes("06A9214036B8A15B512E03D534120006");
		byte[] initvector = CryptoUtils.hexToBytes("3DAFBA429D9EB430B422DA802C9FAC41");
		byte[] plaintext = CryptoUtils.asciiToBytes("Single block msg");
		byte[] ciphertext = CryptoUtils.hexToBytes("E353779C1079AEB82708942DBE77181A");
		
		BlockCipher aescipher = Aes.AES128_CIPHER;
		CbcModeCipher cbccipher = new CbcModeCipher(aescipher, key);
		Cipherer cipherer;
		byte[] temp = plaintext.clone();
		
		cipherer = cbccipher.newCipherer(initvector);
		cipherer.encrypt(temp);
		assertArrayEquals(ciphertext, temp);
		
		cipherer = cbccipher.newCipherer(initvector);
		cipherer.decrypt(temp);
		assertArrayEquals(plaintext, temp);
	}
	
}
