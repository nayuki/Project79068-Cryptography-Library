package p79068.crypto.hash;

import java.math.BigInteger;

import p79068.Assert;
import p79068.crypto.Zeroizer;
import p79068.hash.HashValue;
import p79068.math.IntegerBitMath;


final class FastMd45Core extends BlockHasherCore {
	
	private final boolean md5Mode;
	
	private int[] state;
	
	
	
	public FastMd45Core(boolean md5Mode) {
		this.md5Mode = md5Mode;
		state = new int[]{0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476};
	}
	
	
	
	@Override
	public void compress(byte[] msg, int off, int len) {
		Assert.assertRangeInBounds(msg.length, off, len);
		if (len % 64 != 0)
			throw new AssertionError();
		
		int[] schedule = new int[16];
		int a = state[0];
		int b = state[1];
		int c = state[2];
		int d = state[3];
		
		// For each block of 64 bytes
		for (int i = off, end = off + len; i < end; i += 64) {
			
			// Pack bytes into int32s in little endian
			schedule[ 0] = (msg[i +  0] & 0xFF) | (msg[i +  1] & 0xFF) << 8 | (msg[i +  2] & 0xFF) << 16 | msg[i +  3] << 24;
			schedule[ 1] = (msg[i +  4] & 0xFF) | (msg[i +  5] & 0xFF) << 8 | (msg[i +  6] & 0xFF) << 16 | msg[i +  7] << 24;
			schedule[ 2] = (msg[i +  8] & 0xFF) | (msg[i +  9] & 0xFF) << 8 | (msg[i + 10] & 0xFF) << 16 | msg[i + 11] << 24;
			schedule[ 3] = (msg[i + 12] & 0xFF) | (msg[i + 13] & 0xFF) << 8 | (msg[i + 14] & 0xFF) << 16 | msg[i + 15] << 24;
			schedule[ 4] = (msg[i + 16] & 0xFF) | (msg[i + 17] & 0xFF) << 8 | (msg[i + 18] & 0xFF) << 16 | msg[i + 19] << 24;
			schedule[ 5] = (msg[i + 20] & 0xFF) | (msg[i + 21] & 0xFF) << 8 | (msg[i + 22] & 0xFF) << 16 | msg[i + 23] << 24;
			schedule[ 6] = (msg[i + 24] & 0xFF) | (msg[i + 25] & 0xFF) << 8 | (msg[i + 26] & 0xFF) << 16 | msg[i + 27] << 24;
			schedule[ 7] = (msg[i + 28] & 0xFF) | (msg[i + 29] & 0xFF) << 8 | (msg[i + 30] & 0xFF) << 16 | msg[i + 31] << 24;
			schedule[ 8] = (msg[i + 32] & 0xFF) | (msg[i + 33] & 0xFF) << 8 | (msg[i + 34] & 0xFF) << 16 | msg[i + 35] << 24;
			schedule[ 9] = (msg[i + 36] & 0xFF) | (msg[i + 37] & 0xFF) << 8 | (msg[i + 38] & 0xFF) << 16 | msg[i + 39] << 24;
			schedule[10] = (msg[i + 40] & 0xFF) | (msg[i + 41] & 0xFF) << 8 | (msg[i + 42] & 0xFF) << 16 | msg[i + 43] << 24;
			schedule[11] = (msg[i + 44] & 0xFF) | (msg[i + 45] & 0xFF) << 8 | (msg[i + 46] & 0xFF) << 16 | msg[i + 47] << 24;
			schedule[12] = (msg[i + 48] & 0xFF) | (msg[i + 49] & 0xFF) << 8 | (msg[i + 50] & 0xFF) << 16 | msg[i + 51] << 24;
			schedule[13] = (msg[i + 52] & 0xFF) | (msg[i + 53] & 0xFF) << 8 | (msg[i + 54] & 0xFF) << 16 | msg[i + 55] << 24;
			schedule[14] = (msg[i + 56] & 0xFF) | (msg[i + 57] & 0xFF) << 8 | (msg[i + 58] & 0xFF) << 16 | msg[i + 59] << 24;
			schedule[15] = (msg[i + 60] & 0xFF) | (msg[i + 61] & 0xFF) << 8 | (msg[i + 62] & 0xFF) << 16 | msg[i + 63] << 24;
			
			if (!md5Mode) {
				// The 48 rounds (MD4)
				a += (d ^ (b & (c ^ d))) + schedule[ 0];  a = a <<  3 | a >>> 29;
				d += (c ^ (a & (b ^ c))) + schedule[ 1];  d = d <<  7 | d >>> 25;
				c += (b ^ (d & (a ^ b))) + schedule[ 2];  c = c << 11 | c >>> 21;
				b += (a ^ (c & (d ^ a))) + schedule[ 3];  b = b << 19 | b >>> 13;
				a += (d ^ (b & (c ^ d))) + schedule[ 4];  a = a <<  3 | a >>> 29;
				d += (c ^ (a & (b ^ c))) + schedule[ 5];  d = d <<  7 | d >>> 25;
				c += (b ^ (d & (a ^ b))) + schedule[ 6];  c = c << 11 | c >>> 21;
				b += (a ^ (c & (d ^ a))) + schedule[ 7];  b = b << 19 | b >>> 13;
				a += (d ^ (b & (c ^ d))) + schedule[ 8];  a = a <<  3 | a >>> 29;
				d += (c ^ (a & (b ^ c))) + schedule[ 9];  d = d <<  7 | d >>> 25;
				c += (b ^ (d & (a ^ b))) + schedule[10];  c = c << 11 | c >>> 21;
				b += (a ^ (c & (d ^ a))) + schedule[11];  b = b << 19 | b >>> 13;
				a += (d ^ (b & (c ^ d))) + schedule[12];  a = a <<  3 | a >>> 29;
				d += (c ^ (a & (b ^ c))) + schedule[13];  d = d <<  7 | d >>> 25;
				c += (b ^ (d & (a ^ b))) + schedule[14];  c = c << 11 | c >>> 21;
				b += (a ^ (c & (d ^ a))) + schedule[15];  b = b << 19 | b >>> 13;
				a += ((b & c) | (d & (b | c))) + schedule[ 0] + 0x5A827999;  a = a <<  3 | a >>> 29;
				d += ((a & b) | (c & (a | b))) + schedule[ 4] + 0x5A827999;  d = d <<  5 | d >>> 27;
				c += ((d & a) | (b & (d | a))) + schedule[ 8] + 0x5A827999;  c = c <<  9 | c >>> 23;
				b += ((c & d) | (a & (c | d))) + schedule[12] + 0x5A827999;  b = b << 13 | b >>> 19;
				a += ((b & c) | (d & (b | c))) + schedule[ 1] + 0x5A827999;  a = a <<  3 | a >>> 29;
				d += ((a & b) | (c & (a | b))) + schedule[ 5] + 0x5A827999;  d = d <<  5 | d >>> 27;
				c += ((d & a) | (b & (d | a))) + schedule[ 9] + 0x5A827999;  c = c <<  9 | c >>> 23;
				b += ((c & d) | (a & (c | d))) + schedule[13] + 0x5A827999;  b = b << 13 | b >>> 19;
				a += ((b & c) | (d & (b | c))) + schedule[ 2] + 0x5A827999;  a = a <<  3 | a >>> 29;
				d += ((a & b) | (c & (a | b))) + schedule[ 6] + 0x5A827999;  d = d <<  5 | d >>> 27;
				c += ((d & a) | (b & (d | a))) + schedule[10] + 0x5A827999;  c = c <<  9 | c >>> 23;
				b += ((c & d) | (a & (c | d))) + schedule[14] + 0x5A827999;  b = b << 13 | b >>> 19;
				a += ((b & c) | (d & (b | c))) + schedule[ 3] + 0x5A827999;  a = a <<  3 | a >>> 29;
				d += ((a & b) | (c & (a | b))) + schedule[ 7] + 0x5A827999;  d = d <<  5 | d >>> 27;
				c += ((d & a) | (b & (d | a))) + schedule[11] + 0x5A827999;  c = c <<  9 | c >>> 23;
				b += ((c & d) | (a & (c | d))) + schedule[15] + 0x5A827999;  b = b << 13 | b >>> 19;
				a += (b ^ c ^ d) + schedule[ 0] + 0x6ED9EBA1;  a = a <<  3 | a >>> 29;
				d += (a ^ b ^ c) + schedule[ 8] + 0x6ED9EBA1;  d = d <<  9 | d >>> 23;
				c += (d ^ a ^ b) + schedule[ 4] + 0x6ED9EBA1;  c = c << 11 | c >>> 21;
				b += (c ^ d ^ a) + schedule[12] + 0x6ED9EBA1;  b = b << 15 | b >>> 17;
				a += (b ^ c ^ d) + schedule[ 2] + 0x6ED9EBA1;  a = a <<  3 | a >>> 29;
				d += (a ^ b ^ c) + schedule[10] + 0x6ED9EBA1;  d = d <<  9 | d >>> 23;
				c += (d ^ a ^ b) + schedule[ 6] + 0x6ED9EBA1;  c = c << 11 | c >>> 21;
				b += (c ^ d ^ a) + schedule[14] + 0x6ED9EBA1;  b = b << 15 | b >>> 17;
				a += (b ^ c ^ d) + schedule[ 1] + 0x6ED9EBA1;  a = a <<  3 | a >>> 29;
				d += (a ^ b ^ c) + schedule[ 9] + 0x6ED9EBA1;  d = d <<  9 | d >>> 23;
				c += (d ^ a ^ b) + schedule[ 5] + 0x6ED9EBA1;  c = c << 11 | c >>> 21;
				b += (c ^ d ^ a) + schedule[13] + 0x6ED9EBA1;  b = b << 15 | b >>> 17;
				a += (b ^ c ^ d) + schedule[ 3] + 0x6ED9EBA1;  a = a <<  3 | a >>> 29;
				d += (a ^ b ^ c) + schedule[11] + 0x6ED9EBA1;  d = d <<  9 | d >>> 23;
				c += (d ^ a ^ b) + schedule[ 7] + 0x6ED9EBA1;  c = c << 11 | c >>> 21;
				b += (c ^ d ^ a) + schedule[15] + 0x6ED9EBA1;  b = b << 15 | b >>> 17;
			}
			else {
				// The 64 rounds (MD5)
				a += (d ^ (b & (c ^ d))) + 0xD76AA478 + schedule[ 0];  a = b + (a <<  7 | a >>> 25);
				d += (c ^ (a & (b ^ c))) + 0xE8C7B756 + schedule[ 1];  d = a + (d << 12 | d >>> 20);
				c += (b ^ (d & (a ^ b))) + 0x242070DB + schedule[ 2];  c = d + (c << 17 | c >>> 15);
				b += (a ^ (c & (d ^ a))) + 0xC1BDCEEE + schedule[ 3];  b = c + (b << 22 | b >>> 10);
				a += (d ^ (b & (c ^ d))) + 0xF57C0FAF + schedule[ 4];  a = b + (a <<  7 | a >>> 25);
				d += (c ^ (a & (b ^ c))) + 0x4787C62A + schedule[ 5];  d = a + (d << 12 | d >>> 20);
				c += (b ^ (d & (a ^ b))) + 0xA8304613 + schedule[ 6];  c = d + (c << 17 | c >>> 15);
				b += (a ^ (c & (d ^ a))) + 0xFD469501 + schedule[ 7];  b = c + (b << 22 | b >>> 10);
				a += (d ^ (b & (c ^ d))) + 0x698098D8 + schedule[ 8];  a = b + (a <<  7 | a >>> 25);
				d += (c ^ (a & (b ^ c))) + 0x8B44F7AF + schedule[ 9];  d = a + (d << 12 | d >>> 20);
				c += (b ^ (d & (a ^ b))) + 0xFFFF5BB1 + schedule[10];  c = d + (c << 17 | c >>> 15);
				b += (a ^ (c & (d ^ a))) + 0x895CD7BE + schedule[11];  b = c + (b << 22 | b >>> 10);
				a += (d ^ (b & (c ^ d))) + 0x6B901122 + schedule[12];  a = b + (a <<  7 | a >>> 25);
				d += (c ^ (a & (b ^ c))) + 0xFD987193 + schedule[13];  d = a + (d << 12 | d >>> 20);
				c += (b ^ (d & (a ^ b))) + 0xA679438E + schedule[14];  c = d + (c << 17 | c >>> 15);
				b += (a ^ (c & (d ^ a))) + 0x49B40821 + schedule[15];  b = c + (b << 22 | b >>> 10);
				a += (c ^ (d & (b ^ c))) + 0xF61E2562 + schedule[ 1];  a = b + (a <<  5 | a >>> 27);
				d += (b ^ (c & (a ^ b))) + 0xC040B340 + schedule[ 6];  d = a + (d <<  9 | d >>> 23);
				c += (a ^ (b & (d ^ a))) + 0x265E5A51 + schedule[11];  c = d + (c << 14 | c >>> 18);
				b += (d ^ (a & (c ^ d))) + 0xE9B6C7AA + schedule[ 0];  b = c + (b << 20 | b >>> 12);
				a += (c ^ (d & (b ^ c))) + 0xD62F105D + schedule[ 5];  a = b + (a <<  5 | a >>> 27);
				d += (b ^ (c & (a ^ b))) + 0x02441453 + schedule[10];  d = a + (d <<  9 | d >>> 23);
				c += (a ^ (b & (d ^ a))) + 0xD8A1E681 + schedule[15];  c = d + (c << 14 | c >>> 18);
				b += (d ^ (a & (c ^ d))) + 0xE7D3FBC8 + schedule[ 4];  b = c + (b << 20 | b >>> 12);
				a += (c ^ (d & (b ^ c))) + 0x21E1CDE6 + schedule[ 9];  a = b + (a <<  5 | a >>> 27);
				d += (b ^ (c & (a ^ b))) + 0xC33707D6 + schedule[14];  d = a + (d <<  9 | d >>> 23);
				c += (a ^ (b & (d ^ a))) + 0xF4D50D87 + schedule[ 3];  c = d + (c << 14 | c >>> 18);
				b += (d ^ (a & (c ^ d))) + 0x455A14ED + schedule[ 8];  b = c + (b << 20 | b >>> 12);
				a += (c ^ (d & (b ^ c))) + 0xA9E3E905 + schedule[13];  a = b + (a <<  5 | a >>> 27);
				d += (b ^ (c & (a ^ b))) + 0xFCEFA3F8 + schedule[ 2];  d = a + (d <<  9 | d >>> 23);
				c += (a ^ (b & (d ^ a))) + 0x676F02D9 + schedule[ 7];  c = d + (c << 14 | c >>> 18);
				b += (d ^ (a & (c ^ d))) + 0x8D2A4C8A + schedule[12];  b = c + (b << 20 | b >>> 12);
				a += (b ^ c ^ d) + 0xFFFA3942 + schedule[ 5];  a = b + (a <<  4 | a >>> 28);
				d += (a ^ b ^ c) + 0x8771F681 + schedule[ 8];  d = a + (d << 11 | d >>> 21);
				c += (d ^ a ^ b) + 0x6D9D6122 + schedule[11];  c = d + (c << 16 | c >>> 16);
				b += (c ^ d ^ a) + 0xFDE5380C + schedule[14];  b = c + (b << 23 | b >>>  9);
				a += (b ^ c ^ d) + 0xA4BEEA44 + schedule[ 1];  a = b + (a <<  4 | a >>> 28);
				d += (a ^ b ^ c) + 0x4BDECFA9 + schedule[ 4];  d = a + (d << 11 | d >>> 21);
				c += (d ^ a ^ b) + 0xF6BB4B60 + schedule[ 7];  c = d + (c << 16 | c >>> 16);
				b += (c ^ d ^ a) + 0xBEBFBC70 + schedule[10];  b = c + (b << 23 | b >>>  9);
				a += (b ^ c ^ d) + 0x289B7EC6 + schedule[13];  a = b + (a <<  4 | a >>> 28);
				d += (a ^ b ^ c) + 0xEAA127FA + schedule[ 0];  d = a + (d << 11 | d >>> 21);
				c += (d ^ a ^ b) + 0xD4EF3085 + schedule[ 3];  c = d + (c << 16 | c >>> 16);
				b += (c ^ d ^ a) + 0x04881D05 + schedule[ 6];  b = c + (b << 23 | b >>>  9);
				a += (b ^ c ^ d) + 0xD9D4D039 + schedule[ 9];  a = b + (a <<  4 | a >>> 28);
				d += (a ^ b ^ c) + 0xE6DB99E5 + schedule[12];  d = a + (d << 11 | d >>> 21);
				c += (d ^ a ^ b) + 0x1FA27CF8 + schedule[15];  c = d + (c << 16 | c >>> 16);
				b += (c ^ d ^ a) + 0xC4AC5665 + schedule[ 2];  b = c + (b << 23 | b >>>  9);
				a += (c ^ (b | ~d)) + 0xF4292244 + schedule[ 0];  a = b + (a <<  6 | a >>> 26);
				d += (b ^ (a | ~c)) + 0x432AFF97 + schedule[ 7];  d = a + (d << 10 | d >>> 22);
				c += (a ^ (d | ~b)) + 0xAB9423A7 + schedule[14];  c = d + (c << 15 | c >>> 17);
				b += (d ^ (c | ~a)) + 0xFC93A039 + schedule[ 5];  b = c + (b << 21 | b >>> 11);
				a += (c ^ (b | ~d)) + 0x655B59C3 + schedule[12];  a = b + (a <<  6 | a >>> 26);
				d += (b ^ (a | ~c)) + 0x8F0CCC92 + schedule[ 3];  d = a + (d << 10 | d >>> 22);
				c += (a ^ (d | ~b)) + 0xFFEFF47D + schedule[10];  c = d + (c << 15 | c >>> 17);
				b += (d ^ (c | ~a)) + 0x85845DD1 + schedule[ 1];  b = c + (b << 21 | b >>> 11);
				a += (c ^ (b | ~d)) + 0x6FA87E4F + schedule[ 8];  a = b + (a <<  6 | a >>> 26);
				d += (b ^ (a | ~c)) + 0xFE2CE6E0 + schedule[15];  d = a + (d << 10 | d >>> 22);
				c += (a ^ (d | ~b)) + 0xA3014314 + schedule[ 6];  c = d + (c << 15 | c >>> 17);
				b += (d ^ (c | ~a)) + 0x4E0811A1 + schedule[13];  b = c + (b << 21 | b >>> 11);
				a += (c ^ (b | ~d)) + 0xF7537E82 + schedule[ 4];  a = b + (a <<  6 | a >>> 26);
				d += (b ^ (a | ~c)) + 0xBD3AF235 + schedule[11];  d = a + (d << 10 | d >>> 22);
				c += (a ^ (d | ~b)) + 0x2AD7D2BB + schedule[ 2];  c = d + (c << 15 | c >>> 17);
				b += (d ^ (c | ~a)) + 0xEB86D391 + schedule[ 9];  b = c + (b << 21 | b >>> 11);
			}
			
			a = state[0] += a;
			b = state[1] += b;
			c = state[2] += c;
			d = state[3] += d;
		}
	}
	
	
	@Override
	public HashValue getHashDestructively(byte[] block, int blockLength, BigInteger length) {
		block[blockLength] = (byte)0x80;
		blockLength++;
		for (int i = blockLength; i < block.length; i++)
			block[i] = 0x00;
		if (blockLength + 8 > block.length) {
			compress(block);
			for (int i = 0; i < block.length; i++)
				block[i] = 0x00;
		}
		length = length.shiftLeft(3);  // Length is now in bits
		for (int i = 0; i < 8; i++)
			block[block.length - 8 + i] = length.shiftRight(i * 8).byteValue();
		compress(block);
		return new HashValue(IntegerBitMath.toBytesLittleEndian(state));
	}
	
	
	@Override
	public FastMd45Core clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		FastMd45Core result = (FastMd45Core)super.clone();
		result.state = result.state.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		state = Zeroizer.clear(state);
	}
	
}
