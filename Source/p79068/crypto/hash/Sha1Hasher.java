package p79068.crypto.hash;

import p79068.crypto.Zeroizer;
import p79068.math.IntegerBitMath;
import p79068.util.hash.HashValue;


final class Sha1Hasher extends BlockHasher {
	
	private boolean sha1Mode;
	
	private int[] state;
	
	
	
	Sha1Hasher(Sha1 hashFunc) {
		super(hashFunc, 64);
		sha1Mode = true;
		state = new int[]{0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0};
	}
	
	Sha1Hasher(Sha hashFunc) {
		super(hashFunc, 64);
		sha1Mode = false;
		state = new int[]{0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0};  // Same as above
	}
	
	

	public Sha1Hasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		Sha1Hasher result = (Sha1Hasher)super.clone();
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
		for (int end = off + len; off < end;) {  // For each block of 64 bytes
			for (int i = 0; i < 16; i++, off += 4) {  // Pack bytes into int32s in big endian
				schedule[i] =
					  (message[off + 0] & 0xFF) << 24
					| (message[off + 1] & 0xFF) << 16
					| (message[off + 2] & 0xFF) <<  8
					| (message[off + 3] & 0xFF) <<  0;
			}
			for (int i = 16; i < 80; i++) {  // Expand the schedule
				int tp = schedule[i - 3] ^ schedule[i - 8] ^ schedule[i - 14] ^ schedule[i - 16];
				if (sha1Mode)
					tp = tp << 1 | tp >>> 31;
				schedule[i] = tp;
			}
			for (int i = 0; i < 80; i++) {  // The 80 rounds
				int tp;
				if (0 <= i && i < 20)
					tp = e + (a << 5 | a >>> 27) + (d ^ (b & (c ^ d))) + schedule[i] + 0x5A827999;
				else if (20 <= i && i < 40)
					tp = e + (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule[i] + 0x6ED9EBA1;
				else if (40 <= i && i < 60)
					tp = e + (a << 5 | a >>> 27) + ((b & (c | d)) | (c & d)) + schedule[i] + 0x8F1BBCDC;
				else if (60 <= i && i < 80)
					tp = e + (a << 5 | a >>> 27) + (b ^ c ^ d) + schedule[i] + 0xCA62C1D6;
				else
					throw new AssertionError();
				e = d;
				d = c;
				c = b << 30 | b >>> 2;
				b = a;
				a = tp;
			}
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