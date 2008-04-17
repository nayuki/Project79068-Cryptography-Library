package p79068.math;

import p79068.lang.BoundsChecker;


/**
 * Contains methods for math functions that deal with bits in long integers.
 * <p>Instantiability: <em>Not applicable</em></p>
 */
public final class LongBitMath {
	
	/**
	 * Returns the value of the bit at the specified index of the specified bit sequence.
	 * @param x the bit sequence to test
	 * @param bitIndex the index of the bit in <code>x</code> to test
	 * @return <samp>0</samp> or <samp>1</samp>
	 */
	public static int extractBit(long x, int bitIndex) {
		BoundsChecker.check(64, bitIndex);
		return (int)(x >>> bitIndex) & 1;
	}
	
	
	/**
	 * Returns a contiguous group of bits extracted from the specified bit sequence and placed xxx.
	 * For example: <code>extractBits(0xC0FFEEL, 8, 16)</code> yields <code>0xC0FFL</code>.
	 */
	public static long extractBits(long x, int bitOffset, int bitLength) {
		BoundsChecker.check(64, bitOffset, bitLength);
		if (bitLength == 64)
			return x;  // bitOffset = 0, or else an exception was already thrown.
		return (x >>> bitOffset) & ((1L << bitLength) - 1);
	}
	
	
	/**
	 * Returns the reverse of the specified bit sequence.
	 * @param x the bit sequence to reverse
	 * @return the reverse of <code>x</code>
	 */
	public static long reverse(long x) {
		x = (x & 0x5555555555555555L) << 1 | (x & 0xAAAAAAAAAAAAAAAAL) >>> 1;
		x = (x & 0x3333333333333333L) << 2 | (x & 0xCCCCCCCCCCCCCCCCL) >>> 2;
		x = (x & 0x0F0F0F0F0F0F0F0FL) << 4 | (x & 0xF0F0F0F0F0F0F0F0L) >>> 4;
		x = (x & 0x00FF00FF00FF00FFL) << 8 | (x & 0xFF00FF00FF00FF00L) >>> 8;
		x = (x & 0x0000FFFF0000FFFFL) << 16 | (x & 0xFFFF0000FFFF0000L) >>> 16;
		x = (x & 0x00000000FFFFFFFFL) << 32 | (x & 0xFFFFFFFF00000000L) >>> 32;
		return x;
	}
	
	
	/**
	 * Returns the number of bits set to <code>1</code> in the specified integer. Also known as the Hamming weight or population count function.
	 * @return the number of bits set to <code>1</code>, between <samp>0</samp> (inclusive) and <samp>64</samp> (inclusive)
	 */
	public static int countOnes(long x) {
		x = ((x & 0xAAAAAAAAAAAAAAAAL) >>> 1) + (x & 0x5555555555555555L);
		x = ((x & 0xCCCCCCCCCCCCCCCCL) >>> 2) + (x & 0x3333333333333333L);
		x = ((x & 0xF0F0F0F0F0F0F0F0L) >>> 4) + (x & 0x0F0F0F0F0F0F0F0FL);
		x = ((x & 0xFF00FF00FF00FF00L) >>> 8) + (x & 0x00FF00FF00FF00FFL);
		x = ((x & 0xFFFF0000FFFF0000L) >>> 16) + (x & 0x0000FFFF0000FFFFL);
		x = ((x & 0xFFFFFFFF00000000L) >>> 32) + (x & 0x00000000FFFFFFFFL);
		return (int)x;
	}
	
	/**
	 * Returns the specified bit sequence rotated to the left by the specified number of places. The shift value is taken modulo 64.
	 * @param x the bit sequence to rotate
	 * @param shift the number of places to rotate to the left, taken modulo 64
	 * @return <code>x</code> rotated to the left by <code>shift</code> places
	 */
	public static long rotateLeft(long x, int shift) {
		return x << shift | x >>> (64 - shift);
	}
	
	
	/**
	 * Returns the specified bit sequence rotated to the right by the specified number of places. The shift value is taken modulo 64.
	 * @param x the bit sequence to rotate
	 * @param shift the number of places to rotate to the right, taken modulo 64
	 * @return <code>x</code> rotated to the right by <code>shift</code> places
	 */
	public static long rotateRight(long x, int shift) {
		return x << (64 - shift) | x >>> shift;
	}
	
	
	public static long swapEndian(long x) {
		x = (x & 0x00FF00FF00FF00FFL) << 8 | (x & 0xFF00FF00FF00FF00L) >>> 8;
		x = (x & 0x0000FFFF0000FFFFL) << 16 | (x & 0xFFFF0000FFFF0000L) >>> 16;
		x = (x & 0x00000000FFFFFFFFL) << 32 | (x & 0xFFFFFFFF00000000L) >>> 32;
		return x;
	}
	
	
	
	public static byte[] toBytesBigEndian(long[] ain) {
		byte[] aout = new byte[ain.length * 8];
		for (int i = 0; i < aout.length; i++)
			aout[i] = (byte)(ain[i / 8] >>> ((7 - i % 8) * 8));
		return aout;
	}
	
	
	public static byte[] toBytesLittleEndian(long[] ain) {
		byte[] aout = new byte[ain.length * 8];
		for (int i = 0; i < aout.length; i++)
			aout[i] = (byte)(ain[i / 8] >>> (i % 8 * 8));
		return aout;
	}
	
	
	public static long[] fromBytesBigEndian(byte[] ain) {
		if (ain.length % 8 != 0)
			throw new IllegalArgumentException("Length is not multiple of 8");
		long[] aout = new long[ain.length / 8];
		for (int i = 0; i < ain.length; i++)
			aout[i / 8] |= (ain[i] & 0xFFL) << ((7 - i % 8) * 8);
		return aout;
	}
	
	
	public static long[] fromBytesLittleEndian(byte[] ain) {
		if (ain.length % 8 != 0)
			throw new IllegalArgumentException("Length is not multiple of 8");
		long[] aout = new long[ain.length / 8];
		for (int i = 0; i < ain.length; i++)
			aout[i / 8] |= (ain[i] & 0xFFL) << (i % 8 * 8);
		return aout;
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private LongBitMath() {}
	
}