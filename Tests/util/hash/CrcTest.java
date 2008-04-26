package util.hash;

import org.junit.Test;
import crypto.CryptoUtils;
import p79068.util.hash.Crc;


public class CrcTest {
	
	@Test
	public void testCrc32() {
		CryptoUtils.testWithAsciiMessage(Crc.CRC32_FUNCTION, "", "00000000");
		CryptoUtils.testWithHexMessage(Crc.CRC32_FUNCTION, "FFFFFFFF", "FFFFFFFF");
		CryptoUtils.testWithHexMessage(Crc.CRC32_FUNCTION, "FFFFFFFF00", "FFFFFFFF");
		CryptoUtils.testWithHexMessage(Crc.CRC32_FUNCTION, "FFFFFFFF0000", "FFFFFFFF");
	}
	
}