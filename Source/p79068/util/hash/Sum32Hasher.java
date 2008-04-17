package p79068.util.hash;

import p79068.math.IntegerBitMath;


final class Sum32Hasher extends Hasher {
	
	private int sum;
	
	
	
	Sum32Hasher(Sum32 algor) {
		super(algor);
		sum = 0;
	}
	
	
	
	public void update(byte b) {
		sum += b & 0xFF;
	}
	
	
	public void update(byte[] b, int off, int len) {
		for (int i = off, end = off + len; i < end; i++)
			sum += b[i] & 0xFF;
	}
	
	
	public HashValue getHash() {
		return createHash(IntegerBitMath.toBytesBigEndian(new int[]{sum}));
	}
	
}