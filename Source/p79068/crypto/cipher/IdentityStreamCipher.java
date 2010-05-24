package p79068.crypto.cipher;

import p79068.lang.NullChecker;


/**
 * The identity (null) stream cipher. Encryption and decryption both do not change the message.
 * <p>Instantiability: <em>Singleton</em></p>
 */
public final class IdentityStreamCipher extends StreamCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final IdentityStreamCipher CIPHER = new IdentityStreamCipher();
	
	
	
	@Override
	public StreamCipherer newCipherer(byte[] key) {
		NullChecker.check(key);
		if (key.length != 0)
			throw new IllegalArgumentException();
		return new IdentityStreamCipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: {@code "Identity stream cipher"}.
	 * @return {@code "Identity stream cipher"}
	 */
	@Override
	public String getName() {
		return "Identity stream cipher";
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm: {@code 0} bytes.
	 * @return {@code 0}
	 */
	@Override
	public int getKeyLength() {
		return 0;
	}
	
	
	
	private IdentityStreamCipher() {}
	
}