package p79068.math;

import java.math.*;
import p79068.util.Random;


public final class UnsignedBigInteger implements Comparable<UnsignedBigInteger> {
	
	public static void main(String[] arg) {
		int k = 0;
		java.util.Random r = new java.util.Random();
		while (true) {
			int a = r.nextInt(256);
			int b = r.nextInt(256);
			
			if (!new UnsignedBigInteger(a).GCD(new UnsignedBigInteger(b)).toString().equals("0x" + BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).toString(16)

			))
				System.out.println(a + " " + b);
			k++;
			if (k % 10000 == 0)
				System.out.print("\r" + k);
			if (false)
				break;
		}
		
		if (true)
			return;
		
		int tp = r.nextInt();
		UnsignedBigInteger a = new UnsignedBigInteger(tp);
		BigInteger b = BigInteger.valueOf(tp & 0xFFFFFFFFL);
		while (true) {
			tp = r.nextInt();
			UnsignedBigInteger c = new UnsignedBigInteger(tp);
			BigInteger d = BigInteger.valueOf(tp & 0xFFFFFFFFL);
			tp = r.nextInt(421);
			if (tp < 100) {
				a = a.add(c);
				b = b.add(d);
			} else if (tp < 200) {
				a = a.multiply(c);
				b = b.multiply(d);
			} else if (tp < 300) {
				a = a.or(c);
				b = b.or(d);
			} else if (tp < 350) {
				a = a.xor(c);
				b = b.xor(d);
			} else if (tp < 400) {
				if (a.subtract(c) != null) {
					a = a.subtract(c);
					b = b.subtract(d);
				}
			} else if (tp < 410) {
				int tp1 = r.nextInt(64);
				a = a.shiftLeft(tp1);
				b = b.shiftLeft(tp1);
			} else if (tp < 420) {
				int tp1 = r.nextInt(64);
				a = a.shiftRight(tp1);
				b = b.shiftRight(tp1);
			} else if (tp < 421) {
				a = a.and(c);
				b = b.and(d);
			} else
				throw new RuntimeException();
			if (!a.toString().equals("0x" + b.toString(16))) {
				System.out.println(a);
				System.out.println("0x" + b.toString(16));
				break;
			}
			System.out.print("\r" + a.digit.length);
		}
	}
	
	
	public static final UnsignedBigInteger ZERO = new UnsignedBigInteger(0);
	
	public static final UnsignedBigInteger ONE = new UnsignedBigInteger(1);
	
	
	protected short[] digit;  // In little-endian. length >= 1. The last element is not zero unless it violates the length property.
	
	
	
	public UnsignedBigInteger(int value) {
		digit = trim(new short[]{(short)value, (short)(value >>> 16)});
	}
	
	
	public UnsignedBigInteger(long value) {
		digit = trim(new short[]{(short)value, (short)(value >>> 16), (short)(value >>> 32), (short)(value >>> 48)});
	}
	
	
	public UnsignedBigInteger(byte[] value) {  // The array is in big-endian
		digit = new short[(value.length + 1) / 2];
		int i = value.length - 1;
		int j = 0;
		for (; i >= 1; i -= 2, j++)
			digit[j] = (short)(value[i - 1] << 8 | value[i] & 0xFF);
		if (i == 0)
			digit[j] = (short)(value[i] & 0xFF);
		digit = trim(digit);
	}
	
	
	public UnsignedBigInteger(Random rand, int bitlen) {
		digit = new short[(bitlen + 15) / 16];
		for (int i = 0; i + 1 < digit.length; i += 2) {  // Fill in blocks of 2
			int tp = rand.randomInt();
			digit[i] = (short)(tp >>> 0);
			digit[i | 1] = (short)(tp >>> 16);
		}
		digit[digit.length - 1] = (short)(rand.randomInt() >>> (32 - bitlen % 16));  // Fill the last element
		digit = trim(digit);
	}
	
	
	private UnsignedBigInteger(short[] digit) {  // The array is in little-endian
		this.digit = trim(digit);
	}
	
	
	
	public UnsignedBigInteger add(UnsignedBigInteger val) {
		short[] digitx;  // Argument 0 (always equal or longer than argument 1)
		short[] digity;  // Argument 1
		short[] digitz;  // Result
		if (digit.length >= val.digit.length) {
			digitx = digit;
			digity = val.digit;
		} else {
			digitx = val.digit;
			digity = digit;
		}
		if ((digitx[digitx.length - 1] & 0x8000) == 0 && (digitx.length > digity.length || (digity[digity.length - 1] & 0x8000) == 0))
			digitz = new short[digitx.length];  // No extra carry possible
		else
			digitz = new short[digitx.length + 1];
		int carry = 0;
		int i = 0;
		for (; i < digity.length; i++) {
			carry = (digitx[i] & 0xFFFF) + (digity[i] & 0xFFFF) + carry;
			digitz[i] = (short)carry;
			carry >>>= 16;
		}
		for (; i < digitx.length; i++) {
			carry = (digitx[i] & 0xFFFF) + carry;
			digitz[i] = (short)carry;
			carry >>>= 16;
		}
		if (carry != 0)
			digitz[i] = (short)carry;
		return new UnsignedBigInteger(digitz);
	}
	
	
	public UnsignedBigInteger subtract(UnsignedBigInteger val) {
		short[] digitx = digit;  // Argument 0 (always equal or longer than argument 1)
		short[] digity = val.digit;  // Argument 1
		if (digitx.length < digity.length)
			return null;
		short[] digitz = new short[digitx.length];  // Result
		int carry = 0;  // This is always non-positive.
		int i = 0;
		for (; i < digity.length; i++) {
			carry = (digitx[i] & 0xFFFF) - (digity[i] & 0xFFFF) + carry;
			digitz[i] = (short)carry;
			carry >>= 16;  // Note: This is a signed right shift, which is exceedingly rare.
		}
		for (; i < digitx.length; i++) {
			carry = (digitx[i] & 0xFFFF) + carry;
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
		if (digit.length >= val.digit.length) {
			digitx = digit;
			digity = val.digit;
		} else {
			digitx = val.digit;
			digity = digit;
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
		short[] digitx;  // Argument 0 (always equal or longer than argument 1)
		short[] digity;  // Argument 1
		if (digit.length >= val.digit.length) {
			digitx = digit;
			digity = val.digit;
		} else {
			digitx = val.digit;
			digity = digit;
		}
		short[] digitz = new short[Math.min(digitx.length, digity.length)];  // Result
		for (int i = 0; i < digity.length; i++)
			digitz[i] = (short)(digitx[i] & digity[i]);
		return new UnsignedBigInteger(digitz);
	}
	
	
	public UnsignedBigInteger or(UnsignedBigInteger val) {
		short[] digitx;  // Argument 0 (always equal or longer than argument 1)
		short[] digity;  // Argument 1
		if (digit.length >= val.digit.length) {
			digitx = digit;
			digity = val.digit;
		} else {
			digitx = val.digit;
			digity = digit;
		}
		short[] digitz = new short[Math.max(digitx.length, digity.length)];  // Result
		int i = 0;
		for (; i < digity.length; i++)
			digitz[i] = (short)(digitx[i] | digity[i]);
		for (; i < digitx.length; i++)
			digitz[i] = digitx[i];
		return new UnsignedBigInteger(digitz);
	}
	
	
	public UnsignedBigInteger xor(UnsignedBigInteger val) {
		short[] digitx;  // Argument 0 (always equal or longer than argument 1)
		short[] digity;  // Argument 1
		if (digit.length >= val.digit.length) {
			digitx = digit;
			digity = val.digit;
		} else {
			digitx = val.digit;
			digity = digit;
		}
		short[] digitz = new short[Math.max(digitx.length, digity.length)];  // Result
		int i = 0;
		for (; i < digity.length; i++)
			digitz[i] = (short)(digitx[i] ^ digity[i]);
		for (; i < digitx.length; i++)
			digitz[i] = digitx[i];
		return new UnsignedBigInteger(digitz);
	}
	
	
	public UnsignedBigInteger shiftLeft(int shift) {
		if (shift < 0)
			return shiftRight(-shift);
		short[] digitz;
		if (shift % 16 == 0) {
			if (shift == 0)
				return this;
			digitz = new short[digit.length + shift / 16];
			System.arraycopy(digit, 0, digitz, shift / 16, digit.length);
		} else {
			digitz = new short[digit.length + shift / 16 + 1];
			int shift0 = shift % 16;
			int shift1 = 16 - shift % 16;
			for (int i = 0, j = shift / 16; i < digit.length; i++) {
				digitz[j] |= (short)(digit[i] << shift0);
				j++;
				digitz[j] = (short)((digit[i] & 0xFFFF) >>> shift1);
			}
		}
		return new UnsignedBigInteger(digitz);
	}
	
	
	public UnsignedBigInteger shiftRight(int shift) {
		if (shift < 0)
			return shiftLeft(-shift);
		if (shift >= digit.length * 16)
			return ZERO;
		short[] digitz;
		if (shift % 16 == 0) {
			if (shift == 0)
				return this;
			digitz = new short[digit.length - shift / 16];
			System.arraycopy(digit, shift / 16, digitz, 0, digitz.length);
		} else {
			
			digitz = new short[digit.length - shift / 16];
			int shift0 = shift % 16;
			int shift1 = 16 - shift % 16;
			for (int i = digit.length - 1, j = digitz.length - 1;; i--) {
				digitz[j] |= (short)((digit[i] & 0xFFFF) >>> shift0);
				j--;
				if (j < 0)
					break;
				digitz[j] = (short)(digit[i] << shift1);
			}
		}
		return new UnsignedBigInteger(digitz);
	}
	
	
	public UnsignedBigInteger GCD(UnsignedBigInteger val) {
		UnsignedBigInteger x = this;
		UnsignedBigInteger y = val;
		int pow2 = 0;
		while ((x.digit[0] & 1) == 0 && (y.digit[0] & 1) == 0 && (x.digit.length > 1 || x.digit[0] != 0) && (y.digit.length > 1 || y.digit[0] != 0)) {  // x and y are even but non-zero
			pow2++;
			x = x.shiftRight(1);
			y = y.shiftRight(1);
		}
		while (true) {
			if (x.digit.length == 1 && x.digit[0] == 0)  // If x is 0
				return y.shiftLeft(pow2);
			else if (y.digit.length == 1 && y.digit[0] == 0)  // If y is 0
				return x.shiftLeft(pow2);
			else if ((x.digit[0] & 1) == 0)  // x is even
				x = x.shiftRight(1);
			else if ((y.digit[0] & 1) == 0)  // y is even
				y = y.shiftRight(1);
			else if (x.compareTo(y) >= 0)  // x and y are odd, x >= y
				x = x.subtract(y).shiftRight(1);
			else  // x and y are odd, y > x
				y = y.subtract(x).shiftRight(1);
		}
	}
	

	public boolean equals(Object val) {
		if (super.equals(val))
			return true;
		if (!(val instanceof UnsignedBigInteger))
			return false;
		short[] digity = ((UnsignedBigInteger)val).digit;
		if (digit.length != digity.length)
			return false;
		for (int i = 0; i < digit.length; i++) {
			if (digit[i] != digity[i])
				return false;
		}
		return true;
	}
	
	
	public int compareTo(UnsignedBigInteger val) {
		short[] digity = val.digit;
		if (digit.length != digity.length)
			return digit.length - digity.length;
		for (int i = digit.length - 1;; i--) {
			if (digit[i] != digity[i] || i == 0)
				return (digit[i] & 0xFFFF) - (digity[i] & 0xFFFF);
		}
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer("0x");
		sb.append(Integer.toString(digit[digit.length - 1] & 0xFFFF, 16));
		for (int i = digit.length - 2; i >= 0; i--)
			sb.append(hexdigit[(digit[i] >>> 12) & 0xF]).append(hexdigit[(digit[i] >>> 8) & 0xF]).append(hexdigit[(digit[i] >>> 4) & 0xF]).append(hexdigit[digit[i] & 0xF]);
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
		short[] aout = new short[i + 1];
		System.arraycopy(ain, 0, aout, 0, aout.length);
		return aout;
	}
	
}