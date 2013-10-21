package p79068.crypto.cipher;

import org.junit.Test;

import p79068.crypto.CryptoUtils;
import p79068.util.random.Random;


public abstract class CipherTest {
	
	protected abstract Cipher[] getCiphersToTest();
	
	
	@Test
	public void testInvertibilityRandomly() {
		for (Cipher c : getCiphersToTest()) {
			for (int j = 0; j < 100; j++)
				CryptoUtils.testCipherInvertibility(c, c.getBlockLength());
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
	
}
