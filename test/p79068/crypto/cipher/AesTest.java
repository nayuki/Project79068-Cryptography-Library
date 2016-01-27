package p79068.crypto.cipher;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import p79068.crypto.CryptoUtils;
import p79068.crypto.cipher.mode.CbcModeCipher;
import p79068.util.random.Random;


public final class AesTest extends CipherTest {
	
	protected Cipher[] getCiphersToTest() {
		return new Cipher[] {
			Rijndael.AES128_CIPHER,
			Rijndael.AES192_CIPHER,
			Rijndael.AES256_CIPHER,
		};
	}
	
	
	@Test public void testBasic() {
		testCipher(Rijndael.AES128_CIPHER, "2B7E151628AED2A6ABF7158809CF4F3C", "3243F6A8885A308D313198A2E0370734", "3925841D02DC09FBDC118597196A0B32");
		testCipher(Rijndael.AES128_CIPHER, "2B7E151628AED2A6ABF7158809CF4F3C", "6BC1BEE22E409F96E93D7E117393172A", "3AD77BB40D7A3660A89ECAF32466EF97");
		testCipher(Rijndael.AES128_CIPHER, "2B7E151628AED2A6ABF7158809CF4F3C", "AE2D8A571E03AC9C9EB76FAC45AF8E51", "F5D3D58503B9699DE785895A96FDBAAF");
		testCipher(Rijndael.AES128_CIPHER, "2B7E151628AED2A6ABF7158809CF4F3C", "30C81C46A35CE411E5FBC1191A0A52EF", "43B1CD7F598ECE23881B00E3ED030688");
		testCipher(Rijndael.AES128_CIPHER, "2B7E151628AED2A6ABF7158809CF4F3C", "F69F2445DF4F9B17AD2B417BE66C3710", "7B0C785E27E8AD3F8223207104725DD4");
		testCipher(Rijndael.AES128_CIPHER, "000102030405060708090A0B0C0D0E0F", "00112233445566778899AABBCCDDEEFF", "69C4E0D86A7B0430D8CDB78070B4C55A");
		testCipher(Rijndael.AES192_CIPHER, "000102030405060708090A0B0C0D0E0F1011121314151617", "00112233445566778899AABBCCDDEEFF", "DDA97CA4864CDFE06EAF70A0EC0D7191");
		testCipher(Rijndael.AES192_CIPHER, "8E73B0F7DA0E6452C810F32B809079E562F8EAD2522C6B7B", "6BC1BEE22E409F96E93D7E117393172A", "BD334F1D6E45F25FF712A214571FA5CC");
		testCipher(Rijndael.AES192_CIPHER, "8E73B0F7DA0E6452C810F32B809079E562F8EAD2522C6B7B", "AE2D8A571E03AC9C9EB76FAC45AF8E51", "974104846D0AD3AD7734ECB3ECEE4EEF");
		testCipher(Rijndael.AES192_CIPHER, "8E73B0F7DA0E6452C810F32B809079E562F8EAD2522C6B7B", "30C81C46A35CE411E5FBC1191A0A52EF", "EF7AFD2270E2E60ADCE0BA2FACE6444E");
		testCipher(Rijndael.AES192_CIPHER, "8E73B0F7DA0E6452C810F32B809079E562F8EAD2522C6B7B", "F69F2445DF4F9B17AD2B417BE66C3710", "9A4B41BA738D6C72FB16691603C18E0E");
		testCipher(Rijndael.AES256_CIPHER, "000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F", "00112233445566778899AABBCCDDEEFF", "8EA2B7CA516745BFEAFC49904B496089");
		testCipher(Rijndael.AES256_CIPHER, "603DEB1015CA71BE2B73AEF0857D77811F352C073B6108D72D9810A30914DFF4", "6BC1BEE22E409F96E93D7E117393172A", "F3EED1BDB5D2A03C064B5A7E3DB181F8");
		testCipher(Rijndael.AES256_CIPHER, "603DEB1015CA71BE2B73AEF0857D77811F352C073B6108D72D9810A30914DFF4", "AE2D8A571E03AC9C9EB76FAC45AF8E51", "591CCB10D410ED26DC5BA74A31362870");
		testCipher(Rijndael.AES256_CIPHER, "603DEB1015CA71BE2B73AEF0857D77811F352C073B6108D72D9810A30914DFF4", "30C81C46A35CE411E5FBC1191A0A52EF", "B6ED21B99CA6F4F9F153E7B1BEAFED1D");
		testCipher(Rijndael.AES256_CIPHER, "603DEB1015CA71BE2B73AEF0857D77811F352C073B6108D72D9810A30914DFF4", "F69F2445DF4F9B17AD2B417BE66C3710", "23304B7A39F9F3FF067D8D8F9E24ECC7");
	}
	
	
	@Test public void testAesInvertibilityRandomly() {
		for (int i = 0; i < 1000; i++) {
			int keylength = (1 + Random.DEFAULT.uniformInt(16)) * 4;  // Random key length from 32 bits to 512 bits, at multiples of 32 bits
			int blocklength = 16;
			CryptoUtils.testCipherInvertibility(new Rijndael(blocklength, keylength), blocklength);
		}
	}
	
	
	@Test public void testRijndaelInvertibilityRandomly() {
		for (int i = 0; i < 1000; i++) {
			int keylength = (1 + Random.DEFAULT.uniformInt(16)) * 4;  // Random key length from 32 bits to 512 bits, at multiples of 32 bits
			int blocklength = (5 + Random.DEFAULT.uniformInt(4)) * 4;  // Random block length from 160 bits to 256 bits, at multiples of 32 bits. Other than 128 bits (AES), these are the only other block sizes allowed by Rijndael.
			CryptoUtils.testCipherInvertibility(new Rijndael(blocklength, keylength), blocklength);
		}
	}
	
	
	@Test public void testAesCbcMode() {
		byte[] key = CryptoUtils.hexToBytes("06A9214036B8A15B512E03D534120006");
		byte[] initvector = CryptoUtils.hexToBytes("3DAFBA429D9EB430B422DA802C9FAC41");
		byte[] plaintext = CryptoUtils.asciiToBytes("Single block msg");
		byte[] ciphertext = CryptoUtils.hexToBytes("E353779C1079AEB82708942DBE77181A");
		
		BlockCipher aescipher = Rijndael.AES128_CIPHER;
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
