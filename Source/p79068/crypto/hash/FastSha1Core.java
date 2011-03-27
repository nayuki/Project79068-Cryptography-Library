package p79068.crypto.hash;

import java.util.Arrays;

import p79068.crypto.Zeroizer;
import p79068.lang.BoundsChecker;
import p79068.math.IntegerBitMath;
import p79068.util.hash.HashValue;


final class FastSha1Core extends BlockHasherCore {
	
	private int[] state;
	
	
	
	public FastSha1Core() {
		state = new int[] { 0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0 };
	}
	
	
	
	@Override
	public FastSha1Core clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		FastSha1Core result = (FastSha1Core)super.clone();
		result.state = result.state.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		state = Zeroizer.clear(state);
	}
	
	
	
	@Override
	public void compress(byte[] message, int off, int len) {
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
			
			// The 80 rounds (the schedule is expanded on the fly)
			int tp;
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
			tp = schedule13 ^ schedule08 ^ schedule02 ^ schedule00;  schedule00 = tp << 1 | tp >>> 31;
			d += (e << 5 | e >>> 27) + (c ^ (a & (b ^ c))) + schedule00 + 0x5A827999;  a = a << 30 | a >>> 2;
			tp = schedule14 ^ schedule09 ^ schedule03 ^ schedule01;  schedule01 = tp << 1 | tp >>> 31;
			c += (d << 5 | d >>> 27) + (b ^ (e & (a ^ b))) + schedule01 + 0x5A827999;  e = e << 30 | e >>> 2;
			tp = schedule15 ^ schedule10 ^ schedule04 ^ schedule02;  schedule02 = tp << 1 | tp >>> 31;
			b += (c << 5 | c >>> 27) + (a ^ (d & (e ^ a))) + schedule02 + 0x5A827999;  d = d << 30 | d >>> 2;
			tp = schedule00 ^ schedule11 ^ schedule05 ^ schedule03;  schedule03 = tp << 1 | tp >>> 31;
			a += (b << 5 | b >>> 27) + (e ^ (c & (d ^ e))) + schedule03 + 0x5A827999;  c = c << 30 | c >>> 2;
			tp = schedule01 ^ schedule12 ^ schedule06 ^ schedule04;  schedule04 = tp << 1 | tp >>> 31;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule04 + 0x6ED9EBA1;  b = b << 30 | b >>> 2;
			tp = schedule02 ^ schedule13 ^ schedule07 ^ schedule05;  schedule05 = tp << 1 | tp >>> 31;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule05 + 0x6ED9EBA1;  a = a << 30 | a >>> 2;
			tp = schedule03 ^ schedule14 ^ schedule08 ^ schedule06;  schedule06 = tp << 1 | tp >>> 31;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule06 + 0x6ED9EBA1;  e = e << 30 | e >>> 2;
			tp = schedule04 ^ schedule15 ^ schedule09 ^ schedule07;  schedule07 = tp << 1 | tp >>> 31;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule07 + 0x6ED9EBA1;  d = d << 30 | d >>> 2;
			tp = schedule05 ^ schedule00 ^ schedule10 ^ schedule08;  schedule08 = tp << 1 | tp >>> 31;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule08 + 0x6ED9EBA1;  c = c << 30 | c >>> 2;
			tp = schedule06 ^ schedule01 ^ schedule11 ^ schedule09;  schedule09 = tp << 1 | tp >>> 31;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule09 + 0x6ED9EBA1;  b = b << 30 | b >>> 2;
			tp = schedule07 ^ schedule02 ^ schedule12 ^ schedule10;  schedule10 = tp << 1 | tp >>> 31;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule10 + 0x6ED9EBA1;  a = a << 30 | a >>> 2;
			tp = schedule08 ^ schedule03 ^ schedule13 ^ schedule11;  schedule11 = tp << 1 | tp >>> 31;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule11 + 0x6ED9EBA1;  e = e << 30 | e >>> 2;
			tp = schedule09 ^ schedule04 ^ schedule14 ^ schedule12;  schedule12 = tp << 1 | tp >>> 31;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule12 + 0x6ED9EBA1;  d = d << 30 | d >>> 2;
			tp = schedule10 ^ schedule05 ^ schedule15 ^ schedule13;  schedule13 = tp << 1 | tp >>> 31;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule13 + 0x6ED9EBA1;  c = c << 30 | c >>> 2;
			tp = schedule11 ^ schedule06 ^ schedule00 ^ schedule14;  schedule14 = tp << 1 | tp >>> 31;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule14 + 0x6ED9EBA1;  b = b << 30 | b >>> 2;
			tp = schedule12 ^ schedule07 ^ schedule01 ^ schedule15;  schedule15 = tp << 1 | tp >>> 31;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule15 + 0x6ED9EBA1;  a = a << 30 | a >>> 2;
			tp = schedule13 ^ schedule08 ^ schedule02 ^ schedule00;  schedule00 = tp << 1 | tp >>> 31;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule00 + 0x6ED9EBA1;  e = e << 30 | e >>> 2;
			tp = schedule14 ^ schedule09 ^ schedule03 ^ schedule01;  schedule01 = tp << 1 | tp >>> 31;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule01 + 0x6ED9EBA1;  d = d << 30 | d >>> 2;
			tp = schedule15 ^ schedule10 ^ schedule04 ^ schedule02;  schedule02 = tp << 1 | tp >>> 31;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule02 + 0x6ED9EBA1;  c = c << 30 | c >>> 2;
			tp = schedule00 ^ schedule11 ^ schedule05 ^ schedule03;  schedule03 = tp << 1 | tp >>> 31;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule03 + 0x6ED9EBA1;  b = b << 30 | b >>> 2;
			tp = schedule01 ^ schedule12 ^ schedule06 ^ schedule04;  schedule04 = tp << 1 | tp >>> 31;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule04 + 0x6ED9EBA1;  a = a << 30 | a >>> 2;
			tp = schedule02 ^ schedule13 ^ schedule07 ^ schedule05;  schedule05 = tp << 1 | tp >>> 31;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule05 + 0x6ED9EBA1;  e = e << 30 | e >>> 2;
			tp = schedule03 ^ schedule14 ^ schedule08 ^ schedule06;  schedule06 = tp << 1 | tp >>> 31;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule06 + 0x6ED9EBA1;  d = d << 30 | d >>> 2;
			tp = schedule04 ^ schedule15 ^ schedule09 ^ schedule07;  schedule07 = tp << 1 | tp >>> 31;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule07 + 0x6ED9EBA1;  c = c << 30 | c >>> 2;
			tp = schedule05 ^ schedule00 ^ schedule10 ^ schedule08;  schedule08 = tp << 1 | tp >>> 31;
			e += (a << 5 | a >>> 27) + ((b & (c | d)) | (c & d)) + schedule08 + 0x8F1BBCDC;  b = b << 30 | b >>> 2;
			tp = schedule06 ^ schedule01 ^ schedule11 ^ schedule09;  schedule09 = tp << 1 | tp >>> 31;
			d += (e << 5 | e >>> 27) + ((a & (b | c)) | (b & c)) + schedule09 + 0x8F1BBCDC;  a = a << 30 | a >>> 2;
			tp = schedule07 ^ schedule02 ^ schedule12 ^ schedule10;  schedule10 = tp << 1 | tp >>> 31;
			c += (d << 5 | d >>> 27) + ((e & (a | b)) | (a & b)) + schedule10 + 0x8F1BBCDC;  e = e << 30 | e >>> 2;
			tp = schedule08 ^ schedule03 ^ schedule13 ^ schedule11;  schedule11 = tp << 1 | tp >>> 31;
			b += (c << 5 | c >>> 27) + ((d & (e | a)) | (e & a)) + schedule11 + 0x8F1BBCDC;  d = d << 30 | d >>> 2;
			tp = schedule09 ^ schedule04 ^ schedule14 ^ schedule12;  schedule12 = tp << 1 | tp >>> 31;
			a += (b << 5 | b >>> 27) + ((c & (d | e)) | (d & e)) + schedule12 + 0x8F1BBCDC;  c = c << 30 | c >>> 2;
			tp = schedule10 ^ schedule05 ^ schedule15 ^ schedule13;  schedule13 = tp << 1 | tp >>> 31;
			e += (a << 5 | a >>> 27) + ((b & (c | d)) | (c & d)) + schedule13 + 0x8F1BBCDC;  b = b << 30 | b >>> 2;
			tp = schedule11 ^ schedule06 ^ schedule00 ^ schedule14;  schedule14 = tp << 1 | tp >>> 31;
			d += (e << 5 | e >>> 27) + ((a & (b | c)) | (b & c)) + schedule14 + 0x8F1BBCDC;  a = a << 30 | a >>> 2;
			tp = schedule12 ^ schedule07 ^ schedule01 ^ schedule15;  schedule15 = tp << 1 | tp >>> 31;
			c += (d << 5 | d >>> 27) + ((e & (a | b)) | (a & b)) + schedule15 + 0x8F1BBCDC;  e = e << 30 | e >>> 2;
			tp = schedule13 ^ schedule08 ^ schedule02 ^ schedule00;  schedule00 = tp << 1 | tp >>> 31;
			b += (c << 5 | c >>> 27) + ((d & (e | a)) | (e & a)) + schedule00 + 0x8F1BBCDC;  d = d << 30 | d >>> 2;
			tp = schedule14 ^ schedule09 ^ schedule03 ^ schedule01;  schedule01 = tp << 1 | tp >>> 31;
			a += (b << 5 | b >>> 27) + ((c & (d | e)) | (d & e)) + schedule01 + 0x8F1BBCDC;  c = c << 30 | c >>> 2;
			tp = schedule15 ^ schedule10 ^ schedule04 ^ schedule02;  schedule02 = tp << 1 | tp >>> 31;
			e += (a << 5 | a >>> 27) + ((b & (c | d)) | (c & d)) + schedule02 + 0x8F1BBCDC;  b = b << 30 | b >>> 2;
			tp = schedule00 ^ schedule11 ^ schedule05 ^ schedule03;  schedule03 = tp << 1 | tp >>> 31;
			d += (e << 5 | e >>> 27) + ((a & (b | c)) | (b & c)) + schedule03 + 0x8F1BBCDC;  a = a << 30 | a >>> 2;
			tp = schedule01 ^ schedule12 ^ schedule06 ^ schedule04;  schedule04 = tp << 1 | tp >>> 31;
			c += (d << 5 | d >>> 27) + ((e & (a | b)) | (a & b)) + schedule04 + 0x8F1BBCDC;  e = e << 30 | e >>> 2;
			tp = schedule02 ^ schedule13 ^ schedule07 ^ schedule05;  schedule05 = tp << 1 | tp >>> 31;
			b += (c << 5 | c >>> 27) + ((d & (e | a)) | (e & a)) + schedule05 + 0x8F1BBCDC;  d = d << 30 | d >>> 2;
			tp = schedule03 ^ schedule14 ^ schedule08 ^ schedule06;  schedule06 = tp << 1 | tp >>> 31;
			a += (b << 5 | b >>> 27) + ((c & (d | e)) | (d & e)) + schedule06 + 0x8F1BBCDC;  c = c << 30 | c >>> 2;
			tp = schedule04 ^ schedule15 ^ schedule09 ^ schedule07;  schedule07 = tp << 1 | tp >>> 31;
			e += (a << 5 | a >>> 27) + ((b & (c | d)) | (c & d)) + schedule07 + 0x8F1BBCDC;  b = b << 30 | b >>> 2;
			tp = schedule05 ^ schedule00 ^ schedule10 ^ schedule08;  schedule08 = tp << 1 | tp >>> 31;
			d += (e << 5 | e >>> 27) + ((a & (b | c)) | (b & c)) + schedule08 + 0x8F1BBCDC;  a = a << 30 | a >>> 2;
			tp = schedule06 ^ schedule01 ^ schedule11 ^ schedule09;  schedule09 = tp << 1 | tp >>> 31;
			c += (d << 5 | d >>> 27) + ((e & (a | b)) | (a & b)) + schedule09 + 0x8F1BBCDC;  e = e << 30 | e >>> 2;
			tp = schedule07 ^ schedule02 ^ schedule12 ^ schedule10;  schedule10 = tp << 1 | tp >>> 31;
			b += (c << 5 | c >>> 27) + ((d & (e | a)) | (e & a)) + schedule10 + 0x8F1BBCDC;  d = d << 30 | d >>> 2;
			tp = schedule08 ^ schedule03 ^ schedule13 ^ schedule11;  schedule11 = tp << 1 | tp >>> 31;
			a += (b << 5 | b >>> 27) + ((c & (d | e)) | (d & e)) + schedule11 + 0x8F1BBCDC;  c = c << 30 | c >>> 2;
			tp = schedule09 ^ schedule04 ^ schedule14 ^ schedule12;  schedule12 = tp << 1 | tp >>> 31;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule12 + 0xCA62C1D6;  b = b << 30 | b >>> 2;
			tp = schedule10 ^ schedule05 ^ schedule15 ^ schedule13;  schedule13 = tp << 1 | tp >>> 31;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule13 + 0xCA62C1D6;  a = a << 30 | a >>> 2;
			tp = schedule11 ^ schedule06 ^ schedule00 ^ schedule14;  schedule14 = tp << 1 | tp >>> 31;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule14 + 0xCA62C1D6;  e = e << 30 | e >>> 2;
			tp = schedule12 ^ schedule07 ^ schedule01 ^ schedule15;  schedule15 = tp << 1 | tp >>> 31;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule15 + 0xCA62C1D6;  d = d << 30 | d >>> 2;
			tp = schedule13 ^ schedule08 ^ schedule02 ^ schedule00;  schedule00 = tp << 1 | tp >>> 31;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule00 + 0xCA62C1D6;  c = c << 30 | c >>> 2;
			tp = schedule14 ^ schedule09 ^ schedule03 ^ schedule01;  schedule01 = tp << 1 | tp >>> 31;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule01 + 0xCA62C1D6;  b = b << 30 | b >>> 2;
			tp = schedule15 ^ schedule10 ^ schedule04 ^ schedule02;  schedule02 = tp << 1 | tp >>> 31;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule02 + 0xCA62C1D6;  a = a << 30 | a >>> 2;
			tp = schedule00 ^ schedule11 ^ schedule05 ^ schedule03;  schedule03 = tp << 1 | tp >>> 31;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule03 + 0xCA62C1D6;  e = e << 30 | e >>> 2;
			tp = schedule01 ^ schedule12 ^ schedule06 ^ schedule04;  schedule04 = tp << 1 | tp >>> 31;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule04 + 0xCA62C1D6;  d = d << 30 | d >>> 2;
			tp = schedule02 ^ schedule13 ^ schedule07 ^ schedule05;  schedule05 = tp << 1 | tp >>> 31;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule05 + 0xCA62C1D6;  c = c << 30 | c >>> 2;
			tp = schedule03 ^ schedule14 ^ schedule08 ^ schedule06;  schedule06 = tp << 1 | tp >>> 31;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule06 + 0xCA62C1D6;  b = b << 30 | b >>> 2;
			tp = schedule04 ^ schedule15 ^ schedule09 ^ schedule07;  schedule07 = tp << 1 | tp >>> 31;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule07 + 0xCA62C1D6;  a = a << 30 | a >>> 2;
			tp = schedule05 ^ schedule00 ^ schedule10 ^ schedule08;  schedule08 = tp << 1 | tp >>> 31;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule08 + 0xCA62C1D6;  e = e << 30 | e >>> 2;
			tp = schedule06 ^ schedule01 ^ schedule11 ^ schedule09;  schedule09 = tp << 1 | tp >>> 31;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule09 + 0xCA62C1D6;  d = d << 30 | d >>> 2;
			tp = schedule07 ^ schedule02 ^ schedule12 ^ schedule10;  schedule10 = tp << 1 | tp >>> 31;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule10 + 0xCA62C1D6;  c = c << 30 | c >>> 2;
			tp = schedule08 ^ schedule03 ^ schedule13 ^ schedule11;  schedule11 = tp << 1 | tp >>> 31;
			e += (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule11 + 0xCA62C1D6;  b = b << 30 | b >>> 2;
			tp = schedule09 ^ schedule04 ^ schedule14 ^ schedule12;  schedule12 = tp << 1 | tp >>> 31;
			d += (e << 5 | e >>> 27) + (a ^ b ^ c) + schedule12 + 0xCA62C1D6;  a = a << 30 | a >>> 2;
			tp = schedule10 ^ schedule05 ^ schedule15 ^ schedule13;  schedule13 = tp << 1 | tp >>> 31;
			c += (d << 5 | d >>> 27) + (e ^ a ^ b) + schedule13 + 0xCA62C1D6;  e = e << 30 | e >>> 2;
			tp = schedule11 ^ schedule06 ^ schedule00 ^ schedule14;  schedule14 = tp << 1 | tp >>> 31;
			b += (c << 5 | c >>> 27) + (d ^ e ^ a) + schedule14 + 0xCA62C1D6;  d = d << 30 | d >>> 2;
			tp = schedule12 ^ schedule07 ^ schedule01 ^ schedule15;  schedule15 = tp << 1 | tp >>> 31;
			a += (b << 5 | b >>> 27) + (c ^ d ^ e) + schedule15 + 0xCA62C1D6;  c = c << 30 | c >>> 2;
			
			a = state[0] += a;
			b = state[1] += b;
			c = state[2] += c;
			d = state[3] += d;
			e = state[4] += e;
		}
	}
	
	
	@Override
	public HashValue getHashDestructively(byte[] block, int blockLength, long length) {
		block[blockLength] = (byte)0x80;
		blockLength++;
		Arrays.fill(block, blockLength, block.length, (byte)0);
		if (blockLength + 8 > block.length) {
			compress(block);
			Arrays.fill(block, (byte)0);
		}
		for (int i = 0; i < 8; i++)
			block[block.length - 1 - i] = (byte)((length * 8) >>> (i * 8));
		compress(block);
		return new HashValue(IntegerBitMath.toBytesBigEndian(state));
	}
	
}