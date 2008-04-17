/*
 * A parameterized cyclic redundancy check (CRC) algorithm.
 * degree: The degree of the polynomial, from 1 to 64 (inclusive).
 * poly: The polynomial, whose coefficients are expressed as bits. If there is an x^64 term, it is omitted. e.g. x^16 + x^12 + x^5 + x^0 <-> 0x11021
 * revin: Normally, the more significant bits of a byte take the monomial terms with higher powers. This reverses the interpretation. Internally, this causes the right-shift algorithm to be used.
 * revout: Take the bitwise reverse of the remainder. This is just associated with the low-level implementation of the right-shift-based algorithm.
 * xorin: The initial n bits are XORed with this constant. Only the lower n bits are allowed to be non-zero, where n is the degree.
 * xorout: The remainder is XORed with this constant. Only the lower n bits are allowed to be non-zero, where n is the degree.
 *
 * Order of operations:
 * 0) Apply bit reversal to bytes
 * 1) XOR the initial bits with a constant
 * 2) Take remainder
 * 3) XOR remainder with a constant
 * 4) Apply bit reversal to remainder
 *
 * This parameterized algorithm was adapted from the "Rocksoft^tm Model CRC Algorithm" to correspond more closely to pure polynomial division.
 */


package p79068.util.hash;

import p79068.math.LongBitMath;


final class CrcHasher extends Hasher {
	
	private int degree;
	private boolean revin;
	private boolean revout;
	private long xorout;
	
	private long[] xortable;
	
	private long register;
	
	
	CrcHasher(Crc algor, int degree, long poly, boolean revin, boolean revout, long xorin, long xorout) {
		super(algor);
		if (degree < 1 || degree > 64)
			throw new IllegalArgumentException("Invalid degree");
		if (degree < 64 && (poly >>> degree) != 1)
			throw new IllegalArgumentException("Illegal polynomial");
		if (degree < 64 && (xorin >>> degree) != 0)
			throw new IllegalArgumentException("Invalid initial XOR constant");
		if (degree < 64 && (xorout >>> degree) != 0)
			throw new IllegalArgumentException("Invalid final XOR constant");
		this.degree = degree;
		this.revin = revin;
		this.revout = revout;
		this.xorout = xorout;
		poly <<= 64 - degree;
		xortable = new long[256];
		if (!revin) { // Use the left-shift algorithm
			for (int i = 0; i < 256; i++) {
				long reg = (long)i << 56;
				for (int j = 0; j < 8; j++) {
					if ((reg >>> 63) != 0)
						reg = (reg << 1) ^ poly;
					else
						reg <<= 1;
				}
				xortable[i] = reg;
			}
			register = xorin << (64 - degree);
		} else { // Use the right-shift algorithm
			poly = LongBitMath.reverse(poly);
			for (int i = 0; i < 256; i++) {
				long reg = i;
				for (int j = 0; j < 8; j++) {
					if ((reg & 1) != 0)
						reg = (reg >>> 1) ^ poly;
					else
						reg >>>= 1;
				}
				xortable[i] = reg;
			}
			register = xorin;
		}
	}
	
	
	public void update(byte b) {
		if (!revin)
			register = (register << 8) ^ xortable[(int)(register >>> 56) ^ (b & 0xFF)];
		else
			register = (register >>> 8) ^ xortable[((int)register ^ b) & 0xFF];
	}
	
	public void update(byte[] b, int off, int len) {
		if (!revin) {
			for (len += off; off < len; off++)
				register = (register << 8) ^ xortable[(int)(register >>> 56) ^ (b[off] & 0xFF)];
		} else {
			for (len += off; off < len; off++)
				register = (register >>> 8) ^ xortable[((int)register ^ b[off]) & 0xFF];
		}
	}
	
	public HashValue getHash() {
		long tp;
		if (!revin)
			tp = register >>> (64 - degree);
		else
			tp = LongBitMath.reverse(register) >>> (64 - degree);
		tp ^= xorout;
		if (revout)
			tp = LongBitMath.reverse(tp) >>> (64 - degree);
		byte[] b = new byte[getHashFunction().getHashLength()];
		for (int i = 0; i < b.length; i++)
			b[b.length - 1 - i] = (byte)(tp >>> (i * 8));
		return createHash(b);
	}
}