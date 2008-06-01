package p79068.crypto.hash;

import p79068.util.hash.Hasher;
import p79068.util.hash.HashFunction;


/**
 * The SHA-384 hash function.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 * @see Sha512
 */
public final class Sha384 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the SHA-384 hash function.
	 */
	public final static Sha384 FUNCTION = new Sha384();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	public Hasher newHasher() {
		return new Sha512Hasher(this);
	}
	
	
	/**
	 * Returns the name of this hash function: <code>SHA-384</code>.
	 * @return <code>"SHA-384"</code>
	 */
	public String getName() {
		return "SHA-384";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>48</code> bytes (384 bits).
	 * @return <code>48</code>
	 */
	public int getHashLength() {
		return 48;
	}
	
	
	/**
	 * Returns the block length of this hash function: <code>128</code> bytes (1024 bits).
	 * @return <code>128</code>
	 */
	public int getBlockLength() {
		return 128;
	}
	
	
	
	private Sha384() {}
	
}