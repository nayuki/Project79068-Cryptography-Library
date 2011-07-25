package p79068.crypto.hash;

import p79068.hash.HashFunction;


public interface BlockHashFunction extends HashFunction {
	
	/**
	 * Returns the block length of this hash function, in bytes. This property is required for the HMAC hash function.
	 * @return the block length of this hash function, in bytes
	 */
	public int getBlockLength();
	
}
