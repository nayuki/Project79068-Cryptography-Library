package p79068.crypto.hash;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import p79068.crypto.CryptoUtils;
import p79068.hash.HashFunction;


public final class HmacTest extends CryptoHashFunctionTest {
	
	protected HashFunction[] getHashFunctionsToTest() {
		return new HashFunction[] {
			new Hmac(Md.MD5_FUNCTION, new byte[0]),
			new Hmac(Sha.SHA1_FUNCTION, new byte[0]),
			new Hmac(Sha.SHA224_FUNCTION, new byte[0]),
			new Hmac(Sha.SHA256_FUNCTION, new byte[0]),
			new Hmac(Sha.SHA384_FUNCTION, new byte[0]),
			new Hmac(Sha.SHA512_FUNCTION, new byte[0]),
		};
	}
	
	
	private static final byte[] key0 = CryptoUtils.hexToBytes("0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b");
	private static final byte[] key1 = CryptoUtils.hexToBytes("0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b");
	private static final byte[] key2 = CryptoUtils.asciiToBytes("Jefe");
	private static final byte[] key3 = CryptoUtils.hexToBytes("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	private static final byte[] key4 = CryptoUtils.hexToBytes("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	private static final byte[] key5 = CryptoUtils.hexToBytes("0102030405060708090a0b0c0d0e0f10111213141516171819");
	private static final byte[] key6 = CryptoUtils.hexToBytes("0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c");
	private static final byte[] key7 = CryptoUtils.hexToBytes("0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c");
	private static final byte[] key8 = CryptoUtils.hexToBytes("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	private static final byte[] key9 = CryptoUtils.hexToBytes("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	
	private static final byte[] msg0 = CryptoUtils.asciiToBytes("Hi There");
	private static final byte[] msg1 = CryptoUtils.asciiToBytes("what do ya want for nothing?");
	private static final byte[] msg2 = CryptoUtils.hexToBytes("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
	private static final byte[] msg3 = CryptoUtils.hexToBytes("cdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcd");
	private static final byte[] msg4 = CryptoUtils.asciiToBytes("Test With Truncation");
	private static final byte[] msg5 = CryptoUtils.asciiToBytes("Test Using Larger Than Block-Size Key - Hash Key First");
	private static final byte[] msg6 = CryptoUtils.asciiToBytes("Test Using Larger Than Block-Size Key and Larger Than One Block-Size Data");
	private static final byte[] msg7 = CryptoUtils.asciiToBytes("This is a test using a larger than block-size key and a larger than block-size data. The key needs to be hashed before being used by the HMAC algorithm.");
	
	
	
	@Test public void testHmacMd5() {
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
	
	
	@Test public void testHmacSha1() {
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
	
	
	@Test public void testHmacSha224() {
		Object[][] testCases = {
			{key1, msg0, "896fb1128abbdf196832107cd49df33f47b4b1169912ba4f53684b22"},
			{key2, msg1, "a30e01098bc6dbbf45690f3a7e9e6d0f8bbea2a39e6148008fd05e44"},
			{key4, msg2, "7fb3cb3588c6c1f6ffa9694d7d6ad2649365b0c1f65d69d1ec8333ea"},
			{key5, msg3, "6c11506874013cac6a2abc1bb382627cec6a90d86efc012de7afec5a"},
			{key7, msg4, "0e2aea68a90c8d37c988bcdb9fca6fa8099cd857c7ec4a1815cac54c"},
			{key9, msg5, "95e9a0db962095adaebe9b2d6f0dbce2d499f112f2d2b7273fa6870e"},
			{key9, msg7, "3a854166ac5d9f023f54d517d0b39dbd946770db9c2b95c9f6f565d1"},
		};
		for (Object[] tc : testCases)
			test(Sha.SHA224_FUNCTION, (byte[])tc[0], (byte[])tc[1], (String)tc[2]);
	}
	
	
	@Test public void testHmacSha256() {
		Object[][] testCases = {
			{key1, msg0, "b0344c61d8db38535ca8afceaf0bf12b881dc200c9833da726e9376c2e32cff7"},
			{key2, msg1, "5bdcc146bf60754e6a042426089575c75a003f089d2739839dec58b964ec3843"},
			{key4, msg2, "773ea91e36800e46854db8ebd09181a72959098b3ef8c122d9635514ced565fe"},
			{key5, msg3, "82558a389a443c0ea4cc819899f2083a85f0faa3e578f8077a2e3ff46729665b"},
			{key7, msg4, "a3b6167473100ee06e0c796c2955552bfa6f7c0a6a8aef8b93f860aab0cd20c5"},
			{key9, msg5, "60e431591ee0b67f0d8a26aacbf5b77f8e0bc6213728c5140546040f0ee37f54"},
			{key9, msg7, "9b09ffa71b942fcb27635fbcd5b0e944bfdc63644f0713938a7f51535c3a35e2"},
		};
		for (Object[] tc : testCases)
			test(Sha.SHA256_FUNCTION, (byte[])tc[0], (byte[])tc[1], (String)tc[2]);
	}
	
	
	@Test public void testHmacSha384() {
		Object[][] testCases = {
			{key1, msg0, "afd03944d84895626b0825f4ab46907f15f9dadbe4101ec682aa034c7cebc59cfaea9ea9076ede7f4af152e8b2fa9cb6"},
			{key2, msg1, "af45d2e376484031617f78d2b58a6b1b9c7ef464f5a01b47e42ec3736322445e8e2240ca5e69e2c78b3239ecfab21649"},
			{key4, msg2, "88062608d3e6ad8a0aa2ace014c8a86f0aa635d947ac9febe83ef4e55966144b2a5ab39dc13814b94e3ab6e101a34f27"},
			{key5, msg3, "3e8a69b7783c25851933ab6290af6ca77a9981480850009cc5577c6e1f573b4e6801dd23c4a7d679ccf8a386c674cffb"},
			{key7, msg4, "3abf34c3503b2a23a46efc619baef897f4c8e42c934ce55ccbae9740fcbc1af4ca62269e2a37cd88ba926341efe4aeea"},
			{key9, msg5, "4ece084485813e9088d2c63a041bc5b44f9ef1012a2b588f3cd11f05033ac4c60c2ef6ab4030fe8296248df163f44952"},
			{key9, msg7, "6617178e941f020d351e2f254e8fd32c602420feb0b8fb9adccebb82461e99c5a678cc31e799176d3860e6110c46523e"},
		};
		for (Object[] tc : testCases)
			test(Sha.SHA384_FUNCTION, (byte[])tc[0], (byte[])tc[1], (String)tc[2]);
	}
	
	
	@Test public void testHmacSha512() {
		Object[][] testCases = {
			{key1, msg0, "87aa7cdea5ef619d4ff0b4241a1d6cb02379f4e2ce4ec2787ad0b30545e17cdedaa833b7d6b8a702038b274eaea3f4e4be9d914eeb61f1702e696c203a126854"},
			{key2, msg1, "164b7a7bfcf819e2e395fbe73b56e0a387bd64222e831fd610270cd7ea2505549758bf75c05a994a6d034f65f8f0e6fdcaeab1a34d4a6b4b636e070a38bce737"},
			{key4, msg2, "fa73b0089d56a284efb0f0756c890be9b1b5dbdd8ee81a3655f83e33b2279d39bf3e848279a722c806b485a47e67c807b946a337bee8942674278859e13292fb"},
			{key5, msg3, "b0ba465637458c6990e5a8c5f61d4af7e576d97ff94b872de76f8050361ee3dba91ca5c11aa25eb4d679275cc5788063a5f19741120c4f2de2adebeb10a298dd"},
			{key7, msg4, "415fad6271580a531d4179bc891d87a650188707922a4fbb36663a1eb16da008711c5b50ddd0fc235084eb9d3364a1454fb2ef67cd1d29fe6773068ea266e96b"},
			{key9, msg5, "80b24263c7c1a3ebb71493c1dd7be8b49b46d1f41b4aeec1121b013783f8f3526b56d037e05f2598bd0fd2215d6a1e5295e64f73f63f0aec8b915a985d786598"},
			{key9, msg7, "e37b6a775dc87dbaa4dfa9f96e5e3ffddebd71f8867289865df5a32d20cdc944b6022cac3c4982b10d5eeb55c3e4de15134676fb6de0446065c97440fa8c6a58"},
		};
		for (Object[] tc : testCases)
			test(Sha.SHA512_FUNCTION, (byte[])tc[0], (byte[])tc[1], (String)tc[2]);
	}
	
	
	
	private static void test(BlockHashFunction hashFunc, byte[] key, byte[] message, String expectedHash) {
		byte[] hash0 = new Hmac(hashFunc, key).getHash(message).toBytes();
		byte[] hash1 = CryptoUtils.hexToBytes(expectedHash);
		assertArrayEquals(hash1, hash0);
	}
	
}
