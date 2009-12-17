package p79068.crypto.hash;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import p79068.crypto.CryptoUtils;
import p79068.util.hash.HashFunction;
import p79068.util.hash.HashValue;
import p79068.util.hash.Hasher;


public class Edonkey2000Test {
	
	private static final int BLOCK_LENGTH = 9728000;
	
	
	@Test
	public void testOldEdonkey2000() {
		test(Edonkey2000.FUNCTION, 0 * BLOCK_LENGTH +  0, "31D6CFE0D16AE931B73C59D7E0C089C0");
		test(Edonkey2000.FUNCTION, 0 * BLOCK_LENGTH + 89, "C48B812748FD84C7B4AE116C5CD24CE0");
		test(Edonkey2000.FUNCTION, 1 * BLOCK_LENGTH -  1, "AC44B93FC9AFF773AB0005C911F8396F");
		test(Edonkey2000.FUNCTION, 1 * BLOCK_LENGTH +  0, "FC21D9AF828F92A8DF64BEAC3357425D");
		test(Edonkey2000.FUNCTION, 1 * BLOCK_LENGTH +  1, "06329E9DBA1373512C06386FE29E3C65");
		test(Edonkey2000.FUNCTION, 2 * BLOCK_LENGTH - 31, "145E5CF749DCC29FE7FC766CF72DAD4F");
		test(Edonkey2000.FUNCTION, 2 * BLOCK_LENGTH +  0, "114B21C63A74B6CA922291A11177DD5C");
		test(Edonkey2000.FUNCTION, 2 * BLOCK_LENGTH + 25, "C06ED2428584B4117814B64D27A88F09");
	}
	
	
	@Test
	public void testNewEdonkey2000() {
		test(NewEdonkey2000.FUNCTION, 0 * BLOCK_LENGTH +  0, "31D6CFE0D16AE931B73C59D7E0C089C0");  // Same
		test(NewEdonkey2000.FUNCTION, 0 * BLOCK_LENGTH + 89, "C48B812748FD84C7B4AE116C5CD24CE0");  // Same
		test(NewEdonkey2000.FUNCTION, 1 * BLOCK_LENGTH -  1, "AC44B93FC9AFF773AB0005C911F8396F");  // Same
		test(NewEdonkey2000.FUNCTION, 1 * BLOCK_LENGTH +  0, "D7DEF262A127CD79096A108E7A9FC138");  // Different
		test(NewEdonkey2000.FUNCTION, 1 * BLOCK_LENGTH +  1, "06329E9DBA1373512C06386FE29E3C65");  // Same
		test(NewEdonkey2000.FUNCTION, 2 * BLOCK_LENGTH - 31, "145E5CF749DCC29FE7FC766CF72DAD4F");  // Same
		test(NewEdonkey2000.FUNCTION, 2 * BLOCK_LENGTH +  0, "194EE9E4FA79B2EE9F8829284C466051");  // Different
		test(NewEdonkey2000.FUNCTION, 2 * BLOCK_LENGTH + 25, "C06ED2428584B4117814B64D27A88F09");  // Same
	}
	
	
	@Test
	public void testZeroize() {
		CryptoUtils.testZeroization(Edonkey2000.FUNCTION);
		CryptoUtils.testZeroization(NewEdonkey2000.FUNCTION);
	}
	
	
	
	private static void test(HashFunction hashFunc, int length, String expectedHash) {
		byte[] actualHash = getHashOfZeros(hashFunc, length).toBytes();
		assertArrayEquals(CryptoUtils.hexToBytes(expectedHash), actualHash);
	}
	
	
	private static HashValue getHashOfZeros(HashFunction hashfunc, int length) {
		Hasher hasher = hashfunc.newHasher();
		byte[] b = new byte[1024];
		while (length > 0) {
			int temp = Math.min(b.length, length);
			hasher.update(b, 0, temp);
			length -= temp;
		}
		return hasher.getHash();
	}
	
}