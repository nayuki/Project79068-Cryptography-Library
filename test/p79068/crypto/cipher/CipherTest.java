package p79068.crypto.cipher;

import org.junit.Test;
import p79068.crypto.CryptoUtils;
import p79068.crypto.cipher.mode.CbcModeCipher;
import p79068.crypto.cipher.mode.CfbModeCipher;
import p79068.crypto.cipher.mode.OfbModeStreamCipher;


public final class CipherTest {
	
	private static Cipher[] blockCiphers = {
		Rijndael.AES128_CIPHER,
		Rijndael.AES192_CIPHER,
		Rijndael.AES256_CIPHER,
		Tea.CIPHER,
		Xtea.CIPHER,
		WhirlpoolCipher.CIPHER
	};
	
	private static Cipher[] cipherModes = {
		new CbcModeCipher(Rijndael.AES128_CIPHER, new byte[16]),
		new CfbModeCipher(Rijndael.AES128_CIPHER, new byte[16]),
		new OfbModeStreamCipher(Rijndael.AES128_CIPHER, new byte[16]),
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