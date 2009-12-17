package p79068.math;


public class ChirpZFftTest extends DftTest {
	
	@Override
	protected Dft getInstance(int length) {
		return ChirpZFft.getInstance(length);
	}
	
}