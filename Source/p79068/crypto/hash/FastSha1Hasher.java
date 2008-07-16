package p79068.crypto.hash;

import p79068.crypto.Zeroizer;
import p79068.lang.BoundsChecker;
import p79068.math.IntegerBitMath;
import p79068.util.hash.HashValue;


final class FastSha1Hasher extends BlockHasher {
	
	private int[] state;
	
	
	
	FastSha1Hasher(Sha1 hashFunc) {
		super(hashFunc);
		state = new int[] { 0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0 };
	}
	
	
	
	public FastSha1Hasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		FastSha1Hasher result = (FastSha1Hasher)super.clone();
		result.state = result.state.clone();
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
		BoundsChecker.check(message.length, off, len);
		if (len % 64 != 0)
			throw new AssertionError();
		
		int a = state[0];
		int b = state[1];
		int c = state[2];
		int d = state[3];
		int e = state[4];
		
		// For each block of 64 bytes
		for (int end = off + len; off < end; off += 64) {
			
			// Pack bytes into int32s in big endian
			int schedule00 = message[off +  0] << 24 | (message[off +  1] & 0xFF) << 16 | (message[off +  2] & 0xFF) << 8 | (message[off +  3] & 0xFF);
			int schedule01 = message[off +  4] << 24 | (message[off +  5] & 0xFF) << 16 | (message[off +  6] & 0xFF) << 8 | (message[off +  7] & 0xFF);
			int schedule02 = message[off +  8] << 24 | (message[off +  9] & 0xFF) << 16 | (message[off + 10] & 0xFF) << 8 | (message[off + 11] & 0xFF);
			int schedule03 = message[off + 12] << 24 | (message[off + 13] & 0xFF) << 16 | (message[off + 14] & 0xFF) << 8 | (message[off + 15] & 0xFF);
			int schedule04 = message[off + 16] << 24 | (message[off + 17] & 0xFF) << 16 | (message[off + 18] & 0xFF) << 8 | (message[off + 19] & 0xFF);
			int schedule05 = message[off + 20] << 24 | (message[off + 21] & 0xFF) << 16 | (message[off + 22] & 0xFF) << 8 | (message[off + 23] & 0xFF);
			int schedule06 = message[off + 24] << 24 | (message[off + 25] & 0xFF) << 16 | (message[off + 26] & 0xFF) << 8 | (message[off + 27] & 0xFF);
			int schedule07 = message[off + 28] << 24 | (message[off + 29] & 0xFF) << 16 | (message[off + 30] & 0xFF) << 8 | (message[off + 31] & 0xFF);
			int schedule08 = message[off + 32] << 24 | (message[off + 33] & 0xFF) << 16 | (message[off + 34] & 0xFF) << 8 | (message[off + 35] & 0xFF);
			int schedule09 = message[off + 36] << 24 | (message[off + 37] & 0xFF) << 16 | (message[off + 38] & 0xFF) << 8 | (message[off + 39] & 0xFF);
			int schedule10 = message[off + 40] << 24 | (message[off + 41] & 0xFF) << 16 | (message[off + 42] & 0xFF) << 8 | (message[off + 43] & 0xFF);
			int schedule11 = message[off + 44] << 24 | (message[off + 45] & 0xFF) << 16 | (message[off + 46] & 0xFF) << 8 | (message[off + 47] & 0xFF);
			int schedule12 = message[off + 48] << 24 | (message[off + 49] & 0xFF) << 16 | (message[off + 50] & 0xFF) << 8 | (message[off + 51] & 0xFF);
			int schedule13 = message[off + 52] << 24 | (message[off + 53] & 0xFF) << 16 | (message[off + 54] & 0xFF) << 8 | (message[off + 55] & 0xFF);
			int schedule14 = message[off + 56] << 24 | (message[off + 57] & 0xFF) << 16 | (message[off + 58] & 0xFF) << 8 | (message[off + 59] & 0xFF);
			int schedule15 = message[off + 60] << 24 | (message[off + 61] & 0xFF) << 16 | (message[off + 62] & 0xFF) << 8 | (message[off + 63] & 0xFF);
			
			// Expand the schedule
			int schedule16 = schedule13 ^ schedule08 ^ schedule02 ^ schedule00;  schedule16 = schedule16 << 1 | schedule16 >>> 31;
			int schedule17 = schedule14 ^ schedule09 ^ schedule03 ^ schedule01;  schedule17 = schedule17 << 1 | schedule17 >>> 31;
			int schedule18 = schedule15 ^ schedule10 ^ schedule04 ^ schedule02;  schedule18 = schedule18 << 1 | schedule18 >>> 31;
			int schedule19 = schedule16 ^ schedule11 ^ schedule05 ^ schedule03;  schedule19 = schedule19 << 1 | schedule19 >>> 31;
			int schedule20 = schedule17 ^ schedule12 ^ schedule06 ^ schedule04;  schedule20 = schedule20 << 1 | schedule20 >>> 31;
			int schedule21 = schedule18 ^ schedule13 ^ schedule07 ^ schedule05;  schedule21 = schedule21 << 1 | schedule21 >>> 31;
			int schedule22 = schedule19 ^ schedule14 ^ schedule08 ^ schedule06;  schedule22 = schedule22 << 1 | schedule22 >>> 31;
			int schedule23 = schedule20 ^ schedule15 ^ schedule09 ^ schedule07;  schedule23 = schedule23 << 1 | schedule23 >>> 31;
			int schedule24 = schedule21 ^ schedule16 ^ schedule10 ^ schedule08;  schedule24 = schedule24 << 1 | schedule24 >>> 31;
			int schedule25 = schedule22 ^ schedule17 ^ schedule11 ^ schedule09;  schedule25 = schedule25 << 1 | schedule25 >>> 31;
			int schedule26 = schedule23 ^ schedule18 ^ schedule12 ^ schedule10;  schedule26 = schedule26 << 1 | schedule26 >>> 31;
			int schedule27 = schedule24 ^ schedule19 ^ schedule13 ^ schedule11;  schedule27 = schedule27 << 1 | schedule27 >>> 31;
			int schedule28 = schedule25 ^ schedule20 ^ schedule14 ^ schedule12;  schedule28 = schedule28 << 1 | schedule28 >>> 31;
			int schedule29 = schedule26 ^ schedule21 ^ schedule15 ^ schedule13;  schedule29 = schedule29 << 1 | schedule29 >>> 31;
			int schedule30 = schedule27 ^ schedule22 ^ schedule16 ^ schedule14;  schedule30 = schedule30 << 1 | schedule30 >>> 31;
			int schedule31 = schedule28 ^ schedule23 ^ schedule17 ^ schedule15;  schedule31 = schedule31 << 1 | schedule31 >>> 31;
			int schedule32 = schedule29 ^ schedule24 ^ schedule18 ^ schedule16;  schedule32 = schedule32 << 1 | schedule32 >>> 31;
			int schedule33 = schedule30 ^ schedule25 ^ schedule19 ^ schedule17;  schedule33 = schedule33 << 1 | schedule33 >>> 31;
			int schedule34 = schedule31 ^ schedule26 ^ schedule20 ^ schedule18;  schedule34 = schedule34 << 1 | schedule34 >>> 31;
			int schedule35 = schedule32 ^ schedule27 ^ schedule21 ^ schedule19;  schedule35 = schedule35 << 1 | schedule35 >>> 31;
			int schedule36 = schedule33 ^ schedule28 ^ schedule22 ^ schedule20;  schedule36 = schedule36 << 1 | schedule36 >>> 31;
			int schedule37 = schedule34 ^ schedule29 ^ schedule23 ^ schedule21;  schedule37 = schedule37 << 1 | schedule37 >>> 31;
			int schedule38 = schedule35 ^ schedule30 ^ schedule24 ^ schedule22;  schedule38 = schedule38 << 1 | schedule38 >>> 31;
			int schedule39 = schedule36 ^ schedule31 ^ schedule25 ^ schedule23;  schedule39 = schedule39 << 1 | schedule39 >>> 31;
			int schedule40 = schedule37 ^ schedule32 ^ schedule26 ^ schedule24;  schedule40 = schedule40 << 1 | schedule40 >>> 31;
			int schedule41 = schedule38 ^ schedule33 ^ schedule27 ^ schedule25;  schedule41 = schedule41 << 1 | schedule41 >>> 31;
			int schedule42 = schedule39 ^ schedule34 ^ schedule28 ^ schedule26;  schedule42 = schedule42 << 1 | schedule42 >>> 31;
			int schedule43 = schedule40 ^ schedule35 ^ schedule29 ^ schedule27;  schedule43 = schedule43 << 1 | schedule43 >>> 31;
			int schedule44 = schedule41 ^ schedule36 ^ schedule30 ^ schedule28;  schedule44 = schedule44 << 1 | schedule44 >>> 31;
			int schedule45 = schedule42 ^ schedule37 ^ schedule31 ^ schedule29;  schedule45 = schedule45 << 1 | schedule45 >>> 31;
			int schedule46 = schedule43 ^ schedule38 ^ schedule32 ^ schedule30;  schedule46 = schedule46 << 1 | schedule46 >>> 31;
			int schedule47 = schedule44 ^ schedule39 ^ schedule33 ^ schedule31;  schedule47 = schedule47 << 1 | schedule47 >>> 31;
			int schedule48 = schedule45 ^ schedule40 ^ schedule34 ^ schedule32;  schedule48 = schedule48 << 1 | schedule48 >>> 31;
			int schedule49 = schedule46 ^ schedule41 ^ schedule35 ^ schedule33;  schedule49 = schedule49 << 1 | schedule49 >>> 31;
			int schedule50 = schedule47 ^ schedule42 ^ schedule36 ^ schedule34;  schedule50 = schedule50 << 1 | schedule50 >>> 31;
			int schedule51 = schedule48 ^ schedule43 ^ schedule37 ^ schedule35;  schedule51 = schedule51 << 1 | schedule51 >>> 31;
			int schedule52 = schedule49 ^ schedule44 ^ schedule38 ^ schedule36;  schedule52 = schedule52 << 1 | schedule52 >>> 31;
			int schedule53 = schedule50 ^ schedule45 ^ schedule39 ^ schedule37;  schedule53 = schedule53 << 1 | schedule53 >>> 31;
			int schedule54 = schedule51 ^ schedule46 ^ schedule40 ^ schedule38;  schedule54 = schedule54 << 1 | schedule54 >>> 31;
			int schedule55 = schedule52 ^ schedule47 ^ schedule41 ^ schedule39;  schedule55 = schedule55 << 1 | schedule55 >>> 31;
			int schedule56 = schedule53 ^ schedule48 ^ schedule42 ^ schedule40;  schedule56 = schedule56 << 1 | schedule56 >>> 31;
			int schedule57 = schedule54 ^ schedule49 ^ schedule43 ^ schedule41;  schedule57 = schedule57 << 1 | schedule57 >>> 31;
			int schedule58 = schedule55 ^ schedule50 ^ schedule44 ^ schedule42;  schedule58 = schedule58 << 1 | schedule58 >>> 31;
			int schedule59 = schedule56 ^ schedule51 ^ schedule45 ^ schedule43;  schedule59 = schedule59 << 1 | schedule59 >>> 31;
			int schedule60 = schedule57 ^ schedule52 ^ schedule46 ^ schedule44;  schedule60 = schedule60 << 1 | schedule60 >>> 31;
			int schedule61 = schedule58 ^ schedule53 ^ schedule47 ^ schedule45;  schedule61 = schedule61 << 1 | schedule61 >>> 31;
			int schedule62 = schedule59 ^ schedule54 ^ schedule48 ^ schedule46;  schedule62 = schedule62 << 1 | schedule62 >>> 31;
			int schedule63 = schedule60 ^ schedule55 ^ schedule49 ^ schedule47;  schedule63 = schedule63 << 1 | schedule63 >>> 31;
			int schedule64 = schedule61 ^ schedule56 ^ schedule50 ^ schedule48;  schedule64 = schedule64 << 1 | schedule64 >>> 31;
			int schedule65 = schedule62 ^ schedule57 ^ schedule51 ^ schedule49;  schedule65 = schedule65 << 1 | schedule65 >>> 31;
			int schedule66 = schedule63 ^ schedule58 ^ schedule52 ^ schedule50;  schedule66 = schedule66 << 1 | schedule66 >>> 31;
			int schedule67 = schedule64 ^ schedule59 ^ schedule53 ^ schedule51;  schedule67 = schedule67 << 1 | schedule67 >>> 31;
			int schedule68 = schedule65 ^ schedule60 ^ schedule54 ^ schedule52;  schedule68 = schedule68 << 1 | schedule68 >>> 31;
			int schedule69 = schedule66 ^ schedule61 ^ schedule55 ^ schedule53;  schedule69 = schedule69 << 1 | schedule69 >>> 31;
			int schedule70 = schedule67 ^ schedule62 ^ schedule56 ^ schedule54;  schedule70 = schedule70 << 1 | schedule70 >>> 31;
			int schedule71 = schedule68 ^ schedule63 ^ schedule57 ^ schedule55;  schedule71 = schedule71 << 1 | schedule71 >>> 31;
			int schedule72 = schedule69 ^ schedule64 ^ schedule58 ^ schedule56;  schedule72 = schedule72 << 1 | schedule72 >>> 31;
			int schedule73 = schedule70 ^ schedule65 ^ schedule59 ^ schedule57;  schedule73 = schedule73 << 1 | schedule73 >>> 31;
			int schedule74 = schedule71 ^ schedule66 ^ schedule60 ^ schedule58;  schedule74 = schedule74 << 1 | schedule74 >>> 31;
			int schedule75 = schedule72 ^ schedule67 ^ schedule61 ^ schedule59;  schedule75 = schedule75 << 1 | schedule75 >>> 31;
			int schedule76 = schedule73 ^ schedule68 ^ schedule62 ^ schedule60;  schedule76 = schedule76 << 1 | schedule76 >>> 31;
			int schedule77 = schedule74 ^ schedule69 ^ schedule63 ^ schedule61;  schedule77 = schedule77 << 1 | schedule77 >>> 31;
			int schedule78 = schedule75 ^ schedule70 ^ schedule64 ^ schedule62;  schedule78 = schedule78 << 1 | schedule78 >>> 31;
			int schedule79 = schedule76 ^ schedule71 ^ schedule65 ^ schedule63;  schedule79 = schedule79 << 1 | schedule79 >>> 31;
			
			// The 80 rounds
			e += (a << 5 | a >>> 27) + (d ^ (b & (c ^ d))) + schedule00 + 0x5A827999;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (c ^ (a & (b ^ c))) + schedule01 + 0x5A827999;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (b ^ (e & (a ^ b))) + schedule02 + 0x5A827999;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (a ^ (d & (e ^ a))) + schedule03 + 0x5A827999;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (e ^ (c & (d ^ e))) + schedule04 + 0x5A827999;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (d ^ (b & (c ^ d))) + schedule05 + 0x5A827999;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (c ^ (a & (b ^ c))) + schedule06 + 0x5A827999;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (b ^ (e & (a ^ b))) + schedule07 + 0x5A827999;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (a ^ (d & (e ^ a))) + schedule08 + 0x5A827999;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (e ^ (c & (d ^ e))) + schedule09 + 0x5A827999;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (d ^ (b & (c ^ d))) + schedule10 + 0x5A827999;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (c ^ (a & (b ^ c))) + schedule11 + 0x5A827999;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (b ^ (e & (a ^ b))) + schedule12 + 0x5A827999;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (a ^ (d & (e ^ a))) + schedule13 + 0x5A827999;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (e ^ (c & (d ^ e))) + schedule14 + 0x5A827999;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (d ^ (b & (c ^ d))) + schedule15 + 0x5A827999;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (c ^ (a & (b ^ c))) + schedule16 + 0x5A827999;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (b ^ (e & (a ^ b))) + schedule17 + 0x5A827999;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (a ^ (d & (e ^ a))) + schedule18 + 0x5A827999;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (e ^ (c & (d ^ e))) + schedule19 + 0x5A827999;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule20 + 0x6ED9EBA1;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule21 + 0x6ED9EBA1;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule22 + 0x6ED9EBA1;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule23 + 0x6ED9EBA1;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule24 + 0x6ED9EBA1;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule25 + 0x6ED9EBA1;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule26 + 0x6ED9EBA1;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule27 + 0x6ED9EBA1;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule28 + 0x6ED9EBA1;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule29 + 0x6ED9EBA1;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule30 + 0x6ED9EBA1;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule31 + 0x6ED9EBA1;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule32 + 0x6ED9EBA1;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule33 + 0x6ED9EBA1;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule34 + 0x6ED9EBA1;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule35 + 0x6ED9EBA1;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule36 + 0x6ED9EBA1;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule37 + 0x6ED9EBA1;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule38 + 0x6ED9EBA1;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule39 + 0x6ED9EBA1;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + ((b & (c | d)) | (c & d)) + schedule40 + 0x8F1BBCDC;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + ((a & (b | c)) | (b & c)) + schedule41 + 0x8F1BBCDC;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + ((e & (a | b)) | (a & b)) + schedule42 + 0x8F1BBCDC;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + ((d & (e | a)) | (e & a)) + schedule43 + 0x8F1BBCDC;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + ((c & (d | e)) | (d & e)) + schedule44 + 0x8F1BBCDC;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + ((b & (c | d)) | (c & d)) + schedule45 + 0x8F1BBCDC;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + ((a & (b | c)) | (b & c)) + schedule46 + 0x8F1BBCDC;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + ((e & (a | b)) | (a & b)) + schedule47 + 0x8F1BBCDC;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + ((d & (e | a)) | (e & a)) + schedule48 + 0x8F1BBCDC;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + ((c & (d | e)) | (d & e)) + schedule49 + 0x8F1BBCDC;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + ((b & (c | d)) | (c & d)) + schedule50 + 0x8F1BBCDC;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + ((a & (b | c)) | (b & c)) + schedule51 + 0x8F1BBCDC;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + ((e & (a | b)) | (a & b)) + schedule52 + 0x8F1BBCDC;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + ((d & (e | a)) | (e & a)) + schedule53 + 0x8F1BBCDC;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + ((c & (d | e)) | (d & e)) + schedule54 + 0x8F1BBCDC;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + ((b & (c | d)) | (c & d)) + schedule55 + 0x8F1BBCDC;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + ((a & (b | c)) | (b & c)) + schedule56 + 0x8F1BBCDC;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + ((e & (a | b)) | (a & b)) + schedule57 + 0x8F1BBCDC;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + ((d & (e | a)) | (e & a)) + schedule58 + 0x8F1BBCDC;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + ((c & (d | e)) | (d & e)) + schedule59 + 0x8F1BBCDC;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule60 + 0xCA62C1D6;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule61 + 0xCA62C1D6;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule62 + 0xCA62C1D6;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule63 + 0xCA62C1D6;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule64 + 0xCA62C1D6;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule65 + 0xCA62C1D6;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule66 + 0xCA62C1D6;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule67 + 0xCA62C1D6;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule68 + 0xCA62C1D6;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule69 + 0xCA62C1D6;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule70 + 0xCA62C1D6;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule71 + 0xCA62C1D6;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule72 + 0xCA62C1D6;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule73 + 0xCA62C1D6;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule74 + 0xCA62C1D6;  c = c << 30 | c >>> 2;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule75 + 0xCA62C1D6;  b = b << 30 | b >>> 2;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule76 + 0xCA62C1D6;  a = a << 30 | a >>> 2;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule77 + 0xCA62C1D6;  e = e << 30 | e >>> 2;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule78 + 0xCA62C1D6;  d = d << 30 | d >>> 2;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule79 + 0xCA62C1D6;  c = c << 30 | c >>> 2;
			
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