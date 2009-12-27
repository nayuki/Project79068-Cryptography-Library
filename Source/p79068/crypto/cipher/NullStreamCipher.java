package p79068.crypto.cipher;

import p79068.lang.NullChecker;


/**
 * The null (identity) stream cipher. Encryption and decryption both do not change the message.
 * <p>Instantiability: <em>Singleton</em></p>
 */
public final class NullStreamCipher extends StreamCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final NullStreamCipher CIPHER = new NullStreamCipher();
	
	
	
	@Override
	public StreamCipherer newCipherer(byte[] key) {
		NullChecker.check(key);
		if (key.length != 0)
			throw new IllegalArgumentException();
		return new NullStreamCipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: <samp>Null stream cipher</samp>.
	 * @return <code>"Null stream cipher"</code>
	 */
	@Override
	public String getName() {
		return "Null stream cipher";
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm: <samp>0</samp> bytes.
	 * @return <code>0</code>
	 */
	@Override
	public int getKeyLength() {
		return 0;
	}
	
	
	
	private NullStreamCipher() {}
	
}