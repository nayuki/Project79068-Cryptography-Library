package p79068.crypto.hash;

import p79068.util.hash.Hasher;
import p79068.util.hash.HashFunction;


/**
 * The SHA-512 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public class Sha512 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the SHA-512 hash function.
	 */
	public final static Sha512 FUNCTION = new Sha512();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	public Hasher newHasher() {
		return new Sha512Hasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: <code>SHA-512</code>.
	 */
	public String getName() {
		return "SHA-512";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>64</code> bytes (512 bits).
	 */
	public int getHashLength() {
		return 64;
	}
	
	
	/**
	 * Returns the block length of this hash function: <code>128</code> bytes (1024 bits).
	 */
	public int getBlockLength() {
		return 128;
	}
	
	
	
	private Sha512() {}
	
}