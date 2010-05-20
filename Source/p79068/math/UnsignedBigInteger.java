package p79068.math;

import java.util.Arrays;

import p79068.util.Random;


public final class UnsignedBigInteger implements Comparable<UnsignedBigInteger> {
	
	public static final UnsignedBigInteger ZERO = new UnsignedBigInteger(0);
	
	public static final UnsignedBigInteger ONE = new UnsignedBigInteger(1);
	
	
	
	/**
	 * The sequence of digits representing this number, in little endian. length >= 1. The last element is not zero unless it violates the length property.
	 */
	protected short[] digits;
	
	
	
	public UnsignedBigInteger(int value) {
		digits = trim(new short[]{(short)value, (short)(value >>> 16)});
	}
	
	
	public UnsignedBigInteger(long value) {
		digits = trim(new short[]{(short)value, (short)(value >>> 16), (short)(value >>> 32), (short)(value >>> 48)});
	}
	
	
	public UnsignedBigInteger(byte[] value) {  // The array is in big-endian
		digits = new short[(value.length + 1) / 2];
		int i = value.length - 1;
		int j = 0;
		for (; i >= 1; i -= 2, j++)
			digits[j] = (short)(value[i - 1] << 8 | value[i] & 0xFF);
		if (i == 0)
			digits[j] = (short)(value[i] & 0xFF);
		digits = trim(digits);
	}
	
	
	public UnsignedBigInteger(Random rand, int bitlen) {
		digits = new short[(bitlen + 15) / 16];
		for (int i = 0; i + 1 < digits.length; i += 2) {  // Fill in blocks of 2
			int tp = rand.randomInt();
			digits[i] = (short)(tp >>> 0);
			digits[i | 1] = (short)(tp >>> 16);
		}
		digits[digits.length - 1] = (short)(rand.randomInt() >>> (32 - bitlen % 16));  // Fill the last element
		digits = trim(digits);
	}
	
	
	private UnsignedBigInteger(short[] digit) {  // The array is in little-endian
		this.digits = trim(digit);
	}
	
	
	
