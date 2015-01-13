package p79068.crypto.hash;

import static java.lang.Long.rotateRight;

import java.math.BigInteger;
import java.util.Arrays;

import p79068.Assert;
import p79068.crypto.Zeroizer;
import p79068.hash.HashValue;
import p79068.math.LongBitMath;


class Sha512Core extends BlockHasherCore {
	
	private final int hashLength;  // In bytes
	
	protected long[] state;
	
	
	
	public Sha512Core(int hashLen) {
		hashLength = hashLen;
		switch (hashLen) {
			case 28 /* SHA-512/224 */:  state = new long[]{0x8C3D37C819544DA2L, 0x73E1996689DCD4D6L, 0x1DFAB7AE32FF9C82L, 0x679DD514582F9FCFL, 0x0F6D2B697BD44DA8L, 0x77E36F7304C48942L, 0x3F9D85A86A1D36C8L, 0x1112E6AD91D692A1L};  break;
			case 32 /* SHA-512/256 */:  state = new long[]{0x22312194FC2BF72CL, 0x9F555FA3C84C64C2L, 0x2393B86B6F53B151L, 0x963877195940EABDL, 0x96283EE2A88EFFE3L, 0xBE5E1E2553863992L, 0x2B0199FC2C85B8AAL, 0x0EB72DDC81C52CA2L};  break;
			case 48 /* SHA-384     */:  state = new long[]{0xCBBB9D5DC1059ED8L, 0x629A292A367CD507L, 0x9159015A3070DD17L, 0x152FECD8F70E5939L, 0x67332667FFC00B31L, 0x8EB44A8768581511L, 0xDB0C2E0D64F98FA7L, 0x47B5481DBEFA4FA4L};  break;
			case 64 /* SHA-512     */:  state = new long[]{0x6A09E667F3BCC908L, 0xBB67AE8584CAA73BL, 0x3C6EF372FE94F82BL, 0xA54FF53A5F1D36F1L, 0x510E527FADE682D1L, 0x9B05688C2B3E6C1FL, 0x1F83D9ABFB41BD6BL, 0x5BE0CD19137E2179L};  break;
			default:  throw new AssertionError();
		}
	}
	
	
	
	@Override
	public Sha512Core clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		Sha512Core result = (Sha512Core)super.clone();
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
		Assert.assertRangeInBounds(message.length, off, len);
		if (len % 128 != 0)
			throw new AssertionError();
		
		long[] schedule = new long[80];
		
		// For each block of 128 bytes
		for (int i = off, end = off + len; i < end; ) {
			
			// Pack bytes into int64s in big endian
			for (int j = 0; j < 16; j++, i += 8) {
				schedule[j] =
					  (message[i + 0] & 0xFFL) << 56
					| (message[i + 1] & 0xFFL) << 48
					| (message[i + 2] & 0xFFL) << 40
					| (message[i + 3] & 0xFFL) << 32
					| (message[i + 4] & 0xFFL) << 24
					| (message[i + 5] & 0xFFL) << 16
					| (message[i + 6] & 0xFFL) <<  8
					| (message[i + 7] & 0xFFL) <<  0;
			}
			
			// Expand the schedule
			for (int j = 16; j < 80; j++)
				schedule[j] = schedule[j-16] + schedule[j-7] + smallSigma0(schedule[j-15]) + smallSigma1(schedule[j-2]);
			
			// The 80 rounds
			long a = state[0];
			long b = state[1];
			long c = state[2];
			long d = state[3];
			long e = state[4];
			long f = state[5];
			long g = state[6];
			long h = state[7];
			for (int j = 0; j < 80; j++) {
				long t1 = h + bigSigma1(e) + choose(e, f, g) + K[j] + schedule[j];
				long t2 = bigSigma0(a) + majority(a, b, c);
				h = g;
				g = f;
				f = e;
				e = d + t1;
				d = c;
				c = b;
				b = a;
				a = t1 + t2;
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
	
	
	private static long smallSigma0(long x) { return rotateRight(x,  1) ^ rotateRight(x,  8) ^ (x >>> 7); }
	private static long smallSigma1(long x) { return rotateRight(x, 19) ^ rotateRight(x, 61) ^ (x >>> 6); }
	private static long bigSigma0  (long x) { return rotateRight(x, 28) ^ rotateRight(x, 34) ^ rotateRight(x, 39); }
	private static long bigSigma1  (long x) { return rotateRight(x, 14) ^ rotateRight(x, 18) ^ rotateRight(x, 41); }
	private static long choose  (long x, long y, long z) { return (x & y) ^ (~x & z);          }  // Can be optimized to z ^ (x & (y ^ z))
	private static long majority(long x, long y, long z) { return (x & y) ^ (x & z) ^ (y & z); }  // Can be optimized to (x & (y | z)) | (y & z)
	
	
	@Override
	public HashValue getHashDestructively(byte[] block, int blockFilled, BigInteger length) {
		if (length.bitLength() > 125)  // SHA-384, SHA-512, SHA-512/t only support messages less than 2^128 bits long
			throw new IllegalStateException("Message too long");
		
		block[blockFilled] = (byte)0x80;
		blockFilled++;
		Arrays.fill(block, blockFilled, block.length, (byte)0);
		if (blockFilled + 16 > block.length) {
			compress(block);
			Arrays.fill(block, (byte)0);
		}
		length = length.shiftLeft(3);  // Length is now in bits
		for (int i = 0; i < 16; i++)
			block[block.length - 1 - i] = length.shiftRight(i * 8).byteValue();
		compress(block);
		byte[] hash = LongBitMath.toBytesBigEndian(state);
		return new HashValue(Arrays.copyOf(hash, hashLength));  // Truncate the hash
	}
	
	
	protected static final long[] K = {
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
		0x4CC5D4BECB3E42B6L, 0x597F299CFC657E2AL, 0x5FCB6FAB3AD6FAECL, 0x6C44198C4A475817L,
	};
	
}
