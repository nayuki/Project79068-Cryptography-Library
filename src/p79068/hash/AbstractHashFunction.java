package p79068.hash;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import p79068.Assert;


/**
 * A skeletal implementation of the {@link HashFunction} interface for convenience.
 */
public abstract class AbstractHashFunction implements HashFunction {
	
	// Fields
	
	/**
	 * The name of this hash function.
	 */
	private final String name;
	
	/**
	 * The length of the hash values produced by this hash function, in bytes.
	 */
	private final int hashLength;
	
	
	
	// Constructor
	
	/**
	 * Constructs a hash function with the specified name and output hash length (in bytes).
	 * @param name the name of this hash function
	 * @param hashLen the output hash length of this hash function, in bytes
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the hash length is negative
	 */
	protected AbstractHashFunction(String name, int hashLen) {
		Assert.assertNotNull(name);
		if (hashLen < 0)
			throw new IllegalArgumentException("Hash length must be non-negative");
		this.name = name;
		hashLength = hashLen;
	}
	
	
	
	// Concrete final methods
	
	/**
	 * Returns the name of this hash function.
	 * @return the name of this hash function
	 */
	public final String getName() {
		return name;
	}
	
	
	/**
	 * Returns the length of the hash values produced by this hash function, in bytes.
	 * @return the length of the hash values produced by this hash function, in bytes
	 */
	public final int getHashLength() {
		return hashLength;
	}
	
	
	// Concrete non-final methods
	
	/**
	 * Computes and returns the hash value of the specified byte array. The provided implementation relies on {@link #getHash(byte[], int, int)}.
	 * @param b the byte array to hash
	 * @return the hash value of the specified byte array
	 * @throws NullPointerException if {@code b} is {@code null}
	 */
	public HashValue getHash(byte[] b) {
		Assert.assertNotNull(b);
		return getHash(b, 0, b.length);
	}
	
	
	/**
	 * Computes and returns the hash value of the specified byte array.
	 * @param b the byte array to hash
	 * @param off the offset into {@code b}
	 * @param len the length of the subrange in {@code b}
	 * @return the hash value of the specified byte array range
	 * @throws NullPointerException if {@code b} is {@code null}
	 */
	public HashValue getHash(byte[] b, int off, int len) {
		Assert.assertNotNull(b);
		Hasher hasher = newHasher();
		hasher.update(b, off, len);
		return hasher.getHash();
	}
	
	
	/**
	 * Computes and returns the hash value of the specified file.
	 * @param file the file to hash
	 * @return the hash value of the specified file
	 * @throws IOException if an I/O exception occurs
	 * @throws NullPointerException if {@code file} is {@code null}
	 */
	public HashValue getHash(File file) throws IOException {
		Assert.assertNotNull(file);
		Hasher hasher = newHasher();
		InputStream in = new FileInputStream(file);
		try {
			byte[] buf = new byte[32 * 1024];
			while (true) {
				int n = in.read(buf);
				if (n == -1)
					break;
				hasher.update(buf, 0, n);
			}
		} finally {
			in.close();
		}
		return hasher.getHash();
	}
	
	
	/**
	 * Returns a string representation of this hash function. Currently, it returns the name of the hash function. The string format is subjected to change.
	 * @return a string representation of this hash function
	 */
	@Override
	public String toString() {
		return getName();
	}
	
	
	// Abstract methods
	
	/**
	 * Returns a new hasher of this hash function, which is used to compute a hash value incrementally.
	 * @return a new hasher of this hash function
	 */
	public abstract Hasher newHasher();
	
}
