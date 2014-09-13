package p79068.crypto.hash;

import java.math.BigInteger;
import java.util.Arrays;

import p79068.Assert;
import p79068.crypto.Zeroizer;
import p79068.hash.HashValue;
import p79068.math.IntegerBitMath;


class Md4Core extends BlockHasherCore {
	
	protected int[] state;
	
	
	
	public Md4Core() {
		state = new int[]{0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476};
	}
	
	
	
	@Override
	public Md4Core clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		Md4Core result = (Md4Core)super.clone();
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
		if (len % 64 != 0)
			throw new AssertionError();
		
		int[] schedule = new int[16];
		
		// For each block of 64 bytes
		for (int i = off, end = off + len; i < end; ) {
			
			// Pack bytes into int32s in little endian
			for (int j = 0; j < 16; j++, i += 4) {
				schedule[j] =
					  (message[i + 0] & 0xFF) <<  0
					| (message[i + 1] & 0xFF) <<  8
					| (message[i + 2] & 0xFF) << 16
					| (message[i + 3] & 0xFF) << 24;
			}
			
			// The 48 rounds
			int a = state[0];
			int b = state[1];
			int c = state[2];
			int d = state[3];
			for (int j = 0; j < 48; j++) {
				int f;
				if      ( 0 <= j && j < 16) f = (b & c) | (~b & d);  // Can be optimized to f = d ^ (b & (c ^ d))
				else if (16 <= j && j < 32) f = (b & c) | (d & (b | c));
				else if (32 <= j && j < 48) f = b ^ c ^ d;
				else throw new AssertionError();
				
				int temp = a + f + schedule[K[j / 16 * 16 + j % 16]] + ADD_CON[j / 16];
				int rot = S[j / 16 * 4 + j % 4];
				a = d;
				d = c;
				c = b;
				b = Integer.rotateLeft(temp, rot);
			}
			state[0] += a;
			state[1] += b;
			state[2] += c;
			state[3] += d;
		}
	}
	
	
	@Override
	public HashValue getHashDestructively(byte[] block, int blockFilled, BigInteger length) {
		block[blockFilled] = (byte)0x80;
		blockFilled++;
		Arrays.fill(block, blockFilled, block.length, (byte)0);
		if (blockFilled + 8 > block.length) {
			compress(block);
			Arrays.fill(block, (byte)0);
		}
		length = length.shiftLeft(3);  // Length is now in bits
		for (int i = 0; i < 8; i++)
			block[block.length - 8 + i] = length.shiftRight(i * 8).byteValue();
		compress(block);
		return new HashValue(IntegerBitMath.toBytesLittleEndian(state));
	}
	
	
	
	private static final int[] S = {  // Rotation amounts
		 3,  7, 11, 19,
		 3,  5,  9, 13,
		 3,  9, 11, 15,
	};
	
	
	private static final int[] K = {  // Schedule reading permutation
		 0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15,
		 0,  4,  8, 12,  1,  5,  9, 13,  2,  6, 10, 14,  3,  7, 11, 15,
		 0,  8,  4, 12,  2, 10,  6, 14,  1,  9,  5, 13,  3, 11,  7, 15,
	};
	
	
	private static final int[] ADD_CON = {  // Additive constants
		0x00000000, 0x5A827999, 0x6ED9EBA1
	};
	
}
