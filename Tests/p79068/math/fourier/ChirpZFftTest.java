package p79068.math.fourier;


public final class ChirpZFftTest extends DftTest {
	
	@Override
	protected Dft getInstance(int length) {
		return ChirpZFft.getInstance(length);
	}
	
}