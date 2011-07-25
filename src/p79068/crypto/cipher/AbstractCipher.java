package p79068.crypto.cipher;

import p79068.lang.NullChecker;


/**
 * A cipher algorithm with some functionality implemented for convenience.
 * @see Cipher
 */
public abstract class AbstractCipher implements Cipher {
	
	private final String name;
	
	private final int blockLength;
	
	private final int keyLength;
	
	
	
	/**
	 * Constructs a cipher with the specified name, block length, and key length.
	 * @param name the name of the cipher
	 * @param blockLength the block length of the cipher, in bytes
	 * @param keyLength the key length of the cipher, in bytes
	 */
	protected AbstractCipher(String name, int blockLength, int keyLength) {
		NullChecker.check(name);
		if (blockLength <= 0)
			throw new IllegalArgumentException("Non-positive block length");
		if (keyLength < 0)
			throw new IllegalArgumentException("Negative key length");
		
		this.name = name;
		this.blockLength = blockLength;
		this.keyLength = keyLength;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cipherer newCipherer(byte[] key) {
		NullChecker.check(key);
		if (key.length != keyLength)
			throw new IllegalArgumentException("Invalid key length");
		return newCiphererUnchecked(key);
	}
	
	
	/**
	 * Returns a new cipherer of this algorithm with the specified key. This is the same as {@link #newCipherer(byte[])}, but it is guaranteed that {@code key != null && key.length == getKeyLength()}.
	 * @param key the key to be used
	 * @return a new cipherer of this algorithm with the specified key
	 */
	protected abstract Cipherer newCiphererUnchecked(byte[] key);
	
	
	/**
	 * Returns the name of this cipher algorithm. The value does not change for the lifetime of the cipher object.
	 * @return the name of this cipher algorithm
	 */
	public final String getName() {
		return name;
	}
	
	
	/**
	 * Returns the block length of this cipher algorithm, in bytes. Encryptions and decryptions only act on complete blocks. The value does not change for the lifetime of the cipher object.
	 * @return the block length of this cipher algorithm, in bytes
	 */
	public final int getBlockLength() {
		return blockLength;
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm, in bytes. The value does not change for the lifetime of the cipher object.
	 * @return the key length of this cipher algorithm, in bytes
	 */
	public final int getKeyLength() {
		return keyLength;
	}
	
	
	/**
	 * Returns a string representation of this cipher. The format is: {@code "cipherName (keyLength-bit)"}. This is subject to change.
	 * @return a string representation of this cipher
	 */
	@Override
	public String toString() {
		return String.format("%s (%d-bit)", name, keyLength);
	}
	
}
