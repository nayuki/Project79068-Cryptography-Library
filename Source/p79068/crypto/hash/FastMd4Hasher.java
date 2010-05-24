package p79068.crypto.hash;

import p79068.crypto.Zeroizer;
import p79068.lang.BoundsChecker;
import p79068.math.IntegerBitMath;
import p79068.util.hash.HashValue;


final class FastMd4Hasher extends BlockHasherCore {
	
	private int[] state;
	
	
	
	public FastMd4Hasher() {
		state = new int[] { 0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476 };
	}
	
	
	
	@Override
	public FastMd4Hasher clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		FastMd4Hasher result = (FastMd4Hasher)super.clone();
		result.state = result.state.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		Zeroizer.clear(state);
		state = null;
	}
	
	
	
	@Override
	public void compress(byte[] message, int off, int len) {
		BoundsChecker.check(message.length, off, len);
		if (len % 64 != 0)
			throw new AssertionError();
		
		int[] schedule = new int[16];
		int a = state[0];
		int b = state[1];
		int c = state[2];
		int d = state[3];
		
		// For each block of 64 bytes
		for (int end = off + len; off < end; off += 64) {
			
			// Pack bytes into int32s in little endian
			schedule[ 0] = (message[off +  0] & 0xFF) | (message[off +  1] & 0xFF) << 8 | (message[off +  2] & 0xFF) << 16 | message[off +  3] << 24;
			schedule[ 1] = (message[off +  4] & 0xFF) | (message[off +  5] & 0xFF) << 8 | (message[off +  6] & 0xFF) << 16 | message[off +  7] << 24;
			schedule[ 2] = (message[off +  8] & 0xFF) | (message[off +  9] & 0xFF) << 8 | (message[off + 10] & 0xFF) << 16 | message[off + 11] << 24;
			schedule[ 3] = (message[off + 12] & 0xFF) | (message[off + 13] & 0xFF) << 8 | (message[off + 14] & 0xFF) << 16 | message[off + 15] << 24;
			schedule[ 4] = (message[off + 16] & 0xFF) | (message[off + 17] & 0xFF) << 8 | (message[off + 18] & 0xFF) << 16 | message[off + 19] << 24;
			schedule[ 5] = (message[off + 20] & 0xFF) | (message[off + 21] & 0xFF) << 8 | (message[off + 22] & 0xFF) << 16 | message[off + 23] << 24;
			schedule[ 6] = (message[off + 24] & 0xFF) | (message[off + 25] & 0xFF) << 8 | (message[off + 26] & 0xFF) << 16 | message[off + 27] << 24;
			schedule[ 7] = (message[off + 28] & 0xFF) | (message[off + 29] & 0xFF) << 8 | (message[off + 30] & 0xFF) << 16 | message[off + 31] << 24;
			schedule[ 8] = (message[off + 32] & 0xFF) | (message[off + 33] & 0xFF) << 8 | (message[off + 34] & 0xFF) << 16 | message[off + 35] << 24;
			schedule[ 9] = (message[off + 36] & 0xFF) | (message[off + 37] & 0xFF) << 8 | (message[off + 38] & 0xFF) << 16 | message[off + 39] << 24;
			schedule[10] = (message[off + 40] & 0xFF) | (message[off + 41] & 0xFF) << 8 | (message[off + 42] & 0xFF) << 16 | message[off + 43] << 24;
			schedule[11] = (message[off + 44] & 0xFF) | (message[off + 45] & 0xFF) << 8 | (message[off + 46] & 0xFF) << 16 | message[off + 47] << 24;
			schedule[12] = (message[off + 48] & 0xFF) | (message[off + 49] & 0xFF) << 8 | (message[off + 50] & 0xFF) << 16 | message[off + 51] << 24;
			schedule[13] = (message[off + 52] & 0xFF) | (message[off + 53] & 0xFF) << 8 | (message[off + 54] & 0xFF) << 16 | message[off + 55] << 24;
			schedule[14] = (message[off + 56] & 0xFF) | (message[off + 57] & 0xFF) << 8 | (message[off + 58] & 0xFF) << 16 | message[off + 59] << 24;
			schedule[15] = (message[off + 60] & 0xFF) | (message[off + 61] & 0xFF) << 8 | (message[off + 62] & 0xFF) << 16 | message[off + 63] << 24;
			
			// The 48 rounds
			a = a + (d ^ (b & (c ^ d))) + schedule[ 0];  a = a <<  3 | a >>> 29;
			d = d + (c ^ (a & (b ^ c))) + schedule[ 1];  d = d <<  7 | d >>> 25;
			c = c + (b ^ (d & (a ^ b))) + schedule[ 2];  c = c << 11 | c >>> 21;
			b = b + (a ^ (c & (d ^ a))) + schedule[ 3];  b = b << 19 | b >>> 13;
			a = a + (d ^ (b & (c ^ d))) + schedule[ 4];  a = a <<  3 | a >>> 29;
			d = d + (c ^ (a & (b ^ c))) + schedule[ 5];  d = d <<  7 | d >>> 25;
			c = c + (b ^ (d & (a ^ b))) + schedule[ 6];  c = c << 11 | c >>> 21;
			b = b + (a ^ (c & (d ^ a))) + schedule[ 7];  b = b << 19 | b >>> 13;
			a = a + (d ^ (b & (c ^ d))) + schedule[ 8];  a = a <<  3 | a >>> 29;
			d = d + (c ^ (a & (b ^ c))) + schedule[ 9];  d = d <<  7 | d >>> 25;
			c = c + (b ^ (d & (a ^ b))) + schedule[10];  c = c << 11 | c >>> 21;
			b = b + (a ^ (c & (d ^ a))) + schedule[11];  b = b << 19 | b >>> 13;
			a = a + (d ^ (b & (c ^ d))) + schedule[12];  a = a <<  3 | a >>> 29;
			d = d + (c ^ (a & (b ^ c))) + schedule[13];  d = d <<  7 | d >>> 25;
			c = c + (b ^ (d & (a ^ b))) + schedule[14];  c = c << 11 | c >>> 21;
			b = b + (a ^ (c & (d ^ a))) + schedule[15];  b = b << 19 | b >>> 13;
			a = a + ((b & c) | (d & (b | c))) + schedule[ 0] + 0x5A827999;  a = a <<  3 | a >>> 29;
			d = d + ((a & b) | (c & (a | b))) + schedule[ 4] + 0x5A827999;  d = d <<  5 | d >>> 27;
			c = c + ((d & a) | (b & (d | a))) + schedule[ 8] + 0x5A827999;  c = c <<  9 | c >>> 23;
			b = b + ((c & d) | (a & (c | d))) + schedule[12] + 0x5A827999;  b = b << 13 | b >>> 19;
			a = a + ((b & c) | (d & (b | c))) + schedule[ 1] + 0x5A827999;  a = a <<  3 | a >>> 29;
			d = d + ((a & b) | (c & (a | b))) + schedule[ 5] + 0x5A827999;  d = d <<  5 | d >>> 27;
			c = c + ((d & a) | (b & (d | a))) + schedule[ 9] + 0x5A827999;  c = c <<  9 | c >>> 23;
			b = b + ((c & d) | (a & (c | d))) + schedule[13] + 0x5A827999;  b = b << 13 | b >>> 19;
			a = a + ((b & c) | (d & (b | c))) + schedule[ 2] + 0x5A827999;  a = a <<  3 | a >>> 29;
			d = d + ((a & b) | (c & (a | b))) + schedule[ 6] + 0x5A827999;  d = d <<  5 | d >>> 27;
			c = c + ((d & a) | (b & (d | a))) + schedule[10] + 0x5A827999;  c = c <<  9 | c >>> 23;
			b = b + ((c & d) | (a & (c | d))) + schedule[14] + 0x5A827999;  b = b << 13 | b >>> 19;
			a = a + ((b & c) | (d & (b | c))) + schedule[ 3] + 0x5A827999;  a = a <<  3 | a >>> 29;
			d = d + ((a & b) | (c & (a | b))) + schedule[ 7] + 0x5A827999;  d = d <<  5 | d >>> 27;
			c = c + ((d & a) | (b & (d | a))) + schedule[11] + 0x5A827999;  c = c <<  9 | c >>> 23;
			b = b + ((c & d) | (a & (c | d))) + schedule[15] + 0x5A827999;  b = b << 13 | b >>> 19;
			a = a + (b ^ c ^ d) + schedule[ 0] + 0x6ED9EBA1;  a = a <<  3 | a >>> 29;
			d = d + (a ^ b ^ c) + schedule[ 8] + 0x6ED9EBA1;  d = d <<  9 | d >>> 23;
			c = c + (d ^ a ^ b) + schedule[ 4] + 0x6ED9EBA1;  c = c << 11 | c >>> 21;
			b = b + (c ^ d ^ a) + schedule[12] + 0x6ED9EBA1;  b = b << 15 | b >>> 17;
			a = a + (b ^ c ^ d) + schedule[ 2] + 0x6ED9EBA1;  a = a <<  3 | a >>> 29;
			d = d + (a ^ b ^ c) + schedule[10] + 0x6ED9EBA1;  d = d <<  9 | d >>> 23;
			c = c + (d ^ a ^ b) + schedule[ 6] + 0x6ED9EBA1;  c = c << 11 | c >>> 21;
			b = b + (c ^ d ^ a) + schedule[14] + 0x6ED9EBA1;  b = b << 15 | b >>> 17;
			a = a + (b ^ c ^ d) + schedule[ 1] + 0x6ED9EBA1;  a = a <<  3 | a >>> 29;
			d = d + (a ^ b ^ c) + schedule[ 9] + 0x6ED9EBA1;  d = d <<  9 | d >>> 23;
			c = c + (d ^ a ^ b) + schedule[ 5] + 0x6ED9EBA1;  c = c << 11 | c >>> 21;
			b = b + (c ^ d ^ a) + schedule[13] + 0x6ED9EBA1;  b = b << 15 | b >>> 17;
			a = a + (b ^ c ^ d) + schedule[ 3] + 0x6ED9EBA1;  a = a <<  3 | a >>> 29;
			d = d + (a ^ b ^ c) + schedule[11] + 0x6ED9EBA1;  d = d <<  9 | d >>> 23;
			c = c + (d ^ a ^ b) + schedule[ 7] + 0x6ED9EBA1;  c = c << 11 | c >>> 21;
			b = b + (c ^ d ^ a) + schedule[15] + 0x6ED9EBA1;  b = b << 15 | b >>> 17;
			
			a = state[0] += a;
			b = state[1] += b;
			c = state[2] += c;
			d = state[3] += d;
		}
	}
	
	
	@Override
	public HashValue getHashDestructively(byte[] block, int blockLength, long length) {
		block[blockLength] = (byte)0x80;
		blockLength++;
		for (int i = blockLength; i < block.length; i++)
			block[i] = 0x00;
		if (blockLength + 8 > block.length) {
			compress(block);
			for (int i = 0; i < block.length; i++)
				block[i] = 0x00;
		}
		for (int i = 0; i < 8; i++)
			block[block.length - 8 + i] = (byte)((length * 8) >>> (i * 8));
		compress(block);
		return new HashValue(IntegerBitMath.toBytesLittleEndian(state));
	}
	
}