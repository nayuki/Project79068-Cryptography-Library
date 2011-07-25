package p79068.crypto.hash;

import org.junit.Test;


public final class MdTest {
	
	private static final String msg0 = "";
	private static final String msg1 = "a";
	private static final String msg2 = "abc";
	private static final String msg3 = "message digest";
	private static final String msg4 = "abcdefghijklmnopqrstuvwxyz";
	private static final String msg5 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final String msg6 = "12345678901234567890123456789012345678901234567890123456789012345678901234567890";
	
	
	
	@Test
	public void testMd4() {
		HashUtils.testAscii(Md.MD4_FUNCTION, msg0, "31D6CFE0D16AE931B73C59D7E0C089C0");
		HashUtils.testAscii(Md.MD4_FUNCTION, msg1, "BDE52CB31DE33E46245E05FBDBD6FB24");
		HashUtils.testAscii(Md.MD4_FUNCTION, msg2, "A448017AAF21D8525FC10AE87AA6729D");
		HashUtils.testAscii(Md.MD4_FUNCTION, msg3, "D9130A8164549FE818874806E1C7014B");
		HashUtils.testAscii(Md.MD4_FUNCTION, msg4, "D79E1C308AA5BBCDEEA8ED63DF412DA9");
		HashUtils.testAscii(Md.MD4_FUNCTION, msg5, "043F8582F241DB351CE627E153E7F0E4");
		HashUtils.testAscii(Md.MD4_FUNCTION, msg6, "E33B4DDC9C38F2199C3E7B164FCC0536");
	}
	
	
	@Test
	public void testMd5() {
		HashUtils.testAscii(Md.MD5_FUNCTION, msg0, "D41D8CD98F00B204E9800998ECF8427E");
		HashUtils.testAscii(Md.MD5_FUNCTION, msg1, "0CC175B9C0F1B6A831C399E269772661");
		HashUtils.testAscii(Md.MD5_FUNCTION, msg2, "900150983CD24FB0D6963F7D28E17F72");
		HashUtils.testAscii(Md.MD5_FUNCTION, msg3, "F96B697D7CB7938D525A2F31AAF161D0");
		HashUtils.testAscii(Md.MD5_FUNCTION, msg4, "C3FCD3D76192E4007DFB496CCA67E13B");
		HashUtils.testAscii(Md.MD5_FUNCTION, msg5, "D174AB98D277D9F5A5611C2C9F419D9F");
		HashUtils.testAscii(Md.MD5_FUNCTION, msg6, "57EDF4A22BE3C955AC49DA2E2107B67A");
	}
	
	
	@Test
	public void testZeroize() {
		HashUtils.testZeroization(Md.MD4_FUNCTION);
		HashUtils.testZeroization(Md.MD5_FUNCTION);
	}
	
}
