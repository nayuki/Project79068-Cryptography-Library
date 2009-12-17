package p79068.crypto.hash;

import org.junit.Test;
import p79068.crypto.CryptoUtils;


public class MdTest {
	
	private static final String msg0 = "";
	private static final String msg1 = "a";
	private static final String msg2 = "abc";
	private static final String msg3 = "message digest";
	private static final String msg4 = "abcdefghijklmnopqrstuvwxyz";
	private static final String msg5 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final String msg6 = "12345678901234567890123456789012345678901234567890123456789012345678901234567890";
	
	
	
	@Test
	public void testMd2() {
		CryptoUtils.testWithAsciiMessage(Md2.FUNCTION, msg0, "8350E5A3E24C153DF2275C9F80692773");
		CryptoUtils.testWithAsciiMessage(Md2.FUNCTION, msg1, "32EC01EC4A6DAC72C0AB96FB34C0B5D1");
		CryptoUtils.testWithAsciiMessage(Md2.FUNCTION, msg2, "DA853B0D3F88D99B30283A69E6DED6BB");
		CryptoUtils.testWithAsciiMessage(Md2.FUNCTION, msg3, "AB4F496BFB2A530B219FF33031FE06B0");
		CryptoUtils.testWithAsciiMessage(Md2.FUNCTION, msg4, "4E8DDFF3650292AB5A4108C3AA47940B");
		CryptoUtils.testWithAsciiMessage(Md2.FUNCTION, msg5, "DA33DEF2A42DF13975352846C30338CD");
		CryptoUtils.testWithAsciiMessage(Md2.FUNCTION, msg6, "D5976F79D83D3A0DC9806C3C66F3EFD8");
	}
	
	
	@Test
	public void testMd4() {
		CryptoUtils.testWithAsciiMessage(Md4.FUNCTION, msg0, "31D6CFE0D16AE931B73C59D7E0C089C0");
		CryptoUtils.testWithAsciiMessage(Md4.FUNCTION, msg1, "BDE52CB31DE33E46245E05FBDBD6FB24");
		CryptoUtils.testWithAsciiMessage(Md4.FUNCTION, msg2, "A448017AAF21D8525FC10AE87AA6729D");
		CryptoUtils.testWithAsciiMessage(Md4.FUNCTION, msg3, "D9130A8164549FE818874806E1C7014B");
		CryptoUtils.testWithAsciiMessage(Md4.FUNCTION, msg4, "D79E1C308AA5BBCDEEA8ED63DF412DA9");
		CryptoUtils.testWithAsciiMessage(Md4.FUNCTION, msg5, "043F8582F241DB351CE627E153E7F0E4");
		CryptoUtils.testWithAsciiMessage(Md4.FUNCTION, msg6, "E33B4DDC9C38F2199C3E7B164FCC0536");
	}
	
	
	@Test
	public void testMd5() {
		CryptoUtils.testWithAsciiMessage(Md5.FUNCTION, msg0, "D41D8CD98F00B204E9800998ECF8427E");
		CryptoUtils.testWithAsciiMessage(Md5.FUNCTION, msg1, "0CC175B9C0F1B6A831C399E269772661");
		CryptoUtils.testWithAsciiMessage(Md5.FUNCTION, msg2, "900150983CD24FB0D6963F7D28E17F72");
		CryptoUtils.testWithAsciiMessage(Md5.FUNCTION, msg3, "F96B697D7CB7938D525A2F31AAF161D0");
		CryptoUtils.testWithAsciiMessage(Md5.FUNCTION, msg4, "C3FCD3D76192E4007DFB496CCA67E13B");
		CryptoUtils.testWithAsciiMessage(Md5.FUNCTION, msg5, "D174AB98D277D9F5A5611C2C9F419D9F");
		CryptoUtils.testWithAsciiMessage(Md5.FUNCTION, msg6, "57EDF4A22BE3C955AC49DA2E2107B67A");
	}
	
	
	@Test
	public void testZeroize() {
		CryptoUtils.testZeroization(Md2.FUNCTION);
		CryptoUtils.testZeroization(Md4.FUNCTION);
		CryptoUtils.testZeroization(Md5.FUNCTION);
	}
	
}