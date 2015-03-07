package p79068.hash;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import p79068.crypto.CryptoUtils;
import p79068.util.random.Random;


public abstract class HashFunctionTest {
	
	protected abstract HashFunction[] getHashFunctionsToTest();
	
	
	@Test public void testZeroLength() {
		for (HashFunction hf : getHashFunctionsToTest()) {
			// Simply ensure that no exception is thrown
			hf.getHash(new byte[0]);
		}
	}
	
	
	@Test public void testUpdateByteVsBlockEquivalence() {
		Random r = Random.DEFAULT;
		for (HashFunction hf : getHashFunctionsToTest()) {
			for (int i = 0; i < 1000; i++) {
				byte[] b = new byte[r.uniformInt(1000) + 1];
				r.uniformBytes(b);
				
				Hasher h0 = hf.newHasher();
				Hasher h1 = hf.newHasher();
				for (int j = 0; j < b.length; j++) {
					h0.update(b[j]);
					h1.update(b, j, 1);
				}
				assertEquals(h0.getHash(), h1.getHash());
			}
		}
	}
	
	
	@Test public void testMessageSplittingEquivalence() {
		Random r = Random.DEFAULT;
		for (HashFunction hf : getHashFunctionsToTest()) {
			for (int i = 0; i < 1000; i++) {
				byte[] b = new byte[r.uniformInt(1000) + 1];
				r.uniformBytes(b);
				
				Hasher h = hf.newHasher();
				for (int off = 0; off < b.length; ) {
					int n = r.uniformInt(b.length - off + 1);
					h.update(b, off, n);
					off += n;
				}
				assertEquals(hf.getHash(b), h.getHash());
			}
		}
	}
	
	
	// Always passes. Prints result to standard output.
	@Test public void testHashSpeed() {
		for (HashFunction hf : getHashFunctionsToTest()) {
			// Warm up the JIT compiler, and try to target about 0.1 second of execution time
			int len = 1;
			long startTime = System.nanoTime();
			do {
				testHashSpeed(hf, len);
				if (len <= Integer.MAX_VALUE / 2)
					len *= 2;
			} while (System.nanoTime() - startTime < 100000000);
			System.out.printf("%s: %.1f MiB/s%n", hf.getName(), len / (testHashSpeed(hf, len) / 1.0e9) / 1048576);
		}
	}
	
	
	private static long testHashSpeed(HashFunction hf, int len) {
		long time = 0;
		Hasher h = hf.newHasher();
		byte[] buf = new byte[Math.min(len, 1 << 18)];
		while (len > 0) {
			int n = Math.min(len, buf.length);
			time -= System.nanoTime();
			h.update(buf, 0, n);
			time += System.nanoTime();
			len -= n;
		}
		return time;
	}
	
	
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
	
}
