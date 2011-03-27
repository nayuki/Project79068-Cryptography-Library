package p79068.crypto.hash;

import p79068.hash.AbstractHashFunction;
import p79068.hash.Hasher;


/**
 * The original eDonkey2000 hash function.
 * <p>Specifically, it uses the MD4 function to hash blocks of (exactly) 9 728 000 bytes starting from the beginning. If the message is less than one block long, the hash of the first block is returned. Otherwise: <em>If the message length is a positive multiple of the block length, one extra empty block at the end is hashed and included.</em> The hashes of these blocks are concatenated together are hashed again with MD4 to yield the final hash value.</p>
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see NewEdonkey2000
 */
public final class Edonkey2000 extends AbstractHashFunction {
	
	/**
	 * The singleton instance of the eDonkey2000 hash function. {@code name = "eDonkey2000"}, {@code hashLength = 16}.
	 */
	public final static Edonkey2000 FUNCTION = new Edonkey2000();
	
	
	
	private Edonkey2000() {
		super("eDonkey2000", 16);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new Edonkey2000Hasher(this);
	}
	
}