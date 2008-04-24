package p79068.crypto.hash;

import p79068.crypto.Zeroizer;
import p79068.math.LongBitMath;
import p79068.util.hash.HashValue;


final class Sha512Hasher extends BlockHasher {
	
	private boolean sha384Mode;
	
	private long[] state;
	
	
	
	Sha512Hasher(Sha512 algor) {
		super(algor, 128);
		sha384Mode = false;
		state = new long[]{
				0x6A09E667F3BCC908L, 0xBB67AE8584CAA73BL, 0x3C6EF372FE94F82BL, 0xA54FF53A5F1D36F1L,
				0x510E527FADE682D1L, 0x9B05688C2B3E6C1FL, 0x1F83D9ABFB41BD6BL, 0x5BE0CD19137E2179L
		};
	}
	
	
	Sha512Hasher(Sha384 algor) {
		super(algor, 128);
		sha384Mode = true;
		state = new long[]{  // Different from above
				0xCBBB9D5DC1059ED8L, 0x629A292A367CD507L, 0x9159015A3070DD17L, 0x152FECD8F70E5939L,
				0x67332667FFC00B31L, 0x8EB44A8768581511L, 0xDB0C2E0D64F98FA7L, 0x47B5481DBEFA4FA4L
		};
	}
	
	
	
	public Sha512Hasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		Sha512Hasher result = (Sha512Hasher)super.clone();
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
	
	
	
