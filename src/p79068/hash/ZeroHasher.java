package p79068.hash;

import p79068.Assert;


final class ZeroHasher extends AbstractHasher {
	
	ZeroHasher(ZeroFunction hashFunc) {
		super(hashFunc);
	}
	
	
	
	@Override
	public void update(byte b) {}
	
	
	@Override
	public void update(byte[] b, int off, int len) {
		Assert.assertRangeInBounds(b.length, off, len);
	}
	
	
	@Override
	public HashValue getHash() {
		return new HashValue(new byte[1]);
	}
	
}
