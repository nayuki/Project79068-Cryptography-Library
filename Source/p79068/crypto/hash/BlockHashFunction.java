package p79068.crypto.hash;

import p79068.util.hash.HashFunction;


/**
 * A hash function that operates on blocks of bytes. The internal state of a hasher is only updated after each completed block.
 */
public abstract class BlockHashFunction extends HashFunction {
	
	protected BlockHashFunction() {
		super();
	}
	
	
	/**
	 * Returns the block length of this hash function. This property is required for the HMAC hash function.
	 * @return the block length of this hash function
	 */
	public abstract int getBlockLength();
	
}