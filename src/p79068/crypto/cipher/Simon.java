package p79068.crypto.cipher;


public final class Simon extends AbstractCipher implements BlockCipher {
	
	public static final Simon SIMON32_64_CIPHER   = new Simon( 32,  64, 32, 0);
	public static final Simon SIMON48_72_CIPHER   = new Simon( 48,  72, 36, 0);
	public static final Simon SIMON48_96_CIPHER   = new Simon( 48,  96, 36, 1);
	public static final Simon SIMON64_96_CIPHER   = new Simon( 64,  96, 42, 2);
	public static final Simon SIMON64_128_CIPHER  = new Simon( 64, 128, 44, 3);
	public static final Simon SIMON96_96_CIPHER   = new Simon( 96,  96, 52, 2);
	public static final Simon SIMON96_144_CIPHER  = new Simon( 96, 144, 54, 3);
	public static final Simon SIMON128_128_CIPHER = new Simon(128, 128, 68, 2);
	public static final Simon SIMON128_192_CIPHER = new Simon(128, 192, 69, 3);
	public static final Simon SIMON128_256_CIPHER = new Simon(128, 256, 72, 4);
	
	
	public static Simon getInstance(int blockBits, int keyBits) {
		if (blockBits ==  32 && keyBits ==  64) return SIMON32_64_CIPHER;
		if (blockBits ==  48 && keyBits ==  72) return SIMON48_72_CIPHER;
		if (blockBits ==  48 && keyBits ==  96) return SIMON48_96_CIPHER;
		if (blockBits ==  64 && keyBits ==  96) return SIMON64_96_CIPHER;
		if (blockBits ==  64 && keyBits == 128) return SIMON64_128_CIPHER;
		if (blockBits ==  96 && keyBits ==  96) return SIMON96_96_CIPHER;
		if (blockBits ==  96 && keyBits == 144) return SIMON96_144_CIPHER;
		if (blockBits == 128 && keyBits == 128) return SIMON128_128_CIPHER;
		if (blockBits == 128 && keyBits == 192) return SIMON128_192_CIPHER;
		if (blockBits == 128 && keyBits == 256) return SIMON128_256_CIPHER;
		throw new IllegalArgumentException("Invalid block or key length");
	}
	
	
	
	// For SimonCipherer
	final int numRounds;
	final int zIndex;
	
	
	
	private Simon(int blockBits, int keyBits, int numRounds, int zIndex) {
		super("Simon" + blockBits + "/" + keyBits, blockBits / 8, keyBits / 8);
		this.numRounds = numRounds;
		this.zIndex = zIndex;
	}
	
	
	
	protected Cipherer newCiphererUnchecked(byte[] key) {
		return new SimonCipherer(this, key);
	}
	
}
