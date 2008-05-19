package crypto.cipher;

import org.junit.Test;
import crypto.CryptoUtils;
import p79068.crypto.cipher.*;
import p79068.crypto.cipher.mode.*;


public class CipherTest {
	
	private static Cipher[] blockCiphers = {
		Rijndael.AES128_CIPHER,
		Rijndael.AES192_CIPHER,
		Rijndael.AES256_CIPHER,
		Idea.CIPHER,
		new Shacal1(16),
		new Shacal2(16),
		Tea.CIPHER,
		Xtea.CIPHER,
		Whirlpool0Cipher.CIPHER,
		WhirlpoolTCipher.CIPHER,
		WhirlpoolCipher.CIPHER
	};
	
	private static Cipher[] cipherModes = {
		new BcModeCipher(Rijndael.AES128_CIPHER, new byte[16]),
		new CbcModeCipher(Rijndael.AES128_CIPHER, new byte[16]),
		new CfbModeCipher(Rijndael.AES128_CIPHER, new byte[16]),
		new IgeModeCipher(Rijndael.AES128_CIPHER, new byte[16]),
		new OfbModeStreamCipher(Rijndael.AES128_CIPHER, new byte[16]),
		new PcbcModeCipher(Rijndael.AES128_CIPHER, new byte[16])
	};
	
	
	
	@Test
	public void testBlockCiphersInvertibilityRandomly() {
		for (Cipher cipher : blockCiphers) {
			for (int j = 0; j < 100; j++)
				CryptoUtils.testCipherInvertibility(cipher, cipher.getBlockLength());
		}
	}
	
	
	@Test
	public void testCiphersModesInvertibilityRandomly() {
		for (Cipher cipher : cipherModes) {
			for (int j = 0; j < 100; j++)
				CryptoUtils.testCipherInvertibility(cipher, cipher.getBlockLength());
		}
	}
	
}