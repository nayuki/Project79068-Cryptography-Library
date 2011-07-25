package p79068.crypto.cipher.mode;

import p79068.crypto.Zeroizable;
import p79068.crypto.Zeroizer;
import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.Cipher;
import p79068.crypto.cipher.Cipherer;


/**
 * A cipher using a block cipher in CBC (cipher block chaining) mode.
 * <p>Encryption algorithm:</p>
 * <p><code>ciphertext[-1] = initializationVector<br>
 * ciphertext[i] = encrypt(plaintext[i] XOR ciphertext[i-1])</code></p>
 * <p>Decryption algorithm:</p>
 * <p><code>ciphertext[-1] = initializationVector<br>
 * plaintext[i] = decrypt(ciphertext[i]) XOR ciphertext[i-1]</code></p>
 */
public final class CbcModeCipher extends Cipher implements Zeroizable {
	
	private BlockCipher blockCipher;
	private byte[] key;
	
	
	
	public CbcModeCipher(BlockCipher cipher, byte[] key) {
		if (key.length != cipher.getKeyLength())
			throw new IllegalArgumentException();
		blockCipher = cipher;
		this.key = key.clone();
	}
	
	
	
	@Override
	public Cipherer newCipherer(byte[] initVector) {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		if (initVector.length != blockCipher.getBlockLength())
			throw new IllegalArgumentException();
		return new CbcModeCipherer(this, initVector, blockCipher, key);
	}
	
	
	@Override
	public String getName() {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		return String.format("%s in CBC mode", blockCipher.getName());
	}
	
	
	@Override
	public int getKeyLength() {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		return blockCipher.getBlockLength();
	}
	
	
	@Override
	public int getBlockLength() {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		return blockCipher.getBlockLength();
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
		return obj instanceof CbcModeCipher && blockCipher.equals(((CbcModeCipher)obj).blockCipher);
	}
	
}