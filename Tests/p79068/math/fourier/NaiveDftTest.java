package p79068.math.fourier;


public final class NaiveDftTest extends DftTest {
	
	@Override
	protected Dft getInstance(int length) {
		return NaiveDft.getInstance(length);
	}
	
}