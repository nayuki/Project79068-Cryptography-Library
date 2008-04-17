package p79068.crypto.hash;

import p79068.util.hash.Hasher;
import p79068.util.hash.HashFunction;


/**
The MD5 hash function. It is described in RFC 1321.
<p>Mutability: <em>Immutable</em><br>
 Instantiability: <em>Singleton</em></p>
@see HashFunction
*/
public class Md5 extends BlockHashFunction {
	
	/** The singleton instance of the MD5 hash function. */
	public final static Md5 FUNCTION = new Md5();
	
	
	public Hasher newHasher() {
		return new Md5Hasher(this);
	}
	
	
	/** Returns the name of this hash function: <samp>MD5</samp>. */
	public String getName() {
		return "MD5";
	}
	
	/** Returns the length of hash values produced by this hash function: <samp>16</samp> bytes (128 bits). */
	public int getHashLength() {
		return 16;
	}
	
	/** Returns the block length of this hash function: <samp>64</samp> bytes (512 bits). */
	public int getBlockLength() {
		return 64;
	}
	
	
	private Md5() {}
}