package p79068.crypto.hash;

import p79068.hash.Hasher;


/**
 * The MD hash function family, including MD2, MD4, MD5.
 * <ul>
 *   <li>MD2 is described in RFC 1319.</li>
 *   <li>MD4 is described in RFC 1320.</li>
 *   <li>MD5 is described in RFC 1321.</li>
 * </ul>
 */
public final class Md extends AbstractBlockHashFunction {
	
	/**
	 * The MD2 hash function. {@code name = "MD2"}, {@code hashLength = 16}, {@code blockLength = 16}.
	 */
	public final static Md MD2_FUNCTION = new Md("MD2", 16);
	
	/**
	 * The MD4 hash function. {@code name = "MD4"}, {@code hashLength = 16}, {@code blockLength = 64}.
	 */
	public final static Md MD4_FUNCTION = new Md("MD4", 64);
	
	/**
	 * The MD5 hash function. {@code name = "MD5"}, {@code hashLength = 16}, {@code blockLength = 64}.
	 */
	public final static Md MD5_FUNCTION = new Md("MD5", 64);
	
	
	
	private Md(String name, int blockLen) {
		super(name, 16, blockLen);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		if      (this == MD2_FUNCTION) return new BlockHasher(this, new Md2Core());
		else if (this == MD4_FUNCTION) return new BlockHasher(this, new FastMd4Core());
		else if (this == MD5_FUNCTION) return new BlockHasher(this, new Md5Core());
		else throw new AssertionError();
	}
	
}
