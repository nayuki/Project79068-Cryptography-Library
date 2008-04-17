package p79068.math;


/**
 * Computes the forward and inverse discrete cosine transforms (DCT) of a real vector. The forward transform is the type II DCT; the inverse transform is the type IV DCT.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: Via static factory </code>Dct.getInstance()</code></p>
 * @see Dft
 */
public abstract class Dct {
	
	/**
	 * Returns a DCT algorithm for transforming vectors of the specified length.
	 * <p>An algorithm with time complexity O(<var>n</var> log <var>n</var>) is guaranteed to be returned for all possible lengths.</p>
	 */
	public static Dct getInstance(int length) {
		if (length <= 0)
			throw new IllegalArgumentException();
		return new FastDct(length);
	}
	
	
	
	protected Dct() {}
	
	
	
	/**
	 * Computes the forward transform of the specified real vector.
	 */
	public abstract void transform(double[] in, double[] out);
	
	
	/**
	 * Computes the inverse transform of the specified real vector.
	 */
	public abstract void transformInverse(double[] in, double[] out);
	
}