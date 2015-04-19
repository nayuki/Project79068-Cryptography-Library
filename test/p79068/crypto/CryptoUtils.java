package p79068.crypto;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import p79068.crypto.cipher.Cipher;
import p79068.util.random.Random;


public final class CryptoUtils {
	
	// Tests that the given cipher algorithm, when encrypting and decrypting a random message of the given length, produces the original message.
	public static void testCipherInvertibility(Cipher cipher, int messageLength) {
		byte[] key = getRandomBytes(cipher.getKeyLength());
		byte[] message = getRandomBytes(messageLength);
		byte[] reference = message.clone();
		cipher.newCipherer(key).encrypt(message);
		cipher.newCipherer(key).decrypt(message);  // Reinitialize the cipher (necessary for stateful ones like stream ciphers)
		assertArrayEquals(cipher.toString(), reference, message);
	}
	
	
	// Converts the given hexadecimal string to an array of bytes.
	public static byte[] hexToBytes(String str) {
		if (str.length() % 2 != 0)
			throw new IllegalArgumentException();
		byte[] result = new byte[str.length() / 2];
		for (int i = 0; i < str.length(); i += 2) {
			if (str.charAt(i) == '+' || str.charAt(i) == '-')
				throw new IllegalArgumentException();
			result[i / 2] = (byte)Integer.parseInt(str.substring(i, i + 2), 16);
		}
		return result;
	}
	
	
	// Converts the given array of bytes to an uppercase hexadecimal string.
	public static String bytesToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (byte x : bytes)
			sb.append(String.format("%02X", x));
		return sb.toString();
	}
	
	
	// Converts the given ASCII string to an array of bytes. Throws an exception if there are non-ASCII characters.
	public static byte[] asciiToBytes(String str) {
		byte[] result = new byte[str.length()];
		for (int i = 0; i < result.length; i++) {
			char c = str.charAt(i);
			if (c < 0x80)
				result[i] = (byte)c;
			else
				throw new IllegalArgumentException();
		}
		return result;
	}
	
	
	// Converts the given array of bytes to an ASCII string. Throws an exception if there are non-ASCII bytes.
	public static String bytesToAscii(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] >= 0)  // Test if b[i] is in [0, 128)
				sb.append((char)(bytes[i] & 0xFF));
			else
				throw new IllegalArgumentException();
		}
		return sb.toString();
	}
	
	
	// Returns an array of random bytes of the given length.
	public static byte[] getRandomBytes(int length) {
		byte[] b = new byte[length];
		Random.DEFAULT.uniformBytes(b);
		return b;
	}
	
	
	
	/* Self-check */
	
	@Test public void testHexToBytes() {
		assertArrayEquals(new byte[]{}, hexToBytes(""));
		assertArrayEquals(new byte[]{0x00}, hexToBytes("00"));
		assertArrayEquals(new byte[]{(byte)0x80,(byte)0x03,(byte)0xFF,(byte)0xEA,(byte)0xCD,(byte)0xAA,(byte)0x1B,(byte)0xB9}, hexToBytes("8003FFeaCdaA1bB9"));
	}
	
	
	@Test(expected=IllegalArgumentException.class) public void testHexToBytesOddLength0() {
		hexToBytes("0");
	}
	
	@Test(expected=IllegalArgumentException.class) public void testHexToBytesOddLength1() {
		hexToBytes("abc");
	}
	
	@Test(expected=IllegalArgumentException.class) public void testHexToBytesIllegalChars0() {
		hexToBytes("fg");
	}
	
	@Test(expected=IllegalArgumentException.class) public void testHexToBytesIllegalChars1() {
		hexToBytes("00 00 00");
	}
	
	@Test(expected=IllegalArgumentException.class) public void testHexToBytesIllegalChars2() {
		hexToBytes("-0");
	}
	
	@Test(expected=IllegalArgumentException.class) public void testHexToBytesIllegalChars3() {
		hexToBytes("+0");
	}
	
	@Test(expected=IllegalArgumentException.class) public void testHexToBytesIllegalChars4() {
		hexToBytes("ab  ");
	}
	
	
	@Test public void testBytesToHex() {
		assertEquals("", bytesToHex(new byte[]{}));
		assertEquals("00FFABE237", bytesToHex(new byte[]{(byte)0x00,(byte)0xFF,(byte)0xAB,(byte)0xE2,(byte)0x37}));
	}
	
	
	@Test public void testAsciiToBytes() {
		assertArrayEquals(new byte[]{}, asciiToBytes(""));
		assertArrayEquals(new byte[]{0x61,0x30}, asciiToBytes("a0"));
		assertArrayEquals(new byte[]{0x20,0x3F,0x5C}, asciiToBytes(" ?\\"));
	}
	
	
	@Test(expected=IllegalArgumentException.class) public void testAsciiToBytesInvalid() {
		asciiToBytes("abc\u2000");
	}
	
	
	@Test public void testBytesToAscii() {
		assertEquals("", bytesToAscii(new byte[]{}));
		assertEquals("a0", bytesToAscii(new byte[]{0x61,0x30}));
		assertEquals(" ?\\", bytesToAscii(new byte[]{0x20,0x3F,0x5C}));
	}
	
	
	@Test(expected=IllegalArgumentException.class) public void testBytesToAsciiInvalid() {
		bytesToAscii(new byte[]{(byte)0x80});
	}
	
}
