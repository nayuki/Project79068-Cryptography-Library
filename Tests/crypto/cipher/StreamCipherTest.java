package crypto.cipher;

import static org.junit.Assert.fail;
import org.junit.Test;
import p79068.crypto.cipher.StreamCipher;
import p79068.crypto.cipher.StreamCipherer;
import p79068.util.Random;


public abstract class StreamCipherTest {
	
	protected abstract StreamCipher getCipher();
	
	
	
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
		byte[] key = new byte[cipher.getKeyLength()];
		Random.DEFAULT.randomBytes(key);
		return cipher.newCipherer(key);
	}
	
}