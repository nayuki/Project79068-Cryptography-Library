package p79068.crypto.hash;

import p79068.util.hash.HashFunction;
import p79068.util.hash.Hasher;


/**
 * The new eDonkey2000 hash function.
 * <p>Specifically, it uses the MD4 function to hash blocks of (exactly) 9 728 000 bytes starting from the beginning. If the message is less than <em>or equal</em> one block long, the hash of the first block is returned. Otherwise, the hashes of these blocks are concatenated together are hashed again with MD4 to yield the final hash value.</p>
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see Edonkey2000
 */
public final class NewEdonkey2000 extends HashFunction {
	
	/**
	 * The singleton instance of the new eDonkey2000 hash function.
	 */
	public final static NewEdonkey2000 FUNCTION = new NewEdonkey2000();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new NewEdonkey2000Hasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: {@code New eDonkey2000}.
	 * @return {@code "New eDonkey2000"}
	 */
	@Override
	public String getName() {
		return "New eDonkey2000";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: {@code 16} bytes (128 bits).
	 * @return {@code 16}
	 */
	@Override
	public int getHashLength() {
		return Md4.FUNCTION.getHashLength();
	}
	
	
	
	private NewEdonkey2000() {}
	
}