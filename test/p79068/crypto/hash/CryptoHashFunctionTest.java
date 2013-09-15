package p79068.crypto.hash;

import static org.junit.Assert.fail;
import p79068.crypto.Zeroizable;
import p79068.hash.HashFunction;
import p79068.hash.HashFunctionTest;
import p79068.hash.Hasher;


public abstract class CryptoHashFunctionTest extends HashFunctionTest {
	
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
	
}
