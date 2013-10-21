package p79068.crypto.hash;

import p79068.hash.HashFunction;


/**
 * A hash function that processes the input in blocks of, for example, 64 bytes. The block size is necessary for the HMAC construction.
 */
public interface BlockHashFunction extends HashFunction {
	
	/**
	 * Returns the block length of this hash function, in bytes. This property is required for the {@link p79068.crypto.hash.Hmac HMAC hash function}.
	 * @return the block length of this hash function, in bytes
	 */
	public int getBlockLength();
	
}
