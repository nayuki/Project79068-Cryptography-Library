package p79068.crypto.hash;

import p79068.crypto.Zeroizer;
import p79068.math.IntegerBitMath;
import p79068.util.hash.HashValue;


final class Md4Hasher extends BlockHasher {
	
	private int[] state;
	
	
	
	Md4Hasher(Md4 algor) {
		super(algor, 64);
		state = new int[]{0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476};
	}
	
	
	
	public Md4Hasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		Md4Hasher result = (Md4Hasher)super.clone();
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
	
	
	
	private static final int[] s = {3, 7, 11, 19, 3, 5, 9, 13, 3, 9, 11, 15};
	private static final int[] k = {0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15};
	
	
	protected void compress(byte[] message, int off, int len) {
		int[] schedule = new int[16];
		int a = state[0];
		int b = state[1];
		int c = state[2];
		int d = state[3];
		for (int end = off + len; off < end;) {
			for (int i = 0; i < 16; i++, off += 4) {  // Pack bytes into int32s in little endian
				schedule[i] =
					  (message[off + 0] & 0xFF) <<  0
					| (message[off + 1] & 0xFF) <<  8
					| (message[off + 2] & 0xFF) << 16
					| (message[off + 3] & 0xFF) << 24;
			}
			for (int i = 0; i < 48; i++) {  // The 48 rounds
				int tp;
				int rot;
				if (0 <= i && i < 16) {
					tp = a + (d ^ (b & (c ^ d))) + schedule[i];
					rot = s[i & 3];
				} else if (16 <= i && i < 32) {
					tp = a + ((b & c) | (d & (b | c))) + schedule[k[i & 0xF]] + 0x5A827999;
					rot = s[4 | i & 3];
				} else if (32 <= i && i < 48) {
					tp = a + (b ^ c ^ d) + schedule[k[16 | i & 0xF]] + 0x6ED9EBA1;
					rot = s[8 | i & 3];
				} else
					throw new AssertionError();
				tp = tp << rot | tp >>> (32 - rot);  // Left rotation
				a = d;
				d = c;
				c = b;
				b = tp;
			}
			a = state[0] += a;
			b = state[1] += b;
			c = state[2] += c;
			d = state[3] += d;
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
			block[block.length - 8 + i] = (byte)((length * 8) >>> (i * 8));
		compress();
		return createHash(IntegerBitMath.toBytesLittleEndian(state));
	}
	
}