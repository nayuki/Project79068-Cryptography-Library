package p79068.hash;

import p79068.lang.NullChecker;


/**
 * Incrementally computes the hash value of a byte sequence.
 * <p>Mutability: <em>Mutable</em><br>
 * Thread safety: <em>Unsafe</em> unless otherwise specified<br>
 * Instantiability: Via {@link HashFunction#newHasher()}</p>
 * <p>Usage example:</p>
 * <pre>Hasher hasher = Md5.FUNCTION.newHasher();
while (<i>more data available</i>) {
  byte[] b = <i>readSomeMore</i>();
  hasher.update(b);
}
byte[] hash = hasher.getHash().toBytes();</pre>
 * @see HashFunction
 * @see HashValue
 */
public abstract class AbstractHasher implements Hasher, Cloneable {
	
	/**
	 * The hash function associated with this hasher. This reference must not be modified except for zeroization.
	 */
	protected HashFunction hashFunction;
	
	
	
	/**
	 * Creates a hasher set to the specified hash function.
	 */
	protected AbstractHasher(HashFunction hashFunc) {
		NullChecker.check(hashFunc);
		hashFunction = hashFunc;
	}
	
	
	
	/**
	 * Updates the current state with the specified byte.
	 */
	public void update(byte b) {
		update(new byte[]{b});
	}
	
	
	/**
	 * Updates the current state with the specified byte array.
	 */
	public void update(byte[] b) {
		NullChecker.check(b);
		update(b, 0, b.length);
	}
	
	
	/**
	 * Updates the current state with the specified byte array.
	 */
	public abstract void update(byte[] b, int off, int len);
	
	
	/**
	 * Returns the current hash value. This method does not alter the hasher's internal state.
	 * @return the current hash value
	 */
	public abstract HashValue getHash();
	
	
	/**
	 * Returns the hash function associated with this instance.
	 * @return the hash function associated with this instance
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
	 * Returns a string representation of this hasher. Currently, the hash function's name and the hash value is returned. This is subjected to change.
	 * @return a string representation of this hasher
	 */
	@Override
	public String toString() {
		return String.format("%s: %s", getHashFunction().getName(), getHash().toHexString());
	}
	
}
