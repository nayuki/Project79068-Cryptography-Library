package p79068.hash;

import static org.junit.Assert.assertArrayEquals;


public final class HashUtils {
	
	/**
	 * Tests the specified hash function with the specified message and expected hash. The message is a byte sequence expressed in ASCII. Non-ASCII characters are disallowed. The expected hash is a byte sequence expressed in hexadecimal.
	 * @param hashFunc the hash function to test
	 * @param message the message, in ASCII
	 * @param expectedHash the expected hash, in hexadecimal
	 */
	public static void testAscii(HashFunction hashFunc, String message, String expectedHash) {
		byte[] hash1 = hexToBytes(expectedHash);
		byte[] msg = asciiToBytes(message);
		byte[] hash0 = hashFunc.getHash(msg).toBytes();
		assertArrayEquals(hashFunc.toString(), hash1, hash0);
	}
	
	
	/**
	 * Tests the specified hash function with the specified message and expected hash. The message is a byte sequence expressed in hexadecimal. The expected hash is also a byte sequence expressed in hexadecimal.
	 * @param hashFunc the hash function to test
	 * @param message the message, in hexadecimal
	 * @param expectedHash the expected hash, in hexadecimal
	 */
	public static void testHex(HashFunction hashFunc, String message, String expectedHash) {
		byte[] hash1 = hexToBytes(expectedHash);
		byte[] msg = hexToBytes(message);
		byte[] hash0 = hashFunc.getHash(msg).toBytes();
		assertArrayEquals(hashFunc.toString(), hash1, hash0);
	}
	
	
	public static byte[] hexToBytes(String s) {
		if (s.length() % 2 != 0)
			throw new IllegalArgumentException();
		byte[] b = new byte[s.length() / 2];
		for (int i = 0; i < b.length; i++)
			b[i] = (byte)Integer.parseInt(s.substring(i * 2, (i + 1) * 2), 16);
		return b;
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
	
	
	
	private HashUtils() {}
	
}
