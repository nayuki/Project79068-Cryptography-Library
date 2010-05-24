package p79068.crypto.cipher;

import p79068.lang.NullChecker;


/**
 * The null (identity) cipher. Encryption and decryption both do not change the message.
 * <p>Instantiability: <em>Singleton</em></p>
 */
public final class NullCipher extends BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final NullCipher CIPHER = new NullCipher();
	
	
	
	@Override
	public Cipherer newCipherer(byte[] key) {
		NullChecker.check(key);
		if (key.length != 0)
			throw new IllegalArgumentException();
		return new NullCipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: {@code Null cipher}.
	 * @return {@code "Null cipher"}
	 */
	@Override
	public String getName() {
		return "Null cipher";
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm: {@code 0} bytes.
	 * @return {@code 0}
	 */
	@Override
	public int getKeyLength() {
		return 0;
	}
	
	
	/**
	 * Returns the block length of this cipher algorithm: {@code 1} bytes.
	 * @return {@code 1}
	 */
	@Override
	public int getBlockLength() {
		return 1;
	}
	
	
	
	private NullCipher() {}
	
}