	public UnsignedBigInteger add(UnsignedBigInteger val) {
		short[] x;  // Argument 0 (always equal or longer than argument 1)
		short[] y;  // Argument 1
		short[] z;  // Result
		if (digits.length >= val.digits.length) {
			x = digits;
			y = val.digits;
		} else {
			x = val.digits;
			y = digits;
		}
		if ((x[x.length - 1] & 0x8000) == 0 && (x.length > y.length || (y[y.length - 1] & 0x8000) == 0))
			z = new short[x.length];  // No extra carry possible
		else
			z = new short[x.length + 1];
		int carry = 0;
		int i = 0;
		for (; i < y.length; i++) {
			carry = (x[i] & 0xFFFF) + (y[i] & 0xFFFF) + carry;
			z[i] = (short)carry;
			carry >>>= 16;
		}
		for (; i < x.length; i++) {
			carry = (x[i] & 0xFFFF) + carry;
			z[i] = (short)carry;
			carry >>>= 16;
		}
		if (carry != 0)
			z[i] = (short)carry;
		return new UnsignedBigInteger(z);
	}
	
	
	public UnsignedBigInteger subtract(UnsignedBigInteger val) {
		short[] x = digits;  // Argument 0 (always equal or longer than argument 1)
		short[] y = val.digits;  // Argument 1
		if (x.length < y.length)
			return null;
		short[] digitz = new short[x.length];  // Result
		int carry = 0;  // This is always non-positive.
		int i = 0;
		for (; i < y.length; i++) {
			carry = (x[i] & 0xFFFF) - (y[i] & 0xFFFF) + carry;
			digitz[i] = (short)carry;
			carry >>= 16;  // Note: This is a signed right shift, which is exceedingly rare.
		}
		for (; i < x.length; i++) {
			carry = (x[i] & 0xFFFF) + carry;
			digitz[i] = (short)carry;
			carry >>= 16;
		}
		if (carry != 0)
			return null;
		return new UnsignedBigInteger(digitz);
	}
	
	
	public UnsignedBigInteger multiply(UnsignedBigInteger val) {
		short[] digitx;  // Argument 0 (always equal or longer than argument 1)
		short[] digity;  // Argument 1
		if (digits.length >= val.digits.length) {
			digitx = digits;
			digity = val.digits;
		} else {
			digitx = val.digits;
			digity = digits;
		}
		short[] digitz = new short[digitx.length + digity.length];  // Result
		for (int i = 0; i < digity.length; i++) {
			int y = digity[i] & 0xFFFF;
			int carry = 0;
			for (int j = 0; j < digitx.length; j++) {
				carry = (digitx[j] & 0xFFFF) * y + (digitz[i + j] & 0xFFFF) + carry;
				digitz[i + j] = (short)carry;
				carry >>>= 16;
			}
			digitz[i + digitx.length] = (short)carry;  // At this step, 0 <= carry < 2^16
		}
		return new UnsignedBigInteger(digitz);
	}
	
	
	public UnsignedBigInteger and(UnsignedBigInteger val) {
		short[] x;  // Argument 0 (always equal or longer than argument 1)
		short[] y;  // Argument 1
		if (digits.length >= val.digits.length) {
			x = digits;
			y = val.digits;
		} else {
			x = val.digits;
			y = digits;
		}
		short[] digitz = new short[Math.min(x.length, y.length)];  // Result
		for (int i = 0; i < y.length; i++)
			digitz[i] = (short)(x[i] & y[i]);
		return new UnsignedBigInteger(digitz);
	}
	
	
	public UnsignedBigInteger or(UnsignedBigInteger val) {
		short[] x;  // Argument 0 (always equal or longer than argument 1)
		short[] y;  // Argument 1
		if (digits.length >= val.digits.length) {
			x = digits;
			y = val.digits;
		} else {
			x = val.digits;
			y = digits;
		}
		short[] digitz = new short[Math.max(x.length, y.length)];  // Result
		int i = 0;
		for (; i < y.length; i++)
			digitz[i] = (short)(x[i] | y[i]);
		for (; i < x.length; i++)
			digitz[i] = x[i];
		return new UnsignedBigInteger(digitz);
	}
	
	
	public UnsignedBigInteger xor(UnsignedBigInteger val) {
		short[] x;  // Argument 0 (always equal or longer than argument 1)
		short[] y;  // Argument 1
		if (digits.length >= val.digits.length) {
			x = digits;
			y = val.digits;
		} else {
			x = val.digits;
			y = digits;
		}
		short[] digitz = new short[Math.max(x.length, y.length)];  // Result
		int i = 0;
		for (; i < y.length; i++)
			digitz[i] = (short)(x[i] ^ y[i]);
		for (; i < x.length; i++)
			digitz[i] = x[i];
		return new UnsignedBigInteger(digitz);
	}
	
	
	public UnsignedBigInteger shiftLeft(int shift) {
		if (shift < 0)
			return shiftRight(-shift);
		short[] digitz;
		if (shift % 16 == 0) {
			if (shift == 0)
				return this;
			digitz = new short[digits.length + shift / 16];
			System.arraycopy(digits, 0, digitz, shift / 16, digits.length);
		} else {
			digitz = new short[digits.length + shift / 16 + 1];
			int shift0 = shift % 16;
			int shift1 = 16 - shift % 16;
			for (int i = 0, j = shift / 16; i < digits.length; i++) {
				digitz[j] |= (short)(digits[i] << shift0);
				j++;
				digitz[j] = (short)((digits[i] & 0xFFFF) >>> shift1);
			}
		}
		return new UnsignedBigInteger(digitz);
	}
	
	
	public UnsignedBigInteger shiftRight(int shift) {
		if (shift < 0)
			return shiftLeft(-shift);
		if (shift >= digits.length * 16)
			return ZERO;
		short[] digitz;
		if (shift % 16 == 0) {
			if (shift == 0)
				return this;
			digitz = new short[digits.length - shift / 16];
			System.arraycopy(digits, shift / 16, digitz, 0, digitz.length);
		} else {
			
			digitz = new short[digits.length - shift / 16];
			int shift0 = shift % 16;
			int shift1 = 16 - shift % 16;
			for (int i = digits.length - 1, j = digitz.length - 1;; i--) {
				digitz[j] |= (short)((digits[i] & 0xFFFF) >>> shift0);
				j--;
				if (j < 0)
					break;
				digitz[j] = (short)(digits[i] << shift1);
			}
		}
		return new UnsignedBigInteger(digitz);
	}
	
	
	public UnsignedBigInteger GCD(UnsignedBigInteger val) {
		UnsignedBigInteger x = this;
		UnsignedBigInteger y = val;
		int pow2 = 0;
		while ((x.digits[0] & 1) == 0 && (y.digits[0] & 1) == 0 && (x.digits.length > 1 || x.digits[0] != 0) && (y.digits.length > 1 || y.digits[0] != 0)) {  // x and y are even but non-zero
			pow2++;
			x = x.shiftRight(1);
			y = y.shiftRight(1);
		}
		while (true) {
			if (x.digits.length == 1 && x.digits[0] == 0)  // If x is 0
				return y.shiftLeft(pow2);
			else if (y.digits.length == 1 && y.digits[0] == 0)  // If y is 0
				return x.shiftLeft(pow2);
			else if ((x.digits[0] & 1) == 0)  // x is even
				x = x.shiftRight(1);
			else if ((y.digits[0] & 1) == 0)  // y is even
				y = y.shiftRight(1);
			else if (x.compareTo(y) >= 0)  // x and y are odd, x >= y
				x = x.subtract(y).shiftRight(1);
			else  // x and y are odd, y > x
				y = y.subtract(x).shiftRight(1);
		}
	}
	
	
	@Override
	public boolean equals(Object val) {
		if (super.equals(val))
			return true;
		if (!(val instanceof UnsignedBigInteger))
			return false;
		short[] digity = ((UnsignedBigInteger)val).digits;
		if (digits.length != digity.length)
			return false;
		for (int i = 0; i < digits.length; i++) {
			if (digits[i] != digity[i])
				return false;
		}
		return true;
	}
	
	
	public int compareTo(UnsignedBigInteger val) {
		short[] digity = val.digits;
		if (digits.length != digity.length)
			return digits.length - digity.length;
		for (int i = digits.length - 1;; i--) {
			if (digits[i] != digity[i] || i == 0)
				return (digits[i] & 0xFFFF) - (digity[i] & 0xFFFF);
		}
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("0x");
		sb.append(Integer.toString(digits[digits.length - 1] & 0xFFFF, 16));
		for (int i = digits.length - 2; i >= 0; i--)
			sb.append(hexdigit[(digits[i] >>> 12) & 0xF]).append(hexdigit[(digits[i] >>> 8) & 0xF]).append(hexdigit[(digits[i] >>> 4) & 0xF]).append(hexdigit[digits[i] & 0xF]);
		return sb.toString();
	}
	
	
	private static char[] hexdigit = "0123456789ABCDEF".toCharArray();
	
	
	private static short[] trim(short[] ain) {
		if (ain.length == 0)
			return new short[]{0};
		
		int i;
		for (i = ain.length - 1; i >= 1 && ain[i] == 0; i--);
		
		if (i == ain.length - 1)
			return ain;
		else
			return Arrays.copyOf(ain, i + 1);
	}
	
}