/*
 * Computes the discrete Fourier transform/inverse transform of a complex vector using the naive O(n^2) algorithm.
 */


package p79068.math;


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
	public void transform(double[] inre, double[] inim, double[] outre, double[] outim) {
		for (int i = 0; i < length; i++) {
			double sumre = 0;
			double sumim = 0;
			if (length <= 46340) {
				for (int j = 0; j < length; j++) {
					// Caching i*j%length, this.length, or accesses to cos[], sin[], inre[], or inim[] doesn't seem to make execution faster.
					sumre += inre[j] * cos[i * j % length] + inim[j] * sin[i * j % length];
					sumim += -inre[j] * sin[i * j % length] + inim[j] * cos[i * j % length];
				}
			} else {
				for (int j = 0; j < length; j++) {
					sumre += inre[j] * cos[(int)((long)i * j % length)] + inim[j] * sin[(int)((long)i * j % length)];
					sumim += -inre[j] * sin[(int)((long)i * j % length)] + inim[j] * cos[(int)((long)i * j % length)];
				}
			}
			outre[i] = sumre;
			outim[i] = sumim;
		}
	}
	
}