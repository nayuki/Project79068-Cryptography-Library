package p79068.hash;

import p79068.Assert;


/**
 * A skeletal implementation of the {@link Hasher} interface for convenience.
 */
public abstract class AbstractHasher implements Hasher, Cloneable {
	
	/**
	 * The hash function associated with this hasher. This field must not be modified except for zeroization.
	 */
	protected HashFunction hashFunction;
	
	
	
	/**
	 * Creates a hasher set to the specified hash function.
	 * @param hf the hash function associated with this hasher
	 * @throws NullPointerException if {@code hashFunc} is {@code null}
	 */
	protected AbstractHasher(HashFunction hf) {
		Assert.assertNotNull(hf);
		hashFunction = hf;
	}
	
	
	
	/**
	 * Updates the current state with the specified byte. The provided implementation relies on {@link #update(byte[])}.
	 * @param b the byte to update the state with
	 */
	public void update(byte b) {
		update(new byte[]{b});
	}
	
	
	/**
	 * Updates the current state with the specified byte array. The provided implementation relies on {@link #update(byte[], int, int)}.
	 * @param b the byte array to update the state with
	 * @throws NullPointerException if {@code b} is {@code null}
	 */
	public void update(byte[] b) {
		Assert.assertNotNull(b);
		update(b, 0, b.length);
	}
	
	
	/**
	 * Updates the current state with the specified byte array.
	 * @param b the byte array to update the state with
	 * @param off the offset into {@code b}
	 * @param len the length of the subrange in {@code b}
	 * @throws NullPointerException if {@code b} is {@code null}
	 */
	public abstract void update(byte[] b, int off, int len);
	
	
	/**
	 * Returns the current hash value. This method does not alter the hasher's internal state.
	 * @return the current hash value
	 */
	public abstract HashValue getHash();
	
	
	/**
	 * Returns the hash function associated with this hasher.
	 * @return the hash function associated with this hasher
	 */
	public HashFunction getHashFunction() {
		return hashFunction;
	}
	
	
	/**
	 * Returns a new hasher with the same internal state as this one's. The returned object uses the same algorithm, but its type need not be the same as this one's.
	 * <p>The general contract is that if the original and the clone are updated by the same sequence of bytes, then they must return the same hash value.</p>
	 * @return a clone of this object
	 */
	@Override
	public AbstractHasher clone() {
		try {
			return (AbstractHasher)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
	}
	
	
	/**
	 * Returns a string representation of this hasher. Currently, the hash function's name and the hash value is returned. The string format is subjected to change.
	 * @return a string representation of this hasher
	 */
	@Override
	public String toString() {
		return String.format("%s: %s", getHashFunction().getName(), getHash().toHexString());
	}
	
}
