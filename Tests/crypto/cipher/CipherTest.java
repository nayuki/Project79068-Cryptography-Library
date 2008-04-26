package crypto.cipher;

import org.junit.Test;
import crypto.CryptoUtils;
import p79068.crypto.cipher.*;
import p79068.crypto.cipher.mode.*;


public class CipherTest {
	
	private static Cipher[] ciphers = {
		Rijndael.AES128_CIPHER,
		Rijndael.AES192_CIPHER,
		Rijndael.AES256_CIPHER,
		Idea.CIPHER,
		Tea.CIPHER,
		Xtea.CIPHER,
		Whirlpool0Cipher.CIPHER,
		Whirlpool1Cipher.CIPHER,
		WhirlpoolCipher.CIPHER,
		new BcModeCipher(Rijndael.AES128_CIPHER, new byte[16]),
		new CbcModeCipher(Rijndael.AES128_CIPHER, new byte[16]),
		new CfbModeCipher(Rijndael.AES128_CIPHER, new byte[16]),
		new IgeModeCipher(Rijndael.AES128_CIPHER, new byte[16]),
		new OfbModeStreamCipher(Rijndael.AES128_CIPHER, new byte[16]),
		new PcbcModeCipher(Rijndael.AES128_CIPHER, new byte[16])
	};
	
	
	
	@Test
	public void testAllCiphersInvertibilityRandomly() {
		for (Cipher cipher : ciphers) {
			for (int j = 0; j < 100; j++)
				CryptoUtils.testCipherInvertibility(cipher, cipher.getBlockLength());
		}
	}
	
}