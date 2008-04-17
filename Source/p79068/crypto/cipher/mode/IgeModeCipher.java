package p79068.crypto.cipher.mode;

import p79068.crypto.Zeroizable;
import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.Cipher;
import p79068.crypto.cipher.Cipherer;


/**
A cipher using a block cipher in IGE (infinite garble extension) mode.
<p>Encryption algorithm:</p>
<p><code>plaintext[-1] = zeros<br>
ciphertext[-1] = initialization vector<br>
ciphertext[i] = encrypt(plaintext[i] XOR ciphertext[i-1]) XOR plaintext[i-1]</code></p>
<p>Decryption algorithm:</p>
<p><code>plaintext[-1] = zeros<br>
ciphertext[-1] = initialization vector<br>
plaintext[i] = decrypt(ciphertext[i] XOR plaintext[i-1]) XOR ciphertext[i-1]</code></p>
*/
public final class IgeModeCipher extends Cipher implements Zeroizable {
	
	private BlockCipher blockCipher;
	private byte[] key;
	
	
	public IgeModeCipher(BlockCipher cipher, byte[] key) {
		if (key.length != cipher.getKeyLength())
			throw new IllegalArgumentException();
		blockCipher = cipher;
		this.key = key.clone();
	}
	
	
	public Cipherer newCipherer(byte[] initVector) {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		if (initVector.length != blockCipher.getBlockLength())
			throw new IllegalArgumentException();
		return new IgeModeCipherer(this, initVector, blockCipher, key);
	}
	
	
	public String getName() {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		return String.format("%s in IGE mode", blockCipher.getName());
	}
	
	public int getKeyLength() {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		return blockCipher.getBlockLength();
	}
	
	public int getBlockLength() {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		return blockCipher.getBlockLength();
	}
	
	
	public void zeroize() {
		if (blockCipher == null)
			throw new IllegalStateException("Already zeroized");
		for (int i = 0; i < key.length; i++)
			key[i] = 0;
		key = null;
		if (blockCipher instanceof Zeroizable)
			((Zeroizable)blockCipher).zeroize();
		blockCipher = null;
	}
	
	
	public boolean equals(Object obj) {
		return obj instanceof IgeModeCipher && blockCipher.equals(((IgeModeCipher)obj).blockCipher);
	}
}