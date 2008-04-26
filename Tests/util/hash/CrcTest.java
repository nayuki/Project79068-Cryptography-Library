package util.hash;

import org.junit.Test;
import crypto.CryptoUtils;
import p79068.util.hash.Crc;


public class CrcTest {
	
	@Test
	public void testCrc32() {
		CryptoUtils.testWithAsciiMessage(Crc.CRC32_FUNCTION, "", "00000000");
		CryptoUtils.testWithAsciiMessage(Crc.CRC32_FUNCTION, "a", "E8B7BE43");
		CryptoUtils.testWithAsciiMessage(Crc.CRC32_FUNCTION, "abc", "352441C2");
		CryptoUtils.testWithAsciiMessage(Crc.CRC32_FUNCTION, "message digest", "20159D7F");
		CryptoUtils.testWithAsciiMessage(Crc.CRC32_FUNCTION, "abcdefghijklmnopqrstuvwxyz", "4C2750BD");
		CryptoUtils.testWithAsciiMessage(Crc.CRC32_FUNCTION, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", "1FC2E6d2");
		CryptoUtils.testWithAsciiMessage(Crc.CRC32_FUNCTION, "12345678901234567890123456789012345678901234567890123456789012345678901234567890", "7CA94A72");
		CryptoUtils.testWithHexMessage(Crc.CRC32_FUNCTION, "FFFFFFFF", "FFFFFFFF");
		CryptoUtils.testWithHexMessage(Crc.CRC32_FUNCTION, "FFFFFFFF00", "FFFFFFFF");
		CryptoUtils.testWithHexMessage(Crc.CRC32_FUNCTION, "FFFFFFFF0000", "FFFFFFFF");
	}
	
}