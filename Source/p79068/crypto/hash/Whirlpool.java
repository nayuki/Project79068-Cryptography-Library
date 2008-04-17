package p79068.crypto.hash;

import p79068.util.hash.Hasher;
import p79068.util.hash.HashFunction;


/**
The Whirlpool hash function (version 2).
<p>Revisions of the Whirlpool function:</p>
<ul><li>Version 0: The original Whirlpool algorithm (Whirlpool-0)</li>
 <li>Version 1: S-box changed (Whirlpool-1)</li>
 <li>Version 2: Linear mixing changed (current version, just Whirlpool)</li></ul>
<p>Mutability: <em>Immutable</em><br>
 Instantiability: <em>Singleton</em></p>
@see HashFunction
@see Whirlpool0
@see Whirlpool1
*/
public class Whirlpool extends BlockHashFunction {
	
	/** The singleton instance of the Whirlpool hash function. */
	public final static Whirlpool FUNCTION = new Whirlpool();
	
	
	public Hasher newHasher() {
		return new FastWhirlpoolHasher(this);
	}
	
	
	/** Returns the name of this hash function: <samp>Whirlpool</samp>. */
	public String getName() {
		return "Whirlpool";
	}
	
	/** Returns the length of hash values produced by this hash function: <samp>64</samp> bytes (512 bits). */
	public int getHashLength() {
		return 64;
	}
	
	/** Returns the block length of this hash function: <samp>64</samp> bytes (512 bits). */
	public int getBlockLength() {
		return 64;
	}
	
	
	private Whirlpool() {}
}