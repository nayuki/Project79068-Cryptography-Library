package p79068.crypto.hash;

import p79068.crypto.Zeroizable;
import p79068.lang.*;
import p79068.util.hash.Hasher;
import p79068.util.hash.HashValue;


final class NewEdonkey2000Hasher extends Hasher implements Zeroizable {

	/**
	 * The block length. After each block of this many bytes, the hash of the inner hasher is added to the outer hasher.
	 */
	private static final int BLOCK_LENGTH = 9728000;
	
	
	/**
	 * The outer hasher, which hashes the hash values of individual blocks. This is <code>null</code> if and only if the total number of bytes hashed is less or equal than <code>BLOCK_LENGTH</code>. This differs from the behavior of the old algorithm.
	 */
	private Hasher outerHasher;
	
	/**
	 * The inner hasher, which hashes the current block.
	 */
	private Hasher innerHasher;
	
	/**
	 * The number of bytes hashed in the current block. This is <code>0</code> initially, but it is in the range (<code>0</code>, <code>BLOCK_LENGTH</code>] after each <code>update()</code> operation involving more than 0 bytes. This differs from the behavior of the old algorithm.
	 */
	private int currentBlockLength;
	
	
	
	NewEdonkey2000Hasher(NewEdonkey2000 hashFunc) {
		super(hashFunc);
		outerHasher = null;
		innerHasher = Md4.FUNCTION.newHasher();
		currentBlockLength = 0;
	}
	
	
	
	public void update(byte b) {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		nextBlock();
		innerHasher.update(b);
		currentBlockLength++;
	}
	
	
	public void update(byte[] b, int off, int len) {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		
		while (len > 0) {
			// At this point, currentBlockLength is 0 if the total length hashed is 0; otherwise currentBlockLength is in the range (0, BLOCK_LENGTH]
			nextBlock();
			int templen = Math.min(BLOCK_LENGTH - currentBlockLength, len);
			innerHasher.update(b, off, templen);
			off += templen;
			len -= templen;
			currentBlockLength += templen;
		}
	}
	
	
	public HashValue getHash() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		
		if (outerHasher == null)  // Fewer than or equal to BLOCK_SIZE bytes has been hashed, so return the hash of the one and only block
			return innerHasher.getHash();
		else {
			Hasher temp = outerHasher.clone();
			temp.update(innerHasher.getHash().toBytes());  // Add the hash of the current block, which has more than 0 bytes hashed
			return temp.getHash();
		}
	}
	
	
	public NewEdonkey2000Hasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		
		NewEdonkey2000Hasher result = (NewEdonkey2000Hasher)super.clone();
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