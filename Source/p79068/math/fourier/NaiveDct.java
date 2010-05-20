package p79068.math.fourier;

import p79068.lang.NullChecker;
import p79068.util.KeyedIntener;


/**
 * Computes the DCT by using the naive direct algorithm.
 */
public final class NaiveDct extends Dct {
	
	private static KeyedIntener<Integer,NaiveDct> cache = new KeyedIntener<Integer,NaiveDct>();
	
	
	public static NaiveDct getInstance(int length) {
		if (length <= 0)
			throw new IllegalArgumentException();
		
		NaiveDct dct = cache.get(length);
		if (dct == null) {
			dct = new NaiveDct(length);
			cache.put(length, dct);
		}
		return dct;
	}
	
	
	
	private int length;
	private double[] cos;
	
	
	
	private NaiveDct(int len) {
		if (len < 1)
			throw new IllegalArgumentException("Length less than 1");
		length = len;
		cos = new double[length * 4];
		for (int i = 0; i < length * 4; i++)
			cos[i] = Math.cos(i * Math.PI / (length * 2));
	}
	
	
	
	@Override
	public void transform(double[] in, double[] out) {
		NullChecker.check(in);
		NullChecker.check(out);
		if (in.length != length || out.length != length)
			throw new IllegalArgumentException();
		if (in == out)
			throw new IllegalArgumentException();
		
		for (int i = 0; i < length; i++) {
			double sum = 0;
			for (int j = 0; j < length; j++)
				sum += in[j] * cos[i * (2 * j + 1) % (length * 4)];
			out[i] = sum * 2;
		}
		out[0] /= 2;
	}
	
	
	@Override
	public void inverseTransform(double[] in, double[] out) {
		NullChecker.check(in);
		NullChecker.check(out);
		if (in.length != length || out.length != length)
			throw new IllegalArgumentException();
		if (in == out)
			throw new IllegalArgumentException();
		
		for (int i = 0; i < length; i++) {
			double sum = 0;
			for (int j = 0; j < length; j++)
				sum += in[j] * cos[(2 * i + 1) * j % (length * 4)];
			out[i] = sum;
		}
	}
	
}