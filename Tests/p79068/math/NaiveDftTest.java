package p79068.math;


public class NaiveDftTest extends DftTest {
	
	@Override
	protected Dft getInstance(int length) {
		return NaiveDft.getInstance(length);
	}
	
}