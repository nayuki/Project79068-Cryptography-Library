package p79068.crypto.hash;

import p79068.Assert;
import p79068.crypto.Zeroizable;
import p79068.hash.AbstractHasher;
import p79068.hash.HashValue;
import p79068.hash.Hasher;


final class Edonkey2000Hasher extends AbstractHasher implements Zeroizable {
	
	/**
	 * The block length. After each block of this many bytes, the hash of the inner hasher is added to the outer hasher.
	 */
	private static final int BLOCK_LENGTH = 9728000;
	
	
	private final boolean newEd2kMode;
	
	/**
	 * The outer hasher, which hashes the hash values of individual blocks. For the old algorithm, this is {@code null} if and only if the total number of bytes hashed is less than {@code BLOCK_LENGTH}. For the new algorithm, this is null iff the total bytes is less than or equal to {@code BLOCK_LENGTH}.
	 */
	private Hasher outerHasher;
	
	/**
	 * The inner hasher, which hashes the current block.
	 */
	private Hasher innerHasher;
	
	/**
	 * The number of bytes hashed in the current block. For the old algorithm, this is in the range [{@code 0}, {@code BLOCK_LENGTH}) initially and after each {@code update()} operation. For the new algorithm, this is {@code 0} initially, but after a non-empty {@code update()} it is in the range ({@code 0}, {@code BLOCK_LENGTH}] after each {@code update()} operation.
	 */
	private int currentBlockLength;
	
	
	
	public Edonkey2000Hasher(Edonkey2000 hashFunc, boolean newEd2kMode) {
		super(hashFunc);
		this.newEd2kMode = newEd2kMode;
		outerHasher = null;
		innerHasher = Md.MD4_FUNCTION.newHasher();
		currentBlockLength = 0;
	}
	
	
	
	@Override
	public void update(byte b) {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		
		if (newEd2kMode)
			nextBlock();
		innerHasher.update(b);
		currentBlockLength++;
		if (!newEd2kMode)
			nextBlock();
	}
	
	
	@Override
	public void update(byte[] b, int off, int len) {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
		
		while (len > 0) {
			// Old algorithm: At this point, currentBlockLength is in the range [0, BLOCK_LENGTH)
			// New algorithm: At this point, currentBlockLength is 0 if the total length hashed is 0; otherwise currentBlockLength is in the range (0, BLOCK_LENGTH]
			if (newEd2kMode)
				nextBlock();
			int templen = Math.min(BLOCK_LENGTH - currentBlockLength, len);
			innerHasher.update(b, off, templen);
			off += templen;
			len -= templen;
			currentBlockLength += templen;
			if (!newEd2kMode)
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
			// Add the hash of the current block.
			// Old algorithm: The block has 0 or more bytes hashed
			// New algorithm: The block has more than 0 bytes hashed
			temp.update(innerHasher.getHash().toBytes());
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
				outerHasher = Md.MD4_FUNCTION.newHasher();
			outerHasher.update(innerHasher.getHash().toBytes());
			innerHasher = Md.MD4_FUNCTION.newHasher();
			currentBlockLength = 0;
		}
	}
	
}
