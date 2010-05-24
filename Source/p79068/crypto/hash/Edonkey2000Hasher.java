package p79068.crypto.hash;

import p79068.crypto.Zeroizable;
import p79068.lang.BoundsChecker;
import p79068.util.hash.HashValue;
import p79068.util.hash.Hasher;


final class Edonkey2000Hasher extends Hasher implements Zeroizable {
	
	/**
	 * The block length. After each block of this many bytes, the hash of the inner hasher is added to the outer hasher.
	 */
	private static final int BLOCK_LENGTH = 9728000;
	
	
	/**
	 * The outer hasher, which hashes the hash values of individual blocks. This is {@code null} if and only if the total number of bytes hashed is less than {@code BLOCK_LENGTH}.
	 */
	private Hasher outerHasher;
	
	/**
	 * The inner hasher, which hashes the current block.
	 */
	private Hasher innerHasher;
	
	/**
	 * The number of bytes hashed in the current block. This is in the range [{@code 0}, {@code BLOCK_LENGTH}) initially and after each {@code update()} operation.
	 */
	private int currentBlockLength;
	
	
	
	public Edonkey2000Hasher(Edonkey2000 hashFunc) {
		super(hashFunc);
		outerHasher = null;
		innerHasher = Md4.FUNCTION.newHasher();
		currentBlockLength = 0;
	}
	
	
	
	@Override
	public void update(byte b) {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		innerHasher.update(b);
		currentBlockLength++;
		nextBlock();
	}
	
	
	@Override
	public void update(byte[] b, int off, int len) {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		
		while (len > 0) {
			// At this point, currentBlockLength is in the range [0, BLOCK_LENGTH)
			int templen = Math.min(BLOCK_LENGTH - currentBlockLength, len);
			innerHasher.update(b, off, templen);
			off += templen;
			len -= templen;
			currentBlockLength += templen;
			nextBlock();
		}
	}
	
	
	@Override
	public HashValue getHash() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		
		if (outerHasher == null)  // Fewer than or equal to BLOCK_SIZE bytes has been hashed, so return the hash of the one and only block
			return innerHasher.getHash();
		else {
			Hasher temp = outerHasher.clone();
			temp.update(innerHasher.getHash().toBytes());  // Add the hash of the current block, which has 0 or more bytes hashed
			return temp.getHash();
		}
	}
	
	
	@Override
	public Edonkey2000Hasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		
		Edonkey2000Hasher result = (Edonkey2000Hasher)super.clone();
		result.outerHasher = result.outerHasher.clone();
		result.innerHasher = result.innerHasher.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		
		currentBlockLength = 0;
		if (outerHasher instanceof Zeroizable)
			((Zeroizable)outerHasher).zeroize();
		if (innerHasher instanceof Zeroizable)
			((Zeroizable)innerHasher).zeroize();
		outerHasher = null;
		innerHasher = null;
		hashFunction = null;
	}
	
	
	
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