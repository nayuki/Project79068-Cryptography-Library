package p79068.crypto.hash;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import p79068.crypto.CryptoUtils;
import p79068.crypto.Zeroizable;
import p79068.util.hash.HashFunction;
import p79068.util.hash.Hasher;


public final class HashUtils {
	
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
	
	
	
	private HashUtils() {}
	
}