package p79068.hash;


/**
 * The family of all-zero hash functions, always returning an array of all-zero bytes for any input.
 */
public final class ZeroHash extends AbstractHashFunction {
	
	/**
	 * Constructs a zero hash function with the specified hash length in bytes.
	 * @param hashLen the output hash length, in bytes
	 */
	public ZeroHash(int hashLen) {
		super("Zero Hash", hashLen);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new ZeroHasher(this);
	}
	
}
