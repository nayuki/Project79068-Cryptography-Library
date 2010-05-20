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
	 * Returns this hash value as a hexadecimal string (in uppercase). For example, <samp>"1337C0DE"</samp>.
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
	
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof HashValue))
			return false;
		HashValue hash = (HashValue)o;
		return Arrays.equals(hashValue, hash.hashValue);
	}
	
	
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
	 * Returns the hash code for this hash value.
	 * @return the hash code for this hash value
	 */
	@Override
	public int hashCode() {
		// Directly uses the leading 4 (or fewer) bytes as the result
		int result = 0;
		for (int i = 0; i < Math.min(hashValue.length, 4); i++)
			result = result << 8 | (hashValue[i] & 0xFF);
		return result;
	}
	
	
	/**
	 * Returns a string representation of this hash value. Currently, the hexadecimal hash value is returned. This is subjected to change.
	 * @return a string representation of this hash value
	 */
	@Override
	public String toString() {
		return toHexString();
	}
	
	
	
	private static final char[] hexDigits = "0123456789ABCDEF".toCharArray();
	
}