package p79068.crypto.hash;

import p79068.crypto.Zeroizable;
import p79068.lang.*;
import p79068.util.hash.Hasher;
import p79068.util.hash.HashValue;


final class Edonkey2000Hasher extends Hasher implements Zeroizable {
	
	private Hasher outerHasher;  // Outer hash. Is null if total length < BLOCK_LENGTH for old mode; is null if total length <= BLOCK_LENGTH for new mode.
	private Hasher innerHasher;  // Inner hash
	private int currentBlockLength;     // Length of current block. Is within the range [0,BLOCK_LENGTH) for old mode; (0,BLOCK_LENGTH] for new mode (but can be 0 for the initial block).
	
	

	Edonkey2000Hasher(Edonkey2000 hashFunc) {
		super(hashFunc);
		outerHasher = null;
		innerHasher = Md4.FUNCTION.newHasher();
		currentBlockLength = 0;
	}
	
	
	
	public void update(byte b) {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		innerHasher.update(b);
		currentBlockLength++;
		nextBlock();
	}
	
	
	public void update(byte[] b, int off, int len) {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		while (len > 0) {
			int templen = Math.min(BLOCK_LENGTH - currentBlockLength, len);
			innerHasher.update(b, off, templen);
			off += templen;
			len -= templen;
			currentBlockLength += templen;
			nextBlock();
		}
	}
	
	
	public HashValue getHash() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		if (outerHasher == null)
			return innerHasher.getHash();
		else {
			Hasher temp = outerHasher.clone();
			temp.update(innerHasher.getHash().toBytes());
			return temp.getHash();
		}
	}
	
	
	public Edonkey2000Hasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		Edonkey2000Hasher result = (Edonkey2000Hasher)super.clone();
		result.outerHasher = outerHasher.clone();
		result.innerHasher = innerHasher.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		currentBlockLength = 0;
		if (outerHasher != null && outerHasher instanceof Zeroizable)
			((Zeroizable)outerHasher).zeroize();
		if (innerHasher instanceof Zeroizable)
			((Zeroizable)innerHasher).zeroize();
		outerHasher = null;
		innerHasher = null;
		hashFunction = null;
	}
	
	
	
	private static final int BLOCK_LENGTH = 9728000;
	
	
	private void nextBlock() {
		if (currentBlockLength == BLOCK_LENGTH) {
			if (outerHasher == null)
				outerHasher = Md4.FUNCTION.newHasher();
			outerHasher.update(innerHasher.getHash().toBytes());
			innerHasher = Md4.FUNCTION.newHasher();
			currentBlockLength = 0;
		}
	}
	
}