package p79068.crypto.cipher;


/**
 * The ChaCha20 stream cipher. It is described in RFC 7539.
 * The 48-byte key has substructures: 32 bytes for the key, followed by
 * 4 bytes for the initial block counter, and finally 12 bytes for the nonce.
 */
public final class Chacha20 extends AbstractStreamCipher {
	
	/**
	 * The singleton instance of this cipher algorithm. {@code name = "ChaCha20"}, @code keyLength = 48}.
	 */
	public static final Chacha20 CIPHER = new Chacha20();
	
	
	
	private Chacha20() {
		super("ChaCha20", 48);
	}
	
	
	
	@Override
	protected StreamCipherer newCiphererUnchecked(byte[] key) {
		return new Chacha20Cipherer(this, key);
	}
	
}
