package p79068.crypto.hash;

import p79068.hash.AbstractHashFunction;
import p79068.hash.Hasher;


/**
 * The Keccak hash function, soon to be standardized by NIST as SHA-3.
 */
public final class Keccak extends AbstractHashFunction {
	
	/**
	 * The Keccak-224 hash function. {@code name = "Keccak-224"}, {@code hashLength = 28}.
	 */
	public final static Keccak KECCAK_224_FUNCTION = new Keccak("Keccak-224", 28);
	
	/**
	 * The Keccak-256 hash function. {@code name = "Keccak-256"}, {@code hashLength = 32}.
	 */
	public final static Keccak KECCAK_256_FUNCTION = new Keccak("Keccak-256", 32);
	
	/**
	 * The Keccak-384 hash function. {@code name = "Keccak-384"}, {@code hashLength = 48}.
	 */
	public final static Keccak KECCAK_384_FUNCTION = new Keccak("Keccak-384", 48);
	
	/**
	 * The Keccak-512 hash function. {@code name = "Keccak-512"}, {@code hashLength = 64}.
	 */
	public final static Keccak KECCAK_512_FUNCTION = new Keccak("Keccak-512", 64);
	
	
	
	private Keccak(String name, int hashLen) {
		super(name, hashLen);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new KeccakHasher(this);
	}
	
}
