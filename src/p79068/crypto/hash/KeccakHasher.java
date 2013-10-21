package p79068.crypto.hash;

import static java.lang.Long.rotateLeft;
import p79068.Assert;
import p79068.crypto.Zeroizable;
import p79068.crypto.Zeroizer;
import p79068.hash.AbstractHasher;
import p79068.hash.HashValue;


class KeccakHasher extends AbstractHasher implements Zeroizable {
	
	private long[] state;
	
	private final int blockSize;
	
	private int blockFilled;
	
	
	
	public KeccakHasher(Keccak hashFunc) {
		super(hashFunc);
		state = new long[25];
		blockSize = 200 - hashFunc.getHashLength() * 2;
		if (blockSize <= 0)
			throw new IllegalArgumentException();
		blockFilled = 0;
	}
	
	
	
	@Override
	public void update(byte[] b, int off, int len) {
		Assert.assertRangeInBounds(b.length, off, len);
		int j = blockFilled;
		if (j < 0 || j >= blockSize)
			throw new AssertionError();
		
		if ((blockSize & 7) != 0) {
			// Slow generic code
			for (int i = off, end = off + len; i < end; i++) {
				state[j >>> 3] ^= (b[i] & 0xFFL) << ((j & 7) << 3);
				j++;
				if (j == blockSize) {
					absorb();
					j = 0;
				}
			}
		}
		else {
			int i = off;
			int end = off + len;
			
			// Absorb up to 7 input bytes until we align to the next int64 element of state
			for (; (j & 7) != 0 && i < end; i++, j++)
				state[j >>> 3] ^= (b[i] & 0xFFL) << ((j & 7) << 3);
			if (j == blockSize) {
				absorb();
				j = 0;
			}
			
			// Absorb 8 bytes at a time
			for (; i + 8 <= end; i += 8) {
				state[j >>> 3] ^=
					     (((b[i    ] & 0xFF) | (b[i + 1] & 0xFF) << 8 | (b[i + 2] & 0xFF) << 16 | b[i + 3] << 24) & 0xFFFFFFFFL) |
					(long)((b[i + 4] & 0xFF) | (b[i + 5] & 0xFF) << 8 | (b[i + 6] & 0xFF) << 16 | b[i + 7] << 24) << 32;
				j += 8;
				if (j == blockSize) {
					absorb();
					j = 0;
				}
			}
			
			// Absorb the remaining bytes, at most 7
			for (; i < end; i++, j++)
				state[j >>> 3] ^= (b[i] & 0xFFL) << ((j & 7) << 3);
		}
		
		blockFilled = j;
	}
	
	
	@Override
	public HashValue getHash() {
		KeccakHasher hasher = clone();
		byte[] temp = new byte[hasher.blockSize - hasher.blockFilled];
		temp[0] = 0x01;
		temp[temp.length - 1] ^= 0x80;
		hasher.update(temp);
		
		byte[] result = new byte[hasher.hashFunction.getHashLength()];
		for (int i = 0; i < result.length; i++)
			result[i] = (byte)(hasher.state[i / 8] >>> (i % 8 * 8));
		return new HashValue(result);
	}
	
	
	
