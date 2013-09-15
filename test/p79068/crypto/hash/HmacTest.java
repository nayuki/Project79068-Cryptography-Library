package p79068.crypto.hash;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import p79068.crypto.CryptoUtils;


public final class HmacTest extends CryptoHashFunctionTest {
	
	private static final byte[] key0 = CryptoUtils.hexToBytes("0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b");
	private static final byte[] key1 = CryptoUtils.hexToBytes("0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b");
	private static final byte[] key2 = CryptoUtils.asciiToBytes("Jefe");
	private static final byte[] key3 = CryptoUtils.hexToBytes("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	private static final byte[] key4 = CryptoUtils.hexToBytes("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	private static final byte[] key5 = CryptoUtils.hexToBytes("0102030405060708090a0b0c0d0e0f10111213141516171819");
	private static final byte[] key6 = CryptoUtils.hexToBytes("0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c");
	private static final byte[] key7 = CryptoUtils.hexToBytes("0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c");
	private static final byte[] key8 = CryptoUtils.hexToBytes("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	
	private static final byte[] msg0 = CryptoUtils.asciiToBytes("Hi There");
	private static final byte[] msg1 = CryptoUtils.asciiToBytes("what do ya want for nothing?");
	private static final byte[] msg2 = CryptoUtils.hexToBytes("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
	private static final byte[] msg3 = CryptoUtils.hexToBytes("cdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcd");
	private static final byte[] msg4 = CryptoUtils.asciiToBytes("Test With Truncation");
	private static final byte[] msg5 = CryptoUtils.asciiToBytes("Test Using Larger Than Block-Size Key - Hash Key First");
	private static final byte[] msg6 = CryptoUtils.asciiToBytes("Test Using Larger Than Block-Size Key and Larger Than One Block-Size Data");
	
	
	
	@Test
	public void testHmacMd5() {
		Object[][] testCases = {
			{key0, msg0, "9294727a3638bb1c13f48ef8158bfc9d"},
			{key2, msg1, "750c783e6ab0b503eaa86e310a5db738"},
			{key3, msg2, "56be34521d144c88dbb8c733f0e8b3f6"},
			{key5, msg3, "697eaf0aca3a3aea3a75164746ffaa79"},
			{key6, msg4, "56461ef2342edc00f9bab995690efd4c"},
			{key8, msg5, "6b1ab7fe4bd7bf8f0b62e6ce61b9d0cd"},
			{key8, msg6, "6f630fad67cda0ee1fb1f562db3aa53e"},
		};
		for (Object[] tc : testCases)
			test(Md.MD5_FUNCTION, (byte[])tc[0], (byte[])tc[1], (String)tc[2]);
	}
	
	
	@Test
	public void testHmacSha1() {
		Object[][] testCases = {
			{key1, msg0, "b617318655057264e28bc0b6fb378c8ef146be00"},
			{key2, msg1, "effcdf6ae5eb2fa2d27416d5f184df9c259a7c79"},
			{key4, msg2, "125d7342b9ac11cd91a39af48aa17b4f63f175d3"},
			{key5, msg3, "4c9007f4026250c6bc8414f9bf50c86c2d7235da"},
			{key7, msg4, "4c1a03424b55e07fe7f27be1d58bb9324a9a5a04"},
			{key8, msg5, "aa4ae5e15272d00e95705637ce8a3b55ed402112"},
			{key8, msg6, "e8e99d0f45237d786d6bbaa7965c7808bbff1a91"},
		};
		for (Object[] tc : testCases)
			test(Sha.SHA1_FUNCTION, (byte[])tc[0], (byte[])tc[1], (String)tc[2]);
	}
	
	
	
	private static void test(BlockHashFunction hashFunc, byte[] key, byte[] message, String expectedHash) {
		byte[] hash0 = new Hmac(hashFunc, key).getHash(message).toBytes();
		byte[] hash1 = CryptoUtils.hexToBytes(expectedHash);
		assertArrayEquals(hash1, hash0);
	}
	
}
