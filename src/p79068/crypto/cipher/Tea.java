package p79068.crypto.cipher;

import p79068.lang.NullChecker;


/**
 * The TEA (Tiny Encryption Algorithm) and XTEA (Extended TEA) block ciphers.
 * <p>Key length: 128 bits (16 bytes)</p>
 * <p>Block length: 64 bits (8 bytes)</p>
 * <p>Instantiability: <em>Singleton</em></p>
 */
public final class Tea extends AbstractCipher implements BlockCipher {
	
	/**
	 * The TEA cipher algorithm. {@code name = "TEA", blockLength = 8, keyLength = 16}.
	 */
	public static final Tea TEA_CIPHER = new Tea("TEA");
	
	
	/**
	 * The XTEA cipher algorithm, a revision of TEA. {@code name = "XTEA", blockLength = 8, keyLength = 16}.
	 */
	public static final Tea XTEA_CIPHER = new Tea("XTEA");
	
	
	
	private Tea(String name) {
		super(name, 8, 16);
	}
	
	
	
	@Override
	public Cipherer newCipherer(byte[] key) {
		NullChecker.check(key);
		if (key.length != 16)
			throw new IllegalArgumentException();
		
		if (this == TEA_CIPHER)
			return new TeaCipherer(this, key);
		else
			return new XteaCipherer(this, key);
	}
	
}
