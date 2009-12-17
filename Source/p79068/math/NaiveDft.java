package p79068.math;


/**
 * Computes the discrete Fourier transform/inverse transform of a complex vector using the naive O(n^2) algorithm.
 */
final class NaiveDft extends Dft {
	
	private int length;
	private double[] cos, sin;
	
	
	
	NaiveDft(int len) {
		if (len < 1)
			throw new IllegalArgumentException("Length less than 1");
		length = len;
		cos = new double[len];
		sin = new double[len];
		for (int i = 0; i < len; i++) {
			cos[i] = Math.cos(i * 2 * Math.PI / len);
			sin[i] = Math.sin(i * 2 * Math.PI / len);
		}
	}
	
	
	
	// All arrays must be at least as long as length.
	public void transform(double[] inreal, double[] inimag, double[] outreal, double[] outimag) {
		for (int i = 0; i < length; i++) {
			double sumre = 0;
			double sumim = 0;
			if (length <= 46340) {
				for (int j = 0; j < length; j++) {
					// Caching i*j%length, this.length, or accesses to cos[], sin[], inre[], or inim[] doesn't seem to make execution faster.
					sumre += inreal[j] * cos[i * j % length] + inimag[j] * sin[i * j % length];
					sumim += -inreal[j] * sin[i * j % length] + inimag[j] * cos[i * j % length];
				}
			} else {
				for (int j = 0; j < length; j++) {
					sumre += inreal[j] * cos[(int)((long)i * j % length)] + inimag[j] * sin[(int)((long)i * j % length)];
					sumim += -inreal[j] * sin[(int)((long)i * j % length)] + inimag[j] * cos[(int)((long)i * j % length)];
				}
			}
			outreal[i] = sumre;
			outimag[i] = sumim;
		}
	}
	
}