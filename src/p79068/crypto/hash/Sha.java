package p79068.crypto.hash;

import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.Shacal;
import p79068.hash.Hasher;


/**
 * The SHA hash function family, described in FIPS Publication 180.
 */
public final class Sha extends AbstractBlockHashFunction {
	
	/**
	 * The original SHA hash function, also known as SHA-0. {@code name = "SHA"}, {@code hashLength = 20}, {@code blockLength = 64}. Described in FIPS Publication 180, 1993-05-11.
	 */
	public final static Sha SHA_FUNCTION = new Sha("SHA", 20, 64);
	
	
	/**
	 * The SHA-1 hash function. {@code name = "SHA-1"}, {@code hashLength = 20}, {@code blockLength = 64}.
	 */
	public final static Sha SHA1_FUNCTION = new Sha("SHA-1", 20, 64);
	
	
	/**
	 * The SHA-224 hash function. {@code name = "SHA-224"}, {@code hashLength = 28}, {@code blockLength = 64}.
	 */
	public final static Sha SHA224_FUNCTION = new Sha("SHA-224", 28, 64);
	
	
	/**
	 * The SHA-256 hash function. {@code name = "SHA-256"}, {@code hashLength = 32}, {@code blockLength = 64}.
	 */
	public final static Sha SHA256_FUNCTION = new Sha("SHA-256", 32, 64);
	
	
	/**
	 * The SHA-384 hash function. {@code name = "SHA-384"}, {@code hashLength = 48}, {@code blockLength = 128}.
	 */
	public final static Sha SHA384_FUNCTION = new Sha("SHA-384", 48, 128);
	
	
	/**
	 * The SHA-512 hash function. {@code name = "SHA-512"}, {@code hashLength = 64}, {@code blockLength = 128}.
	 */
	public final static Sha SHA512_FUNCTION = new Sha("SHA-512", 64, 128);
	
	
	/**
	 * The SHA-512/224 hash function. {@code name = "SHA-512/224"}, {@code hashLength = 28}, {@code blockLength = 128}.
	 */
	public final static Sha SHA512_224_FUNCTION = new Sha("SHA-512/224", 28, 128);
	
	
	/**
	 * The SHA-512/256 hash function. {@code name = "SHA-512/256"}, {@code hashLength = 32}, {@code blockLength = 128}.
	 */
	public final static Sha SHA512_256_FUNCTION = new Sha("SHA-512/256", 32, 128);
	
	
	
	private Sha(String name, int hashLen, int blockLen) {
		super(name, hashLen, blockLen);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		if      (this == SHA_FUNCTION   ) return new BlockHasher(this, new Sha1Core(false));
		else if (this == SHA1_FUNCTION  ) return new BlockHasher(this, new FastSha1Core());
		else if (this == SHA224_FUNCTION) return new BlockHasher(this, new FastSha256Core(false));
		else if (this == SHA256_FUNCTION) return new BlockHasher(this, new FastSha256Core(true));
		else if (this == SHA384_FUNCTION || this == SHA512_FUNCTION || this == SHA512_224_FUNCTION || this == SHA512_256_FUNCTION)
			return new BlockHasher(this, new FastSha512Core(getHashLength()));
		else throw new AssertionError();
	}
	
	
	public static Cipherer newShacalCipherer(Shacal cipher, byte[] key) {
		if (cipher.isShacal1())
			return new Shacal1Cipherer(cipher, key);
		else
			return new Shacal2Cipherer(cipher, key);
	}
	
}
