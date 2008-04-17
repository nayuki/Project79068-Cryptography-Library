package p79068.math;


/**
 * Computes the forward and inverse discrete Fourier transforms (DFT) of a complex vector.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: Via static factory </code>Dft.getInstance()</code></p>
 * 
 * <p>Important note: These algorithms do not perform any scaling on the vectors. Remember: iDFT(DFT(vector)) = vector*vector.length</p>
 * <p>Some useful identities for the inverse transform (x is a complex vector):</p>
 * <ul>
 *  <li>IDFT(x) = conj(DFT(conj(x)), where conj is complex conjugation (a+bi -> a-bi)</li>
 *  <li>IDFT(x) = swap(DFT(swap(x)), where swap is the swapping of the real and imaginary parts (a+bi -> b+ai), swap(x) = i*conj(x)</li>
 * </ul>
 */
public abstract class Dft {
	
	/**
	 * Returns a DFT algorithm for transforming vectors of the specified length.
	 * <p>An algorithm with time complexity O(<var>n</var> log <var>n</var>) is guaranteed to be returned for all possible lengths.</p>
	 */
	public static Dft getInstance(int length) {
		if (length < 1)
			throw new IllegalArgumentException();
		if (IntegerMath.isPowerOf2(length))
			return new Fft(length);
		else
			return new ChirpZFft(length);
	}
	
	
	
	protected Dft() {}
	
	
	
	/**
	 * Computes the forward transform of the specified complex vector, done in-place if possible.
	 */
	public void transform(double[] re, double[] im) {
		double[] outre = new double[re.length];
		double[] outim = new double[im.length];
		transform(re, im, outre, outim);
		for (int i = 0; i < re.length; i++) {
			re[i] = outre[i];
			im[i] = outim[i];
		}
	}
	
	
	/**
	 * Computes the forward transform of the specified complex vector.
	 */
	public abstract void transform(double[] inre, double[] inim, double[] outre, double[] outim);
	
	
	/**
	 * Computes the inverse transform of the specified complex vector, done in-place if possible.
	 */
	public void transformInverse(double[] re, double[] im) {
		transform(im, re);
	}
	
	
	/**
	 * Computes the inverse transform of the specified complex vector.
	 */
	public void transformInverse(double[] inre, double[] inim, double[] outre, double[] outim) {
		transform(inim, inre, outim, outre);
	}
	
}