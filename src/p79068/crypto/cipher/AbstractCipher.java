package p79068.crypto.cipher;


/**
 * An abstract cipher algorithm.
 * @see Cipherer
 */
public abstract class AbstractCipher {
	
	private final String name;
	
	private int blockLength;
	
	private int keyLength;
	
	
	
	public AbstractCipher(String name, int blockLength, int keyLength) {
		this.name = name;
		this.blockLength = blockLength;
		this.keyLength = keyLength;
	}
	
	
	/**
	 * Returns a new cipherer, which is used to encrypt and decrypt byte blocks.
	 */
	public abstract Cipherer newCipherer(byte[] key);
	
	
	/**
	 * Returns the name of this cipher algorithm.
	 * @return the name of this cipher algorithm
	 */
	public final String getName() {
		return name;
	}
	
	
	/**
	 * Returns the block length of this cipher algorithm, in bytes. Encryptions and decryptions only act on complete blocks.
	 * @return the block length of this cipher algorithm, in bytes
	 */
	public final int getBlockLength() {
		return blockLength;
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm, in bytes.
	 * @return the key length of this cipher algorithm, in bytes
	 */
	public final int getKeyLength() {
		return keyLength;
	}
	
	
	/**
	 * Returns a string representation of this cipher. The format is: {@code "<var>name</var> cipher"}. This is subject to change.
	 */
	@Override
	public String toString() {
		return String.format("%s cipher", getName());
	}
	
}
