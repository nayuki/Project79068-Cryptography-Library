package p79068.crypto;

import static org.junit.Assert.assertArrayEquals;
import p79068.crypto.cipher.Cipher;
import p79068.util.random.Random;


public final class CryptoUtils {
	
	public static void testCipherInvertibility(Cipher cipher, int messageLength) {
		byte[] key = getRandomBytes(cipher.getKeyLength());
		byte[] message = getRandomBytes(messageLength);
		byte[] reference = message.clone();
		cipher.newCipherer(key).encrypt(message);
		cipher.newCipherer(key).decrypt(message);  // Reinitialize the cipher (necessary for stateful ones like stream ciphers)
		assertArrayEquals(cipher.toString(), reference, message);
	}
	
	
	
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
	
	
	public static String bytesToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (byte x : bytes)
			sb.append(String.format("%02X", x));
		return sb.toString();
	}
	
	
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
	
	
	public static byte[] getRandomBytes(int length) {
		byte[] b = new byte[length];
		Random.DEFAULT.uniformBytes(b);
		return b;
	}
	
	
	
	private CryptoUtils() {}
	
}
