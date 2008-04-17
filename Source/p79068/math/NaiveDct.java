/*
 * Computes the DCT by using the naive direct algorithm.
 */


package p79068.math;


final class NaiveDct extends Dct {
	
	private int length;
	private double[] cos;
	
	
	
	NaiveDct(int len) {
		if (len < 1)
			throw new IllegalArgumentException("Length less than 1");
		length = len;
		cos = new double[length * 4];
		for (int i = 0; i < length * 4; i++)
			cos[i] = Math.cos(i * Math.PI / (length * 2));
	}
	
	
	
	public void transform(double[] in, double[] out) {
		for (int i = 0; i < length; i++) {
			double sum = 0;
			for (int j = 0; j < length; j++)
				sum += in[j] * cos[i * (2 * j + 1) % (length * 4)];
			out[i] = sum * 2;
		}
		out[0] /= 2;
	}
	
	
	public void transformInverse(double[] in, double[] out) {
		for (int i = 0; i < length; i++) {
			double sum = 0;
			for (int j = 0; j < length; j++)
				sum += in[j] * cos[(2 * i + 1) * j % (length * 4)];
			out[i] = sum;
		}
	}
	
}