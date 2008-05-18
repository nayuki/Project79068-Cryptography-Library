package p79068.math;


/**
 * Computes the discrete Fourier transform/inverse transform of a complex vector using the radix-2 decimation in time (O(n log n)) algorithm.
 * 
 * Multi-thread safe.
 */
final class Fft extends Dft {
	
	private int length;
	private int[] permutation;  // Bit-reversed addressing
	private double[] cos, sin;
	
	
	
	public Fft(int length) {
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
		
		int levels = log2(length);
		permutation = new int[length];
		for (int i = 0; i < length; i++)
			permutation[i] = IntegerBitMath.reverseBits(i) >>> (32 - levels);
	}
	
	
	
	public void transform(double[] inre, double[] inim, double[] outre, double[] outim) {
		// Permute input array onto output array
		for (int i = 0; i < length; i++) {
			outre[i] = inre[permutation[i]];
			outim[i] = inim[permutation[i]];
		}
		
		// Transform in-place on the output array
		transformPrivate(outre, outim);
	}
	
	
	public void transform(double[] re, double[] im) {
		// Do the permutation in-place. This is possible because the permutation is self-inverting.
		for (int i = 0; i < length; i++) {
			if (permutation[i] > i) {
				double tempreal = re[i];
				re[i] = re[permutation[i]];
				re[permutation[i]] = tempreal;
				
				double tempimag = im[i];
				im[i] = im[permutation[i]];
				im[permutation[i]] = tempimag;
			}
		}
		transformPrivate(re, im);
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
	
	
	private static int log2(int x) {
		if (x <= 0)
			throw new IllegalArgumentException("Argument must be positive");
		for (int i = 0; i < 32; i++) {
			if ((x >>> i) == 1)
				return i;
		}
		throw new AssertionError();
	}
	
}