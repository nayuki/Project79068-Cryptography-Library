package p79068.math.fourier;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import p79068.util.Random;


public abstract class DftTest {
	
	protected abstract Dft getInstance(int length);
	
	
	
	@Test
	public void testTransformLowestWave() {
		int length = 16;  // Must be even
		
		// True answer
		double[] refreal = new double[length];
		double[] refimag = new double[length];
		refimag[1] = -length / 2;
		refimag[length - 1] = length / 2;
		
		// Computed answer
		double[] real = new double[length];
		double[] imag = new double[length];
		for (int i = 0; i < real.length; i++)
			real[i] = Math.sin(i * 2 * Math.PI / length);
		Dft dft = getInstance(length);
		dft.transform(real, imag);
		
		double error = Math.max(getMaxAbsoluteError(real, refreal), getMaxAbsoluteError(imag, refimag)) / length;
		assertEquals(0, error, 1e-15);
	}
	
	
	@Test
	public void testTransformHighestWave() {
		int length = 16;  // Must be even
		
		// True answer
		double[] refreal = new double[length];
		double[] refimag = new double[length];
		refreal[0] = length / 2;
		refreal[length / 2] = -length / 2;
		
		// Computed answer
		double[] real = new double[length];
		double[] imag = new double[length];
		for (int i = 0; i < real.length; i++)
			real[i] = i % 2;
		Dft dft = getInstance(length);
		dft.transform(real, imag);
		
		double error = Math.max(getMaxAbsoluteError(real, refreal), getMaxAbsoluteError(imag, refimag)) / length;
		assertEquals(0, error, 1e-14);
	}
	
	
	@Test
	public void testInvertibilityRandomly() {
		for (int i = 0; i < 30; i++) {
			int length = Random.DEFAULT.randomInt(100) + 1;
			double[][] temp = newRandomVector(length);
			double[] real = temp[0], imag = temp[1];
			double[] refreal = real.clone();
			double[] refimag = imag.clone();
			
			Dft dft = getInstance(length);
			dft.transform(real, imag);
			dft.inverseTransform(real, imag);
			for (int j = 0; j < length; j++) {
				real[j] /= length;
				imag[j] /= length;
			}
			
			double error = Math.max(getMaxAbsoluteError(real, refreal), getMaxAbsoluteError(imag, refimag));
			assertEquals(0, error, 1e-14);
		}
	}
	
	
	@Test
	public void testEnergyConservationRandomly() {
		for (int i = 0; i < 30; i++) {
			int length = Random.DEFAULT.randomInt(100) + 1;
			double[][] temp = newRandomVector(length);
			double[] real = temp[0], imag = temp[1];
			
			double refenergy = 0;
			for (int j = 0; j < length; j++)
				refenergy += real[j] * real[j] + imag[j] * imag[j];
			
			Dft dft = getInstance(length);
			dft.transform(real, imag);
			double energy = 0;
			for (int j = 0; j < length; j++)
				energy += (real[j] * real[j] + imag[j] * imag[j]) / length;
			
			double error = Math.abs(energy - refenergy) / length;
			assertEquals(0, error, 1e-14);
		}
	}
	
	
	@Test
	public void testLinearityRandomly() {
		for (int i = 0; i < 30; i++) {
			int length = Random.DEFAULT.randomInt(100) + 1;
			double[][] temp0 = newRandomVector(length);
			double[][] temp1 = newRandomVector(length);
			double[] real0 = temp0[0], imag0 = temp0[1];
			double[] real1 = temp1[0], imag1 = temp1[1];
			
			double[] real2 = new double[length];
			double[] imag2 = new double[length];
			for (int j = 0; j < length; j++) {
				real2[j] = real0[j] + real1[j];
				imag2[j] = imag0[j] + imag1[j];
			}
			
			Dft dft = getInstance(length);
			dft.transform(real0, imag0);
			dft.transform(real1, imag1);
			dft.transform(real2, imag2);
			
			for (int j = 0; j < length; j++) {
				real0[j] += real1[j];
				imag0[j] += imag1[j];
			}
			
			double error = Math.max(getMaxAbsoluteError(real0, real2), getMaxAbsoluteError(imag0, imag2)) / length;
			assertEquals(0, error, 1e-14);
		}
	}
	
	
	private static double[][] newRandomVector(int length) {
		double[] real = new double[length];
		double[] imag = new double[length];
		for (int j = 0; j < length; j++) {
			real[j] = Random.DEFAULT.randomDouble() * 2 - 1;
			imag[j] = Random.DEFAULT.randomDouble() * 2 - 1;
		}
		return new double[][]{real, imag};
	}
	
	
	private static double getMaxAbsoluteError(double[] x, double[] y) {
		if (x.length != y.length)
			throw new IllegalArgumentException();
		double error = 0;
		for (int i = 0; i < x.length; i++) {
			error = Math.max(Math.abs(x[i] - y[i]), error);
		}
		return error;
	}
	
}