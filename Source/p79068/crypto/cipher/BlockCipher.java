/*
Block Cipher

Each block must be encrypted independently of other blocks, and the internal state of the cipher must not change.
Therefore, a stream cipher is not a block cipher.
*/


package p79068.crypto.cipher;


/**
An abstract block cipher in ECB (electronic codebook) mode.
<p>Mutability: <em>Immutable</em>, except for being Zeroizable</p>
*/
public abstract class BlockCipher extends Cipher {
	
	public BlockCipher() {}
}