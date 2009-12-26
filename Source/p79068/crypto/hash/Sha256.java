package p79068.crypto.hash;

import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.Shacal2;
import p79068.util.hash.HashFunction;
import p79068.util.hash.Hasher;


/**
 * The SHA-256 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public final class Sha256 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the SHA-256 hash function.
	 */
	public final static Sha256 FUNCTION = new Sha256();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new Sha256Hasher(true));
	}
	
	
	/**
	 * Returns the name of this hash function: <code>SHA-256</code>.
	 * @return <code>"SHA-256"</code>
	 */
	@Override
	public String getName() {
		return "SHA-256";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>32</code> bytes (256 bits).
	 * @return <code>32</code>
	 */
	@Override
	public int getHashLength() {
		return 32;
	}
	
	
	/**
	 * Returns the block length of this hash function: <code>64</code> bytes (512 bits).
	 * @return <code>64</code>
	 */
	@Override
	public int getBlockLength() {
		return 64;
	}
	
	
	public Cipherer newCipherer(Shacal2 cipher, byte[] key) {
		return new Shacal2Cipherer(cipher, key);
	}
	
	
	
	private Sha256() {}
	
}