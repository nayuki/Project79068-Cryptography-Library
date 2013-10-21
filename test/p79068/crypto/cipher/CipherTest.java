package p79068.crypto.cipher;

import org.junit.Test;
import p79068.crypto.CryptoUtils;
import p79068.crypto.cipher.mode.BcModeCipher;
import p79068.crypto.cipher.mode.CbcModeCipher;
import p79068.crypto.cipher.mode.CfbModeCipher;
import p79068.crypto.cipher.mode.IgeModeCipher;
import p79068.crypto.cipher.mode.OfbModeStreamCipher;
import p79068.crypto.cipher.mode.PcbcModeCipher;


public final class CipherTest {
	
	private static Cipher[] blockCiphers = {
		Rijndael.AES128_CIPHER,
		Rijndael.AES192_CIPHER,
		Rijndael.AES256_CIPHER,
		Des.DES_56_CIPHER,
		Des.DES_64_CIPHER,
		Idea.CIPHER,
		new Shacal1(16),
		new Shacal2(16),
		Tea.TEA_CIPHER,
		Tea.XTEA_CIPHER,
		WhirlpoolCipher.WHIRLPOOL_CIPHER,
		WhirlpoolCipher.WHIRLPOOL_T_CIPHER,
		WhirlpoolCipher.WHIRLPOOL0_CIPHER,
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
