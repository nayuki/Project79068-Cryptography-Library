package p79068.math;


public final class KahanSum {
	
	private double sum;
	
	private double compensation;
	
	
	
	public KahanSum() {
		sum = 0;
		compensation = 0;
	}
	
	
	
	public KahanSum add(double x) {
		double y = x - compensation;
		double temp = sum + y;
		compensation = (temp - sum) - y;
		return this;
	}
	
	
	public double result() {
		return sum;
	}
	
}