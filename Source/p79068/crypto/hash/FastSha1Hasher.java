package p79068.crypto.hash;

import p79068.crypto.Zeroizer;
import p79068.math.IntegerBitMath;
import p79068.util.hash.HashValue;


final class FastSha1Hasher extends BlockHasher {
	
	private int[] state;
	
	
	
	FastSha1Hasher(Sha1 algor) {
		super(algor, 64);
		state = new int[]{0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0};
	}
	
	
	
	public FastSha1Hasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		FastSha1Hasher result = (FastSha1Hasher)super.clone();
		result.state = state.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		Zeroizer.clear(state);
		state = null;
		super.zeroize();
	}
	
	
	
	protected void compress(byte[] message, int off, int len) {
		int[] schedule = new int[80];
		int a = state[0];
		int b = state[1];
		int c = state[2];
		int d = state[3];
		int e = state[4];
		
		for (int end = off + len; off < end; off += 64) {
			schedule[ 0] = message[off +  0] << 24 | (message[off +  1] & 0xFF) << 16 | (message[off +  2] & 0xFF) << 8 | (message[off +  3] & 0xFF);
			schedule[ 1] = message[off +  4] << 24 | (message[off +  5] & 0xFF) << 16 | (message[off +  6] & 0xFF) << 8 | (message[off +  7] & 0xFF);
			schedule[ 2] = message[off +  8] << 24 | (message[off +  9] & 0xFF) << 16 | (message[off + 10] & 0xFF) << 8 | (message[off + 11] & 0xFF);
			schedule[ 3] = message[off + 12] << 24 | (message[off + 13] & 0xFF) << 16 | (message[off + 14] & 0xFF) << 8 | (message[off + 15] & 0xFF);
			schedule[ 4] = message[off + 16] << 24 | (message[off + 17] & 0xFF) << 16 | (message[off + 18] & 0xFF) << 8 | (message[off + 19] & 0xFF);
			schedule[ 5] = message[off + 20] << 24 | (message[off + 21] & 0xFF) << 16 | (message[off + 22] & 0xFF) << 8 | (message[off + 23] & 0xFF);
			schedule[ 6] = message[off + 24] << 24 | (message[off + 25] & 0xFF) << 16 | (message[off + 26] & 0xFF) << 8 | (message[off + 27] & 0xFF);
			schedule[ 7] = message[off + 28] << 24 | (message[off + 29] & 0xFF) << 16 | (message[off + 30] & 0xFF) << 8 | (message[off + 31] & 0xFF);
			schedule[ 8] = message[off + 32] << 24 | (message[off + 33] & 0xFF) << 16 | (message[off + 34] & 0xFF) << 8 | (message[off + 35] & 0xFF);
			schedule[ 9] = message[off + 36] << 24 | (message[off + 37] & 0xFF) << 16 | (message[off + 38] & 0xFF) << 8 | (message[off + 39] & 0xFF);
			schedule[10] = message[off + 40] << 24 | (message[off + 41] & 0xFF) << 16 | (message[off + 42] & 0xFF) << 8 | (message[off + 43] & 0xFF);
			schedule[11] = message[off + 44] << 24 | (message[off + 45] & 0xFF) << 16 | (message[off + 46] & 0xFF) << 8 | (message[off + 47] & 0xFF);
			schedule[12] = message[off + 48] << 24 | (message[off + 49] & 0xFF) << 16 | (message[off + 50] & 0xFF) << 8 | (message[off + 51] & 0xFF);
			schedule[13] = message[off + 52] << 24 | (message[off + 53] & 0xFF) << 16 | (message[off + 54] & 0xFF) << 8 | (message[off + 55] & 0xFF);
			schedule[14] = message[off + 56] << 24 | (message[off + 57] & 0xFF) << 16 | (message[off + 58] & 0xFF) << 8 | (message[off + 59] & 0xFF);
			schedule[15] = message[off + 60] << 24 | (message[off + 61] & 0xFF) << 16 | (message[off + 62] & 0xFF) << 8 | (message[off + 63] & 0xFF);
			
			int tp;
			tp = schedule[13] ^ schedule[ 8] ^ schedule[ 2] ^ schedule[ 0];  schedule[16] = tp << 1 | tp >>> 31;
			tp = schedule[14] ^ schedule[ 9] ^ schedule[ 3] ^ schedule[ 1];  schedule[17] = tp << 1 | tp >>> 31;
			tp = schedule[15] ^ schedule[10] ^ schedule[ 4] ^ schedule[ 2];  schedule[18] = tp << 1 | tp >>> 31;
			tp = schedule[16] ^ schedule[11] ^ schedule[ 5] ^ schedule[ 3];  schedule[19] = tp << 1 | tp >>> 31;
			tp = schedule[17] ^ schedule[12] ^ schedule[ 6] ^ schedule[ 4];  schedule[20] = tp << 1 | tp >>> 31;
			tp = schedule[18] ^ schedule[13] ^ schedule[ 7] ^ schedule[ 5];  schedule[21] = tp << 1 | tp >>> 31;
			tp = schedule[19] ^ schedule[14] ^ schedule[ 8] ^ schedule[ 6];  schedule[22] = tp << 1 | tp >>> 31;
			tp = schedule[20] ^ schedule[15] ^ schedule[ 9] ^ schedule[ 7];  schedule[23] = tp << 1 | tp >>> 31;
			tp = schedule[21] ^ schedule[16] ^ schedule[10] ^ schedule[ 8];  schedule[24] = tp << 1 | tp >>> 31;
			tp = schedule[22] ^ schedule[17] ^ schedule[11] ^ schedule[ 9];  schedule[25] = tp << 1 | tp >>> 31;
			tp = schedule[23] ^ schedule[18] ^ schedule[12] ^ schedule[10];  schedule[26] = tp << 1 | tp >>> 31;
			tp = schedule[24] ^ schedule[19] ^ schedule[13] ^ schedule[11];  schedule[27] = tp << 1 | tp >>> 31;
			tp = schedule[25] ^ schedule[20] ^ schedule[14] ^ schedule[12];  schedule[28] = tp << 1 | tp >>> 31;
			tp = schedule[26] ^ schedule[21] ^ schedule[15] ^ schedule[13];  schedule[29] = tp << 1 | tp >>> 31;
			tp = schedule[27] ^ schedule[22] ^ schedule[16] ^ schedule[14];  schedule[30] = tp << 1 | tp >>> 31;
			tp = schedule[28] ^ schedule[23] ^ schedule[17] ^ schedule[15];  schedule[31] = tp << 1 | tp >>> 31;
			tp = schedule[29] ^ schedule[24] ^ schedule[18] ^ schedule[16];  schedule[32] = tp << 1 | tp >>> 31;
			tp = schedule[30] ^ schedule[25] ^ schedule[19] ^ schedule[17];  schedule[33] = tp << 1 | tp >>> 31;
			tp = schedule[31] ^ schedule[26] ^ schedule[20] ^ schedule[18];  schedule[34] = tp << 1 | tp >>> 31;
			tp = schedule[32] ^ schedule[27] ^ schedule[21] ^ schedule[19];  schedule[35] = tp << 1 | tp >>> 31;
			tp = schedule[33] ^ schedule[28] ^ schedule[22] ^ schedule[20];  schedule[36] = tp << 1 | tp >>> 31;
			tp = schedule[34] ^ schedule[29] ^ schedule[23] ^ schedule[21];  schedule[37] = tp << 1 | tp >>> 31;
			tp = schedule[35] ^ schedule[30] ^ schedule[24] ^ schedule[22];  schedule[38] = tp << 1 | tp >>> 31;
			tp = schedule[36] ^ schedule[31] ^ schedule[25] ^ schedule[23];  schedule[39] = tp << 1 | tp >>> 31;
			tp = schedule[37] ^ schedule[32] ^ schedule[26] ^ schedule[24];  schedule[40] = tp << 1 | tp >>> 31;
			tp = schedule[38] ^ schedule[33] ^ schedule[27] ^ schedule[25];  schedule[41] = tp << 1 | tp >>> 31;
			tp = schedule[39] ^ schedule[34] ^ schedule[28] ^ schedule[26];  schedule[42] = tp << 1 | tp >>> 31;
			tp = schedule[40] ^ schedule[35] ^ schedule[29] ^ schedule[27];  schedule[43] = tp << 1 | tp >>> 31;
			tp = schedule[41] ^ schedule[36] ^ schedule[30] ^ schedule[28];  schedule[44] = tp << 1 | tp >>> 31;
			tp = schedule[42] ^ schedule[37] ^ schedule[31] ^ schedule[29];  schedule[45] = tp << 1 | tp >>> 31;
			tp = schedule[43] ^ schedule[38] ^ schedule[32] ^ schedule[30];  schedule[46] = tp << 1 | tp >>> 31;
			tp = schedule[44] ^ schedule[39] ^ schedule[33] ^ schedule[31];  schedule[47] = tp << 1 | tp >>> 31;
			tp = schedule[45] ^ schedule[40] ^ schedule[34] ^ schedule[32];  schedule[48] = tp << 1 | tp >>> 31;
			tp = schedule[46] ^ schedule[41] ^ schedule[35] ^ schedule[33];  schedule[49] = tp << 1 | tp >>> 31;
			tp = schedule[47] ^ schedule[42] ^ schedule[36] ^ schedule[34];  schedule[50] = tp << 1 | tp >>> 31;
			tp = schedule[48] ^ schedule[43] ^ schedule[37] ^ schedule[35];  schedule[51] = tp << 1 | tp >>> 31;
			tp = schedule[49] ^ schedule[44] ^ schedule[38] ^ schedule[36];  schedule[52] = tp << 1 | tp >>> 31;
			tp = schedule[50] ^ schedule[45] ^ schedule[39] ^ schedule[37];  schedule[53] = tp << 1 | tp >>> 31;
			tp = schedule[51] ^ schedule[46] ^ schedule[40] ^ schedule[38];  schedule[54] = tp << 1 | tp >>> 31;
			tp = schedule[52] ^ schedule[47] ^ schedule[41] ^ schedule[39];  schedule[55] = tp << 1 | tp >>> 31;
			tp = schedule[53] ^ schedule[48] ^ schedule[42] ^ schedule[40];  schedule[56] = tp << 1 | tp >>> 31;
			tp = schedule[54] ^ schedule[49] ^ schedule[43] ^ schedule[41];  schedule[57] = tp << 1 | tp >>> 31;
			tp = schedule[55] ^ schedule[50] ^ schedule[44] ^ schedule[42];  schedule[58] = tp << 1 | tp >>> 31;
			tp = schedule[56] ^ schedule[51] ^ schedule[45] ^ schedule[43];  schedule[59] = tp << 1 | tp >>> 31;
			tp = schedule[57] ^ schedule[52] ^ schedule[46] ^ schedule[44];  schedule[60] = tp << 1 | tp >>> 31;
			tp = schedule[58] ^ schedule[53] ^ schedule[47] ^ schedule[45];  schedule[61] = tp << 1 | tp >>> 31;
			tp = schedule[59] ^ schedule[54] ^ schedule[48] ^ schedule[46];  schedule[62] = tp << 1 | tp >>> 31;
			tp = schedule[60] ^ schedule[55] ^ schedule[49] ^ schedule[47];  schedule[63] = tp << 1 | tp >>> 31;
			tp = schedule[61] ^ schedule[56] ^ schedule[50] ^ schedule[48];  schedule[64] = tp << 1 | tp >>> 31;
			tp = schedule[62] ^ schedule[57] ^ schedule[51] ^ schedule[49];  schedule[65] = tp << 1 | tp >>> 31;
			tp = schedule[63] ^ schedule[58] ^ schedule[52] ^ schedule[50];  schedule[66] = tp << 1 | tp >>> 31;
			tp = schedule[64] ^ schedule[59] ^ schedule[53] ^ schedule[51];  schedule[67] = tp << 1 | tp >>> 31;
			tp = schedule[65] ^ schedule[60] ^ schedule[54] ^ schedule[52];  schedule[68] = tp << 1 | tp >>> 31;
			tp = schedule[66] ^ schedule[61] ^ schedule[55] ^ schedule[53];  schedule[69] = tp << 1 | tp >>> 31;
			tp = schedule[67] ^ schedule[62] ^ schedule[56] ^ schedule[54];  schedule[70] = tp << 1 | tp >>> 31;
			tp = schedule[68] ^ schedule[63] ^ schedule[57] ^ schedule[55];  schedule[71] = tp << 1 | tp >>> 31;
			tp = schedule[69] ^ schedule[64] ^ schedule[58] ^ schedule[56];  schedule[72] = tp << 1 | tp >>> 31;
			tp = schedule[70] ^ schedule[65] ^ schedule[59] ^ schedule[57];  schedule[73] = tp << 1 | tp >>> 31;
			tp = schedule[71] ^ schedule[66] ^ schedule[60] ^ schedule[58];  schedule[74] = tp << 1 | tp >>> 31;
			tp = schedule[72] ^ schedule[67] ^ schedule[61] ^ schedule[59];  schedule[75] = tp << 1 | tp >>> 31;
			tp = schedule[73] ^ schedule[68] ^ schedule[62] ^ schedule[60];  schedule[76] = tp << 1 | tp >>> 31;
			tp = schedule[74] ^ schedule[69] ^ schedule[63] ^ schedule[61];  schedule[77] = tp << 1 | tp >>> 31;
			tp = schedule[75] ^ schedule[70] ^ schedule[64] ^ schedule[62];  schedule[78] = tp << 1 | tp >>> 31;
			tp = schedule[76] ^ schedule[71] ^ schedule[65] ^ schedule[63];  schedule[79] = tp << 1 | tp >>> 31;
			
			e += (a << 5 | a >>> 27) + (d ^ (b & (c ^ d))) + schedule[ 0] + 0x5A827999;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (c ^ (a & (b ^ c))) + schedule[ 1] + 0x5A827999;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (b ^ (e & (a ^ b))) + schedule[ 2] + 0x5A827999;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (a ^ (d & (e ^ a))) + schedule[ 3] + 0x5A827999;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (e ^ (c & (d ^ e))) + schedule[ 4] + 0x5A827999;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (d ^ (b & (c ^ d))) + schedule[ 5] + 0x5A827999;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (c ^ (a & (b ^ c))) + schedule[ 6] + 0x5A827999;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (b ^ (e & (a ^ b))) + schedule[ 7] + 0x5A827999;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (a ^ (d & (e ^ a))) + schedule[ 8] + 0x5A827999;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (e ^ (c & (d ^ e))) + schedule[ 9] + 0x5A827999;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (d ^ (b & (c ^ d))) + schedule[10] + 0x5A827999;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (c ^ (a & (b ^ c))) + schedule[11] + 0x5A827999;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (b ^ (e & (a ^ b))) + schedule[12] + 0x5A827999;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (a ^ (d & (e ^ a))) + schedule[13] + 0x5A827999;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (e ^ (c & (d ^ e))) + schedule[14] + 0x5A827999;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (d ^ (b & (c ^ d))) + schedule[15] + 0x5A827999;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (c ^ (a & (b ^ c))) + schedule[16] + 0x5A827999;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (b ^ (e & (a ^ b))) + schedule[17] + 0x5A827999;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (a ^ (d & (e ^ a))) + schedule[18] + 0x5A827999;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (e ^ (c & (d ^ e))) + schedule[19] + 0x5A827999;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule[20] + 0x6ED9EBA1;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule[21] + 0x6ED9EBA1;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule[22] + 0x6ED9EBA1;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule[23] + 0x6ED9EBA1;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule[24] + 0x6ED9EBA1;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule[25] + 0x6ED9EBA1;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule[26] + 0x6ED9EBA1;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule[27] + 0x6ED9EBA1;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule[28] + 0x6ED9EBA1;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule[29] + 0x6ED9EBA1;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule[30] + 0x6ED9EBA1;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule[31] + 0x6ED9EBA1;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule[32] + 0x6ED9EBA1;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule[33] + 0x6ED9EBA1;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule[34] + 0x6ED9EBA1;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule[35] + 0x6ED9EBA1;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule[36] + 0x6ED9EBA1;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule[37] + 0x6ED9EBA1;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule[38] + 0x6ED9EBA1;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule[39] + 0x6ED9EBA1;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + ((b & (c | d)) | (c & d)) + schedule[40] + 0x8F1BBCDC;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + ((a & (b | c)) | (b & c)) + schedule[41] + 0x8F1BBCDC;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + ((e & (a | b)) | (a & b)) + schedule[42] + 0x8F1BBCDC;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + ((d & (e | a)) | (e & a)) + schedule[43] + 0x8F1BBCDC;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + ((c & (d | e)) | (d & e)) + schedule[44] + 0x8F1BBCDC;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + ((b & (c | d)) | (c & d)) + schedule[45] + 0x8F1BBCDC;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + ((a & (b | c)) | (b & c)) + schedule[46] + 0x8F1BBCDC;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + ((e & (a | b)) | (a & b)) + schedule[47] + 0x8F1BBCDC;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + ((d & (e | a)) | (e & a)) + schedule[48] + 0x8F1BBCDC;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + ((c & (d | e)) | (d & e)) + schedule[49] + 0x8F1BBCDC;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + ((b & (c | d)) | (c & d)) + schedule[50] + 0x8F1BBCDC;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + ((a & (b | c)) | (b & c)) + schedule[51] + 0x8F1BBCDC;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + ((e & (a | b)) | (a & b)) + schedule[52] + 0x8F1BBCDC;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + ((d & (e | a)) | (e & a)) + schedule[53] + 0x8F1BBCDC;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + ((c & (d | e)) | (d & e)) + schedule[54] + 0x8F1BBCDC;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + ((b & (c | d)) | (c & d)) + schedule[55] + 0x8F1BBCDC;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + ((a & (b | c)) | (b & c)) + schedule[56] + 0x8F1BBCDC;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + ((e & (a | b)) | (a & b)) + schedule[57] + 0x8F1BBCDC;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + ((d & (e | a)) | (e & a)) + schedule[58] + 0x8F1BBCDC;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + ((c & (d | e)) | (d & e)) + schedule[59] + 0x8F1BBCDC;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule[60] + 0xCA62C1D6;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule[61] + 0xCA62C1D6;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule[62] + 0xCA62C1D6;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule[63] + 0xCA62C1D6;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule[64] + 0xCA62C1D6;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule[65] + 0xCA62C1D6;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule[66] + 0xCA62C1D6;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule[67] + 0xCA62C1D6;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule[68] + 0xCA62C1D6;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule[69] + 0xCA62C1D6;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule[70] + 0xCA62C1D6;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule[71] + 0xCA62C1D6;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule[72] + 0xCA62C1D6;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule[73] + 0xCA62C1D6;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule[74] + 0xCA62C1D6;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule[75] + 0xCA62C1D6;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule[76] + 0xCA62C1D6;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule[77] + 0xCA62C1D6;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule[78] + 0xCA62C1D6;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule[79] + 0xCA62C1D6;  c = c << 30 | c >>> 2;
			
			a = state[0] += a;
			b = state[1] += b;
			c = state[2] += c;
			d = state[3] += d;
			e = state[4] += e;
		}
	}
	
	
	protected HashValue getHashDestructively() {
		block[blockLength] = (byte)0x80;
		for (int i = blockLength + 1; i < block.length; i++)
			block[i] = 0x00;
		if (blockLength + 1 > block.length - 8) {
			compress();
			for (int i = 0; i < block.length; i++)
				block[i] = 0x00;
		}
		for (int i = 0; i < 8; i++)
			block[block.length - 1 - i] = (byte)((length * 8) >>> (i * 8));
		compress();
		return createHash(IntegerBitMath.toBytesBigEndian(state));
	}
	
}