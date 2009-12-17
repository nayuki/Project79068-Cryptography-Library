package p79068.math;

import p79068.lang.NullChecker;


/**
 * Computes the discrete Fourier transform/inverse transform of a complex vector using Bluestein's/chirp-z algorithm.
 * The asymptotic time complexity is O(n log n). However, it uses more initialization time, execution time, and memory compared to the native radix-2 FFT. Also, it experiences slightly more numerical error, although it is nowhere near as bad as the naive transform.
 * 
 * Multi-thread safe.
 */
final class ChirpZFft extends Dft {
	
	private int length;
	private int convolutionLength;  // The smallest power of 2 such that convlen >= len * 2 - 1 .
	private Dft fft;
	private double[] cos, sin;
	private double[] convreal, convimag;
	
	
	
	ChirpZFft(int length) {
		if (length < 1)
			throw new IllegalArgumentException("Length must be positive");
		if (length > (1 << 29))
			throw new IllegalArgumentException("Length too large");
		
		this.length = length;
		convolutionLength = IntegerMath.ceilingToPowerOf2(length * 2 - 1);
		
		cos = new double[length];
		sin = new double[length];
		for (int i = 0; i < length; i++) {
			cos[i] = Math.cos((long)i * i % (length * 2) * Math.PI / length);
			sin[i] = Math.sin((long)i * i % (length * 2) * Math.PI / length);
		}
		
		convreal = new double[convolutionLength];
		convimag = new double[convolutionLength];
		convreal[0] = cos[0] / convolutionLength;
		convimag[0] = sin[0] / convolutionLength;
		for (int i = 1; i < length; i++) {
			convreal[i] = cos[i] / convolutionLength;
			convimag[i] = convimag[convolutionLength - i] = sin[i] / convolutionLength;
			convreal[convolutionLength - i] = convreal[i];
			convimag[convolutionLength - i] = convimag[i];
		}
		
		fft = new Fft(convolutionLength);
		fft.transform(convreal, convimag);
	}
	
	
	
	public void transform(double[] inreal, double[] inimag, double[] outreal, double[] outimag) {
		NullChecker.check(inreal, inimag, outreal, outimag);
		if (outreal == outimag)
			throw new IllegalArgumentException();
		
		double[] tpre = new double[convolutionLength];
		double[] tpim = new double[convolutionLength];
		for (int i = 0; i < length; i++) {
			tpre[i] =  inreal[i] * cos[i] + inimag[i] * sin[i];
			tpim[i] = -inreal[i] * sin[i] + inimag[i] * cos[i];
		}
		
		fft.transform(tpre, tpim);
		
		for (int i = 0; i < convolutionLength; i++) {
			double re = tpre[i] * convreal[i] - tpim[i] * convimag[i];
			double im = tpre[i] * convimag[i] + tpim[i] * convreal[i];
			tpre[i] = re;
			tpim[i] = im;
		}
		
		fft.inverseTransform(tpre, tpim);
		
		for (int i = 0; i < length; i++) {
			outreal[i] =  tpre[i] * cos[i] + tpim[i] * sin[i];
			outimag[i] = -tpre[i] * sin[i] + tpim[i] * cos[i];
		}
	}
	
	
	public void transform(double[] real, double[] imag) {
		transform(real, imag, real, imag);
	}
	
}