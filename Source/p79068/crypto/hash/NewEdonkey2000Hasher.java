package p79068.crypto.hash;

import p79068.crypto.Zeroizable;
import p79068.lang.*;
import p79068.util.hash.Hasher;
import p79068.util.hash.HashValue;


final class NewEdonkey2000Hasher extends Hasher implements Zeroizable {
	
	private Hasher hasher;       // Outer hash. Is null if total length < BLOCK_LENGTH for old mode; is null if total length <= BLOCK_LENGTH for new mode.
	private Hasher blockHasher;  // Inner hash
	private int blockLength;     // Length of current block. Is within the range [0,BLOCK_LENGTH) for old mode; (0,BLOCK_LENGTH] for new mode (but can be 0 for the initial block).
	
	
	
	NewEdonkey2000Hasher(NewEdonkey2000 hashFunc) {
		super(hashFunc);
		hasher = null;
		blockHasher = Md4.FUNCTION.newHasher();
		blockLength = 0;
	}
	
	
	
	public void update(byte b) {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		newNextBlock();
		blockHasher.update(b);
		blockLength++;
	}
	
	
	public void update(byte[] b, int off, int len) {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		while (len > 0) {
			newNextBlock();
			int templen = Math.min(BLOCK_LENGTH - blockLength, len);
			blockHasher.update(b, off, templen);
			off += templen;
			len -= templen;
			blockLength += templen;
		}
	}
	
	
	public HashValue getHash() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		if (hasher == null)
			return blockHasher.getHash();
		else {
			Hasher temp = hasher.clone();
			temp.update(blockHasher.getHash().toBytes());
			return temp.getHash();
		}
	}
	
	
	public NewEdonkey2000Hasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		NewEdonkey2000Hasher result = (NewEdonkey2000Hasher)super.clone();
		result.hasher = hasher.clone();
		result.blockHasher = blockHasher.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		blockLength = 0;
		if (hasher != null && hasher instanceof Zeroizable)
			((Zeroizable)hasher).zeroize();
		if (blockHasher instanceof Zeroizable)
			((Zeroizable)blockHasher).zeroize();
		hasher = null;
		blockHasher = null;
		hashFunction = null;
	}
	
	
	
	private static final int BLOCK_LENGTH = 9728000;
	
	
	private void newNextBlock() {
		nextBlock();
	}
	
	
	private void nextBlock() {
		if (blockLength == BLOCK_LENGTH) {
			if (hasher == null)
				hasher = Md4.FUNCTION.newHasher();
			hasher.update(blockHasher.getHash().toBytes());
			blockHasher = Md4.FUNCTION.newHasher();
			blockLength = 0;
		}
	}
	
}