package p79068.hash;

import java.io.File;
import java.io.IOException;


public interface HashFunction {
	
	/**
	 * Computes and returns the hash value of the specified byte array.
	 */
	public HashValue getHash(byte[] b);
	
	
	/**
	 * Computes and returns the hash value of the specified byte array.
	 */
	public HashValue getHash(byte[] b, int off, int len);
	
	
	/**
	 * Computes and returns the hash value of the specified file.
	 */
	public HashValue getHash(File file) throws IOException;
	
	
	/**
	 * Returns a new hasher of this hash function, which is used to compute a hash value incrementally.
	 * @return a new hasher of this hash function
	 */
	public Hasher newHasher();
	
	
	/**
	 * Returns the name of this hash function.
	 * @return the name of this hash function
	 */
	public String getName();
	
	
	/**
	 * Returns the length of the hash values produced by this hash function, in bytes.
	 * @return the length of the hash values produced by this hash function, in bytes
	 */
	public int getHashLength();
	
	
	/**
	 * Returns a string representation of this hash function. This is subjected to change.
	 * @return a string representation of this hash function
	 */
	public String toString();
	
}
