package p79068.crypto.hash;

import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.Shacal1;
import p79068.util.hash.AbstractHashFunction;
import p79068.util.hash.Hasher;


/**
 * The SHA-1 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 * Instantiability: <em>Singleton</em></p>
 * @see AbstractHashFunction
 */
public final class Sha1 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the SHA-1 hash function. {@code name = "SHA-1"}, {@code hashLength = 20}, {@code blockLength = 64};
	 */
	public final static Sha1 FUNCTION = new Sha1();
	
	
	
	private Sha1() {
		super("SHA-1", 20, 64);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new FastSha1Hasher());
	}
	
	
	public Cipherer newCipherer(Shacal1 cipher, byte[] key) {
		return new Shacal1Cipherer(cipher, key);
	}
	
}