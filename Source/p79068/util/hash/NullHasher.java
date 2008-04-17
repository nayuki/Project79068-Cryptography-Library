package p79068.util.hash;

import p79068.lang.BoundsChecker;


final class NullHasher extends Hasher {
	
	NullHasher(NullFunction algor) {
		super(algor);
	}
	
	
	
	public void update(byte b) {}
	
	
	public void update(byte[] b, int off, int len) {
		BoundsChecker.check(b.length, off, len);
	}
	
	
	public HashValue getHash() {
		return createHash(new byte[1]);
	}
	
}