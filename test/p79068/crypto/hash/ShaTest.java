package p79068.crypto.hash;

import java.util.Arrays;

import org.junit.Test;

import p79068.hash.HashFunction;


public final class ShaTest extends CryptoHashFunctionTest {
	
	protected HashFunction[] getHashFunctionsToTest() {
		return new HashFunction[] {
			Sha.SHA_FUNCTION,
			Sha.SHA1_FUNCTION,
			Sha.SHA224_FUNCTION,
			Sha.SHA256_FUNCTION,
			Sha.SHA384_FUNCTION,
			Sha.SHA512_FUNCTION,
		};
	}
	
	
	private static String msg0 = "";
	private static String msg1 = "a";
	private static String msg2 = "abc";
	private static String msg3 = "message digest";
	private static String msg4 = "abcdefghijklmnopqrstuvwxyz";
	private static String msg5 = "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq";
	private static String msg6 = "abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmnoijklmnopjklmnopqklmnopqrlmnopqrsmnopqrstnopqrstu";
	private static String msg7;  // One million a's
	
	static {
		char[] c = new char[1000000];
		Arrays.fill(c, 'a');
		msg7 = new String(c);
	}
	
	
	
	@Test
	public void testSha() {
		testAscii(Sha.SHA_FUNCTION, new String[][] {
			{msg0, "F96CEA198AD1DD5617AC084A3D92C6107708C0EF"},
			{msg1, "37F297772FAE4CB1BA39B6CF9CF0381180BD62F2"},
			{msg2, "0164B8A914CD2A5E74C4F7FF082C4D97F1EDF880"},
			{msg3, "C1B0F222D150EBB9AA36A40CAFDC8BCBED830B14"},
			{msg4, "B40CE07A430CFD3C033039B9FE9AFEC95DC1BDCD"},
			{msg5, "D2516EE1ACFA5BAF33DFC1C471E438449EF134C8"},
			{msg7, "3232AFFA48628A26653B5AAA44541FD90D690603"},
		});
	}
	
	
	@Test
	public void testSha1() {
		testAscii(Sha.SHA1_FUNCTION, new String[][] {
			{msg0, "DA39A3EE5E6B4B0D3255BFEF95601890AFD80709"},
			{msg1, "86F7E437FAA5A7FCE15D1DDCB9EAEAEA377667B8"},
			{msg2, "A9993E364706816ABA3E25717850C26C9CD0D89D"},
			{msg3, "C12252CEDA8BE8994D5FA0290A47231C1D16AAE3"},
			{msg4, "32D10C7B8CF96570CA04CE37F2A19D84240D3A89"},
			{msg5, "84983E441C3BD26EBAAE4AA1F95129E5E54670F1"},
			{msg7, "34AA973CD4C4DAA4F61EEB2BDBAD27316534016F"},
		});
	}
	
	
	@Test
	public void testSha224() {
		testAscii(Sha.SHA224_FUNCTION, new String[][] {
			{msg0, "D14A028C2A3A2BC9476102BB288234C415A2B01F828EA62AC5B3E42F"},
			{msg2, "23097D223405D8228642A477BDA255B32AADBCE4BDA0B3F7E36C9DA7"},
			{msg5, "75388B16512776CC5DBA5DA1FD890150B0C6455CB4F58B1952522525"},
			{msg7, "20794655980C91D8BBB4C1EA97618A4BF03F42581948B2EE4EE7AD67"},
		});
	}
	
	
	@Test
	public void testSha256() {
		testAscii(Sha.SHA256_FUNCTION, new String[][] {
			{msg0, "E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855"},
			{msg1, "CA978112CA1BBDCAFAC231B39A23DC4DA786EFF8147C4E72B9807785AFEE48BB"},
			{msg2, "BA7816BF8F01CFEA414140DE5DAE2223B00361A396177A9CB410FF61F20015AD"},
			{msg3, "F7846F55CF23E14EEBEAB5B4E1550CAD5B509E3348FBC4EFA3A1413D393CB650"},
			{msg4, "71C480DF93D6AE2F1EFAD1447C66C9525E316218CF51FC8D9ED832F2DAF18B73"},
			{msg5, "248D6A61D20638B8E5C026930C3E6039A33CE45964FF2167F6ECEDD419DB06C1"},
			{msg7, "CDC76E5C9914FB9281A1C7E284D73E67F1809A48A497200E046D39CCC7112CD0"},
		});
	}
	
	
	@Test
	public void testSha384() {
		testAscii(Sha.SHA384_FUNCTION, new String[][] {
			{msg0, "38B060A751AC96384CD9327EB1B1E36A21FDB71114BE07434C0CC7BF63F6E1DA274EDEBFE76F65FBD51AD2F14898B95B"},
			{msg1, "54A59B9F22B0B80880D8427E548B7C23ABD873486E1F035DCE9CD697E85175033CAA88E6D57BC35EFAE0B5AFD3145F31"},
			{msg2, "CB00753F45A35E8BB5A03D699AC65007272C32AB0EDED1631A8B605A43FF5BED8086072BA1E7CC2358BAECA134C825A7"},
			{msg3, "473ED35167EC1F5D8E550368A3DB39BE54639F828868E9454C239FC8B52E3C61DBD0D8B4DE1390C256DCBB5D5FD99CD5"},
			{msg4, "FEB67349DF3DB6F5924815D6C3DC133F091809213731FE5C7B5F4999E463479FF2877F5F2936FA63BB43784B12F3EBB4"},
			{msg6, "09330C33F71147E83D192FC782CD1B4753111B173B3B05D22FA08086E3B0F712FCC7C71A557E2DB966C3E9FA91746039"},
			{msg7, "9D0E1809716474CB086E834E310A4A1CED149E9C00F248527972CEC5704C2A5B07B8B3DC38ECC4EBAE97DDD87F3D8985"},
		});
	}
	
	
	@Test
	public void testSha512() {
		testAscii(Sha.SHA512_FUNCTION, new String[][] {
			{msg0, "CF83E1357EEFB8BDF1542850D66D8007D620E4050B5715DC83F4A921D36CE9CE47D0D13C5D85F2B0FF8318D2877EEC2F63B931BD47417A81A538327AF927DA3E"},
			{msg1, "1F40FC92DA241694750979EE6CF582F2D5D7D28E18335DE05ABC54D0560E0F5302860C652BF08D560252AA5E74210546F369FBBBCE8C12CFC7957B2652FE9A75"},
			{msg2, "DDAF35A193617ABACC417349AE20413112E6FA4E89A97EA20A9EEEE64B55D39A2192992A274FC1A836BA3C23A3FEEBBD454D4423643CE80E2A9AC94FA54CA49F"},
			{msg3, "107DBF389D9E9F71A3A95F6C055B9251BC5268C2BE16D6C13492EA45B0199F3309E16455AB1E96118E8A905D5597B72038DDB372A89826046DE66687BB420E7C"},
			{msg4, "4DBFF86CC2CA1BAE1E16468A05CB9881C97F1753BCE3619034898FAA1AABE429955A1BF8EC483D7421FE3C1646613A59ED5441FB0F321389F77F48A879C7B1F1"},
			{msg6, "8E959B75DAE313DA8CF4F72814FC143F8F7779C6EB9F7FA17299AEADB6889018501D289E4900F7E4331B99DEC4B5433AC7D329EEB6DD26545E96E55B874BE909"},
			{msg7, "E718483D0CE769644E2E42C7BC15B4638E1F98B13B2044285632A803AFA973EBDE0FF244877EA60A4CB0432CE577C31BEB009C5C2C49AA2E4EADB217AD8CC09B"},
		});
	}
	
}
