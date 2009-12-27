package p79068.crypto.hash;

import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.Cipherer;
import p79068.util.hash.Hasher;


abstract class AbstractWhirlpool extends BlockHashFunction {
	
	public AbstractWhirlpool() {}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new FastWhirlpoolHasher(getParameters()));
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>64</code> bytes (512 bits).
	 * @return <code>64</code>
	 */
	@Override
	public final int getHashLength() {
		return 64;
	}
	
	
	/**
	 * Returns the block length of this hash function: <code>64</code> bytes (512 bits).
	 * @return <code>64</code>
	 */
	@Override
	public final int getBlockLength() {
		return 64;
	}
	
	
	
	abstract WhirlpoolParameters getParameters();
	
	
	public Cipherer newCipherer(BlockCipher cipher, byte[] key) {
		return new WhirlpoolCipherer(cipher, key, getParameters());
	}
	
}