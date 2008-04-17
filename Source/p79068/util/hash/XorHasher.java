package p79068.util.hash;

import p79068.lang.BoundsChecker;


final class XorHasher extends Hasher {
	
	/**
	 * The running XOR of all the bytes seen. Note that the top 24 bits are irrelevant.
	 */
	private int xor;
	
	
	
	XorHasher(Xor algor) {
		super(algor);
		xor = 0;
	}
	
	
	
	public void update(byte b) {
		xor ^= b;
	}
	
	
	public void update(byte[] b, int off, int len) {
		BoundsChecker.check(b.length, off, len);
		for (int i = off, end = off + len; i < end; i++)
			xor ^= b[i];
	}
	
	
	public HashValue getHash() {
		return createHash(new byte[]{(byte)xor});
	}
	
}