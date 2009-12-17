package p79068.math;

import p79068.lang.NullChecker;
import p79068.util.KeyedIntener;


/**
 * Computes the discrete Fourier transform/inverse transform of a complex vector using the naive O(n^2) algorithm.
 */
public final class NaiveDft extends Dft {
	
private static KeyedIntener<Integer,NaiveDft> cache = new KeyedIntener<Integer,NaiveDft>();
	
	
	public static NaiveDft getInstance(int length) {
		if (length <= 0)
			throw new IllegalArgumentException();
		if (!IntegerMath.isPowerOf2(length))
			throw new IllegalArgumentException();
		
		NaiveDft dft = cache.get(length);
		if (dft == null) {
			dft = new NaiveDft(length);
			cache.put(length, dft);
		}
		return dft;
	}
	
	
	
	private int length;
	private double[] cos, sin;
	
	
	
	private NaiveDft(int len) {
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
	
	
	
	public void transform(double[] inreal, double[] inimag, double[] outreal, double[] outimag) {
		NullChecker.check(inreal, inimag, outreal, outimag);
		if (inreal.length != length || inimag.length != length || outreal.length != length || outimag.length != length)
			throw new IllegalArgumentException();
		if (inreal == outreal || inimag == outimag || inreal == outimag || inimag == outreal || outreal == outimag)
			throw new IllegalArgumentException();
		
		for (int i = 0; i < length; i++) {
			double sumreal = 0;
			double sumimag = 0;
			if (length <= 46341) {
				for (int j = 0; j < length; j++) {
					int index = i * j % length;
					sumreal +=  inreal[j] * cos[index] + inimag[j] * sin[index];
					sumimag += -inreal[j] * sin[index] + inimag[j] * cos[index];
				}
			} else {  // Use long arithmetic for indexing
				for (int j = 0; j < length; j++) {
					int index = (int)((long)i * j % length);
					sumreal +=  inreal[j] * cos[index] + inimag[j] * sin[index];
					sumimag += -inreal[j] * sin[index] + inimag[j] * cos[index];
				}
			}
			outreal[i] = sumreal;
			outimag[i] = sumimag;
		}
	}
	
}