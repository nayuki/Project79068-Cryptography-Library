package p79068.crypto.hash;

import p79068.crypto.Zeroizable;
import p79068.hash.HashValue;
import p79068.lang.NullChecker;


/**
 * This abstract superclass is stateless. Subclasses will have state.
 */
public abstract class BlockHasherCore implements Cloneable, Zeroizable {
	
	/**
	 * Applies the compression function to combine the current message block into the hasher's internal state. This calls {@code compress(this.block, 0, this.block.length)}.
	 */
	public void compress(byte[] message) {
		NullChecker.check(message);
		compress(message, 0, message.length);
	}
	
	
	/**
	 * Applies the compression function to combine the specified message blocks into the hasher's internal state.
	 * @param message the byte array to compress
	 * @param off the offset into array {@code message}
	 * @param len the number of bytes to process, which is always a multiple of the block size
	 */
	public abstract void compress(byte[] message, int off, int len);
	
	
	/**
	 * Returns the hash value, possibly changing this hasher's internal state.
	 * @return the hash value
	 */
	public abstract HashValue getHashDestructively(byte[] block, int blockLength, long length);
	
	
	
	@Override
	public BlockHasherCore clone() {
		try {
			return (BlockHasherCore)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
	}
	
	
	/**
	 * Does nothing. It is safe for direct subclasses to omit calling {@code super.zeroize()}.
	 */
	public void zeroize() {}
	
}