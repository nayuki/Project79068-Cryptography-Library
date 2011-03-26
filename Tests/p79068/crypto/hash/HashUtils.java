package p79068.crypto.hash;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import p79068.crypto.CryptoUtils;
import p79068.crypto.Zeroizable;
import p79068.util.hash.HashFunction;
import p79068.util.hash.AbstractHasher;


public final class HashUtils {
	
	/**
	 * Tests the specified hash function with the specified message and expected hash. The message is a byte sequence expressed in ASCII. Non-ASCII characters are disallowed. The expected hash is a byte sequence expressed in hexadecimal.
	 * @param hashFunc the hash function to test
	 * @param message the message, in ASCII
	 * @param expectedHash the expected hash, in hexadecimal
	 */
	public static void testAscii(HashFunction hashFunc, String message, String expectedHash) {
		byte[] hash1 = CryptoUtils.hexToBytes(expectedHash);
		byte[] msg = CryptoUtils.asciiToBytes(message);
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
		byte[] hash1 = CryptoUtils.hexToBytes(expectedHash);
		byte[] msg = CryptoUtils.hexToBytes(message);
		byte[] hash0 = hashFunc.getHash(msg).toBytes();
		assertArrayEquals(hashFunc.toString(), hash1, hash0);
	}
	
	
	public static void testZeroization(HashFunction hashFunc) {
		AbstractHasher hasher = hashFunc.newHasher();
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
	
	
	
	private HashUtils() {}
	
}