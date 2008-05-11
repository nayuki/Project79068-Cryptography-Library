package crypto;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import p79068.crypto.Zeroizable;
import p79068.crypto.cipher.Cipher;
import p79068.crypto.cipher.Cipherer;
import p79068.util.Random;
import p79068.util.hash.HashFunction;
import p79068.util.hash.Hasher;


public class CryptoUtils {
	
	public static void testWithAsciiMessage(HashFunction hashFunc, String message, String expectedHash) {
		byte[] hash1 = CryptoUtils.hexToBytes(expectedHash);
		byte[] msg = CryptoUtils.asciiToBytes(message);
		byte[] hash0 = hashFunc.getHash(msg).toBytes();
		assertArrayEquals(hashFunc.toString(), hash1, hash0);
	}
	
	
	public static void testWithHexMessage(HashFunction hashFunc, String message, String expectedHash) {
		byte[] hash1 = CryptoUtils.hexToBytes(expectedHash);
		byte[] msg = CryptoUtils.hexToBytes(message);
		byte[] hash0 = hashFunc.getHash(msg).toBytes();
		assertArrayEquals(hashFunc.toString(), hash1, hash0);
	}
	
	
	public static void testZeroization(HashFunction hashFunc) {
		Hasher hasher = hashFunc.newHasher();
		hasher.update(new byte[200]);
		if (!(hasher instanceof Zeroizable))
			fail();
		else {
			try {
				((Zeroizable)hasher).zeroize();
			} catch (IllegalStateException e) {
				fail();
			}
			try {
				((Zeroizable)hasher).zeroize();
				fail();
			} catch (IllegalStateException e) {}  // Pass
		}
	}
	
	
	public static void testCipher(Cipher cipher, String key, String plaintext, String expectedCiphertext) {
		testCipher(cipher, hexToBytes(key), hexToBytes(plaintext), hexToBytes(expectedCiphertext));
	}
	
	
	public static void testCipher(Cipher cipher, byte[] key, byte[] plaintext, byte[] expectedCiphertext) {
		Cipherer cipherer = cipher.newCipherer(key);
		byte[] temp = plaintext.clone();
		
		cipherer.encrypt(temp);
		assertArrayEquals(cipher.toString(), expectedCiphertext, temp);
		
		cipherer.decrypt(temp);
		assertArrayEquals(cipher.toString(), plaintext, temp);
	}
	
	
	public static void testCipherInvertibility(Cipher cipher, int messageLength) {
		byte[] key = new byte[cipher.getKeyLength()];
		byte[] message0 = new byte[messageLength];
		Random.DEFAULT.randomBytes(key);
		Random.DEFAULT.randomBytes(message0);
		byte[] message1 = message0.clone();
		Cipherer cipherer = cipher.newCipherer(key);
		cipherer.encrypt(message1);
		cipherer = cipher.newCipherer(key);  // Reinitialize the cipher
		cipherer.decrypt(message1);
		assertArrayEquals(cipher.toString(), message0, message1);
	}
	
	
	
	public static byte[] hexToBytes(String s) {
		if (s.length() % 2 != 0)
			throw new IllegalArgumentException();
		byte[] b = new byte[s.length() / 2];
		for (int i = 0; i < b.length; i++)
			b[i] = (byte)Integer.parseInt(s.substring(i * 2, (i + 1) * 2), 16);
		return b;
	}
	
	
	public static String bytesToHex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(Integer.toString(b[i] >>> 4 & 0xF, 16));
			sb.append(Integer.toString(b[i] >>> 0 & 0xF, 16));
		}
		return sb.toString();
	}
	
	
	public static byte[] asciiToBytes(String s) {
		byte[] b = new byte[s.length()];
		for (int i = 0; i < b.length; i++) {
			char c = s.charAt(i);
			if (c >= 0x80)
				c = '?';
			b[i] = (byte)c;
		}
		return b;
	}
	
	
	public static String bytesToAscii(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			if ((b[i] & 0xFF) < 0x80)
				sb.append((char)(b[i] & 0xFF));
			else
				sb.append('?');
		}
		return sb.toString();
	}
	
	
	
	private CryptoUtils() {}
	
}