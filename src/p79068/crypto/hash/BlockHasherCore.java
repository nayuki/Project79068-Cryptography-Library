package p79068.crypto.hash;

import java.math.BigInteger;
import p79068.Assert;
import p79068.crypto.Zeroizable;
import p79068.hash.HashValue;


/**
 * Provides a compression function and holds the hash state for a {@link BlockHasher}. This abstract superclass is stateless, but subclasses will have state.
 */
public abstract class BlockHasherCore implements Cloneable, Zeroizable {
	
	/**
	 * Applies the compression function to combine the current message block into the hasher's internal state. This calls {@code compress(msg, 0, msg.length)}.
	 * @param msg the byte array to compress
	 */
	public void compress(byte[] msg) {
		Assert.assertNotNull(msg);
		compress(msg, 0, msg.length);
	}
	
	
	/**
	 * Applies the compression function to combine the specified message blocks into the hasher's internal state.
	 * @param msg the byte array to compress
	 * @param off the offset into array {@code msg}
	 * @param len the number of bytes to process, which is always a multiple of the block size
	 */
	public abstract void compress(byte[] msg, int off, int len);
	
	
	/**
	 * Given the contents of the final message block, this computes and returns the hash value. This hasher's internal state can change as a result of calling this method.
	 * <p>The suffix bytes in {@code block} starting at index {@code blockFilled} are garbage and the computation must not depend on them.</p> 
	 * @param block the final message block (which is not completely filled)
	 * @param blockFilled the number of bytes in the block, in the range [0, block.length)
	 * @param length the length of the entire message, in bytes
	 * @return the hash value
	 */
	public abstract HashValue getHashDestructively(byte[] block, int blockFilled, BigInteger length);
	
	
	/**
	 * Returns a clone of this object. {@link BlockHasher} uses this in conjunction with {@link #getHashDestructively()}.
	 * @return a clone of this object
	 */
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
