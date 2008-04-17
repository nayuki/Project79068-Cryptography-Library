import static org.junit.Assert.assertTrue;
import org.junit.Test;
import p79068.crypto.hash.BlockHashFunction;
import p79068.crypto.hash.Hmac;
import p79068.crypto.hash.Md5;
import p79068.crypto.hash.Sha1;


public class HmacTest {
	
	static final byte[] key0 = Debug.hexToBytes("0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b");
	static final byte[] key1 = Debug.hexToBytes("0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b");
	static final byte[] key2 = Debug.asciiToBytes("Jefe");
	static final byte[] key3 = Debug.hexToBytes("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	static final byte[] key4 = Debug.hexToBytes("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	static final byte[] key5 = Debug.hexToBytes("0102030405060708090a0b0c0d0e0f10111213141516171819");
	static final byte[] key6 = Debug.hexToBytes("0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c");
	static final byte[] key7 = Debug.hexToBytes("0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c");
	static final byte[] key8 = Debug.hexToBytes("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	
	static final byte[] msg0 = Debug.asciiToBytes("Hi There");
	static final byte[] msg1 = Debug.asciiToBytes("what do ya want for nothing?");
	static final byte[] msg2 = Debug.hexToBytes("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
	static final byte[] msg3 = Debug.hexToBytes("cdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcd");
	static final byte[] msg4 = Debug.asciiToBytes("Test With Truncation");
	static final byte[] msg5 = Debug.asciiToBytes("Test Using Larger Than Block-Size Key - Hash Key First");
	static final byte[] msg6 = Debug.asciiToBytes("Test Using Larger Than Block-Size Key and Larger Than One Block-Size Data");
	
	
	@Test
	public void hmacMd5() {
		test(Md5.FUNCTION, key0, msg0, "9294727a3638bb1c13f48ef8158bfc9d");
		test(Md5.FUNCTION, key2, msg1, "750c783e6ab0b503eaa86e310a5db738");
		test(Md5.FUNCTION, key3, msg2, "56be34521d144c88dbb8c733f0e8b3f6");
		test(Md5.FUNCTION, key5, msg3, "697eaf0aca3a3aea3a75164746ffaa79");
		test(Md5.FUNCTION, key6, msg4, "56461ef2342edc00f9bab995690efd4c");
		test(Md5.FUNCTION, key8, msg5, "6b1ab7fe4bd7bf8f0b62e6ce61b9d0cd");
		test(Md5.FUNCTION, key8, msg6, "6f630fad67cda0ee1fb1f562db3aa53e");
	}
	
	@Test
	public void hmacSha1() {
		test(Sha1.FUNCTION, key1, msg0, "b617318655057264e28bc0b6fb378c8ef146be00");
		test(Sha1.FUNCTION, key2, msg1, "effcdf6ae5eb2fa2d27416d5f184df9c259a7c79");
		test(Sha1.FUNCTION, key4, msg2, "125d7342b9ac11cd91a39af48aa17b4f63f175d3");
		test(Sha1.FUNCTION, key5, msg3, "4c9007f4026250c6bc8414f9bf50c86c2d7235da");
		test(Sha1.FUNCTION, key7, msg4, "4c1a03424b55e07fe7f27be1d58bb9324a9a5a04");
		test(Sha1.FUNCTION, key8, msg5, "aa4ae5e15272d00e95705637ce8a3b55ed402112");
		test(Sha1.FUNCTION, key8, msg6, "e8e99d0f45237d786d6bbaa7965c7808bbff1a91");
	}
	
	
	static void test(BlockHashFunction hashfunc, byte[] key, byte[] message, String hash) {
		byte[] hash0 = new Hmac(hashfunc, key).getHash(message).toBytes();
		byte[] hash1 = Debug.hexToBytes(hash);
		assertTrue(Debug.areEqual(hash0, hash1));
	}
}