	private static final long[] k = {
		0x428A2F98D728AE22L, 0x7137449123EF65CDL, 0xB5C0FBCFEC4D3B2FL, 0xE9B5DBA58189DBBCL,
		0x3956C25BF348B538L, 0x59F111F1B605D019L, 0x923F82A4AF194F9BL, 0xAB1C5ED5DA6D8118L,
		0xD807AA98A3030242L, 0x12835B0145706FBEL, 0x243185BE4EE4B28CL, 0x550C7DC3D5FFB4E2L,
		0x72BE5D74F27B896FL, 0x80DEB1FE3B1696B1L, 0x9BDC06A725C71235L, 0xC19BF174CF692694L,
		0xE49B69C19EF14AD2L, 0xEFBE4786384F25E3L, 0x0FC19DC68B8CD5B5L, 0x240CA1CC77AC9C65L,
		0x2DE92C6F592B0275L, 0x4A7484AA6EA6E483L, 0x5CB0A9DCBD41FBD4L, 0x76F988DA831153B5L,
		0x983E5152EE66DFABL, 0xA831C66D2DB43210L, 0xB00327C898FB213FL, 0xBF597FC7BEEF0EE4L,
		0xC6E00BF33DA88FC2L, 0xD5A79147930AA725L, 0x06CA6351E003826FL, 0x142929670A0E6E70L,
		0x27B70A8546D22FFCL, 0x2E1B21385C26C926L, 0x4D2C6DFC5AC42AEDL, 0x53380D139D95B3DFL,
		0x650A73548BAF63DEL, 0x766A0ABB3C77B2A8L, 0x81C2C92E47EDAEE6L, 0x92722C851482353BL,
		0xA2BFE8A14CF10364L, 0xA81A664BBC423001L, 0xC24B8B70D0F89791L, 0xC76C51A30654BE30L,
		0xD192E819D6EF5218L, 0xD69906245565A910L, 0xF40E35855771202AL, 0x106AA07032BBD1B8L,
		0x19A4C116B8D2D0C8L, 0x1E376C085141AB53L, 0x2748774CDF8EEB99L, 0x34B0BCB5E19B48A8L,
		0x391C0CB3C5C95A63L, 0x4ED8AA4AE3418ACBL, 0x5B9CCA4F7763E373L, 0x682E6FF3D6B2B8A3L,
		0x748F82EE5DEFB2FCL, 0x78A5636F43172F60L, 0x84C87814A1F0AB72L, 0x8CC702081A6439ECL,
		0x90BEFFFA23631E28L, 0xA4506CEBDE82BDE9L, 0xBEF9A3F7B2C67915L, 0xC67178F2E372532BL,
		0xCA273ECEEA26619CL, 0xD186B8C721C0C207L, 0xEADA7DD6CDE0EB1EL, 0xF57D4F7FEE6ED178L,
		0x06F067AA72176FBAL, 0x0A637DC5A2C898A6L, 0x113F9804BEF90DAEL, 0x1B710B35131C471BL,
		0x28DB77F523047D84L, 0x32CAAB7B40C72493L, 0x3C9EBE0A15C9BEBCL, 0x431D67C49C100D4CL,
		0x4CC5D4BECB3E42B6L, 0x597F299CFC657E2AL, 0x5FCB6FAB3AD6FAECL, 0x6C44198C4A475817L
	};
	
	
	protected void compress(byte[] message, int off, int len) {
		long[] schedule = new long[80];
		long a = state[0];
		long b = state[1];
		long c = state[2];
		long d = state[3];
		long e = state[4];
		long f = state[5];
		long g = state[6];
		long h = state[7];
		for (int end = off + len; off < end;) {  // For each block of 128 bytes
			for (int i = 0; i < 16; i++, off += 8) {  // Pack bytes into int64s in big endian
				schedule[i] =
					  (message[off + 0] & 0xFFL) << 56
					| (message[off + 1] & 0xFFL) << 48
					| (message[off + 2] & 0xFFL) << 40
					| (message[off + 3] & 0xFFL) << 32
					| (message[off + 4] & 0xFFL) << 24
					| (message[off + 5] & 0xFFL) << 16
					| (message[off + 6] & 0xFFL) <<  8
					| (message[off + 7] & 0xFFL) <<  0;
			}
			for (int i = 16; i < 80; i++)  // Expand the schedule
				schedule[i] = schedule[i - 16] + ((schedule[i - 15] << 63 | schedule[i - 15] >>> 1) ^ (schedule[i - 15] << 56 | schedule[i - 15] >>> 8) ^ (schedule[i - 15] >>> 7)) + schedule[i - 7] + ((schedule[i - 2] << 45 | schedule[i - 2] >>> 19) ^ (schedule[i - 2] << 3 | schedule[i - 2] >>> 61) ^ (schedule[i - 2] >>> 6));
			for (int i = 0; i < 80; i++) {  // The 80 rounds
				long t1 = h + ((e << 50 | e >>> 14) ^ (e << 46 | e >>> 18) ^ (e << 23 | e >>> 41)) + (g ^ (e & (f ^ g))) + k[i] + schedule[i];
				long t2 = ((a << 36 | a >>> 28) ^ (a << 30 | a >>> 34) ^ (a << 25 | a >>> 39)) + ((a & (b | c)) | (b & c));
				h = g;
				g = f;
				f = e;
				e = d + t1;
				d = c;
				c = b;
				b = a;
				a = t1 + t2;
			}
			a = state[0] += a;
			b = state[1] += b;
			c = state[2] += c;
			d = state[3] += d;
			e = state[4] += e;
			f = state[5] += f;
			g = state[6] += g;
			h = state[7] += h;
		}
	}
	
	
	protected HashValue getHashDestructively() {
		block[blockLength] = (byte)0x80;
		for (int i = blockLength + 1; i < block.length; i++)
			block[i] = 0x00;
		if (blockLength + 1 > block.length - 16) {
			compress();
			for (int i = 0; i < block.length; i++)
				block[i] = 0x00;
		}
		for (int i = 0; i < 8; i++)
			block[block.length - 1 - i] = (byte)((length * 8) >>> (i * 8));  // SHA-512 and SHA-384 support lengths just less than 2^128 bits (2^125 bytes), but this implementation only counts to just less than 2^64 bytes.
		compress();
		if (sha384Mode) {
			long[] truncstate = new long[6];  // state, truncated from 8 to 6 elements
			System.arraycopy(state, 0, truncstate, 0, truncstate.length);
			state = truncstate;
		}
		return createHash(LongBitMath.toBytesBigEndian(state));
	}
	
}