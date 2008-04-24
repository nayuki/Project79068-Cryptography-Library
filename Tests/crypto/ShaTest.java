package crypto;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import p79068.crypto.Zeroizable;
import p79068.crypto.hash.Sha1;
import p79068.crypto.hash.Sha224;
import p79068.crypto.hash.Sha256;
import p79068.crypto.hash.Sha384;
import p79068.crypto.hash.Sha512;
import p79068.util.hash.HashFunction;
import p79068.util.hash.Hasher;


public class ShaTest {
	
	private static String msg0 = "";
	private static String msg1 = "abc";
	private static String msg2 = "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq";
	private static String msg3 = "abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmnoijklmnopjklmnopqklmnopqrlmnopqrsmnopqrstnopqrstu";
	private static String msg4;
	
	
	static {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 1000000; i++)
			sb.append('a');
		msg4 = sb.toString();
	}
	
	
	
	@Test
	public void testSha1() {
		test(Sha1.FUNCTION, msg0, "DA39A3EE5E6B4B0D3255BFEF95601890AFD80709");
		test(Sha1.FUNCTION, msg1, "A9993E364706816ABA3E25717850C26C9CD0D89D");
		test(Sha1.FUNCTION, msg2, "84983E441C3BD26EBAAE4AA1F95129E5E54670F1");
		test(Sha1.FUNCTION, msg4, "34AA973CD4C4DAA4F61EEB2BDBAD27316534016F");
	}
	
	
	@Test
	public void testSha224() {
		test(Sha224.FUNCTION, msg1, "23097D223405D8228642A477BDA255B32AADBCE4BDA0B3F7E36C9DA7");
		test(Sha224.FUNCTION, msg2, "75388B16512776CC5DBA5DA1FD890150B0C6455CB4F58B1952522525");
		test(Sha224.FUNCTION, msg4, "20794655980C91D8BBB4C1EA97618A4BF03F42581948B2EE4EE7AD67");
	}
	
	
	@Test
	public void testSha256() {
		test(Sha256.FUNCTION, msg0, "E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855");
		test(Sha256.FUNCTION, msg1, "BA7816BF8F01CFEA414140DE5DAE2223B00361A396177A9CB410FF61F20015AD");
		test(Sha256.FUNCTION, msg2, "248D6A61D20638B8E5C026930C3E6039A33CE45964FF2167F6ECEDD419DB06C1");
		test(Sha256.FUNCTION, msg4, "CDC76E5C9914FB9281A1C7E284D73E67F1809A48A497200E046D39CCC7112CD0");
	}
	
	
	@Test
	public void testSha384() {
		test(Sha384.FUNCTION, msg1, "CB00753F45A35E8BB5A03D699AC65007272C32AB0EDED1631A8B605A43FF5BED8086072BA1E7CC2358BAECA134C825A7");
		test(Sha384.FUNCTION, msg3, "09330C33F71147E83D192FC782CD1B4753111B173B3B05D22FA08086E3B0F712FCC7C71A557E2DB966C3E9FA91746039");
		test(Sha384.FUNCTION, msg4, "9D0E1809716474CB086E834E310A4A1CED149E9C00F248527972CEC5704C2A5B07B8B3DC38ECC4EBAE97DDD87F3D8985");
	}
	
	
	@Test
	public void testSha512() {
		test(Sha512.FUNCTION, msg0, "CF83E1357EEFB8BDF1542850D66D8007D620E4050B5715DC83F4A921D36CE9CE47D0D13C5D85F2B0FF8318D2877EEC2F63B931BD47417A81A538327AF927DA3E");
		test(Sha512.FUNCTION, msg1, "DDAF35A193617ABACC417349AE20413112E6FA4E89A97EA20A9EEEE64B55D39A2192992A274FC1A836BA3C23A3FEEBBD454D4423643CE80E2A9AC94FA54CA49F");
		test(Sha512.FUNCTION, msg3, "8E959B75DAE313DA8CF4F72814FC143F8F7779C6EB9F7FA17299AEADB6889018501D289E4900F7E4331B99DEC4B5433AC7D329EEB6DD26545E96E55B874BE909");
		test(Sha512.FUNCTION, msg4, "E718483D0CE769644E2E42C7BC15B4638E1F98B13B2044285632A803AFA973EBDE0FF244877EA60A4CB0432CE577C31BEB009C5C2C49AA2E4EADB217AD8CC09B");
	}
	
	
	@Test
	public void testZeroize() {
		try {
			testZeroization(Sha1.FUNCTION);
			testZeroization(Sha224.FUNCTION);
			testZeroization(Sha256.FUNCTION);
			testZeroization(Sha384.FUNCTION);
			testZeroization(Sha512.FUNCTION);
		} catch (ClassCastException e) {
			fail();
		}
	}
	
	
	
	private static void test(HashFunction hashfunc, String message, String hash) {
		byte[] msg = Debug.asciiToBytes(message);
		byte[] hash0 = hashfunc.getHash(msg).toBytes();
		byte[] hash1 = Debug.hexToBytes(hash);
		assertArrayEquals(hash1, hash0);
	}
	
	
	private static void testZeroization(HashFunction hashFunc) {
		Hasher hasher = hashFunc.newHasher();
		hasher.update(new byte[200]);
		((Zeroizable)hasher).zeroize();
	}
	
}