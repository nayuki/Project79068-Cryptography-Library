package p79068.hash;


/**
 * A hasher, which consumes bytes and produces hash values. This allows the hash value of a byte sequence to be computed incrementally.
 * <p>Usage example:</p>
 * <pre>Hasher hasher = Md.MD5_FUNCTION.newHasher();
 *while (...) {
 *    byte[] buffer = (...);
 *    hasher.update(buffer);
 *}
 *byte[] hash = hasher.getHash().toBytes();</pre>
 * @see HashFunction
 * @see HashValue
 */
public interface Hasher {
	
	/**
	 * Updates the current state with the specified byte.
	 * @param b the byte to update the state with
	 */
	public void update(byte b);
	
	/**
	 * Updates the current state with the specified byte array.
	 * @param b the byte array to update the state with
	 * @throws NullPointerException if {@code b} is {@code null}
	 */
	public void update(byte[] b);
	
	/**
	 * Updates the current state with the specified byte array.
	 * @param b the byte array to update the state with
	 * @param off the offset into {@code b}
	 * @param len the length of the subrange in {@code b}
	 * @throws NullPointerException if {@code b} is {@code null}
	 */
	public void update(byte[] b, int off, int len);
	
	
	/**
	 * Returns the current hash value. This method does not alter the hasher's internal state.
	 * @return the current hash value
	 */
	public HashValue getHash();
	
	
	/**
	 * Returns the hash function associated with this hasher.
	 * @return the hash function associated with this hasher
	 */
	public HashFunction getHashFunction();
	
	
	/**
	 * Returns a new hasher with the same internal state as this one's. The returned object uses the same algorithm, but its type need not be the same as this one's.
	 * <p>The general contract is that if the original and the clone are updated by the same sequence of bytes, then they must return the same hash value.</p>
	 * @return a clone of this object
	 */
	public Hasher clone();
	
	
	/**
	 * Returns a string representation of this hasher. The string format is subjected to change.
	 * @return a string representation of this hasher
	 */
	public String toString();
	
}
