package p79068.crypto.hash;

import p79068.util.hash.AbstractHashFunction;


/**
 * A hash function that operates on blocks of bytes. The internal state of a hasher is only updated after each completed block.
 */
public abstract class BlockHashFunction extends AbstractHashFunction {
	
	/**
	 * The block length of this hash function, in bytes.
	 */
	private int blockLength;
	
	
	
	protected BlockHashFunction(String name, int hashLen, int blockLen) {
		super(name, hashLen);
		blockLength = blockLen;
	}
	
	
	
	/**
	 * Returns the block length of this hash function, in bytes. This property is required for the HMAC hash function.
	 * @return the block length of this hash function, in bytes
	 */
	public final int getBlockLength() {
		return blockLength;
	}
	
}