package p79068.hash;

import p79068.Assert;


/**
 * Represents a hash value produced by a hash function. Immutable.
 * If the hash function produces a hash value whose length is not a multiple of 8 bits, then the hash function needs to specify the bit packing order.
 * @see HashFunction
 * @see Hasher
 */
public final class HashValue implements Comparable<HashValue> {
	
	/**
	 * The hash value as a sequence of bytes.
	 */
	private final byte[] hashValue;
	
	
	
	/**
	 * Constructs a hash value from the specified byte array. The array's contents are cloned.
	 * @param hash the byte array representing the hash
	 * @throws NullPointerException if {@code hash} is {@code null}
	 */
	public HashValue(byte[] hash) {
		Assert.assertNotNull(hash);
		hashValue = hash.clone();
	}
	
	
	
	/**
	 * Returns this hash value as a new array of bytes.
	 * @return this hash value as a new array of bytes
	 */
	public byte[] toBytes() {
		return hashValue.clone();
	}
	
	
	/**
	 * Returns this hash value as an uppercase hexadecimal string. For example, {@code "1337C0DE"}.
	 * @return this hash value as an uppercase hexadecimal string
	 */
	public String toHexString() {
		StringBuilder sb = new StringBuilder();
		for (byte b : hashValue) {
			sb.append(HEX_DIGITS[(b >>> 4) & 0xF]);
			sb.append(HEX_DIGITS[(b >>> 0) & 0xF]);
		}
		return sb.toString();
	}
	
	
	/**
	 * Returns the length of this hash value, in bytes.
	 * @return the length of this hash value, in bytes
	 */
	public int getLength() {
		return hashValue.length;
	}
	
	
	/**
	 * Tests whether this hash value is equal to the specified object. Returns {@code true} if the specified object is a hash value and its byte array has the same length and contents as this hash value's byte array. Otherwise returns {@code false}.
	 * @param other the object to test for equality
	 * @return whether {@code other} is a hash value with the same sequence of bytes
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof HashValue))
			return false;
		
		byte[] ohv = ((HashValue)other).hashValue;
		if (hashValue.length != ohv.length)
			return false;
		
		// Equality comparison whose running time is independent of the data values
		int diff = 0;
		for (int i = 0; i < hashValue.length; i++)
			diff |= hashValue[i] ^ ohv[i];
		return diff == 0;
	}
	
	
	/**
	 * Compares this hash value with the specified hash value for order. Returns a negative integer, zero, or positive integer respectively if this hash value is less than, equal to, or greater than the specified hash value. The comparison involves lexicographical ordering on unsigned byte values.
	 * @param other the hash value to compare to
	 * @return a negative integer, zero, or positive integer respectively if {@code this} is less than, equal to, or greater than {@code other}
	 */
	public int compareTo(HashValue other) {
		Assert.assertNotNull(other);
		byte[] ohv = other.hashValue;
		
		// Lexicographical comparison whose running time is independent of the data values
		int result = 0;
		for (int i = 0; i < hashValue.length && i < ohv.length; i++) {
			// Equivalent to: if (result == 0) result = (hashValue[i] & 0xFF) - (ohv[i] & 0xFF);
			int mask = (result | (-result)) >> 31;
			result = (mask & result) | (~mask & ((hashValue[i] & 0xFF) - (ohv[i] & 0xFF)));
		}
		// Equivalent to: return result != 0 ? result : (hashValue.length - ohv.length);
		int mask = (result | (-result)) >> 31;
		return (mask & result) | (~mask & (hashValue.length - ohv.length));
	}
	
	
	/**
	 * Returns the hash code for this hash value. The hash code algorithm is subjected to change.
	 * @return the hash code for this hash value
	 */
	@Override
	public int hashCode() {
		// Directly uses the leading 4 (or fewer) bytes as the result, because we assume that the hash function that produced this hash value is good
		int result = 0;
		for (int i = 0; i < Math.min(hashValue.length, 4); i++)
			result = result << 8 | (hashValue[i] & 0xFF);
		return result;
	}
	
	
	/**
	 * Returns a string representation of this hash value. The string's format is subjected to change.
	 * @return a string representation of this hash value
	 */
	@Override
	public String toString() {
		return String.format("%s (%d)", toHexString(), getLength());
	}
	
	
	
	private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
	
}
