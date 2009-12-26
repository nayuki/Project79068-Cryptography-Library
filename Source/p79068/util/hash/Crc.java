package p79068.util.hash;

import p79068.util.HashCoder;


/**
 * A family of CRC hash functions.
 * @see HashFunction
 */
public class Crc extends HashFunction {
	
	/**
	 * An instance of the common CRC-32 hash function.
	 * <p>Its parameters:</p>
	 * <ul>
	 *  <li><code>name = "CRC-32"</li>
	 *  <li><code>degree = 32</li>
	 *  <li><code>polynomial = 0x104C11DB7L</code> (<var>x</var><sup>32</sup> + <var>x</var><sup>26</sup> + <var>x</var><sup>23</sup> + <var>x</var><sup>22</sup> + <var>x</var><sup>16</sup> + <var>x</var><sup>12</sup> + <var>x</var><sup>11</sup> + <var>x</var><sup>10</sup> + <var>x</var><sup>8</sup> + <var>x</var><sup>7</sup> + <var>x</var><sup>5</sup> + <var>x</var><sup>4</sup> + <var>x</var><sup>2</sup> + <var>x</var><sup>1</sup> + <var>x</var><sup>0</sup>)</li>
	 *  <li><code>reversein = true</code></li>
	 *  <li><code>reverseout = true</code></li>
	 *  <li><code>xorin = 0xFFFFFFFFL</code></li>
	 *  <li><code>xorout = 0xFFFFFFFFL</code></li>
	 * </ul>
	 */
	public static final Crc CRC32_FUNCTION = new Crc("CRC-32", 32, 0x104C11DB7L, true, true, 0xFFFFFFFFL, 0xFFFFFFFFL);
	
	
	private String name;
	private int degree;
	private long polynomial;
	private boolean reverseInputBits;
	private boolean reverseOutputBits;
	private long xorInput;
	private long xorOutput;
	
	
	
	/**
	 * Constructs a CRC hash function with the specified parameters.
	 *
	 * @param name the name of the hash function
	 * @param deg the degree of the polynomial used
	 * @param poly the polynomial
	 * @param revin whether the bits in each input byte will be reversed
	 * @param revout whether the bits in the output hash value will be reversed
	 * @param xorin the value to XOR with the first degree bits of the input (after input reversal)
	 * @param xorout the value to XOR with the output hash value (before output reversal)
	 */
	public Crc(String name, int deg, long poly, boolean revin, boolean revout, long xorin, long xorout) {
		if (name == null)
			throw new IllegalArgumentException();
		if (deg < 1 || deg > 64)
			throw new IllegalArgumentException("Invalid degree");
		if (deg < 64) {
			if ((poly >>> deg) != 1)
				throw new IllegalArgumentException("Illegal polynomial");
			if ((xorin >>> deg) != 0)
				throw new IllegalArgumentException("Invalid initial XOR constant");
			if ((xorout >>> deg) != 0)
				throw new IllegalArgumentException("Invalid final XOR constant");
		}
		this.name = name;
		this.degree = deg;
		this.polynomial = poly;
		this.reverseInputBits = revin;
		this.reverseOutputBits = revout;
		this.xorInput = xorin;
		this.xorOutput = xorout;
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		if (equals(CRC32_FUNCTION))
			return new Crc32Hasher(this);
		return new CrcHasher(this, degree, polynomial, reverseInputBits, reverseOutputBits, xorInput, xorOutput);
	}
	
	
	/**
	 * Returns the name of this hash function, which is specified when it is constructed.
	 */
	@Override
	public String getName() {
		return name;
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>ceiling(degree/8)</code> bytes.
	 */
	@Override
	public int getHashLength() {
		return (degree + 7) / 8;
	}
	
	
	/**
	 * Tests for equality to the specified hash function.
	 * @param obj the hash function to be compared for equality with
	 * @return <code>true</code> if the specified hash function has the same parameters as this one
	 */
	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;
		if (!(obj instanceof Crc))
			return false;
		Crc hashfunc = (Crc)obj;
		return name.equals(hashfunc.name) && degree == hashfunc.degree && polynomial == hashfunc.polynomial && reverseInputBits == hashfunc.reverseInputBits && reverseOutputBits == hashfunc.reverseOutputBits && xorInput == hashfunc.xorInput && xorOutput == hashfunc.xorOutput;
	}
	
	
	@Override
	public int hashCode() {
		HashCoder h = HashCoder.newInstance();
		h.add(name);
		h.add(degree);
		h.add(polynomial);
		h.add(reverseInputBits);
		h.add(reverseOutputBits);
		h.add(xorInput);
		h.add(xorOutput);
		return h.getHashCode();
	}
	
}