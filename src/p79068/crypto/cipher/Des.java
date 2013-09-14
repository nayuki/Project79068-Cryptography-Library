package p79068.crypto.cipher;


/**
 * The DES (Data Encryption Standard) block cipher.
 * <p>Block length: 64 bits (8 bytes)</p>
 */
public final class Des extends AbstractCipher implements BlockCipher {
	
	/**
	 * The DES cipher algorithm where all 56 key bits are used. It is expanded to a 64-bit key by adding a padding bit after each block of 7 bits. {@code name = "DES", blockLength = 8, keyLength = 7}.
	 */
	public static final Des DES_56_CIPHER = new Des(7);
	
	/**
	 * The DES cipher algorithm with a padded key. The least-significant bit of each byte is ignored, so the effective key length is 56 bits. {@code name = "DES", blockLength = 8, keyLength = 8}.
	 */
	public static final Des DES_64_CIPHER = new Des(8);
	
	
	
	private Des(int keyLen) {
		super("DES", 8, keyLen);
	}
	
	
	
	@Override
	protected Cipherer newCiphererUnchecked(byte[] key) {
		return new DesCipherer(this, key);
	}
	
}
