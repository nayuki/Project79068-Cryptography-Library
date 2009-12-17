package p79068.crypto.hash;

import p79068.crypto.Zeroizer;
import p79068.lang.BoundsChecker;
import p79068.math.IntegerBitMath;
import p79068.util.hash.HashValue;


final class Md5Hasher extends BlockHasherCore {
	
	private int[] state;
	
	
	
	Md5Hasher() {
		state = new int[]{0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476};
	}
	
	
	
	public Md5Hasher clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		Md5Hasher result = (Md5Hasher)super.clone();
		result.state = result.state.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		Zeroizer.clear(state);
		state = null;
	}
	
	
	
	private static final int[] t = {
		0xD76AA478, 0xE8C7B756, 0x242070DB, 0xC1BDCEEE,
		0xF57C0FAF, 0x4787C62A, 0xA8304613, 0xFD469501,
		0x698098D8, 0x8B44F7AF, 0xFFFF5BB1, 0x895CD7BE,
		0x6B901122, 0xFD987193, 0xA679438E, 0x49B40821,
		0xF61E2562, 0xC040B340, 0x265E5A51, 0xE9B6C7AA,
		0xD62F105D, 0x02441453, 0xD8A1E681, 0xE7D3FBC8,
		0x21E1CDE6, 0xC33707D6, 0xF4D50D87, 0x455A14ED,
		0xA9E3E905, 0xFCEFA3F8, 0x676F02D9, 0x8D2A4C8A,
		0xFFFA3942, 0x8771F681, 0x6D9D6122, 0xFDE5380C,
		0xA4BEEA44, 0x4BDECFA9, 0xF6BB4B60, 0xBEBFBC70,
		0x289B7EC6, 0xEAA127FA, 0xD4EF3085, 0x04881D05,
		0xD9D4D039, 0xE6DB99E5, 0x1FA27CF8, 0xC4AC5665,
		0xF4292244, 0x432AFF97, 0xAB9423A7, 0xFC93A039,
		0x655B59C3, 0x8F0CCC92, 0xFFEFF47D, 0x85845DD1,
		0x6FA87E4F, 0xFE2CE6E0, 0xA3014314, 0x4E0811A1,
		0xF7537E82, 0xBD3AF235, 0x2AD7D2BB, 0xEB86D391
	};
	
	
	private static final int[] s = {
		 7, 12, 17, 22,
		 5,  9, 14, 20,
		 4, 11, 16, 23,
		 6, 10, 15, 21
	};
	
	
	
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
		for (int end = off + len; off < end;) {
			
			// Pack bytes into int32s in little endian
			for (int i = 0; i < 16; i++, off += 4) {
				schedule[i] =
					  (message[off + 0] & 0xFF) <<  0
					| (message[off + 1] & 0xFF) <<  8
					| (message[off + 2] & 0xFF) << 16
					| (message[off + 3] & 0xFF) << 24;
			}
			
			// The 64 rounds
			for (int i = 0; i < 64; i++) {
				int f;
				int k;
				if (0 <= i && i < 16) {
					f = d ^ (b & (c ^ d));  // Same as (b & c) | (~b & d)
					k = i;
				} else if (16 <= i && i < 32) {
					f = c ^ (d & (b ^ c));  // Same as (d & b) | (~d & c)
					k = (5 * i + 1) % 16;
				} else if (32 <= i && i < 48) {
					f = b ^ c ^ d;
					k = (3 * i + 5) % 16;
				} else if (48 <= i && i < 64) {
					f = c ^ (b | (~d));
					k = 7 * i % 16;
				} else
					throw new AssertionError();
				
				int temp = a + f + t[i] + schedule[k];
				int rot = s[i / 16 * 4 + i % 4];
				temp = b + (temp << rot | temp >>> (32 - rot));  // Addition and left rotation
				a = d;
				d = c;
				c = b;
				b = temp;
			}
			
			state[0] += a;
			state[1] += b;
			state[2] += c;
			state[3] += d;
			a = state[0];
			b = state[1];
			c = state[2];
			d = state[3];
		}
	}
	
	
	public HashValue getHashDestructively(byte[] block, int blockLength, long length) {
		block[blockLength] = (byte)0x80;
		for (int i = blockLength + 1; i < block.length; i++)
			block[i] = 0x00;
		if (blockLength + 1 > block.length - 8) {
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