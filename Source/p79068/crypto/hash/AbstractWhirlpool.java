package p79068.crypto.hash;

import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.Cipherer;
import p79068.util.hash.Hasher;


public abstract class AbstractWhirlpool extends BlockHashFunction {
	
	
	protected AbstractWhirlpool(String name) {
		super(name, 64, 64);
	}
	
	
	
	/**
	 * Returns the Whirlpool parameters of this Whirlpool hash function.
	 * @return the Whirlpool parameters
	 */
	abstract WhirlpoolParameters getParameters();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new FastWhirlpoolHasher(getParameters()));
	}
	
	
	
	public Cipherer newCipherer(BlockCipher cipher, byte[] key) {
		return new FastWhirlpoolCipherer(cipher, key, getParameters());
	}
	
}