package p79068.crypto.hash;

import p79068.Assert;


final class FastMd45Core extends Md4Core {
	
	private final boolean md5Mode;
	
	
	
	public FastMd45Core(boolean md5Mode) {
		this.md5Mode = md5Mode;
	}
	
	
	
	@Override
	public void compress(byte[] msg, int off, int len) {
		Assert.assertRangeInBounds(msg.length, off, len);
		if (len % 64 != 0)
			throw new AssertionError();
		
		int a = state[0];
		int b = state[1];
		int c = state[2];
		int d = state[3];
		
		// For each block of 64 bytes
		for (int i = off, end = off + len; i < end; i += 64) {
			
			// Pack bytes into int32s in little endian
			int schedule00 = (msg[i +  0] & 0xFF) | (msg[i +  1] & 0xFF) << 8 | (msg[i +  2] & 0xFF) << 16 | msg[i +  3] << 24;
			int schedule01 = (msg[i +  4] & 0xFF) | (msg[i +  5] & 0xFF) << 8 | (msg[i +  6] & 0xFF) << 16 | msg[i +  7] << 24;
			int schedule02 = (msg[i +  8] & 0xFF) | (msg[i +  9] & 0xFF) << 8 | (msg[i + 10] & 0xFF) << 16 | msg[i + 11] << 24;
			int schedule03 = (msg[i + 12] & 0xFF) | (msg[i + 13] & 0xFF) << 8 | (msg[i + 14] & 0xFF) << 16 | msg[i + 15] << 24;
			int schedule04 = (msg[i + 16] & 0xFF) | (msg[i + 17] & 0xFF) << 8 | (msg[i + 18] & 0xFF) << 16 | msg[i + 19] << 24;
			int schedule05 = (msg[i + 20] & 0xFF) | (msg[i + 21] & 0xFF) << 8 | (msg[i + 22] & 0xFF) << 16 | msg[i + 23] << 24;
			int schedule06 = (msg[i + 24] & 0xFF) | (msg[i + 25] & 0xFF) << 8 | (msg[i + 26] & 0xFF) << 16 | msg[i + 27] << 24;
			int schedule07 = (msg[i + 28] & 0xFF) | (msg[i + 29] & 0xFF) << 8 | (msg[i + 30] & 0xFF) << 16 | msg[i + 31] << 24;
			int schedule08 = (msg[i + 32] & 0xFF) | (msg[i + 33] & 0xFF) << 8 | (msg[i + 34] & 0xFF) << 16 | msg[i + 35] << 24;
			int schedule09 = (msg[i + 36] & 0xFF) | (msg[i + 37] & 0xFF) << 8 | (msg[i + 38] & 0xFF) << 16 | msg[i + 39] << 24;
			int schedule10 = (msg[i + 40] & 0xFF) | (msg[i + 41] & 0xFF) << 8 | (msg[i + 42] & 0xFF) << 16 | msg[i + 43] << 24;
			int schedule11 = (msg[i + 44] & 0xFF) | (msg[i + 45] & 0xFF) << 8 | (msg[i + 46] & 0xFF) << 16 | msg[i + 47] << 24;
			int schedule12 = (msg[i + 48] & 0xFF) | (msg[i + 49] & 0xFF) << 8 | (msg[i + 50] & 0xFF) << 16 | msg[i + 51] << 24;
			int schedule13 = (msg[i + 52] & 0xFF) | (msg[i + 53] & 0xFF) << 8 | (msg[i + 54] & 0xFF) << 16 | msg[i + 55] << 24;
			int schedule14 = (msg[i + 56] & 0xFF) | (msg[i + 57] & 0xFF) << 8 | (msg[i + 58] & 0xFF) << 16 | msg[i + 59] << 24;
			int schedule15 = (msg[i + 60] & 0xFF) | (msg[i + 61] & 0xFF) << 8 | (msg[i + 62] & 0xFF) << 16 | msg[i + 63] << 24;
			
			if (!md5Mode) {
				// The 48 rounds (MD4)
				a += (d ^ (b & (c ^ d))) + schedule00;  a = a <<  3 | a >>> 29;
				d += (c ^ (a & (b ^ c))) + schedule01;  d = d <<  7 | d >>> 25;
				c += (b ^ (d & (a ^ b))) + schedule02;  c = c << 11 | c >>> 21;
				b += (a ^ (c & (d ^ a))) + schedule03;  b = b << 19 | b >>> 13;
				a += (d ^ (b & (c ^ d))) + schedule04;  a = a <<  3 | a >>> 29;
				d += (c ^ (a & (b ^ c))) + schedule05;  d = d <<  7 | d >>> 25;
				c += (b ^ (d & (a ^ b))) + schedule06;  c = c << 11 | c >>> 21;
				b += (a ^ (c & (d ^ a))) + schedule07;  b = b << 19 | b >>> 13;
				a += (d ^ (b & (c ^ d))) + schedule08;  a = a <<  3 | a >>> 29;
				d += (c ^ (a & (b ^ c))) + schedule09;  d = d <<  7 | d >>> 25;
				c += (b ^ (d & (a ^ b))) + schedule10;  c = c << 11 | c >>> 21;
				b += (a ^ (c & (d ^ a))) + schedule11;  b = b << 19 | b >>> 13;
				a += (d ^ (b & (c ^ d))) + schedule12;  a = a <<  3 | a >>> 29;
				d += (c ^ (a & (b ^ c))) + schedule13;  d = d <<  7 | d >>> 25;
				c += (b ^ (d & (a ^ b))) + schedule14;  c = c << 11 | c >>> 21;
				b += (a ^ (c & (d ^ a))) + schedule15;  b = b << 19 | b >>> 13;
				a += ((b & c) | (d & (b | c))) + schedule00 + 0x5A827999;  a = a <<  3 | a >>> 29;
				d += ((a & b) | (c & (a | b))) + schedule04 + 0x5A827999;  d = d <<  5 | d >>> 27;
				c += ((d & a) | (b & (d | a))) + schedule08 + 0x5A827999;  c = c <<  9 | c >>> 23;
				b += ((c & d) | (a & (c | d))) + schedule12 + 0x5A827999;  b = b << 13 | b >>> 19;
				a += ((b & c) | (d & (b | c))) + schedule01 + 0x5A827999;  a = a <<  3 | a >>> 29;
				d += ((a & b) | (c & (a | b))) + schedule05 + 0x5A827999;  d = d <<  5 | d >>> 27;
				c += ((d & a) | (b & (d | a))) + schedule09 + 0x5A827999;  c = c <<  9 | c >>> 23;
				b += ((c & d) | (a & (c | d))) + schedule13 + 0x5A827999;  b = b << 13 | b >>> 19;
				a += ((b & c) | (d & (b | c))) + schedule02 + 0x5A827999;  a = a <<  3 | a >>> 29;
				d += ((a & b) | (c & (a | b))) + schedule06 + 0x5A827999;  d = d <<  5 | d >>> 27;
				c += ((d & a) | (b & (d | a))) + schedule10 + 0x5A827999;  c = c <<  9 | c >>> 23;
				b += ((c & d) | (a & (c | d))) + schedule14 + 0x5A827999;  b = b << 13 | b >>> 19;
				a += ((b & c) | (d & (b | c))) + schedule03 + 0x5A827999;  a = a <<  3 | a >>> 29;
				d += ((a & b) | (c & (a | b))) + schedule07 + 0x5A827999;  d = d <<  5 | d >>> 27;
				c += ((d & a) | (b & (d | a))) + schedule11 + 0x5A827999;  c = c <<  9 | c >>> 23;
				b += ((c & d) | (a & (c | d))) + schedule15 + 0x5A827999;  b = b << 13 | b >>> 19;
				a += (b ^ c ^ d) + schedule00 + 0x6ED9EBA1;  a = a <<  3 | a >>> 29;
				d += (a ^ b ^ c) + schedule08 + 0x6ED9EBA1;  d = d <<  9 | d >>> 23;
				c += (d ^ a ^ b) + schedule04 + 0x6ED9EBA1;  c = c << 11 | c >>> 21;
				b += (c ^ d ^ a) + schedule12 + 0x6ED9EBA1;  b = b << 15 | b >>> 17;
				a += (b ^ c ^ d) + schedule02 + 0x6ED9EBA1;  a = a <<  3 | a >>> 29;
				d += (a ^ b ^ c) + schedule10 + 0x6ED9EBA1;  d = d <<  9 | d >>> 23;
				c += (d ^ a ^ b) + schedule06 + 0x6ED9EBA1;  c = c << 11 | c >>> 21;
				b += (c ^ d ^ a) + schedule14 + 0x6ED9EBA1;  b = b << 15 | b >>> 17;
				a += (b ^ c ^ d) + schedule01 + 0x6ED9EBA1;  a = a <<  3 | a >>> 29;
				d += (a ^ b ^ c) + schedule09 + 0x6ED9EBA1;  d = d <<  9 | d >>> 23;
				c += (d ^ a ^ b) + schedule05 + 0x6ED9EBA1;  c = c << 11 | c >>> 21;
				b += (c ^ d ^ a) + schedule13 + 0x6ED9EBA1;  b = b << 15 | b >>> 17;
				a += (b ^ c ^ d) + schedule03 + 0x6ED9EBA1;  a = a <<  3 | a >>> 29;
				d += (a ^ b ^ c) + schedule11 + 0x6ED9EBA1;  d = d <<  9 | d >>> 23;
				c += (d ^ a ^ b) + schedule07 + 0x6ED9EBA1;  c = c << 11 | c >>> 21;
				b += (c ^ d ^ a) + schedule15 + 0x6ED9EBA1;  b = b << 15 | b >>> 17;
			}
			else {
				// The 64 rounds (MD5)
				a += (d ^ (b & (c ^ d))) + 0xD76AA478 + schedule00;  a = b + (a <<  7 | a >>> 25);
				d += (c ^ (a & (b ^ c))) + 0xE8C7B756 + schedule01;  d = a + (d << 12 | d >>> 20);
				c += (b ^ (d & (a ^ b))) + 0x242070DB + schedule02;  c = d + (c << 17 | c >>> 15);
				b += (a ^ (c & (d ^ a))) + 0xC1BDCEEE + schedule03;  b = c + (b << 22 | b >>> 10);
				a += (d ^ (b & (c ^ d))) + 0xF57C0FAF + schedule04;  a = b + (a <<  7 | a >>> 25);
				d += (c ^ (a & (b ^ c))) + 0x4787C62A + schedule05;  d = a + (d << 12 | d >>> 20);
				c += (b ^ (d & (a ^ b))) + 0xA8304613 + schedule06;  c = d + (c << 17 | c >>> 15);
				b += (a ^ (c & (d ^ a))) + 0xFD469501 + schedule07;  b = c + (b << 22 | b >>> 10);
				a += (d ^ (b & (c ^ d))) + 0x698098D8 + schedule08;  a = b + (a <<  7 | a >>> 25);
				d += (c ^ (a & (b ^ c))) + 0x8B44F7AF + schedule09;  d = a + (d << 12 | d >>> 20);
				c += (b ^ (d & (a ^ b))) + 0xFFFF5BB1 + schedule10;  c = d + (c << 17 | c >>> 15);
				b += (a ^ (c & (d ^ a))) + 0x895CD7BE + schedule11;  b = c + (b << 22 | b >>> 10);
				a += (d ^ (b & (c ^ d))) + 0x6B901122 + schedule12;  a = b + (a <<  7 | a >>> 25);
				d += (c ^ (a & (b ^ c))) + 0xFD987193 + schedule13;  d = a + (d << 12 | d >>> 20);
				c += (b ^ (d & (a ^ b))) + 0xA679438E + schedule14;  c = d + (c << 17 | c >>> 15);
				b += (a ^ (c & (d ^ a))) + 0x49B40821 + schedule15;  b = c + (b << 22 | b >>> 10);
				a += (c ^ (d & (b ^ c))) + 0xF61E2562 + schedule01;  a = b + (a <<  5 | a >>> 27);
				d += (b ^ (c & (a ^ b))) + 0xC040B340 + schedule06;  d = a + (d <<  9 | d >>> 23);
				c += (a ^ (b & (d ^ a))) + 0x265E5A51 + schedule11;  c = d + (c << 14 | c >>> 18);
				b += (d ^ (a & (c ^ d))) + 0xE9B6C7AA + schedule00;  b = c + (b << 20 | b >>> 12);
				a += (c ^ (d & (b ^ c))) + 0xD62F105D + schedule05;  a = b + (a <<  5 | a >>> 27);
				d += (b ^ (c & (a ^ b))) + 0x02441453 + schedule10;  d = a + (d <<  9 | d >>> 23);
				c += (a ^ (b & (d ^ a))) + 0xD8A1E681 + schedule15;  c = d + (c << 14 | c >>> 18);
				b += (d ^ (a & (c ^ d))) + 0xE7D3FBC8 + schedule04;  b = c + (b << 20 | b >>> 12);
				a += (c ^ (d & (b ^ c))) + 0x21E1CDE6 + schedule09;  a = b + (a <<  5 | a >>> 27);
				d += (b ^ (c & (a ^ b))) + 0xC33707D6 + schedule14;  d = a + (d <<  9 | d >>> 23);
				c += (a ^ (b & (d ^ a))) + 0xF4D50D87 + schedule03;  c = d + (c << 14 | c >>> 18);
				b += (d ^ (a & (c ^ d))) + 0x455A14ED + schedule08;  b = c + (b << 20 | b >>> 12);
				a += (c ^ (d & (b ^ c))) + 0xA9E3E905 + schedule13;  a = b + (a <<  5 | a >>> 27);
				d += (b ^ (c & (a ^ b))) + 0xFCEFA3F8 + schedule02;  d = a + (d <<  9 | d >>> 23);
				c += (a ^ (b & (d ^ a))) + 0x676F02D9 + schedule07;  c = d + (c << 14 | c >>> 18);
				b += (d ^ (a & (c ^ d))) + 0x8D2A4C8A + schedule12;  b = c + (b << 20 | b >>> 12);
				a += (b ^ c ^ d) + 0xFFFA3942 + schedule05;  a = b + (a <<  4 | a >>> 28);
				d += (a ^ b ^ c) + 0x8771F681 + schedule08;  d = a + (d << 11 | d >>> 21);
				c += (d ^ a ^ b) + 0x6D9D6122 + schedule11;  c = d + (c << 16 | c >>> 16);
				b += (c ^ d ^ a) + 0xFDE5380C + schedule14;  b = c + (b << 23 | b >>>  9);
				a += (b ^ c ^ d) + 0xA4BEEA44 + schedule01;  a = b + (a <<  4 | a >>> 28);
				d += (a ^ b ^ c) + 0x4BDECFA9 + schedule04;  d = a + (d << 11 | d >>> 21);
				c += (d ^ a ^ b) + 0xF6BB4B60 + schedule07;  c = d + (c << 16 | c >>> 16);
				b += (c ^ d ^ a) + 0xBEBFBC70 + schedule10;  b = c + (b << 23 | b >>>  9);
				a += (b ^ c ^ d) + 0x289B7EC6 + schedule13;  a = b + (a <<  4 | a >>> 28);
				d += (a ^ b ^ c) + 0xEAA127FA + schedule00;  d = a + (d << 11 | d >>> 21);
				c += (d ^ a ^ b) + 0xD4EF3085 + schedule03;  c = d + (c << 16 | c >>> 16);
				b += (c ^ d ^ a) + 0x04881D05 + schedule06;  b = c + (b << 23 | b >>>  9);
				a += (b ^ c ^ d) + 0xD9D4D039 + schedule09;  a = b + (a <<  4 | a >>> 28);
				d += (a ^ b ^ c) + 0xE6DB99E5 + schedule12;  d = a + (d << 11 | d >>> 21);
				c += (d ^ a ^ b) + 0x1FA27CF8 + schedule15;  c = d + (c << 16 | c >>> 16);
				b += (c ^ d ^ a) + 0xC4AC5665 + schedule02;  b = c + (b << 23 | b >>>  9);
				a += (c ^ (b | ~d)) + 0xF4292244 + schedule00;  a = b + (a <<  6 | a >>> 26);
				d += (b ^ (a | ~c)) + 0x432AFF97 + schedule07;  d = a + (d << 10 | d >>> 22);
				c += (a ^ (d | ~b)) + 0xAB9423A7 + schedule14;  c = d + (c << 15 | c >>> 17);
				b += (d ^ (c | ~a)) + 0xFC93A039 + schedule05;  b = c + (b << 21 | b >>> 11);
				a += (c ^ (b | ~d)) + 0x655B59C3 + schedule12;  a = b + (a <<  6 | a >>> 26);
				d += (b ^ (a | ~c)) + 0x8F0CCC92 + schedule03;  d = a + (d << 10 | d >>> 22);
				c += (a ^ (d | ~b)) + 0xFFEFF47D + schedule10;  c = d + (c << 15 | c >>> 17);
				b += (d ^ (c | ~a)) + 0x85845DD1 + schedule01;  b = c + (b << 21 | b >>> 11);
				a += (c ^ (b | ~d)) + 0x6FA87E4F + schedule08;  a = b + (a <<  6 | a >>> 26);
				d += (b ^ (a | ~c)) + 0xFE2CE6E0 + schedule15;  d = a + (d << 10 | d >>> 22);
				c += (a ^ (d | ~b)) + 0xA3014314 + schedule06;  c = d + (c << 15 | c >>> 17);
				b += (d ^ (c | ~a)) + 0x4E0811A1 + schedule13;  b = c + (b << 21 | b >>> 11);
				a += (c ^ (b | ~d)) + 0xF7537E82 + schedule04;  a = b + (a <<  6 | a >>> 26);
				d += (b ^ (a | ~c)) + 0xBD3AF235 + schedule11;  d = a + (d << 10 | d >>> 22);
				c += (a ^ (d | ~b)) + 0x2AD7D2BB + schedule02;  c = d + (c << 15 | c >>> 17);
				b += (d ^ (c | ~a)) + 0xEB86D391 + schedule09;  b = c + (b << 21 | b >>> 11);
			}
			
			a = state[0] += a;
			b = state[1] += b;
			c = state[2] += c;
			d = state[3] += d;
		}
	}
	
}
