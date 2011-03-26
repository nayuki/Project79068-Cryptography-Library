package p79068.crypto.hash;

import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.Shacal2;
import p79068.util.hash.AbstractHashFunction;
import p79068.util.hash.Hasher;


/**
 * The SHA-256 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 */
public final class Sha256 extends AbstractBlockHashFunction {
	
	/**
	 * The singleton instance of the SHA-256 hash function. {@code name = "SHA-256"}, {@code hashLength = 32}, {@code blockLength = 64};
	 */
	public final static Sha256 FUNCTION = new Sha256();
	
	
	
	private Sha256() {
		super("SHA-256", 32, 64);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new Sha256Hasher(true));
	}
	
	
	public Cipherer newCipherer(Shacal2 cipher, byte[] key) {
		return new Shacal2Cipherer(cipher, key);
	}
	
}