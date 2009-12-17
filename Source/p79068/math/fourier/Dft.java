package p79068.math.fourier;

import p79068.math.IntegerMath;


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
			return Fft.getInstance(length);
		else
			return ChirpZFft.getInstance(length);
	}
	
	
	
	protected Dft() {}
	
	
	
	/**
	 * Computes the forward transform of the specified complex vector, done in-place if possible.
	 */
	public void transform(double[] real, double[] imag) {
		double[] outre = new double[real.length];
		double[] outim = new double[imag.length];
		transform(real, imag, outre, outim);
		for (int i = 0; i < real.length; i++) {
			real[i] = outre[i];
			imag[i] = outim[i];
		}
	}
	
	
	/**
	 * Computes the forward transform of the specified complex vector.
	 */
	public abstract void transform(double[] inreal, double[] inimag, double[] outreal, double[] outimag);
	
	
	/**
	 * Computes the inverse transform of the specified complex vector, done in-place if possible.
	 */
	public void inverseTransform(double[] real, double[] imag) {
		transform(imag, real);
	}
	
	
	/**
	 * Computes the inverse transform of the specified complex vector.
	 */
	public void inverseTransform(double[] inreal, double[] inimag, double[] outreal, double[] outimag) {
		transform(inimag, inreal, outimag, outreal);
	}
	
}