package p79068.util.hash;

import p79068.lang.BoundsChecker;


final class ZeroHasher extends Hasher {
	
	ZeroHasher(ZeroFunction hashFunc) {
		super(hashFunc);
	}
	
	
	
	@Override
	public void update(byte b) {}
	
	
	@Override
	public void update(byte[] b, int off, int len) {
		BoundsChecker.check(b.length, off, len);
	}
	
	
	@Override
	public HashValue getHash() {
		return new HashValue(new byte[1]);
	}
	
}