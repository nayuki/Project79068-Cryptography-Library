package p79068.math.fourier;

import p79068.math.IntegerMath;


public class FftTest extends DftTest {
	
	@Override
	protected Dft getInstance(int length) {
		if (IntegerMath.isPowerOf2(length))
			return Fft.getInstance(length);
		else  // To pass the random length tests
			return ChirpZFft.getInstance(length);
	}
	
}