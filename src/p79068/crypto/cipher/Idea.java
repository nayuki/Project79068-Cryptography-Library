package p79068.crypto.cipher;


/**
 * The IDEA (International Data Encryption Algorithm) block cipher. This cipher is patented.
 * <p>Instantiability: <em>Singleton</em></p>
 */
public final class Idea extends AbstractCipher implements BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm. {@code name = "IDEA"}, {@code blockLength = 8}, {@code keyLength = 16}.
	 */
	public static final Idea CIPHER = new Idea();
	
	
	
	private Idea() {
		super("IDEA", 8, 16);
	}
	
	
	
	@Override
	public Cipherer newCiphererUnchecked(byte[] key) {
		return new IdeaCipherer(this, key);
	}
	
}
