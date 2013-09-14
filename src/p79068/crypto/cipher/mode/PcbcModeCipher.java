package p79068.crypto.cipher.mode;

import p79068.crypto.Zeroizable;
import p79068.crypto.Zeroizer;
import p79068.crypto.cipher.AbstractCipher;
import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.Cipherer;


/**
 * A cipher using a block cipher in PCBC (propagating cipher block chaining) mode.
 * <p>Encryption algorithm:</p>
 * <p><code>plainXorCipher[-1] = initializationVector<br>
 * plainXorCipher[i] = plaintext[i] XOR ciphertext[i]<br>
 * ciphertext[i] = encrypt(plaintext[i] XOR plainXorCipher[i-1])</code></p>
 * <p>Decryption algorithm:</p>
 * <p><code>plainXorCipher[-1] = initializationVector<br>
 * plainXorCipher[i] = plaintext[i] XOR ciphertext[i]<br>
 * plaintext[i] = decrypt(ciphertext[i]) XOR plainXorCipher[i-1]</code></p>
 */
public final class PcbcModeCipher extends AbstractCipher implements Zeroizable {
	
	private BlockCipher blockCipher;
	private byte[] key;
	
	
	
	public PcbcModeCipher(BlockCipher cipher, byte[] key) {
		super(cipher.getName() + "-PCBC", cipher.getBlockLength(), cipher.getBlockLength());
		if (key.length != cipher.getKeyLength())
			throw new IllegalArgumentException();
		blockCipher = cipher;
		this.key = key.clone();
	}
	
	
	
	@Override
	protected Cipherer newCiphererUnchecked(byte[] initVector) {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		return new PcbcModeCipherer(this, initVector, blockCipher, key);
	}
	
	
	public void zeroize() {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		Zeroizer.clear(key);
		key = null;
		if (blockCipher instanceof Zeroizable)
			((Zeroizable)blockCipher).zeroize();
		blockCipher = null;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof PcbcModeCipher && blockCipher.equals(((PcbcModeCipher)obj).blockCipher);
	}
	
	
	@Override
	public int hashCode() {
		return blockCipher.hashCode();
	}
	
}
