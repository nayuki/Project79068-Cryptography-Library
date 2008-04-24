package crypto;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import p79068.crypto.cipher.*;
import p79068.crypto.cipher.mode.*;
import p79068.util.Random;


public class CipherTest {
	
	private static Cipher[] ciphers = {Rijndael.AES128_CIPHER, Rijndael.AES192_CIPHER, Rijndael.AES256_CIPHER, Idea.CIPHER, Tea.CIPHER, Xtea.CIPHER, Whirlpool0Cipher.CIPHER, Whirlpool1Cipher.CIPHER, WhirlpoolCipher.CIPHER, new BcModeCipher(Rijndael.AES192_CIPHER, new byte[24]), new CbcModeCipher(Rijndael.AES192_CIPHER, new byte[24]), new CfbModeCipher(Rijndael.AES192_CIPHER, new byte[24]), new IgeModeCipher(Rijndael.AES192_CIPHER, new byte[24]), new OfbModeStreamCipher(Rijndael.AES192_CIPHER, new byte[24]), new PcbcModeCipher(Rijndael.AES192_CIPHER, new byte[24])};
	
	
	
	@Test
	public void allCiphers() {
		for (int i = 0; i < ciphers.length; i++) {
			for (int j = 0; j < 256; j++)
				test(ciphers[i]);
		}
	}
	
	
	@Test
	public void rijndael() {  // Test the wide parameter range of Rijndael
		for (int blocklen = 16; blocklen <= 32; blocklen += 4) {
			for (int keylen = 4; keylen <= 384; keylen += 4) {
				test(new Rijndael(keylen, blocklen));
			}
		}
	}
	
	
	
	private static void test(Cipher cipher) {
		byte[] key = new byte[cipher.getKeyLength()];
		byte[] plaintext = new byte[4096 / cipher.getBlockLength() * cipher.getBlockLength()];
		Random.DEFAULT.randomBytes(key);
		Random.DEFAULT.randomBytes(plaintext);
		byte[] plaintextref = plaintext.clone();
		Cipherer cipherer = cipher.newCipherer(key);
		cipherer.encrypt(plaintext);
		cipherer = cipher.newCipherer(key);
		cipherer.decrypt(plaintext);
		assertArrayEquals(plaintextref, plaintext);
	}
	
}