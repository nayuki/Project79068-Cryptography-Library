package p79068.hash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import p79068.util.random.Random;


public final class CrcTest extends HashFunctionTest {
	
	protected HashFunction[] getHashFunctionsToTest() {
		return new HashFunction[] { Crc.CRC32_FUNCTION };
	}
	
	
	@Test
	public void testCrc32Basic() {
		HashFunction hf = Crc.CRC32_FUNCTION;
		testAscii(hf, "", "00000000");
		testAscii(hf, "a", "E8B7BE43");
		testAscii(hf, "abc", "352441C2");
		testAscii(hf, "message digest", "20159D7F");
		testAscii(hf, "abcdefghijklmnopqrstuvwxyz", "4C2750BD");
		testAscii(hf, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", "1FC2E6d2");
		testAscii(hf, "12345678901234567890123456789012345678901234567890123456789012345678901234567890", "7CA94A72");
	}
	
	
	// Using the prefix 0xFFFFFFFF creates a zero register. Any number of zeros appended directly after this has no effect on the CRC.
	@Test
	public void testCrc32ZeroPadding() {
		HashFunction hf = Crc.CRC32_FUNCTION;
		testHex(hf, "FFFFFFFF"    , "FFFFFFFF");
		testHex(hf, "FFFFFFFF00"  , "FFFFFFFF");
		testHex(hf, "FFFFFFFF0000", "FFFFFFFF");
		testHex(hf, "FFFFFFFF80"    , "12477CDF");
		testHex(hf, "FFFFFFFF0080"  , "12477CDF");
		testHex(hf, "FFFFFFFF000080", "12477CDF");
		testHex(hf, "FFFFFFFF3A"        , "39F3264D");
		testHex(hf, "FFFFFFFF000000003A", "39F3264D");
	}
	
	
	// For any message m, crc32(m ++ littleEndian(crc32(m))) == 0x2144DF1C
	@Test
	public void testCrc32AppendRemainder() {
		HashFunction hf = Crc.CRC32_FUNCTION;
		testHex(hf, "61"                        , "E8B7BE43");
		testHex(hf, "6143BEB7E8"                , "2144DF1C");
		testHex(hf, "6143BEB7E81CDF4421"        , "2144DF1C");
		testHex(hf, "6143BEB7E81CDF44211CDF4421", "2144DF1C");
		testHex(hf, "616263"        , "352441C2");
		testHex(hf, "616263C2412435", "2144DF1C");
	}
	
	
	// CRC-32 always detects a 1-bit error, guaranteed
	@Test
	public void testOneBitErrorSmallRandomly() {
		Random r = Random.DEFAULT;
		for (int i = 0; i < 100; i++) {
			byte[] msg = new byte[r.uniformInt(1000) + 1];
			r.uniformBytes(msg);
			HashValue crc0 = Crc.CRC32_FUNCTION.getHash(msg);
			
			int errorBitPos = r.uniformInt(msg.length * 8);
			msg[errorBitPos / 8] ^= 1 << (errorBitPos % 8);
			HashValue crc1 = Crc.CRC32_FUNCTION.getHash(msg);
			assertFalse(crc0.equals(crc1));
		}
	}
	
	
	// CRC-32 always detects a 1-bit error, guaranteed
	@Test
	public void testOneBitErrorLargeRandomly() {
		Random r = Random.DEFAULT;
		for (int i = 0; i < 10; i++) {
			int len = r.uniformInt(10000000);
			int errorBitPos = r.uniformInt(len * 8);
			Hasher hasher0 = Crc.CRC32_FUNCTION.newHasher();
			Hasher hasher1 = Crc.CRC32_FUNCTION.newHasher();
			byte[] buf = new byte[10000];
			for (int j = 0; j < len; ) {
				r.uniformBytes(buf);
				int n = Math.min(buf.length, len - j);
				hasher0.update(buf, 0, n);
				int errorBytePos = errorBitPos / 8;
				if (errorBytePos >= j && errorBytePos < j + n)
					buf[errorBytePos - j] ^= 1 << (errorBitPos % 8);
				hasher1.update(buf, 0, n);
				j += n;
			}
			assertFalse(hasher0.getHash().equals(hasher1.getHash()));
		}
	}
	
	
	/* 
	 * crc32(x) ^ crc32(y)
	 * = (rawcrc32(x ^ 0xFFFFFFFF0...0) ^ 0xFFFFFFF) ^ (rawcrc32(y ^ 0xFFFFFFFF0...0) ^ 0xFFFFFFFF)
	 * = rawcrc32(x ^ 0xFFFFFFFF0...0) ^ rawcrc32(y ^ 0xFFFFFFFF0...0)
	 * = rawcrc32(x) ^ rawcrc32(y) ^ rawcrc32(0xFFFFFFFF0...0) ^ rawcrc32(0xFFFFFFFF0...0)
	 * = rawcrc32(x) ^ rawcrc32(y)
	 * = rawcrc32(x ^ y).
	 * 
	 * crc32(x ^ y)
	 * = rawcrc32(x ^ y ^ 0xFFFFFFFF0...0) ^ 0xFFFFFFFF
	 * = rawcrc32(x ^ y) ^ rawcrc32(0xFFFFFFFF0...0) ^ 0xFFFFFFFF
	 * = crc32(x) ^ crc32(y) ^ crc32(0...0).
	 */
	@Test
	public void testCrc32PseudolinearityRandomly() {
		Random r = Random.DEFAULT;
		for (int i = 0; i < 1000; i++) {
			int n = r.uniformInt(1000) + 4;
			byte[] x = new byte[n];
			byte[] y = new byte[n];
			byte[] xor = new byte[n];
			byte[] zeros = new byte[n];
			r.uniformBytes(x);
			r.uniformBytes(y);
			for (int j = 0; j < xor.length; j++)
				xor[j] = (byte)(x[j] ^ y[j]);
			assertEquals(getCrc32HashInt(xor), getCrc32HashInt(x) ^ getCrc32HashInt(y) ^ getCrc32HashInt(zeros));
		}
	}
	
	
	private static int getCrc32HashInt(byte[] b) {
		byte[] h = Crc.CRC32_FUNCTION.getHash(b).toBytes();
		return h[0] << 24 | (h[1] & 0xFF) << 16 | (h[2] & 0xFF) << 8 | (h[3] & 0xFF);
	}
	
}
