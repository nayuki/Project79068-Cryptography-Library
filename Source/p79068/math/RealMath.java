package p79068.math;


/**
Contains methods for math functions that deal with real numbers.
<p>Instantiability: <em>Not applicable</em></p>
*/
public class RealMath {
	
	/** Returns the sine of the specified number of complete revolutions. It is similar to <code>Math.sin(2*Math.PI*x)</code>. */
	public static float sinRevs(float x) {
		if (x >= 0f) {
			if (x >= 1f)
				x %= 1;
			if (x >= 0.75f)
				x--;
			else if (x >= 0.25f)
				x = 0.5f - x;
		} else {
		}
		double y = x * x;
		return (float)((((((3.9653034141171493607e1) * y - 7.6564695440839330551e1) * y + 8.1601632709834543134e1) * y - 4.1341664669615459394e1) * y + 6.2831851989975942410e0) * x);
	}
	
	/** Returns the cosine of the specified number of complete revolutions. It is similar to <code>Math.cos(2*Math.PI*x)</code>. */
	public static float cosRevs(float x) {
		float sign = 1;
		if (x >= 0f) {
			if (x >= 1f)
				x %= 1;
			if (x >= 0.75f)
				x--;
			else if (x >= 0.25f) {
				x = 0.5f - x;
				sign = -1;
			}
		} else {
		}
		double y = x * x;
		return (float)(sign * (((((5.6445734579155567248e1) * y - 8.5263204534087686108e1) * y + 6.4935380310507999466e1) * y - 1.9739179942871861928e1) * y + 9.9999996727011546714e-1));
	}
	
	/** Returns the tangent of the specified number of complete revolutions. It is similar to <code>Math.tan(2*Math.PI*x)</code>. */
	public static float tanRevs(float x) {
		return sinRevs(x) / cosRevs(x);
	}
}