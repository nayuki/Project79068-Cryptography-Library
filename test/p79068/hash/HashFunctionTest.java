package p79068.hash;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import p79068.util.random.Random;


public abstract class HashFunctionTest {
	
	protected abstract HashFunction[] getHashFunctionsToTest();
	
	
	@Test
	public void testZeroLength() {
		for (HashFunction hf : getHashFunctionsToTest()) {
			// Simply ensure that no exception is thrown
			hf.getHash(new byte[0]);
		}
	}
	
	
	@Test
	public void testUpdateByteVsBlockEquivalence() {
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
	
	
	@Test
	public void testMessageSplittingEquivalence() {
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
	
}
