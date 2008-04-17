package p79068.crypto.hash;

import p79068.crypto.Zeroizable;
import p79068.lang.*;
import p79068.util.hash.Hasher;
import p79068.util.hash.HashValue;


final class Edonkey2000Hasher extends Hasher implements Zeroizable {
	
	private boolean oldmode;
	
	private Hasher hasher;       // Outer hash. Is null if total length < BLOCK_LENGTH for old mode; is null if total length <= BLOCK_LENGTH for new mode.
	private Hasher blockHasher;  // Inner hash
	private int blockLength;     // Length of current block. Is within the range [0,BLOCK_LENGTH) for old mode; (0,BLOCK_LENGTH] for new mode (but can be 0 for the initial block).
	
	

	Edonkey2000Hasher(Edonkey2000 algor) {
		super(algor);
		oldmode = true;
		hasher = null;
		blockHasher = Md4.FUNCTION.newHasher();
		blockLength = 0;
	}
	
	
	Edonkey2000Hasher(NewEdonkey2000 algor) {
		super(algor);
		oldmode = false;
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
		oldNextBlock();
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
			oldNextBlock();
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
	
	
	public Edonkey2000Hasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		Edonkey2000Hasher result = (Edonkey2000Hasher)super.clone();
		result.hasher = hasher.clone();
		result.blockHasher = blockHasher.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		oldmode = false;
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
	
	
	private void oldNextBlock() {
		if (oldmode)
			nextBlock();
	}
	
	
	private void newNextBlock() {
		if (!oldmode)
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