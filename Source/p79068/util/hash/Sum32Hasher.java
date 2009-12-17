package p79068.util.hash;

import p79068.lang.BoundsChecker;
import p79068.math.IntegerBitMath;


final class Sum32Hasher extends Hasher {
	
	/**
	 * The running sum of all the bytes seen, modulo 2<sup>32</sup>.
	 */
	private int sum;
	
	
	
	Sum32Hasher(Sum32 hashFunc) {
		super(hashFunc);
		sum = 0;
	}
	
	
	
	public void update(byte b) {
		sum += b & 0xFF;
	}
	
	
	public void update(byte[] b, int off, int len) {
		BoundsChecker.check(b.length, off, len);
		for (int i = off, end = off + len; i < end; i++)
			sum += b[i] & 0xFF;
	}
	
	
	public HashValue getHash() {
		return new HashValue(IntegerBitMath.toBytesBigEndian(new int[]{sum}));
	}
	
}