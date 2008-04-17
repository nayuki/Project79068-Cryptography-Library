package p79068.math;


public final class Bezier {
	
	private double[] polyCoef; // Polynomial coefficients
	

	public Bezier(double[] controlPoints) { // Compiles from Bezier form to monomial form, allowing O(n) evaluation (Horner algorithm) instead of O(n^2) (de Casteljau algorithm)
		if (controlPoints.length == 0)
			throw new IllegalArgumentException("Invalid number of control points");
		int[] pt = pascalTriangle(controlPoints.length - 1);
		int[] tppt = new int[controlPoints.length + 1]; // Padded with a zero on the left
		tppt[1] = 1;
		polyCoef = new double[controlPoints.length];
		polyCoef[0] = controlPoints[controlPoints.length - 1];
		for (int i = 1; i < controlPoints.length; i++) {
			for (int j = i; j >= 0; j--) {
				tppt[j + 1] += tppt[j];
				polyCoef[j] += (1 - (i + j & 1) * 2) * controlPoints[controlPoints.length - 1 - i] * tppt[j + 1] * pt[i];
			}
		}
	} // If i+j mod 2 = 0, don't negate, else do negate
	

	public double evaluate(double t) {
		if (t < 0 || t > 1)
			throw new IllegalArgumentException("Parameter out of range");
		double s = polyCoef[0];
		for (int i = 1; i < polyCoef.length; i++)
			s = s * t + polyCoef[i];
		return s;
	}
	
	
	private static int[] pascalTriangle(int n) {
		if (n > 33)
			throw new ArithmeticOverflowException();
		int[] pt = new int[n + 1];
		pt[0] = 1;
		for (int i = 1; i <= n; i++) {
			for (int j = i; j >= 1; j--)
				pt[j] += pt[j - 1];
		}
		return pt;
	}
}