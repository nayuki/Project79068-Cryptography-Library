package p79068.crypto.cipher;


/**
 * The identity (null) stream cipher. Encryption and decryption both do not change the message.
 * <p>Instantiability: <em>Singleton</em></p>
 */
public final class IdentityStreamCipher extends AbstractStreamCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final IdentityStreamCipher CIPHER = new IdentityStreamCipher();
	
	
	
	private IdentityStreamCipher() {
		super("Identity stream cipher", 0);
	}
	
	
	
	@Override
	protected StreamCipherer newCiphererUnchecked(byte[] key) {
		return new IdentityStreamCipherer(this, key);
	}
	
}
