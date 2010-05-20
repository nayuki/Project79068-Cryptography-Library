package p79068.crypto.hash;

import p79068.crypto.Zeroizer;
import p79068.lang.BoundsChecker;
import p79068.math.IntegerBitMath;
import p79068.util.hash.HashValue;


final class Sha1Hasher extends BlockHasherCore {
	
	private boolean sha1Mode;
	
	private int[] state;
	
	
	
	public Sha1Hasher(boolean sha1Mode) {
		this.sha1Mode = sha1Mode;
		state = new int[] { 0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0 };
	}
	
	
	
	@Override
	public Sha1Hasher clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		Sha1Hasher result = (Sha1Hasher)super.clone();
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
		
		int[] schedule = new int[80];
		
		// For each block of 64 bytes
		for (int end = off + len; off < end;) {
			
			// Pack bytes into int32s in big endian
			for (int i = 0; i < 16; i++, off += 4) {
				schedule[i] =
					  (message[off + 0] & 0xFF) << 24
					| (message[off + 1] & 0xFF) << 16
					| (message[off + 2] & 0xFF) <<  8
					| (message[off + 3] & 0xFF) <<  0;
			}
			
			// Expand the schedule
			for (int i = 16; i < 80; i++) {
				int temp = schedule[i - 3] ^ schedule[i - 8] ^ schedule[i - 14] ^ schedule[i - 16];
				if (sha1Mode)  // This is the only difference between SHA and SHA-1
					temp = temp << 1 | temp >>> 31;
				schedule[i] = temp;
			}
			
			int[] tempState = state.clone();
			encrypt(schedule, tempState);
			
			state[0] += tempState[0];
			state[1] += tempState[1];
			state[2] += tempState[2];
			state[3] += tempState[3];
			state[4] += tempState[4];
		}
	}
	
	
	@Override
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
			block[block.length - 1 - i] = (byte)((length * 8) >>> (i * 8));
		compress(block);
		return new HashValue(IntegerBitMath.toBytesBigEndian(state));
	}
	
	
	
	private static final int[] k = {0x5A827999, 0x6ED9EBA1, 0x8F1BBCDC, 0xCA62C1D6};
	
	
	/*
	 * Each round performs a transform of this form:
	 *  a' = e + f(a,b,c,d)
	 *  b' = a
	 *  c' = b ROTLEFT 30
	 *  d' = c
	 *  e' = d
	 * The primed variables represent the output.
	 * The actual implementation is an in-place version of this description.
	 */
	static void encrypt(int[] keySchedule, int[] message) {
		int a = message[0];
		int b = message[1];
		int c = message[2];
		int d = message[3];
		int e = message[4];
		
		// The 80 rounds
		for (int i = 0; i < 80; i++) {
			int f;
			if (0 <= i && i < 20)
				f = d ^ (b & (c ^ d));  // Same as (b & c) | (~b & d)
			else if (20 <= i && i < 40)
				f = b ^ c ^ d;
			else if (40 <= i && i < 60)
				f = (b & (c | d)) | (c & d);  // Same as (b & c) | (c & d) | (d & b)
			else if (60 <= i && i < 80)
				f = b ^ c ^ d;
			else
				throw new AssertionError();
			
			int temp = (a << 5 | a >>> 27) + f + e + k[i / 20] + keySchedule[i];
			e = d;
			d = c;
			c = b << 30 | b >>> 2;
			b = a;
			a = temp;
		}
		
		message[0] = a;
		message[1] = b;
		message[2] = c;
		message[3] = d;
		message[4] = e;
	}
	
	
	/*
	 * Each round performs a transform of this form:
	 *  a = b'
	 *  b = c' ROTLEFT 2
	 *  c = d'
	 *  d = e'
	 *  e = a' - f(a,b,c,d)
	 * The primed variables represent the input.
	 * Therefore, equivalently: e = a' - f(b', c' ROTLEFT 2, d', e')
	 * The actual implementation is an in-place version of this description.
	 */
	static void decrypt(int[] keySchedule, int[] message) {
		int a = message[0];
		int b = message[1];
		int c = message[2];
		int d = message[3];
		int e = message[4];
		
		// The 80 rounds
		for (int i = 79; i >= 0; i--) {
			int temp = a;
			a = b;
			b = c << 2 | c >>> 30;
			c = d;
			d = e;
			
			int f;
			if (0 <= i && i < 20)
				f = d ^ (b & (c ^ d));
			else if (20 <= i && i < 40)
				f = b ^ c ^ d;
			else if (40 <= i && i < 60)
				f = (b & (c | d)) | (c & d);
			else if (60 <= i && i < 80)
				f = b ^ c ^ d;
			else
				throw new AssertionError();
			
			e = temp - ((a << 5 | a >>> 27) + f + k[i / 20] + keySchedule[i]);
		}
		
		message[0] = a;
		message[1] = b;
		message[2] = c;
		message[3] = d;
		message[4] = e;
	}
	
}