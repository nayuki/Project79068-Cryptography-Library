package p79068.math.fourier;

import p79068.lang.NullChecker;
import p79068.util.KeyedIntener;


/**
 * Computes the DCT by hijacking an FFT algorithm.
 */
public final class FastDct extends Dct {
	
	private static KeyedIntener<Integer,FastDct> cache = new KeyedIntener<Integer,FastDct>();
	
	
	public static FastDct getInstance(int length) {
		if (length <= 0)
			throw new IllegalArgumentException();
		
		FastDct dct = cache.get(length);
		if (dct == null) {
			dct = new FastDct(length);
			cache.put(length, dct);
		}
		return dct;
	}
	
	
	
	private int length;
	private Dft fft;
	private double[] cos, sin;
	
	
	
	private FastDct(int len) {
		if (len < 1)
			throw new IllegalArgumentException("Length less than 1");
		length = len;
		fft = Dft.getInstance(length * 2);
		cos = new double[length];
		sin = new double[length];
		for (int i = 0; i < len; i++) {
			cos[i] = Math.cos(i * Math.PI / (length * 2));
			sin[i] = Math.sin(i * Math.PI / (length * 2));
		}
	}
	
	
	
	@Override
	public void transform(double[] in, double[] out) {
		NullChecker.check(in);
		NullChecker.check(out);
		if (in.length != length || out.length != length)
			throw new IllegalArgumentException();
		
		double[] tpre = new double[length * 2];
		double[] tpim = new double[length * 2];
		for (int i = 0; i < length; i++)
			tpre[i] = in[i];
		fft.transform(tpre, tpim);
		out[0] = tpre[0];
		for (int i = 1; i < length; i++)
			out[i] = (tpre[i] * cos[i] + tpim[i] * sin[i]) * 2;
	}
	
	
	@Override
	public void inverseTransform(double[] in, double[] out) {
		NullChecker.check(in);
		NullChecker.check(out);
		if (in.length != length || out.length != length)
			throw new IllegalArgumentException();
		
		double[] tpre = new double[length * 2];
		double[] tpim = new double[length * 2];
		for (int i = 0; i < length; i++) {
			tpre[i] = in[i] * cos[i];
			tpim[i] = in[i] * sin[i];
		}
		fft.inverseTransform(tpre, tpim);
		for (int i = 0; i < length; i++)
			out[i] = tpre[i];
	}
	
}