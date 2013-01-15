package p79068.hash;

import org.junit.Test;
import static p79068.hash.HashUtils.testAscii;
import static p79068.hash.HashUtils.testHex;


public final class CrcTest {
	
	@Test
	public void testCrc32() {
		testAscii(Crc.CRC32_FUNCTION, "", "00000000");
		testAscii(Crc.CRC32_FUNCTION, "a", "E8B7BE43");
		testAscii(Crc.CRC32_FUNCTION, "abc", "352441C2");
		testAscii(Crc.CRC32_FUNCTION, "message digest", "20159D7F");
		testAscii(Crc.CRC32_FUNCTION, "abcdefghijklmnopqrstuvwxyz", "4C2750BD");
		testAscii(Crc.CRC32_FUNCTION, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", "1FC2E6d2");
		testAscii(Crc.CRC32_FUNCTION, "12345678901234567890123456789012345678901234567890123456789012345678901234567890", "7CA94A72");
	}
	
	
	// Using the prefix 0xFFFFFFFF creates a zero register. Any number of zeros appended directly after this has no effect on the CRC.
	@Test
	public void testCrc32ZeroPadding() {
		testHex(Crc.CRC32_FUNCTION, "FFFFFFFF", "FFFFFFFF");
		testHex(Crc.CRC32_FUNCTION, "FFFFFFFF00", "FFFFFFFF");
		testHex(Crc.CRC32_FUNCTION, "FFFFFFFF0000", "FFFFFFFF");
		
		testHex(Crc.CRC32_FUNCTION, "FFFFFFFF80", "12477CDF");
		testHex(Crc.CRC32_FUNCTION, "FFFFFFFF0080", "12477CDF");
		testHex(Crc.CRC32_FUNCTION, "FFFFFFFF000080", "12477CDF");
	}
	
	
	// For any message m, crc32(m ++ littleEndian(crc32(m))) == 0x2144DF1C
	@Test
	public void testCrc32AppendRemainder() {
		testHex(Crc.CRC32_FUNCTION, "61", "E8B7BE43");
		testHex(Crc.CRC32_FUNCTION, "6143BEB7E8", "2144DF1C");
		testHex(Crc.CRC32_FUNCTION, "6143BEB7E81CDF4421", "2144DF1C");
		
		testHex(Crc.CRC32_FUNCTION, "6143BEB7E81CDF44211CDF4421", "2144DF1C");
		testHex(Crc.CRC32_FUNCTION, "616263", "352441C2");
		testHex(Crc.CRC32_FUNCTION, "616263C2412435", "2144DF1C");
	}
	
}
