package p79068.math;


public class FftTest extends DftTest {
	
	@Override
	protected Dft getInstance(int length) {
		return Fft.getInstance(length);
	}
	
}