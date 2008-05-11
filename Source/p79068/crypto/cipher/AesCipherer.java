/*
 * Advanced Encryption Standard
 *
 * Key length (bits): 128, 192, 256 (AES); any positive multiple of 32 (Rijndael)
 * Data length (bits): 128
 *
 * Reference: FIPS-197
 *
 *
 * Keep in mind that all values, except any values for indexing arrays, represent polynomials in a finite field modulo another polynomial.
 */


package p79068.crypto.cipher;

import p79068.lang.*;


final class AesCipherer extends RijndaelCipherer {
	
	AesCipherer(Rijndael cipher, byte[] key) {
		super(cipher, key);
		if (cipher.getBlockLength() != 16)
			throw new AssertionError();
		setKey(key);
	}
	
	
	
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if ((len & 0xF) != 0)
			throw new IllegalArgumentException("Invalid block length");
		byte[] block = new byte[16];  // Column-major indexed
		byte[] temp = new byte[16];
		for (len += off; off < len; off += 16) {
			System.arraycopy(b, off, block, 0, 16);
			encrypt(block, temp);
			System.arraycopy(temp, 0, b, off, 16);
		}
	}
	
	
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if ((len & 0xF) != 0)
			throw new IllegalArgumentException("Invalid block length");
		byte[] block = new byte[16];  // Column-major indexed
		byte[] temp = new byte[16];
		for (len += off; off < len; off += 16) {
			System.arraycopy(b, off, block, 0, 16);
			decrypt(block, temp);
			System.arraycopy(temp, 0, b, off, 16);
		}
	}
	
	
	
	protected void subBytes(byte[] block) {
		for (int i = 0; i < 16; i++)
			block[i] = RijndaelUtils.sub[block[i] & 0xFF];
	}
	
	
	protected void shiftRows(byte[] blockin, byte[] blockout) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++)
				blockout[i + j * 4] = blockin[i + (j + i) % 4 * 4];
		}
	}
	
	
	protected void mixColumns(byte[] blockin, byte[] blockout) {
		for (int i = 0; i < 16; i += 4) {
			for (int j = 0; j < 4; j++)
				blockout[i + j] = (byte)(mul02[blockin[i + j] & 0xFF] ^ mul03[blockin[i + (j + 1) % 4] & 0xFF] ^ blockin[i + (j + 2) % 4] ^ blockin[i + (j + 3) % 4]);
		}
	}
	
	
	// Self-inverting
	protected void addRoundKey(byte[] block, byte[] key) {
		for (int i = 0; i < 16; i++)
			block[i] ^= key[i];
	}
	
	
	protected void subByteblockinverse(byte[] block) {
		for (int i = 0; i < 16; i++)
			block[i] = RijndaelUtils.subinv[block[i] & 0xFF];
	}
	
	
	protected void shiftRowblockinverse(byte[] blockin, byte[] blockout) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++)
				blockout[i + j * 4] = blockin[i + (j - i + 4) % 4 * 4];
		}
	}
	
	
	protected void mixColumnblockinverse(byte[] blockin, byte[] blockout) {
		for (int i = 0; i < 16; i += 4) {
			for (int j = 0; j < 4; j++)
				blockout[i + j] = (byte)(mul0E[blockin[i + j] & 0xFF] ^ mul0B[blockin[i + (j + 1) % 4] & 0xFF] ^ mul0D[blockin[i + (j + 2) % 4] & 0xFF] ^ mul09[blockin[i + (j + 3) % 4] & 0xFF]);
		}
	}
	
}