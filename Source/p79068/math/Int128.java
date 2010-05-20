package p79068.math;

import java.math.BigInteger;
import p79068.util.HashCoder;


/**
 * Represents a signed 128-bit integer. It is useful for providing headroom for calculations involving arguments that can span the entire int64 range.
 */
public final class Int128 implements Comparable<Int128> {
	
	/**
	 * The number zero, which has high = 0 and low = 0.
	 */
	public static final Int128 ZERO = new Int128(0);
	
	
	/**
	 * The number one, which has high = 0 and low = 1.
	 */
	public static final Int128 ONE = new Int128(1);
	
	
	
	/**
	 * The most significant 64 bits of this integer.
	 */
	public final long high;
	
	/**
	 * The least significant 64 bits of this integer.
	 */
	public final long low;
	
	
	
	/**
	 * Constructs an int128 representing the specified number, performing sign extension.
	 */
	public Int128(long low) {
		this(low >> 63, low);
	}
	
	
	/**
	 * Constructs an int128 representing the specified number.
	 */
	public Int128(long high, long low) {
		this.high = high;
		this.low = low;
	}
	
	
	
	/**
	 * Returns <code>this + num</code>.
	 */
	public Int128 add(Int128 num) {
		long newlow = low + num.low;
		long newhigh = high + num.high;
		if (LongMath.compareUnsigned(newlow, low) < 0)
			newhigh++;  // Carry
		return new Int128(newhigh, newlow);
	}
	
	
	/**
	 * Returns <code>this - num</code>.
	 */
	public Int128 subtract(Int128 num) {
		long newlow = low - num.low;
		long newhigh = high - num.high;
		if (LongMath.compareUnsigned(newlow, low) > 0)
			newhigh--;  // Borrow
		return new Int128(newhigh, newlow);
	}
	
	
	/**
	 * Returns <code>this * num</code>.
	 */
	public Int128 multiply(Int128 num) {
		int[] x = {(int)low, (int)(low >>> 32), (int)high, (int)(high >>> 32)};  // All in little-endian
		int[] y = {(int)num.low, (int)(num.low >>> 32), (int)num.high, (int)(num.high >>> 32)};
		int[] z = new int[4];
		for (int i = 0; i < x.length; i++) {
			long carry = 0;
			for (int j = 0; j < y.length && i + j < z.length; j++) {
				long temp = (x[i] & 0xFFFFFFFFL) * (y[j] & 0xFFFFFFFFL);  // In [0,0xFFFFFFFE00000001]
				temp += carry;  // In [0,0xFFFFFFFF00000000]
				temp += (z[i + j] & 0xFFFFFFFFL);  // In [0,0xFFFFFFFFFFFFFFFF] (still not overflowing =) )
				z[i + j] = (int)temp;
				carry = temp >>> 32;  // In [0,0xFFFFFFFF]
			}
		}
		return new Int128((long)z[3] << 32 | (z[2] & 0xFFFFFFFFL), (long)z[1] << 32 | (z[0] & 0xFFFFFFFFL));
	}
	
	
	/**
	 * Returns <code>this / num</code>.
	 */
	public Int128 divide(Int128 num) {
		if (num.equals(ZERO))
			throw new ArithmeticException("Division by zero");
		
		boolean neg = this.compareTo(ZERO) < 0 ^ num.compareTo(ZERO) < 0;
		Int128 x = this;
		Int128 y = num;
		if (x.compareTo(ZERO) > 0)  // Make x non-positive
			x = x.negate();
		if (y.compareTo(ZERO) > 0)  // Make y non-positive
			y = y.negate();
		
		int i = 0;
		while (y.compareTo(x) > 0 && y.shiftLeft(1).shiftRight(1).equals(y)) {
			i++;
			y = y.shiftLeft(1);
		}
		
		Int128 MINUS_ONE = new Int128(-1);
		Int128 z = ZERO;
		for (; i >= 0; i--) {
			if (y.compareTo(x) >= 0) {
				x = x.subtract(y);
				z = z.add(MINUS_ONE.shiftLeft(i));
			}
			y = y.shiftRight(1);
		}
		
		if (neg)
			return z;
		else
			return z.negate();
	}
	
	
	/**
	 * Returns <code>this % num</code>.
	 */
	public Int128 remainder(Int128 num) {
		if (num.equals(ZERO))
			throw new ArithmeticException("Division by zero");
		
		boolean neg = this.compareTo(ZERO) < 0;
		Int128 x = this;
		Int128 y = num;
		if (x.compareTo(ZERO) > 0)  // Make x non-positive
			x = x.negate();
		if (y.compareTo(ZERO) > 0)  // Make y non-positive
			y = y.negate();
		
		int i = 0;
		while (y.compareTo(x) > 0 && y.shiftLeft(1).shiftRight(1).equals(y)) {
			i++;
			y = y.shiftLeft(1);
		}
		
		for (; i >= 0; i--) {
			if (y.compareTo(x) >= 0)
				x = x.subtract(y);
			y = y.shiftRight(1);
		}
		
		if (neg)
			return x;
		else
			return x.negate();
	}
	
	
	/**
	 * Returns <code>-this</code>.
	 */
	public Int128 negate() {
		if (low == 0)
			return new Int128(-high, 0);
		else
			return new Int128(-high - 1, -low);
	}
	
	
	/**
	 * Returns <code>~this</code>.
	 */
	public Int128 not() {
		return new Int128(~high, ~low);
	}
	
	
	/**
	 * Returns <code>this & num</code>.
	 */
	public Int128 and(Int128 num) {
		return new Int128(high & num.high, low & num.low);
	}
	
	
	/**
	 * Returns <code>this | num</code>.
	 */
	public Int128 or(Int128 num) {
		return new Int128(high | num.high, low | num.low);
	}
	
	
	/**
	 * Returns <code>this ^ num</code>.
	 */
	public Int128 xor(Int128 num) {
		return new Int128(high ^ num.high, low ^ num.low);
	}
	
	
	/**
	 * Returns <code>this << shift</code>. <code>shift</code> is interpreted as its value modulo 128.
	 */
	public Int128 shiftLeft(int shift) {
		shift &= 0x7F;
		if (shift == 0)
			return this;
		else if (shift < 64)
			return new Int128(high << shift | low >>> (64 - shift), low << shift);
		else
			return new Int128(low << (shift - 64), 0);
	}
	
	
	/**
	 * Returns <code>this >> shift</code>. <code>shift</code> is interpreted as its value modulo 128.
	 */
	public Int128 shiftRight(int shift) {
		shift &= 0x7F;
		if (shift == 0)
			return this;
		else if (shift < 64)
			return new Int128(high >> shift, high << (64 - shift) | low >>> shift);
		else {
			if (high >= 0)
				return new Int128(0L, high >> (shift - 64));
			else
				return new Int128(-1L, high >> (shift - 64));
		}
	}
	
	
	/**
	 * Returns <code>this >>> shift</code>. <code>shift</code> is interpreted as its value modulo 128.
	 */
	public Int128 shiftRightUnsigned(int shift) {
		shift &= 0x7F;
		if (shift == 0)
			return this;
		else if (shift < 64)
			return new Int128(high >>> shift, high << (64 - shift) | low >>> shift);
		else
			return new Int128(0, high >>> (shift - 64));
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Int128))
			return false;
		Int128 num = (Int128)obj;
		return high == num.high && low == num.low;
	}
	
	
	public int compareTo(Int128 num) {
		if (high != num.high)
			return LongMath.compare(high, num.high);
		else
			return LongMath.compareUnsigned(low, num.low);
	}
	
	
	@Override
	public int hashCode() {
		HashCoder hc = HashCoder.newInstance();
		hc.add(high);
		hc.add(low);
		return hc.getHashCode();
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		Int128 temp = this;
		int comp = temp.compareTo(ZERO);
		if (comp == 0)
			return "0";
		
		boolean neg = comp < 0;
		if (comp > 0)
			temp = temp.negate();
		
		Int128 BASE = new Int128(10);
		while (!temp.equals(ZERO)) {
			int digit = (int)temp.remainder(BASE).low;
			sb.append((char)('0' - digit));
			temp = temp.divide(BASE);
		}
		
		if (neg)
			sb.append("-");
		
		return sb.reverse().toString();
	}
	
}