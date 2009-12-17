package p79068.math;


public class FftTest extends DftTest {
	
	@Override
	protected Dft getInstance(int length) {
		if (IntegerMath.isPowerOf2(length))
			return Fft.getInstance(length);
		else  // To pass the random length tests
			return ChirpZFft.getInstance(length);
	}
	
}