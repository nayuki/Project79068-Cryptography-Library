package p79068.math;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import p79068.util.Random;


public abstract class DftTest {
	
	private static Random random = Random.newInstance();
	
	
	protected abstract Dft getInstance(int length);
	
	
	
	@Test
	public void testTransformLowestWave() {
		int length = 16;
		double[] real = new double[length];
		double[] imag = new double[length];
		for (int i = 0; i < real.length; i++)
			real[i] = Math.sin(i * 2 * Math.PI / length);
		
		Dft dft = getInstance(length);
		dft.transform(real, imag);
		
		double[] refreal = new double[length];
		double[] refimag = new double[length];
		refimag[1] = -8;
		refimag[15] = 8;
		
		double error = Math.max(getMaxAbsoluteError(real, refreal), getMaxAbsoluteError(imag, refimag));
		assertEquals(0, error, 1e-14);
	}
	
	
	@Test
	public void testTransformHighestWave() {
		int length = 16;
		double[] real = new double[length];
		double[] imag = new double[length];
		for (int i = 0; i < real.length; i++)
			real[i] = i % 2;
		
		Dft dft = getInstance(length);
		dft.transform(real, imag);
		
		double[] refreal = new double[length];
		double[] refimag = new double[length];
		refreal[0] = 8;
		refreal[8] = -8;
		
		double error = Math.max(getMaxAbsoluteError(real, refreal), getMaxAbsoluteError(imag, refimag));
		assertEquals(0, error, 1e-14);
	}
	
	
	@Test
	public void testInvertibilityRandom() {
		int length = 64;
		double[] real = new double[length];
		double[] imag = new double[length];
		for (int i = 0; i < length; i++) {
			real[i] = random.randomDouble() * 2 - 1;
			imag[i] = random.randomDouble() * 2 - 1;
		}
		
		double[] refreal = real.clone();
		double[] refimag = imag.clone();
		
		Dft dft = getInstance(length);
		dft.transform(real, imag);
		dft.inverseTransform(real, imag);
		for (int i = 0; i < length; i++) {
			real[i] /= length;
			imag[i] /= length;
		}
		
		double error = Math.max(getMaxAbsoluteError(real, refreal), getMaxAbsoluteError(imag, refimag));
		assertEquals(0, error, 1e-14);
	}
	
	
	private double getMaxAbsoluteError(double[] x, double[] y) {
		if (x.length != y.length)
			throw new IllegalArgumentException();
		double error = 0;
		for (int i = 0; i < x.length; i++) {
			error = Math.max(Math.abs(x[i] - y[i]), error);
		}
		return error;
	}
	
}