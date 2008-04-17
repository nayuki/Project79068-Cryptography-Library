package p79068.math;

import p79068.lang.BoundsChecker;


/**
 * Contains methods for math functions that deal with bits in integers.
 * <p>Instantiability: <em>Not applicable</em></p>
 */
public final class IntegerBitMath {
	
	/**
	 * Returns the value of the bit at the specified index of the specified bit sequence.
	 * @param x the bit sequence to test
	 * @param bitIndex the index of the bit in <code>x</code> to test
	 * @return <samp>0</samp> or <samp>1</samp>
	 */
	public static int extractBit(int x, int bitIndex) {
		BoundsChecker.check(32, bitIndex);
		return (x >>> bitIndex) & 1;
	}
	
	
	/**
	 * Returns a contiguous group of bits extracted from the specified bit sequence and placed xxx.
	 * For example: <code>extractBits(0xCAFE, 4, 8)</code> yields <code>0xAF</code>.
	 */
	public static int extractBits(int x, int bitOffset, int bitLength) {
		BoundsChecker.check(32, bitOffset, bitLength);
		if (bitLength == 32)
			return x;  // bitOffset = 0, or else an exception was already thrown.
		else
			return (x >>> bitOffset) & ((1 << bitLength) - 1);
	}
	
	
	/**
	 * Returns the reverse of the specified bit sequence.
	 * @param x the bit sequence to reverse
	 * @return the reverse of <code>x</code>
	 */
	public static int reverseBits(int x) {
		x = (x & 0x55555555) << 1 | (x & 0xAAAAAAAA) >>> 1;
		x = (x & 0x33333333) << 2 | (x & 0xCCCCCCCC) >>> 2;
		x = (x & 0x0F0F0F0F) << 4 | (x & 0xF0F0F0F0) >>> 4;
		x = (x & 0x00FF00FF) << 8 | (x & 0xFF00FF00) >>> 8;
		x = (x & 0x0000FFFF) << 16 | (x & 0xFFFF0000) >>> 16;
		return x;
	}
	
	
	/**
	 * Returns the number of bits set to <code>1</code> in the specified integer. Also known as the Hamming weight or population count function.
	 * @return the number of bits set to <code>1</code>, between <samp>0</samp> (inclusive) and <samp>32</samp> (inclusive)
	 */
	public static int countOnes(int x) {
		x = ((x & 0xAAAAAAAA) >>> 1) + (x & 0x55555555);
		x = ((x & 0xCCCCCCCC) >>> 2) + (x & 0x33333333);
		x = ((x & 0xF0F0F0F0) >>> 4) + (x & 0x0F0F0F0F);
		x = ((x & 0xFF00FF00) >>> 8) + (x & 0x00FF00FF);
		x = ((x & 0xFFFF0000) >>> 16) + (x & 0x0000FFFF);
		return x;
	}
	
	
	/**
	 * Returns the specified bit sequence rotated to the left by the specified number of places. The shift value is taken modulo 32.
	 * @param x the bit sequence to rotate
	 * @param shift the number of places to rotate to the left, taken modulo 32
	 * @return <code>x</code> rotated to the left by <code>shift</code> places
	 */
	public static int rotateLeft(int x, int shift) {
		return x << shift | x >>> (32 - shift);
	}
	
	
	/**
	 * Returns the specified bit sequence rotated to the right by the specified number of places. The shift value is taken modulo 32.
	 * @param x the bit sequence to rotate
	 * @param shift the number of places to rotate to the right, taken modulo 32
	 * @return <code>x</code> rotated to the right by <code>shift</code> places
	 */
	public static int rotateRight(int x, int shift) {
		return x << (32 - shift) | x >>> shift;
	}
	
	
	public static int swapEndian(int x) {
		x = (x & 0x00FF00FF) << 8 | (x & 0xFF00FF00) >>> 8;
		x = (x & 0x0000FFFF) << 16 | (x & 0xFFFF0000) >>> 16;
		return x;
	}
	
	
	
	public static byte[] toBytesBigEndian(int[] ain) {
		byte[] aout = new byte[ain.length * 4];
		for (int i = 0; i < aout.length; i++)
			aout[i] = (byte)(ain[i / 4] >>> ((3 - i % 4) * 8));
		return aout;
	}
	
	
	public static byte[] toBytesLittleEndian(int[] ain) {
		byte[] aout = new byte[ain.length * 4];
		for (int i = 0; i < aout.length; i++)
			aout[i] = (byte)(ain[i / 4] >>> (i % 4 * 8));
		return aout;
	}
	
	
	public static int[] fromBytesBigEndian(byte[] ain) {
		if (ain.length % 4 != 0)
			throw new IllegalArgumentException("Length is not multiple of 4");
		int[] aout = new int[ain.length / 4];
		for (int i = 0; i < ain.length; i++)
			aout[i / 4] |= (ain[i] & 0xFF) << ((3 - i % 4) * 8);
		return aout;
	}
	
	
	public static int[] fromBytesLittleEndian(byte[] ain) {
		if (ain.length % 4 != 0)
			throw new IllegalArgumentException("Length is not multiple of 4");
		int[] aout = new int[ain.length / 4];
		for (int i = 0; i < ain.length; i++)
			aout[i / 4] |= (ain[i] & 0xFF) << (i % 4 * 8);
		return aout;
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private IntegerBitMath() {}
	
}