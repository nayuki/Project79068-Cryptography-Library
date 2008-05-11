/*
Block Cipher

Each block must be encrypted independently of other blocks, and the internal state of the cipher must not change.
Therefore, a stream cipher is not a block cipher.
*/


package p79068.crypto.cipher;


/**
 * An abstract block cipher in ECB (electronic codebook) mode.
 * <p>Mutability: <em>Immutable</em>, except for being Zeroizable</p>
 */
public abstract class BlockCipher extends Cipher {
	
	protected BlockCipher() {}
	
	
	/**
	 * Returns a new cipherer, which is used to encrypt and decrypt byte blocks. The cipherer returned must be immutable except for being zeroizable. In particular, its encryption and decryption functions must be pure. That means for encryption, the result must be the same each time the same message is given. The same applies for decryption.
	 */
	public abstract Cipherer newCipherer(byte[] key);
	
}