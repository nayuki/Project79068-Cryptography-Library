package p79068.crypto.cipher;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import p79068.crypto.CryptoUtils;
import p79068.util.random.Random;


public abstract class CipherTest {
	
	protected abstract Cipher[] getCiphersToTest();
	
	
	/* Inherited methods (tests common to all ciphers) */
	
	@Test
	public void testInvertibilityRandomly() {
		for (Cipher c : getCiphersToTest()) {
			for (int j = 0; j < 100; j++)
				CryptoUtils.testCipherInvertibility(c, c.getBlockLength());
		}
	}
	
	
	@Test
	public void testBlockOffset() {
		for (Cipher c : getCiphersToTest()) {
			int blockLen = c.getBlockLength();
			byte[] key = CryptoUtils.getRandomBytes(c.getKeyLength());
			byte[] plaintext = CryptoUtils.getRandomBytes(blockLen);
			
			byte[] ciphertext = plaintext.clone();
			c.newCipherer(key).encrypt(ciphertext);
			
			for (int i = 1; i < 300; i++) {
				byte[] msg = new byte[i + blockLen + 100];
				System.arraycopy(plaintext, 0, msg, i, blockLen);
				c.newCipherer(key).encrypt(msg, i, blockLen);
				for (int j = 0; j < msg.length; j++)
					assertEquals(j < i || j >= i + blockLen ? 0 : ciphertext[j - i], msg[j]);
			}
		}
	}
	
	
	@Test
	public void testMultiBlock() {
		for (Cipher c : getCiphersToTest()) {
			for (int blocks = 2; blocks <= 10; blocks++) {
				int blockLen = c.getBlockLength();
				int off = Random.DEFAULT.uniformInt(blockLen * 4);
				
				byte[] msg = new byte[off + blocks * blockLen];
				Random.DEFAULT.uniformBytes(msg, off, blockLen);
				for (int i = 1; i < blocks; i++) {
					for (int j = 0; j < blockLen; j++)
						msg[off + i * blockLen + j] = msg[off + j];
				}
				
				byte[] key = CryptoUtils.getRandomBytes(c.getKeyLength());
				c.newCipherer(key).encrypt(msg, off, blocks * blockLen);
				
				for (int i = 0; i < msg.length; i++) {
					if (i < off)
						assertEquals(0, msg[i]);
					else if (i >= off + blockLen)
						assertEquals(msg[off + (i - off) % blockLen], msg[i]);
				}
			}
		}
	}
	
	
	@Test
	public void testInvalidOffset() {
		for (Cipher c : getCiphersToTest()) {
			for (int i = 0; i < 100; i++) {
				byte[] msg = new byte[(Random.DEFAULT.uniformInt(4) + 1) * c.getBlockLength()];
				int off;
				do off = Random.DEFAULT.uniformInt(msg.length * 3) - msg.length;
				while (off == 0);
				
				Cipherer cp = c.newCipherer(new byte[c.getKeyLength()]);
				try {
					cp.encrypt(msg, off, msg.length);
					fail();
				} catch (IndexOutOfBoundsException e) {}  // Pass
			}
		}
	}
	
	
	@Test
	public void testNotMultipleOfBlockLength() {
		for (Cipher c : getCiphersToTest()) {
			for (int i = 0; i < 100; i++) {
				int blockLen = c.getBlockLength();
				if (blockLen == 1)
					break;  // Stream ciphers always pass
				
				byte[] msg = new byte[blockLen * 5];
				int off = Random.DEFAULT.uniformInt(blockLen);
				int len;
				do len = Random.DEFAULT.uniformInt(blockLen * 4);
				while (len % blockLen == 0);
				
				Cipherer cp = c.newCipherer(new byte[c.getKeyLength()]);
				try {
					cp.encrypt(msg, off, len);
					fail();
				} catch (IllegalArgumentException e) {}  // Pass
			}
		}
	}
	
	
	// Always passes. Prints result to standard output.
	@Test
	public void testCipherSpeed() {
		for (Cipher c : getCiphersToTest()) {
			// Warm up the JIT compiler, and try to target about 0.1 second of execution time
			int len = c.getBlockLength();
			long startTime = System.nanoTime();
			do {
				testCipherSpeed(c, len);
				if (len <= Integer.MAX_VALUE / 2)
					len *= 2;
			} while (System.nanoTime() - startTime < 100000000);
			System.out.printf("%s: %.1f MiB/s%n", c.getName(), len / (testCipherSpeed(c, len) / 1.0e9) / 1048576);
		}
	}
	
	
	private static long testCipherSpeed(Cipher c, int len) {
		int blockLen = c.getBlockLength();
		if (len % blockLen != 0)
			throw new IllegalArgumentException();
		
		byte[] key = new byte[c.getKeyLength()];
		Random.DEFAULT.uniformBytes(key);
		Cipherer cc = c.newCipherer(key);
		
		long time = 0;
		byte[] buf = new byte[Math.min(len, (1 << 18) / blockLen * blockLen)];
		while (len > 0) {
			int n = Math.min(len, buf.length);
			time -= System.nanoTime();
			cc.encrypt(buf, 0, n);
			time += System.nanoTime();
			len -= n;
		}
		return time;
	}
	
	
	/* Static methods (helpers for subclasses) */
	
	public static void testCipher(Cipher cipher, String key, String plaintext, String expectedCiphertext) {
		testCipher(cipher, CryptoUtils.hexToBytes(key), CryptoUtils.hexToBytes(plaintext), CryptoUtils.hexToBytes(expectedCiphertext));
	}
	
	
	public static void testCipher(Cipher cipher, byte[] key, byte[] plaintext, byte[] expectedCiphertext) {
		Cipherer cipherer;
		byte[] temp = plaintext.clone();
		
		cipherer = cipher.newCipherer(key);
		cipherer.encrypt(temp);
		assertArrayEquals(cipher.toString(), expectedCiphertext, temp);
		
		cipherer = cipher.newCipherer(key);
		cipherer.decrypt(temp);
		assertArrayEquals(cipher.toString(), plaintext, temp);
	}
	
}
