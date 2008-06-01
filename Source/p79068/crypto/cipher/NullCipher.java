package p79068.crypto.cipher;


/**
 * The null (identity) cipher. Encryption and decryption both do not change the message.
 * <p>Instantiability: <em>Singleton</em></p>
 */
public final class NullCipher extends BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final NullCipher CIPHER = new NullCipher();
	
	
	
	public Cipherer newCipherer(byte[] key) {
		if (key.length != 0)
			throw new IllegalArgumentException();
		return new NullCipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: <samp>Null cipher</samp>.
	 * @return <code>"Null cipher"</code>
	 */
	public String getName() {
		return "Null cipher";
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm: <samp>0</samp> bytes.
	 * @return <code>0</code>
	 */
	public int getKeyLength() {
		return 0;
	}
	
	
	/**
	 * Returns the block length of this cipher algorithm: <samp>1</samp> bytes.
	 * @return <code>1</code>
	 */
	public int getBlockLength() {
		return 1;
	}
	
	
	
	private NullCipher() {}
	
}