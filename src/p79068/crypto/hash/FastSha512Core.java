package p79068.crypto.hash;

import static java.lang.Long.rotateRight;
import p79068.Assert;


final class FastSha512Core extends Sha512Core {
	
	public FastSha512Core(int hashLen) {
		super(hashLen);
	}
	
	
	
	@Override
	public void compress(byte[] msg, int off, int len) {
		Assert.assertRangeInBounds(msg.length, off, len);
		if (len % 128 != 0)
			throw new AssertionError();
		
		long[] sch = new long[80];
		
		// For each block of 128 bytes
		for (int i = off, end = off + len; i < end; ) {
			
			// Pack bytes into int64s in big endian
			for (int j = 0; j < 16; j++, i += 8) {
				sch[j] =
					  (long)msg[i] << 56
					| (msg[i + 1] & 0xFFL) << 48
					| (msg[i + 2] & 0xFFL) << 40
					| (msg[i + 3] & 0xFFL) << 32
					| (msg[i + 4] & 0xFFL) << 24
					| (msg[i + 5] & 0xFFL) << 16
					| (msg[i + 6] & 0xFFL) <<  8
					| (msg[i + 7] & 0xFFL);
			}
			
			// Expand the schedule
			for (int j = 16; j < 80; j++) {
				long s = sch[j - 15];
				long t = sch[j - 2];
				sch[j] = sch[j - 16] + sch[j - 7] + (rotateRight(s, 1) ^ rotateRight(s, 8) ^ (s >>> 7)) + (rotateRight(t, 19) ^ rotateRight(t, 61) ^ (t >>> 6));
			}
			
			// The 80 rounds
			long a = state[0];
			long b = state[1];
			long c = state[2];
			long d = state[3];
			long e = state[4];
			long f = state[5];
			long g = state[6];
			long h = state[7];
			for (int j = 0; j < 80; j += 8) {
				h += (rotateRight(e, 14) ^ rotateRight(e, 18) ^ rotateRight(e, 41)) + (g ^ (e & (f ^ g))) + K[j +  0] + sch[j +  0];  d += h;  h += (rotateRight(a, 28) ^ rotateRight(a, 34) ^ rotateRight(a, 39)) + ((a & (b | c)) | (b & c));
				g += (rotateRight(d, 14) ^ rotateRight(d, 18) ^ rotateRight(d, 41)) + (f ^ (d & (e ^ f))) + K[j +  1] + sch[j +  1];  c += g;  g += (rotateRight(h, 28) ^ rotateRight(h, 34) ^ rotateRight(h, 39)) + ((h & (a | b)) | (a & b));
				f += (rotateRight(c, 14) ^ rotateRight(c, 18) ^ rotateRight(c, 41)) + (e ^ (c & (d ^ e))) + K[j +  2] + sch[j +  2];  b += f;  f += (rotateRight(g, 28) ^ rotateRight(g, 34) ^ rotateRight(g, 39)) + ((g & (h | a)) | (h & a));
				e += (rotateRight(b, 14) ^ rotateRight(b, 18) ^ rotateRight(b, 41)) + (d ^ (b & (c ^ d))) + K[j +  3] + sch[j +  3];  a += e;  e += (rotateRight(f, 28) ^ rotateRight(f, 34) ^ rotateRight(f, 39)) + ((f & (g | h)) | (g & h));
				d += (rotateRight(a, 14) ^ rotateRight(a, 18) ^ rotateRight(a, 41)) + (c ^ (a & (b ^ c))) + K[j +  4] + sch[j +  4];  h += d;  d += (rotateRight(e, 28) ^ rotateRight(e, 34) ^ rotateRight(e, 39)) + ((e & (f | g)) | (f & g));
				c += (rotateRight(h, 14) ^ rotateRight(h, 18) ^ rotateRight(h, 41)) + (b ^ (h & (a ^ b))) + K[j +  5] + sch[j +  5];  g += c;  c += (rotateRight(d, 28) ^ rotateRight(d, 34) ^ rotateRight(d, 39)) + ((d & (e | f)) | (e & f));
				b += (rotateRight(g, 14) ^ rotateRight(g, 18) ^ rotateRight(g, 41)) + (a ^ (g & (h ^ a))) + K[j +  6] + sch[j +  6];  f += b;  b += (rotateRight(c, 28) ^ rotateRight(c, 34) ^ rotateRight(c, 39)) + ((c & (d | e)) | (d & e));
				a += (rotateRight(f, 14) ^ rotateRight(f, 18) ^ rotateRight(f, 41)) + (h ^ (f & (g ^ h))) + K[j +  7] + sch[j +  7];  e += a;  a += (rotateRight(b, 28) ^ rotateRight(b, 34) ^ rotateRight(b, 39)) + ((b & (c | d)) | (c & d));
			}
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
