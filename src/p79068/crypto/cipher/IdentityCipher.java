package p79068.crypto.cipher;


/**
 * The identity (null) cipher. Encryption and decryption both do not change the message.
 * <p>Instantiability: <em>Singleton</em></p>
 */
public final class IdentityCipher extends AbstractCipher implements BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final IdentityCipher CIPHER = new IdentityCipher();
	
	
	
	private IdentityCipher() {
		super("Identity cipher", 1, 0);
	}
	
	
	
	@Override
	protected Cipherer newCiphererUnchecked(byte[] key) {
		return new IdentityCipherer(this, key);
	}
	
}
