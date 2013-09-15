package p79068.crypto.hash;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import p79068.crypto.Zeroizable;
import p79068.hash.HashFunction;
import p79068.hash.HashFunctionTest;
import p79068.hash.Hasher;


public abstract class CryptoHashFunctionTest extends HashFunctionTest {
	
	@Test
	public void testZeroization() {
		for (HashFunction hf : getHashFunctionsToTest()) {
			Hasher hasher = hf.newHasher();
			hasher.update(new byte[200]);
			
			assertTrue(hasher instanceof Zeroizable);
			Zeroizable zh = (Zeroizable)hasher;
			try {
				zh.zeroize();
			} catch (IllegalStateException e) {
				fail();
			}
			try {
				zh.zeroize();
				fail();
			} catch (IllegalStateException e) {}  // Pass
		}
	}
	
	
	protected static void testAscii(HashFunction hashFunc, String[][] testCases) {
		for (String[] tc : testCases)
			testAscii(hashFunc, tc[0], tc[1]);
	}
	
}
