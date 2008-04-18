package p79068.util;


public class ExponentialDistribution {

	/**
	 * @param r a real number uniformly distributed in [0, 1)
	 * @param mean the mean of this exponential distribution
	 */
	public double randomExponential(double r, double mean) {
		return -Math.log(1 - r) * mean;
	}

}