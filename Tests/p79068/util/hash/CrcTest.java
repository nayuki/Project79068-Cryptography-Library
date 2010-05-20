package p79068.util.hash;

import org.junit.Test;
import p79068.crypto.hash.HashUtils;


public final class CrcTest {
	
	@Test
	public void testCrc32() {
		HashUtils.testWithAsciiMessage(Crc.CRC32_FUNCTION, "", "00000000");
		HashUtils.testWithAsciiMessage(Crc.CRC32_FUNCTION, "a", "E8B7BE43");
		HashUtils.testWithAsciiMessage(Crc.CRC32_FUNCTION, "abc", "352441C2");
		HashUtils.testWithAsciiMessage(Crc.CRC32_FUNCTION, "message digest", "20159D7F");
		HashUtils.testWithAsciiMessage(Crc.CRC32_FUNCTION, "abcdefghijklmnopqrstuvwxyz", "4C2750BD");
		HashUtils.testWithAsciiMessage(Crc.CRC32_FUNCTION, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", "1FC2E6d2");
		HashUtils.testWithAsciiMessage(Crc.CRC32_FUNCTION, "12345678901234567890123456789012345678901234567890123456789012345678901234567890", "7CA94A72");
		HashUtils.testWithHexMessage(Crc.CRC32_FUNCTION, "FFFFFFFF", "FFFFFFFF");
		HashUtils.testWithHexMessage(Crc.CRC32_FUNCTION, "FFFFFFFF00", "FFFFFFFF");
		HashUtils.testWithHexMessage(Crc.CRC32_FUNCTION, "FFFFFFFF0000", "FFFFFFFF");
		HashUtils.testWithHexMessage(Crc.CRC32_FUNCTION, "FFFFFFFF80", "12477CDF");
	}
	
}