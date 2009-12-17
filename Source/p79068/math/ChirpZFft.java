package p79068.math;


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
	private double[] convre, convim;
	
	
	
	ChirpZFft(int length) {
		if (length < 1)
			throw new IllegalArgumentException("Length must be positive");
		if (length > (1 << 29))
			throw new IllegalArgumentException("Length too large");
		
		this.length = length;
		for (convolutionLength = 1; convolutionLength < length * 2 - 1; convolutionLength *= 2);
		
		cos = new double[length];
		sin = new double[length];
		for (int i = 0; i < length; i++) {
			cos[i] = Math.cos((long)i * i % (length * 2) * Math.PI / length);
			sin[i] = Math.sin((long)i * i % (length * 2) * Math.PI / length);
		}
		
		convre = new double[convolutionLength];
		convim = new double[convolutionLength];
		convre[0] = cos[0] / convolutionLength;
		convim[0] = sin[0] / convolutionLength;
		for (int i = 1; i < length; i++) {
			convre[i] = cos[i] / convolutionLength;
			convim[i] = convim[convolutionLength - i] = sin[i] / convolutionLength;
			convre[convolutionLength - i] = convre[i];
			convim[convolutionLength - i] = convim[i];
		}
		
		fft = new Fft(convolutionLength);
		fft.transform(convre, convim);
	}
	
	
	
	public void transform(double[] inre, double[] inim, double[] outre, double[] outim) {
		double[] tpre = new double[convolutionLength];
		double[] tpim = new double[convolutionLength];
		for (int i = 0; i < length; i++) {
			tpre[i] = inre[i] * cos[i] + inim[i] * sin[i];
			tpim[i] = -inre[i] * sin[i] + inim[i] * cos[i];
		}
		
		fft.transform(tpre, tpim);
		
		for (int i = 0; i < convolutionLength; i++) {
			double re = tpre[i] * convre[i] - tpim[i] * convim[i];
			tpim[i] = tpre[i] * convim[i] + tpim[i] * convre[i];
			tpre[i] = re;
		}
		
		fft.inverseTransform(tpre, tpim);
		
		for (int i = 0; i < length; i++) {
			outre[i] = tpre[i] * cos[i] + tpim[i] * sin[i];
			outim[i] = -tpre[i] * sin[i] + tpim[i] * cos[i];
		}
	}
	
	
	public void transform(double[] re, double[] im) {
		transform(re, im, re, im);
	}
	
}