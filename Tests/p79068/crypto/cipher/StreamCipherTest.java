package p79068.crypto.cipher;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import p79068.crypto.CryptoUtils;


public abstract class StreamCipherTest {
	
	protected abstract StreamCipher getCipher();
	
	
	
	@Test
	public void testSkip() {
		int[] skips = {0, 1, 2, 3, 6, 7, 24, 51, 139};
		StreamCipher cipher = getCipher();
		byte[] key = CryptoUtils.getRandomBytes(cipher.getKeyLength());
		StreamCipherer cipherer0 = cipher.newCipherer(key);
		StreamCipherer cipherer1 = cipher.newCipherer(key);
		byte[] message0 = new byte[64];
		byte[] message1 = new byte[64];
		for (int skip : skips) {
			cipherer0.skip(skip);
			cipherer1.encrypt(new byte[skip]);
			cipherer0.encrypt(message0);
			cipherer1.encrypt(message1);
			assertArrayEquals(message1, message0);
		}
	}
	
	
	@Test
	public void testInvalidSkip() {
		StreamCipherer cipherer = newCipherer();
		int[] skips = {-1, -2, -5, -36, Integer.MIN_VALUE / 2, Integer.MIN_VALUE};
		for (int skip : skips) {
			try {
				cipherer.skip(skip);
				fail();
			} catch (IllegalArgumentException e) {}
		}
	}
	
	
	
	private StreamCipherer newCipherer() {
		StreamCipher cipher = getCipher();
		byte[] key = CryptoUtils.getRandomBytes(cipher.getKeyLength());
		return cipher.newCipherer(key);
	}
	
}