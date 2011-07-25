package p79068.crypto.cipher;

import org.junit.Test;
import p79068.crypto.CryptoUtils;
import p79068.crypto.cipher.mode.CbcModeCipher;
import p79068.crypto.cipher.mode.CfbModeCipher;
import p79068.crypto.cipher.mode.OfbModeStreamCipher;


public final class CipherTest {
	
	private static Cipher[] blockCiphers = {
		Aes.AES128_CIPHER,
		Aes.AES192_CIPHER,
		Aes.AES256_CIPHER,
		Tea.TEA_CIPHER,
		Tea.XTEA_CIPHER,
	};
	
	private static Cipher[] cipherModes = {
		new CbcModeCipher(Aes.AES128_CIPHER, new byte[16]),
		new CfbModeCipher(Aes.AES128_CIPHER, new byte[16]),
		new OfbModeStreamCipher(Aes.AES128_CIPHER, new byte[16]),
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
