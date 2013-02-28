package p79068.hash;

import static org.junit.Assert.assertFalse;
import static p79068.hash.HashUtils.testAscii;
import static p79068.hash.HashUtils.testHex;

import org.junit.Test;

import p79068.util.random.Random;


public final class CrcTest {
	
	private static Random random = Random.DEFAULT;
	
	
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
		testHex(Crc.CRC32_FUNCTION, "FFFFFFFF"    , "FFFFFFFF");
		testHex(Crc.CRC32_FUNCTION, "FFFFFFFF00"  , "FFFFFFFF");
		testHex(Crc.CRC32_FUNCTION, "FFFFFFFF0000", "FFFFFFFF");
		
		testHex(Crc.CRC32_FUNCTION, "FFFFFFFF80"    , "12477CDF");
		testHex(Crc.CRC32_FUNCTION, "FFFFFFFF0080"  , "12477CDF");
		testHex(Crc.CRC32_FUNCTION, "FFFFFFFF000080", "12477CDF");
	}
	
	
	// For any message m, crc32(m ++ littleEndian(crc32(m))) == 0x2144DF1C
	@Test
	public void testCrc32AppendRemainder() {
		testHex(Crc.CRC32_FUNCTION, "61"                        , "E8B7BE43");
		testHex(Crc.CRC32_FUNCTION, "6143BEB7E8"                , "2144DF1C");
		testHex(Crc.CRC32_FUNCTION, "6143BEB7E81CDF4421"        , "2144DF1C");
		testHex(Crc.CRC32_FUNCTION, "6143BEB7E81CDF44211CDF4421", "2144DF1C");
		
		testHex(Crc.CRC32_FUNCTION, "616263"        , "352441C2");
		testHex(Crc.CRC32_FUNCTION, "616263C2412435", "2144DF1C");
	}
	
	
	// CRC-32 always detects a 1-bit error, guaranteed.
	@Test
	public void testOneBitErrorSmallRandomly() {
		for (int i = 0; i < 100; i++) {
			byte[] b = new byte[random.uniformInt(1000) + 1];
			random.uniformBytes(b);
			HashValue crc0 = Crc.CRC32_FUNCTION.getHash(b);
			
			int j = random.uniformInt(b.length * 8);  // Bit position
			b[j / 8] ^= 1 << (j % 8);
			HashValue crc1 = Crc.CRC32_FUNCTION.getHash(b);
			assertFalse(crc0.equals(crc1));
		}
	}
	
	
	// CRC-32 always detects a 1-bit error, guaranteed.
	@Test
	public void testOneBitErrorLargeRandomly() {
		for (int i = 0; i < 10; i++) {
			int len = random.uniformInt(10000000);
			int k = random.uniformInt(len * 8);  // Bit position
			Hasher hasher0 = Crc.CRC32_FUNCTION.newHasher();
			Hasher hasher1 = Crc.CRC32_FUNCTION.newHasher();
			byte[] b = new byte[10000];
			for (int j = 0; j < len; ) {
				random.uniformBytes(b);
				int actualLen = Math.min(b.length, len - j);
				hasher0.update(b, 0, actualLen);
				if (k / 8 >= j && k / 8 < j + actualLen)
					b[k / 8 - j] ^= 1 << k % 8;
				hasher1.update(b, 0, actualLen);
				j += actualLen;
			}
			assertFalse(hasher0.getHash().equals(hasher1.getHash()));
		}
	}
	
}