	@Override
	public KeccakHasher clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		KeccakHasher result = (KeccakHasher)super.clone();
		result.state = result.state.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		state = Zeroizer.clear(state);
	}
	
	
	
	private static final long[] RC = {
		0x0000000000000001L, 0x0000000000008082L, 0x800000000000808AL, 0x8000000080008000L,
		0x000000000000808BL, 0x0000000080000001L, 0x8000000080008081L, 0x8000000000008009L,
		0x000000000000008AL, 0x0000000000000088L, 0x0000000080008009L, 0x000000008000000AL,
		0x000000008000808BL, 0x800000000000008BL, 0x8000000000008089L, 0x8000000000008003L,
		0x8000000000008002L, 0x8000000000000080L, 0x000000000000800AL, 0x800000008000000AL,
		0x8000000080008081L, 0x8000000000008080L, 0x0000000080000001L, 0x8000000080008008L,
	};
	
	
	private void absorb() {
		long a00 = state[ 0], a01 = state[ 1], a02 = state[ 2], a03 = state[ 3], a04 = state[ 4];
		long a05 = state[ 5], a06 = state[ 6], a07 = state[ 7], a08 = state[ 8], a09 = state[ 9];
		long a10 = state[10], a11 = state[11], a12 = state[12], a13 = state[13], a14 = state[14];
		long a15 = state[15], a16 = state[16], a17 = state[17], a18 = state[18], a19 = state[19];
		long a20 = state[20], a21 = state[21], a22 = state[22], a23 = state[23], a24 = state[24];
		
		for (long rc : RC) {
			// Theta step
			long c0 = a00 ^ a05 ^ a10 ^ a15 ^ a20;
			long c1 = a01 ^ a06 ^ a11 ^ a16 ^ a21;
			long c2 = a02 ^ a07 ^ a12 ^ a17 ^ a22;
			long c3 = a03 ^ a08 ^ a13 ^ a18 ^ a23;
			long c4 = a04 ^ a09 ^ a14 ^ a19 ^ a24;
			long d0 = c4 ^ rotateLeft(c1, 1);
			long d1 = c0 ^ rotateLeft(c2, 1);
			long d2 = c1 ^ rotateLeft(c3, 1);
			long d3 = c2 ^ rotateLeft(c4, 1);
			long d4 = c3 ^ rotateLeft(c0, 1);
			a00 ^= d0;  a05 ^= d0;  a10 ^= d0;  a15 ^= d0;  a20 ^= d0;
			a01 ^= d1;  a06 ^= d1;  a11 ^= d1;  a16 ^= d1;  a21 ^= d1;
			a02 ^= d2;  a07 ^= d2;  a12 ^= d2;  a17 ^= d2;  a22 ^= d2;
			a03 ^= d3;  a08 ^= d3;  a13 ^= d3;  a18 ^= d3;  a23 ^= d3;
			a04 ^= d4;  a09 ^= d4;  a14 ^= d4;  a19 ^= d4;  a24 ^= d4;
			
			// Rho and pi steps
			long b00 = rotateLeft(a00,  0);
			long b16 = rotateLeft(a05, 36);
			long b07 = rotateLeft(a10,  3);
			long b23 = rotateLeft(a15, 41);
			long b14 = rotateLeft(a20, 18);
			long b10 = rotateLeft(a01,  1);
			long b01 = rotateLeft(a06, 44);
			long b17 = rotateLeft(a11, 10);
			long b08 = rotateLeft(a16, 45);
			long b24 = rotateLeft(a21,  2);
			long b20 = rotateLeft(a02, 62);
			long b11 = rotateLeft(a07,  6);
			long b02 = rotateLeft(a12, 43);
			long b18 = rotateLeft(a17, 15);
			long b09 = rotateLeft(a22, 61);
			long b05 = rotateLeft(a03, 28);
			long b21 = rotateLeft(a08, 55);
			long b12 = rotateLeft(a13, 25);
			long b03 = rotateLeft(a18, 21);
			long b19 = rotateLeft(a23, 56);
			long b15 = rotateLeft(a04, 27);
			long b06 = rotateLeft(a09, 20);
			long b22 = rotateLeft(a14, 39);
			long b13 = rotateLeft(a19,  8);
			long b04 = rotateLeft(a24, 14);
			
			// Chi step
			a00 = b00 ^ (~b01 & b02) ^ rc;  // Iota step
			a05 = b05 ^ (~b06 & b07);
			a10 = b10 ^ (~b11 & b12);
			a15 = b15 ^ (~b16 & b17);
			a20 = b20 ^ (~b21 & b22);
			a01 = b01 ^ (~b02 & b03);
			a06 = b06 ^ (~b07 & b08);
			a11 = b11 ^ (~b12 & b13);
			a16 = b16 ^ (~b17 & b18);
			a21 = b21 ^ (~b22 & b23);
			a02 = b02 ^ (~b03 & b04);
			a07 = b07 ^ (~b08 & b09);
			a12 = b12 ^ (~b13 & b14);
			a17 = b17 ^ (~b18 & b19);
			a22 = b22 ^ (~b23 & b24);
			a03 = b03 ^ (~b04 & b00);
			a08 = b08 ^ (~b09 & b05);
			a13 = b13 ^ (~b14 & b10);
			a18 = b18 ^ (~b19 & b15);
			a23 = b23 ^ (~b24 & b20);
			a04 = b04 ^ (~b00 & b01);
			a09 = b09 ^ (~b05 & b06);
			a14 = b14 ^ (~b10 & b11);
			a19 = b19 ^ (~b15 & b16);
			a24 = b24 ^ (~b20 & b21);
		}
		
		state[ 0] = a00;  state[ 1] = a01;  state[ 2] = a02;  state[ 3] = a03;  state[ 4] = a04;
		state[ 5] = a05;  state[ 6] = a06;  state[ 7] = a07;  state[ 8] = a08;  state[ 9] = a09;
		state[10] = a10;  state[11] = a11;  state[12] = a12;  state[13] = a13;  state[14] = a14;
		state[15] = a15;  state[16] = a16;  state[17] = a17;  state[18] = a18;  state[19] = a19;
		state[20] = a20;  state[21] = a21;  state[22] = a22;  state[23] = a23;  state[24] = a24;
	}
	
}
