package p79068.crypto.hash;

import p79068.crypto.Zeroizable;
import p79068.lang.BoundsChecker;
import p79068.util.hash.HashValue;
import p79068.util.hash.AbstractHasher;


final class HmacHasher extends AbstractHasher implements Zeroizable {
	
	private AbstractHasher inner;
	private AbstractHasher outer;
	
	
	
	public HmacHasher(Hmac hashFunc, AbstractHasher inner, AbstractHasher outer) {
		super(hashFunc);
		this.inner = inner.clone();
		this.outer = outer.clone();
	}
	
	
	
	@Override
	public void update(byte[] b, int off, int len) {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		inner.update(b, off, len);
	}
	
	
	@Override
	public HashValue getHash() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		AbstractHasher temp = outer.clone();
		temp.update(inner.getHash().toBytes());
		return temp.getHash();
	}
	
	
	@Override
	public HmacHasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		HmacHasher result = (HmacHasher)super.clone();
		result.inner = result.inner.clone();
		result.outer = result.outer.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		if (inner instanceof Zeroizable)
			((Zeroizable)inner).zeroize();
		if (outer instanceof Zeroizable)
			((Zeroizable)outer).zeroize();
		inner = null;
		outer = null;
		hashFunction = null;
	}
	
}