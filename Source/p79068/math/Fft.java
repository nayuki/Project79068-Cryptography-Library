package p79068.math;

import p79068.lang.NullChecker;
import p79068.util.KeyedIntener;


/**
 * Computes the discrete Fourier transform/inverse transform of a complex vector using the radix-2 decimation in time (O(n log n)) algorithm.
 * 
 * Multi-thread safe.
 */
public final class Fft extends Dft {
	
	private static KeyedIntener<Integer,Fft> cache = new KeyedIntener<Integer,Fft>();
	
	
	public static synchronized Fft getInstance(int length) {
		if (length <= 0)
			throw new IllegalArgumentException();
		if (!IntegerMath.isPowerOf2(length))
			throw new IllegalArgumentException();
		
		Fft dft = cache.get(length);
		if (dft == null) {
			dft = new Fft(length);
			cache.put(length, dft);
		}
		return dft;
	}
	
	
	
	private int length;
	private int[] permutation;  // Bit-reversed addressing
	private double[] cos, sin;
	
	
	
	private Fft(int length) {
		if (length < 1)
			throw new IllegalArgumentException("Length must be positive");
		if (!IntegerMath.isPowerOf2(length))
			throw new IllegalArgumentException("Length must be a power of 2");
		
		this.length = length;
		
		cos = new double[length / 2];
		sin = new double[length / 2];
		for (int i = 0; i < length / 2; i++) {
			cos[i] = Math.cos(i * 2 * Math.PI / length);
			sin[i] = Math.sin(i * 2 * Math.PI / length);
		}
		
		int levels = IntegerMath.log2Floor(length);  // 1 <= levels <= 31
		permutation = new int[length];
		for (int i = 0; i < length; i++)
			permutation[i] = IntegerBitMath.reverseBits(i) >>> (32 - levels);
	}
	
	
	
	public void transform(double[] inreal, double[] inimag, double[] outreal, double[] outimag) {
		NullChecker.check(inreal, inimag, outreal, outimag);
		if (inreal == outreal || inimag == outimag || inreal == outimag || inimag == outreal || outreal == outimag)
			throw new IllegalArgumentException();
		if (inreal.length != length || inimag.length != length || outreal.length != length || outimag.length != length)
			throw new IllegalArgumentException();
		
		// Permute input array onto output array
		for (int i = 0; i < length; i++) {
			outreal[i] = inreal[permutation[i]];
			outimag[i] = inimag[permutation[i]];
		}
		
		// Transform in-place on the output array
		transformPrivate(outreal, outimag);
	}
	
	
	public void transform(double[] real, double[] imag) {
		NullChecker.check(real, imag);
		if (real.length != length || imag.length != length)
			throw new IllegalArgumentException();
		
		// Do the permutation in-place. This is possible because the permutation is self-inverting.
		for (int i = 0; i < length; i++) {
			if (permutation[i] > i) {
				double tempreal = real[i];
				real[i] = real[permutation[i]];
				real[permutation[i]] = tempreal;
				
				double tempimag = imag[i];
				imag[i] = imag[permutation[i]];
				imag[permutation[i]] = tempimag;
			}
		}
		transformPrivate(real, imag);
	}
	
	
	
	/**
	 * Computes the fast Fourier transform on the specified complex vector in-place.
	 * @param re the real parts of the vector
	 * @param im the imaginary parts of the vector
	 */
	private void transformPrivate(double[] re, double[] im) {
		if (length >= 2) {
			// Perform a multiply-less length-2 DFT
			for (int i = 0; i < length; i += 2) {
				double tpre = re[i | 1];
				double tpim = im[i | 1];
				re[i | 1] = re[i] - tpre;
				im[i | 1] = im[i] - tpim;
				re[i] += tpre;
				im[i] += tpim;
			}
		}
		
		if (length >= 4) {
			// Perform a multiply-less length-4 DFT
			for (int i = 0; i < length; i += 4) {
				double tpre;
				double tpim;
				tpre = re[i | 2];
				tpim = im[i | 2];
				re[i | 2] = re[i] - tpre;
				im[i | 2] = im[i] - tpim;
				re[i] += tpre;
				im[i] += tpim;
				
				tpre =  im[i | 3];
				tpim = -re[i | 3];
				re[i | 3] = re[i | 1] - tpre;
				im[i | 3] = im[i | 1] - tpim;
				re[i | 1] += tpre;
				im[i | 1] += tpim;
			}
		}
		
		for (int size = 8; size <= length; size *= 2) {
			int halfsize = size / 2;
			int tablestep = length / size;
			
			for (int i = 0, j = 0, blockend = halfsize;;) {
				if (i < blockend) {
					double tpre =  re[i | halfsize] * cos[j] + im[i | halfsize] * sin[j];
					double tpim = -re[i | halfsize] * sin[j] + im[i | halfsize] * cos[j];
					re[i | halfsize] = re[i] - tpre;
					im[i | halfsize] = im[i] - tpim;
					re[i] += tpre;
					im[i] += tpim;
					i++;
					j += tablestep;
				} else {
					i += halfsize;
					j = 0;
					if (i == length)
						break;
					blockend += size;
				}
			}
		}
	}
	
}