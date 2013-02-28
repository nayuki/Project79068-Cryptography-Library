package p79068.hash;



public interface Hasher {
	
	/**
	 * Updates the current state with the specified byte.
	 */
	public void update(byte b);
	

	/**
	 * Updates the current state with the specified byte array.
	 */
	public void update(byte[] b);
	

	/**
	 * Updates the current state with the specified byte array.
	 */
	public void update(byte[] b, int off, int len);
	

	/**
	 * Returns the current hash value. This method does not alter the hasher's internal state.
	 * @return the current hash value
	 */
	public HashValue getHash();
	

	/**
	 * Returns the hash function associated with this instance.
	 * @return the hash function associated with this instance
	 */
	public HashFunction getHashFunction();
	

	/**
	 * Returns a new hasher with the same internal state as this one's. The returned object uses the same algorithm, but its type need not be the same as this one's.
	 * <p>The general contract is that if the original and the clone are updated by the same sequence of bytes, then they must return the same hash value.</p>
	 * @return a clone of this object
	 */
	public Hasher clone();
	
}
