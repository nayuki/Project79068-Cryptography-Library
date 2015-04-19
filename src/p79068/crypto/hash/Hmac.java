package p79068.crypto.hash;

import java.util.Arrays;
import p79068.Assert;
import p79068.crypto.Zeroizable;
import p79068.hash.AbstractHashFunction;
import p79068.hash.Hasher;


/**
 * An HMAC (keyed hash message authentication code) hash function.
 * <p>The HMAC specification requires a block hash function because the initial state of an HMAC instance need not retain incomplete message bytes.</p>
 * <p>The underlying hash function's block length must not be smaller than its output hash value length.</p>
 * <p>Mutability: <em>Mutable</em></p>
 */
public final class Hmac extends AbstractHashFunction implements Zeroizable {
	
	private Hasher inner;
	private Hasher outer;
	
	
	
	/**
	 * Constructs an HMAC algorithm with the specified underlying block hash function and secret key.
	 * @param hashFunc the underlying block hash function
	 * @param key the secret key
	 * @throws IllegalArgumentException if the hash function's block length is smaller than its hash value length
	 * @throws NullPointerException if the hash function or secret key is {@code null}
	 */
	public Hmac(BlockHashFunction hashFunc, byte[] key) {
		super("HMAC-" + hashFunc.getName(), hashFunc.getHashLength());
		int blockLen = hashFunc.getBlockLength();
		if (blockLen < hashFunc.getHashLength())
			throw new IllegalArgumentException("Block length smaller than hash value length");
		
		// Preprocess the key
		Assert.assertNotNull(key);
		if (key.length > blockLen)
			key = hashFunc.getHash(key).toBytes();
		key = Arrays.copyOf(key, blockLen);
		
		// Set initial state of hashers
		inner = hashFunc.newHasher();
		for (int i = 0; i < key.length; i++)
			key[i] ^= 0x36;
		inner.update(key);
		outer = hashFunc.newHasher();
		for (int i = 0; i < key.length; i++)
			key[i] ^= 0x36 ^ 0x5C;
		outer.update(key);
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		if (outer == null)
			throw new IllegalStateException("Already zeroized");
		return new HmacHasher(this, inner, outer);
	}
	
	
	/**
	 * Clears the state of this object for privacy.
	 * @throws IllegalStateException if this object has been zeroized
	 */
	public void zeroize() {
		if (outer == null)
			throw new IllegalStateException("Already zeroized");
		if (inner instanceof Zeroizable)
			((Zeroizable)inner).zeroize();
		if (outer instanceof Zeroizable)
			((Zeroizable)outer).zeroize();
		inner = null;
		outer = null;
	}
	
}
