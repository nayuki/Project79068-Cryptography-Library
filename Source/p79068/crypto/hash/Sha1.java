package p79068.crypto.hash;

import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.Shacal1;
import p79068.util.hash.HashFunction;
import p79068.util.hash.Hasher;


/**
 * The SHA-1 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 * Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public final class Sha1 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the SHA-1 hash function.
	 */
	public final static Sha1 FUNCTION = new Sha1();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new FastSha1Hasher());
	}
	
	
	/**
	 * Returns the name of this hash function: <code>SHA-1</code>.
	 * @return <code>"SHA-1"</code>
	 */
	@Override
	public String getName() {
		return "SHA-1";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>20</code> bytes (160 bits).
	 * @return <code>20</code>
	 */
	@Override
	public int getHashLength() {
		return 20;
	}
	
	
	/**
	 * Returns the block length of this hash function: <code>64</code> bytes (512 bits).
	 * @return <code>64</code>
	 */
	@Override
	public int getBlockLength() {
		return 64;
	}
	
	
	
	public Cipherer newCipherer(Shacal1 cipher, byte[] key) {
		return new Shacal1Cipherer(cipher, key);
	}
	
	
	
	private Sha1() {}
	
}