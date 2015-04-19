package p79068.crypto.hash;

import p79068.hash.AbstractHashFunction;
import p79068.hash.Hasher;


/**
 * The eDonkey2000 hash functions. Specifically, it uses the MD4 function to hash blocks of (exactly) 9 728 000 bytes starting from the beginning.
 * <ul>
 *   <li>Old ed2k: If the message is less than one block long, the hash of the first block is returned. Otherwise: <em>If the message length is a positive multiple of the block length, one extra empty block at the end is hashed and included.</em> The hashes of these blocks are concatenated together and hashed again with MD4 to yield the final hash value.</li>
 *   <li>New ed2k: If the message is less than <em>or equal</em> one block long, the hash of the first block is returned. Otherwise, the hashes of these blocks are concatenated together and hashed again with MD4 to yield the final hash value.</li>
 * </ul>
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 */
public final class Edonkey2000 extends AbstractHashFunction {
	
	/**
	 * The original eDonkey2000 hash function. {@code name = "eDonkey2000"}, {@code hashLength = 16}.
	 */
	public final static Edonkey2000 ED2K_FUNCTION = new Edonkey2000("eDonkey 2000");
	
	/**
	 * The new eDonkey2000 hash function. {@code name = "New eDonkey2000"}, {@code hashLength = 16}.
	 */
	public final static Edonkey2000 NEW_ED2K_FUNCTION = new Edonkey2000("New eDonkey 2000");
	
	
	
	private Edonkey2000(String name) {
		super(name, 16);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		if (this == ED2K_FUNCTION)
			return new Edonkey2000Hasher(this, false);
		else if (this == NEW_ED2K_FUNCTION)
			return new Edonkey2000Hasher(this, true);
		else
			throw new AssertionError();
	}
	
}
