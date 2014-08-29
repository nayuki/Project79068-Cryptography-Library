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
		
		int[] sch = new int[64];
		
		// For each block of 64 bytes
		for (int i = off, end = off + len; i < end; ) {
			
			// Pack bytes into int32s in big endian
			for (int j = 0; j < 16; j++, i += 4)
				sch[j] = msg[i] << 24 | (msg[i + 1] & 0xFF) << 16 | (msg[i + 2] & 0xFF) << 8 | (msg[i + 3] & 0xFF);
			
			// Expand the schedule
			for (int j = 16; j < 64; j++) {
				int x = sch[j - 15];
				int y = sch[j - 2];
				sch[j] = sch[j - 16] + sch[j - 7] + (rotateRight(x, 7) ^ rotateRight(x, 18) ^ (x >>> 3)) + (rotateRight(y, 17) ^ rotateRight(y, 19) ^ (y >>> 10));
			}
			
			// The 64 rounds
			int a = state[0];
			int b = state[1];
			int c = state[2];
			int d = state[3];
			int e = state[4];
			int f = state[5];
			int g = state[6];
			int h = state[7];
			for (int j = 0; j < 64; j += 8) {
				h += (rotateRight(e, 6) ^ rotateRight(e, 11) ^ rotateRight(e, 25)) + (g ^ (e & (f ^ g))) + K[j + 0] + sch[j + 0];  d += h;  h += (rotateRight(a, 2) ^ rotateRight(a, 13) ^ rotateRight(a, 22)) + ((a & (b | c)) | (b & c));
				g += (rotateRight(d, 6) ^ rotateRight(d, 11) ^ rotateRight(d, 25)) + (f ^ (d & (e ^ f))) + K[j + 1] + sch[j + 1];  c += g;  g += (rotateRight(h, 2) ^ rotateRight(h, 13) ^ rotateRight(h, 22)) + ((h & (a | b)) | (a & b));
				f += (rotateRight(c, 6) ^ rotateRight(c, 11) ^ rotateRight(c, 25)) + (e ^ (c & (d ^ e))) + K[j + 2] + sch[j + 2];  b += f;  f += (rotateRight(g, 2) ^ rotateRight(g, 13) ^ rotateRight(g, 22)) + ((g & (h | a)) | (h & a));
				e += (rotateRight(b, 6) ^ rotateRight(b, 11) ^ rotateRight(b, 25)) + (d ^ (b & (c ^ d))) + K[j + 3] + sch[j + 3];  a += e;  e += (rotateRight(f, 2) ^ rotateRight(f, 13) ^ rotateRight(f, 22)) + ((f & (g | h)) | (g & h));
				d += (rotateRight(a, 6) ^ rotateRight(a, 11) ^ rotateRight(a, 25)) + (c ^ (a & (b ^ c))) + K[j + 4] + sch[j + 4];  h += d;  d += (rotateRight(e, 2) ^ rotateRight(e, 13) ^ rotateRight(e, 22)) + ((e & (f | g)) | (f & g));
				c += (rotateRight(h, 6) ^ rotateRight(h, 11) ^ rotateRight(h, 25)) + (b ^ (h & (a ^ b))) + K[j + 5] + sch[j + 5];  g += c;  c += (rotateRight(d, 2) ^ rotateRight(d, 13) ^ rotateRight(d, 22)) + ((d & (e | f)) | (e & f));
				b += (rotateRight(g, 6) ^ rotateRight(g, 11) ^ rotateRight(g, 25)) + (a ^ (g & (h ^ a))) + K[j + 6] + sch[j + 6];  f += b;  b += (rotateRight(c, 2) ^ rotateRight(c, 13) ^ rotateRight(c, 22)) + ((c & (d | e)) | (d & e));
				a += (rotateRight(f, 6) ^ rotateRight(f, 11) ^ rotateRight(f, 25)) + (h ^ (f & (g ^ h))) + K[j + 7] + sch[j + 7];  e += a;  a += (rotateRight(b, 2) ^ rotateRight(b, 13) ^ rotateRight(b, 22)) + ((b & (c | d)) | (c & d));
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
