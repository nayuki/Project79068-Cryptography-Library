package p79068.crypto.hash;

import static java.lang.Integer.rotateRight;
import p79068.Assert;


final class FastSha256Core extends Sha256Core {
	
	public FastSha256Core(boolean sha256Mode) {
		super(sha256Mode);
	}
	
	
	
	@Override
	public void compress(byte[] msg, int off, int len) {
		Assert.assertRangeInBounds(msg.length, off, len);
		if (len % 64 != 0)
			throw new AssertionError();
		
		// For each block of 64 bytes
		for (int i = off, end = off + len; i < end; i += 64) {
			
			// Pack bytes into int32s in big endian
			int sch00 = msg[i +  0] << 24 | (msg[i +  1] & 0xFF) << 16 | (msg[i +  2] & 0xFF) << 8 | (msg[i +  3] & 0xFF);
			int sch01 = msg[i +  4] << 24 | (msg[i +  5] & 0xFF) << 16 | (msg[i +  6] & 0xFF) << 8 | (msg[i +  7] & 0xFF);
			int sch02 = msg[i +  8] << 24 | (msg[i +  9] & 0xFF) << 16 | (msg[i + 10] & 0xFF) << 8 | (msg[i + 11] & 0xFF);
			int sch03 = msg[i + 12] << 24 | (msg[i + 13] & 0xFF) << 16 | (msg[i + 14] & 0xFF) << 8 | (msg[i + 15] & 0xFF);
			int sch04 = msg[i + 16] << 24 | (msg[i + 17] & 0xFF) << 16 | (msg[i + 18] & 0xFF) << 8 | (msg[i + 19] & 0xFF);
			int sch05 = msg[i + 20] << 24 | (msg[i + 21] & 0xFF) << 16 | (msg[i + 22] & 0xFF) << 8 | (msg[i + 23] & 0xFF);
			int sch06 = msg[i + 24] << 24 | (msg[i + 25] & 0xFF) << 16 | (msg[i + 26] & 0xFF) << 8 | (msg[i + 27] & 0xFF);
			int sch07 = msg[i + 28] << 24 | (msg[i + 29] & 0xFF) << 16 | (msg[i + 30] & 0xFF) << 8 | (msg[i + 31] & 0xFF);
			int sch08 = msg[i + 32] << 24 | (msg[i + 33] & 0xFF) << 16 | (msg[i + 34] & 0xFF) << 8 | (msg[i + 35] & 0xFF);
			int sch09 = msg[i + 36] << 24 | (msg[i + 37] & 0xFF) << 16 | (msg[i + 38] & 0xFF) << 8 | (msg[i + 39] & 0xFF);
			int sch10 = msg[i + 40] << 24 | (msg[i + 41] & 0xFF) << 16 | (msg[i + 42] & 0xFF) << 8 | (msg[i + 43] & 0xFF);
			int sch11 = msg[i + 44] << 24 | (msg[i + 45] & 0xFF) << 16 | (msg[i + 46] & 0xFF) << 8 | (msg[i + 47] & 0xFF);
			int sch12 = msg[i + 48] << 24 | (msg[i + 49] & 0xFF) << 16 | (msg[i + 50] & 0xFF) << 8 | (msg[i + 51] & 0xFF);
			int sch13 = msg[i + 52] << 24 | (msg[i + 53] & 0xFF) << 16 | (msg[i + 54] & 0xFF) << 8 | (msg[i + 55] & 0xFF);
			int sch14 = msg[i + 56] << 24 | (msg[i + 57] & 0xFF) << 16 | (msg[i + 58] & 0xFF) << 8 | (msg[i + 59] & 0xFF);
			int sch15 = msg[i + 60] << 24 | (msg[i + 61] & 0xFF) << 16 | (msg[i + 62] & 0xFF) << 8 | (msg[i + 63] & 0xFF);
			
			int a = state[0];
			int b = state[1];
			int c = state[2];
			int d = state[3];
			int e = state[4];
			int f = state[5];
			int g = state[6];
			int h = state[7];
			
			// The first 48 rounds (the schedule is expanded on the fly)
			for (int j = 0; j < 48; j += 16) {
				h += (rotateRight(e, 6) ^ rotateRight(e, 11) ^ rotateRight(e, 25)) + (g ^ (e & (f ^ g))) + K[j +  0] + sch00;  d += h;  h += (rotateRight(a, 2) ^ rotateRight(a, 13) ^ rotateRight(a, 22)) + ((a & (b | c)) | (b & c));  sch00 += sch09 + (rotateRight(sch01, 7) ^ rotateRight(sch01, 18) ^ (sch01 >>> 3)) + (rotateRight(sch14, 17) ^ rotateRight(sch14, 19) ^ (sch14 >>> 10));
				g += (rotateRight(d, 6) ^ rotateRight(d, 11) ^ rotateRight(d, 25)) + (f ^ (d & (e ^ f))) + K[j +  1] + sch01;  c += g;  g += (rotateRight(h, 2) ^ rotateRight(h, 13) ^ rotateRight(h, 22)) + ((h & (a | b)) | (a & b));  sch01 += sch10 + (rotateRight(sch02, 7) ^ rotateRight(sch02, 18) ^ (sch02 >>> 3)) + (rotateRight(sch15, 17) ^ rotateRight(sch15, 19) ^ (sch15 >>> 10));
				f += (rotateRight(c, 6) ^ rotateRight(c, 11) ^ rotateRight(c, 25)) + (e ^ (c & (d ^ e))) + K[j +  2] + sch02;  b += f;  f += (rotateRight(g, 2) ^ rotateRight(g, 13) ^ rotateRight(g, 22)) + ((g & (h | a)) | (h & a));  sch02 += sch11 + (rotateRight(sch03, 7) ^ rotateRight(sch03, 18) ^ (sch03 >>> 3)) + (rotateRight(sch00, 17) ^ rotateRight(sch00, 19) ^ (sch00 >>> 10));
				e += (rotateRight(b, 6) ^ rotateRight(b, 11) ^ rotateRight(b, 25)) + (d ^ (b & (c ^ d))) + K[j +  3] + sch03;  a += e;  e += (rotateRight(f, 2) ^ rotateRight(f, 13) ^ rotateRight(f, 22)) + ((f & (g | h)) | (g & h));  sch03 += sch12 + (rotateRight(sch04, 7) ^ rotateRight(sch04, 18) ^ (sch04 >>> 3)) + (rotateRight(sch01, 17) ^ rotateRight(sch01, 19) ^ (sch01 >>> 10));
				d += (rotateRight(a, 6) ^ rotateRight(a, 11) ^ rotateRight(a, 25)) + (c ^ (a & (b ^ c))) + K[j +  4] + sch04;  h += d;  d += (rotateRight(e, 2) ^ rotateRight(e, 13) ^ rotateRight(e, 22)) + ((e & (f | g)) | (f & g));  sch04 += sch13 + (rotateRight(sch05, 7) ^ rotateRight(sch05, 18) ^ (sch05 >>> 3)) + (rotateRight(sch02, 17) ^ rotateRight(sch02, 19) ^ (sch02 >>> 10));
				c += (rotateRight(h, 6) ^ rotateRight(h, 11) ^ rotateRight(h, 25)) + (b ^ (h & (a ^ b))) + K[j +  5] + sch05;  g += c;  c += (rotateRight(d, 2) ^ rotateRight(d, 13) ^ rotateRight(d, 22)) + ((d & (e | f)) | (e & f));  sch05 += sch14 + (rotateRight(sch06, 7) ^ rotateRight(sch06, 18) ^ (sch06 >>> 3)) + (rotateRight(sch03, 17) ^ rotateRight(sch03, 19) ^ (sch03 >>> 10));
				b += (rotateRight(g, 6) ^ rotateRight(g, 11) ^ rotateRight(g, 25)) + (a ^ (g & (h ^ a))) + K[j +  6] + sch06;  f += b;  b += (rotateRight(c, 2) ^ rotateRight(c, 13) ^ rotateRight(c, 22)) + ((c & (d | e)) | (d & e));  sch06 += sch15 + (rotateRight(sch07, 7) ^ rotateRight(sch07, 18) ^ (sch07 >>> 3)) + (rotateRight(sch04, 17) ^ rotateRight(sch04, 19) ^ (sch04 >>> 10));
				a += (rotateRight(f, 6) ^ rotateRight(f, 11) ^ rotateRight(f, 25)) + (h ^ (f & (g ^ h))) + K[j +  7] + sch07;  e += a;  a += (rotateRight(b, 2) ^ rotateRight(b, 13) ^ rotateRight(b, 22)) + ((b & (c | d)) | (c & d));  sch07 += sch00 + (rotateRight(sch08, 7) ^ rotateRight(sch08, 18) ^ (sch08 >>> 3)) + (rotateRight(sch05, 17) ^ rotateRight(sch05, 19) ^ (sch05 >>> 10));
				h += (rotateRight(e, 6) ^ rotateRight(e, 11) ^ rotateRight(e, 25)) + (g ^ (e & (f ^ g))) + K[j +  8] + sch08;  d += h;  h += (rotateRight(a, 2) ^ rotateRight(a, 13) ^ rotateRight(a, 22)) + ((a & (b | c)) | (b & c));  sch08 += sch01 + (rotateRight(sch09, 7) ^ rotateRight(sch09, 18) ^ (sch09 >>> 3)) + (rotateRight(sch06, 17) ^ rotateRight(sch06, 19) ^ (sch06 >>> 10));
				g += (rotateRight(d, 6) ^ rotateRight(d, 11) ^ rotateRight(d, 25)) + (f ^ (d & (e ^ f))) + K[j +  9] + sch09;  c += g;  g += (rotateRight(h, 2) ^ rotateRight(h, 13) ^ rotateRight(h, 22)) + ((h & (a | b)) | (a & b));  sch09 += sch02 + (rotateRight(sch10, 7) ^ rotateRight(sch10, 18) ^ (sch10 >>> 3)) + (rotateRight(sch07, 17) ^ rotateRight(sch07, 19) ^ (sch07 >>> 10));
				f += (rotateRight(c, 6) ^ rotateRight(c, 11) ^ rotateRight(c, 25)) + (e ^ (c & (d ^ e))) + K[j + 10] + sch10;  b += f;  f += (rotateRight(g, 2) ^ rotateRight(g, 13) ^ rotateRight(g, 22)) + ((g & (h | a)) | (h & a));  sch10 += sch03 + (rotateRight(sch11, 7) ^ rotateRight(sch11, 18) ^ (sch11 >>> 3)) + (rotateRight(sch08, 17) ^ rotateRight(sch08, 19) ^ (sch08 >>> 10));
				e += (rotateRight(b, 6) ^ rotateRight(b, 11) ^ rotateRight(b, 25)) + (d ^ (b & (c ^ d))) + K[j + 11] + sch11;  a += e;  e += (rotateRight(f, 2) ^ rotateRight(f, 13) ^ rotateRight(f, 22)) + ((f & (g | h)) | (g & h));  sch11 += sch04 + (rotateRight(sch12, 7) ^ rotateRight(sch12, 18) ^ (sch12 >>> 3)) + (rotateRight(sch09, 17) ^ rotateRight(sch09, 19) ^ (sch09 >>> 10));
				d += (rotateRight(a, 6) ^ rotateRight(a, 11) ^ rotateRight(a, 25)) + (c ^ (a & (b ^ c))) + K[j + 12] + sch12;  h += d;  d += (rotateRight(e, 2) ^ rotateRight(e, 13) ^ rotateRight(e, 22)) + ((e & (f | g)) | (f & g));  sch12 += sch05 + (rotateRight(sch13, 7) ^ rotateRight(sch13, 18) ^ (sch13 >>> 3)) + (rotateRight(sch10, 17) ^ rotateRight(sch10, 19) ^ (sch10 >>> 10));
				c += (rotateRight(h, 6) ^ rotateRight(h, 11) ^ rotateRight(h, 25)) + (b ^ (h & (a ^ b))) + K[j + 13] + sch13;  g += c;  c += (rotateRight(d, 2) ^ rotateRight(d, 13) ^ rotateRight(d, 22)) + ((d & (e | f)) | (e & f));  sch13 += sch06 + (rotateRight(sch14, 7) ^ rotateRight(sch14, 18) ^ (sch14 >>> 3)) + (rotateRight(sch11, 17) ^ rotateRight(sch11, 19) ^ (sch11 >>> 10));
				b += (rotateRight(g, 6) ^ rotateRight(g, 11) ^ rotateRight(g, 25)) + (a ^ (g & (h ^ a))) + K[j + 14] + sch14;  f += b;  b += (rotateRight(c, 2) ^ rotateRight(c, 13) ^ rotateRight(c, 22)) + ((c & (d | e)) | (d & e));  sch14 += sch07 + (rotateRight(sch15, 7) ^ rotateRight(sch15, 18) ^ (sch15 >>> 3)) + (rotateRight(sch12, 17) ^ rotateRight(sch12, 19) ^ (sch12 >>> 10));
				a += (rotateRight(f, 6) ^ rotateRight(f, 11) ^ rotateRight(f, 25)) + (h ^ (f & (g ^ h))) + K[j + 15] + sch15;  e += a;  a += (rotateRight(b, 2) ^ rotateRight(b, 13) ^ rotateRight(b, 22)) + ((b & (c | d)) | (c & d));  sch15 += sch08 + (rotateRight(sch00, 7) ^ rotateRight(sch00, 18) ^ (sch00 >>> 3)) + (rotateRight(sch13, 17) ^ rotateRight(sch13, 19) ^ (sch13 >>> 10));
			}
			
			// The last 16 rounds (schedule expansion is complete)
			h += (rotateRight(e, 6) ^ rotateRight(e, 11) ^ rotateRight(e, 25)) + (g ^ (e & (f ^ g))) + 0x19A4C116 + sch00;  d += h;  h += (rotateRight(a, 2) ^ rotateRight(a, 13) ^ rotateRight(a, 22)) + ((a & (b | c)) | (b & c));
			g += (rotateRight(d, 6) ^ rotateRight(d, 11) ^ rotateRight(d, 25)) + (f ^ (d & (e ^ f))) + 0x1E376C08 + sch01;  c += g;  g += (rotateRight(h, 2) ^ rotateRight(h, 13) ^ rotateRight(h, 22)) + ((h & (a | b)) | (a & b));
			f += (rotateRight(c, 6) ^ rotateRight(c, 11) ^ rotateRight(c, 25)) + (e ^ (c & (d ^ e))) + 0x2748774C + sch02;  b += f;  f += (rotateRight(g, 2) ^ rotateRight(g, 13) ^ rotateRight(g, 22)) + ((g & (h | a)) | (h & a));
			e += (rotateRight(b, 6) ^ rotateRight(b, 11) ^ rotateRight(b, 25)) + (d ^ (b & (c ^ d))) + 0x34B0BCB5 + sch03;  a += e;  e += (rotateRight(f, 2) ^ rotateRight(f, 13) ^ rotateRight(f, 22)) + ((f & (g | h)) | (g & h));
			d += (rotateRight(a, 6) ^ rotateRight(a, 11) ^ rotateRight(a, 25)) + (c ^ (a & (b ^ c))) + 0x391C0CB3 + sch04;  h += d;  d += (rotateRight(e, 2) ^ rotateRight(e, 13) ^ rotateRight(e, 22)) + ((e & (f | g)) | (f & g));
			c += (rotateRight(h, 6) ^ rotateRight(h, 11) ^ rotateRight(h, 25)) + (b ^ (h & (a ^ b))) + 0x4ED8AA4A + sch05;  g += c;  c += (rotateRight(d, 2) ^ rotateRight(d, 13) ^ rotateRight(d, 22)) + ((d & (e | f)) | (e & f));
			b += (rotateRight(g, 6) ^ rotateRight(g, 11) ^ rotateRight(g, 25)) + (a ^ (g & (h ^ a))) + 0x5B9CCA4F + sch06;  f += b;  b += (rotateRight(c, 2) ^ rotateRight(c, 13) ^ rotateRight(c, 22)) + ((c & (d | e)) | (d & e));
			a += (rotateRight(f, 6) ^ rotateRight(f, 11) ^ rotateRight(f, 25)) + (h ^ (f & (g ^ h))) + 0x682E6FF3 + sch07;  e += a;  a += (rotateRight(b, 2) ^ rotateRight(b, 13) ^ rotateRight(b, 22)) + ((b & (c | d)) | (c & d));
			h += (rotateRight(e, 6) ^ rotateRight(e, 11) ^ rotateRight(e, 25)) + (g ^ (e & (f ^ g))) + 0x748F82EE + sch08;  d += h;  h += (rotateRight(a, 2) ^ rotateRight(a, 13) ^ rotateRight(a, 22)) + ((a & (b | c)) | (b & c));
			g += (rotateRight(d, 6) ^ rotateRight(d, 11) ^ rotateRight(d, 25)) + (f ^ (d & (e ^ f))) + 0x78A5636F + sch09;  c += g;  g += (rotateRight(h, 2) ^ rotateRight(h, 13) ^ rotateRight(h, 22)) + ((h & (a | b)) | (a & b));
			f += (rotateRight(c, 6) ^ rotateRight(c, 11) ^ rotateRight(c, 25)) + (e ^ (c & (d ^ e))) + 0x84C87814 + sch10;  b += f;  f += (rotateRight(g, 2) ^ rotateRight(g, 13) ^ rotateRight(g, 22)) + ((g & (h | a)) | (h & a));
			e += (rotateRight(b, 6) ^ rotateRight(b, 11) ^ rotateRight(b, 25)) + (d ^ (b & (c ^ d))) + 0x8CC70208 + sch11;  a += e;  e += (rotateRight(f, 2) ^ rotateRight(f, 13) ^ rotateRight(f, 22)) + ((f & (g | h)) | (g & h));
			d += (rotateRight(a, 6) ^ rotateRight(a, 11) ^ rotateRight(a, 25)) + (c ^ (a & (b ^ c))) + 0x90BEFFFA + sch12;  h += d;  d += (rotateRight(e, 2) ^ rotateRight(e, 13) ^ rotateRight(e, 22)) + ((e & (f | g)) | (f & g));
			c += (rotateRight(h, 6) ^ rotateRight(h, 11) ^ rotateRight(h, 25)) + (b ^ (h & (a ^ b))) + 0xA4506CEB + sch13;  g += c;  c += (rotateRight(d, 2) ^ rotateRight(d, 13) ^ rotateRight(d, 22)) + ((d & (e | f)) | (e & f));
			b += (rotateRight(g, 6) ^ rotateRight(g, 11) ^ rotateRight(g, 25)) + (a ^ (g & (h ^ a))) + 0xBEF9A3F7 + sch14;  f += b;  b += (rotateRight(c, 2) ^ rotateRight(c, 13) ^ rotateRight(c, 22)) + ((c & (d | e)) | (d & e));
			a += (rotateRight(f, 6) ^ rotateRight(f, 11) ^ rotateRight(f, 25)) + (h ^ (f & (g ^ h))) + 0xC67178F2 + sch15;  e += a;  a += (rotateRight(b, 2) ^ rotateRight(b, 13) ^ rotateRight(b, 22)) + ((b & (c | d)) | (c & d));
			
			state[0] += a;
			state[1] += b;
			state[2] += c;
			state[3] += d;
			state[4] += e;
			state[5] += f;
			state[6] += g;
			state[7] += h;
		}
	}
	
}
