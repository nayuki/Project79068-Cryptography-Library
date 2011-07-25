package p79068.crypto.hash;

import p79068.hash.AbstractHashFunction;
import p79068.hash.Hasher;


/**
 * The new eDonkey2000 hash function.
 * <p>Specifically, it uses the MD4 function to hash blocks of (exactly) 9 728 000 bytes starting from the beginning. If the message is less than <em>or equal</em> one block long, the hash of the first block is returned. Otherwise, the hashes of these blocks are concatenated together are hashed again with MD4 to yield the final hash value.</p>
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see Edonkey2000
 */
public final class NewEdonkey2000 extends AbstractHashFunction {
	
	/**
	 * The singleton instance of the new eDonkey2000 hash function. {@code name = "New eDonkey2000"}, {@code hashLength = 16}.
	 */
	public final static NewEdonkey2000 FUNCTION = new NewEdonkey2000();
	
	
	
	private NewEdonkey2000() {
		super("New eDonkey2000", 16);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new NewEdonkey2000Hasher(this);
	}
	
}
