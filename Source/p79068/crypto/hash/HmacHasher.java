package p79068.crypto.hash;

import p79068.crypto.Zeroizable;
import p79068.lang.*;
import p79068.util.hash.Hasher;
import p79068.util.hash.HashValue;


final class HmacHasher extends Hasher implements Zeroizable {
	
	private Hasher inner;
	private Hasher outer;
	
	
	
	HmacHasher(Hmac hashFunc, Hasher inner, Hasher outer) {
		super(hashFunc);
		this.inner = inner.clone();
		this.outer = outer.clone();
	}
	
	
	
	public void update(byte[] b, int off, int len) {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		inner.update(b, off, len);
	}
	
	
	public HashValue getHash() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		Hasher temp = outer.clone();
		temp.update(inner.getHash().toBytes());
		return temp.getHash();
	}
	
	
	public HmacHasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		HmacHasher result = (HmacHasher)super.clone();
		result.inner = inner.clone();
		result.outer = outer.clone();
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