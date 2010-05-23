package p79068.util.hash;

import java.util.Arrays;
import p79068.lang.NullChecker;


/**
 * Represents a hash value produced by a hash function. Immutable.
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
		NullChecker.check(hash);
		hashValue = hash.clone();
	}
	
	
	
	/**
	 * Returns this hash value as an array of bytes.
	 */
	public byte[] toBytes() {
		return hashValue.clone();
	}
	
	
	/**
	 * Returns this hash value as a hexadecimal string (in uppercase). For example, {@code "1337C0DE"}.
	 */
	public String toHexString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < hashValue.length; i++)
			sb.append(hexDigits[hashValue[i] >>> 4 & 0xF])
			  .append(hexDigits[hashValue[i] >>> 0 & 0xF]);
		return sb.toString();
	}
	
	
	/**
	 * Returns the length of this hash value, in bytes.
	 */
	public int getLength() {
		return hashValue.length;
	}
	
	
	/**
	 * Compares the specified object with this hash value for equality. Returns <code>true</code> if the specified object is a hash value and its byte array has the same length and contents as this hash value's byte array. Otherwise, this method returns <code>false</code>.
	 * @param other the object to compare to
	 * @return whether the <code>other</code> is a hash value with the same sequence of bytes
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof HashValue)
			return Arrays.equals(hashValue, ((HashValue)other).hashValue);
		else
			return false;
	}
	
	
	/**
	 * Compares this hash value with the specified hash value for order. Returns a negative integer, zero, or positive integer respectively if this hash value is less than, equal to, or greater than the specified hash value. The comparison involves lexicographical ordering on unsigned byte values. Hash values of different lengths are incomparable and an exception will be thrown.
	 * @param other the object to compare to
	 * @return a negative integer, zero, or positive integer respectively if {@code this} is less than, equal to, or greater than {@code other}
	 * @throws IllegalArgumentException if {@code other} has a different hash length than {@code this}
	 */
	public int compareTo(HashValue other) {
		NullChecker.check(other);
		if (hashValue.length != other.hashValue.length)
			throw new IllegalArgumentException("Hash lengths are different");
		
		for (int i = 0; i < hashValue.length && i < other.hashValue.length; i++) {
			if (hashValue[i] != other.hashValue[i])
				return (hashValue[i] & 0xFF) - (other.hashValue[i] & 0xFF);
		}
		return 0;
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
	 * Returns a string representation of this hash value. Currently, the hexadecimal hash value is returned. The string's format is subjected to change.
	 * @return a string representation of this hash value
	 */
	@Override
	public String toString() {
		return toHexString();
	}
	
	
	
	private static final char[] hexDigits = "0123456789ABCDEF".toCharArray();
	
}