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


package p79068.hash;

import p79068.lang.BoundsChecker;
import p79068.math.LongBitMath;


final class CrcHasher extends AbstractHasher {
	
	private int degree;
	private boolean reverseInputBits;
	private boolean reverseOutputBits;
	private long xorOutput;
	
	private long[] xorTable;
	
	private long state;  // State of the shift register
	
	
	
	CrcHasher(Crc hashFunc, int degree, long poly, boolean revIn, boolean revOut, long xorIn, long xorOut) {
		super(hashFunc);
		if (degree < 1 || degree > 64)
			throw new IllegalArgumentException("Invalid degree");
		if (degree < 64 && (poly >>> degree) != 1)
			throw new IllegalArgumentException("Illegal polynomial");
		if (degree < 64 && (xorIn >>> degree) != 0)
			throw new IllegalArgumentException("Invalid initial XOR constant");
		if (degree < 64 && (xorOut >>> degree) != 0)
			throw new IllegalArgumentException("Invalid final XOR constant");
		
		this.degree = degree;
		this.reverseInputBits = revIn;
		this.reverseOutputBits = revOut;
		this.xorOutput = xorOut;
		
		// Build look-up table to allow whole-byte CRC processing
		poly <<= 64 - degree;
		xorTable = new long[256];
		
		if (!revIn) {  // Use the left-shift algorithm
			for (int i = 0; i < 256; i++) {
				long reg = (long)i << 56;
				for (int j = 0; j < 8; j++)
					reg = (reg >>> 1) ^ (reg >>> 63) * poly;
				xorTable[i] = reg;
			}
			state = xorIn << (64 - degree);
			
		} else {  // Use the right-shift algorithm
			poly = LongBitMath.reverse(poly);
			for (int i = 0; i < 256; i++) {
				long reg = i;
				for (int j = 0; j < 8; j++)
					reg = (reg >>> 1) ^ (reg & 1) * poly;
				xorTable[i] = reg;
			}
			state = xorIn;
		}
	}
	
	
	
	@Override
	public void update(byte b) {
		if (!reverseInputBits)
			state = (state << 8) ^ xorTable[(int)(state >>> 56) ^ (b & 0xFF)];
		else
			state = (state >>> 8) ^ xorTable[((int)state ^ b) & 0xFF];
	}
	
	
	@Override
	public void update(byte[] b, int off, int len) {
		BoundsChecker.check(b.length, off, len);
		if (!reverseInputBits) {
			for (int i = off, end = off + len; i < end; i++)
				state = (state << 8) ^ xorTable[(int)(state >>> 56) ^ (b[i] & 0xFF)];
		} else {
			for (int i = off, end = off + len; i < end; i++)
				state = (state >>> 8) ^ xorTable[((int)state ^ b[i]) & 0xFF];
		}
	}
	
	
	@Override
	public HashValue getHash() {
		long temp;
		if (!reverseInputBits) temp = state >>> (64 - degree);
		else temp = LongBitMath.reverse(state) >>> (64 - degree);
		temp ^= xorOutput;
		if (reverseOutputBits)
			temp = LongBitMath.reverse(temp) >>> (64 - degree);
		byte[] b = new byte[getHashFunction().getHashLength()];
		for (int i = 0; i < b.length; i++)
			b[b.length - 1 - i] = (byte)(temp >>> (i * 8));
		return new HashValue(b);
	}
	
}
