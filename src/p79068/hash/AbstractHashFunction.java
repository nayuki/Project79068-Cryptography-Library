package p79068.hash;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import p79068.lang.NullChecker;


/**
 * Computes the hash value of byte sequences and produces {@link Hasher Hashers}.
 * <p>Mutability: <em>Immutable</em> unless otherwise noted</p>
 * <p>Example - one-shot hashing:</p>
 * <pre>byte[] b = <i>getWholeMessage</i>();
 *byte[] hash = Sha1.FUNCTION.getHash(b).toBytes();</pre>
 * <p>Example - incremental hashing:</p>
 * <pre>Hasher hasher = Crc.CRC32_FUNCTION.newHasher();
 *while (<i>more data available</i>) {
 *  byte[] b = <i>readSomeMore</i>();
 *  hasher.update(b);
 *}
 *String hash = hasher.getHash().toHexString();</pre>
 * <p>Although some hash function can operate on messages of bits rather than bytes, this is not supported at the moment.</p>
 * @see Hasher
 * @see HashValue
 */
public abstract class AbstractHashFunction implements HashFunction {
	
	/**
	 * The name of this hash function.
	 */
	private final String name;
	
	
	/**
	 * The length of the hash values produced by this hash function, in bytes.
	 */
	private final int hashLength;
	
	
	
	protected AbstractHashFunction(String name, int hashLen) {
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
	 * Computes and returns the hash value of the specified byte array.
	 */
	public HashValue getHash(byte[] b) {
		NullChecker.check(b);
		return getHash(b, 0, b.length);
	}
	
	
	/**
	 * Computes and returns the hash value of the specified byte array.
	 */
	public HashValue getHash(byte[] b, int off, int len) {
		NullChecker.check(b);
		Hasher hasher = newHasher();
		hasher.update(b, off, len);
		return hasher.getHash();
	}
	
	
	/**
	 * Computes and returns the hash value of the specified file.
	 */
	public HashValue getHash(File file) throws IOException {
		NullChecker.check(file);
		Hasher hasher = newHasher();
		InputStream in = new FileInputStream(file);
		try {
			byte[] b = new byte[32 * 1024];
			while (true) {
				int temp = in.read(b);
				if (temp == -1)
					break;
				hasher.update(b, 0, temp);
			}
		} finally {
			in.close();
		}
		return hasher.getHash();
	}
	
	
	/**
	 * Returns a string representation of this hash function. Currently, it returns the name of the hash function. This is subjected to change.
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
