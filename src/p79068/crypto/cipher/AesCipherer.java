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

import p79068.lang.BoundsChecker;


final class AesCipherer extends RijndaelCipherer {
	
	AesCipherer(Aes cipher, byte[] key) {
		super(cipher, key);
		if (cipher.getBlockLength() != 16)
			throw new AssertionError();
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % 8 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		byte[] block = new byte[16];  // Column-major indexed
		byte[] temp = new byte[16];
		for (int end = off + len; off < end; off += 16) {  // For each block of 16 bytes
			System.arraycopy(b, off, block, 0, 16);
			encrypt(block, temp);
			System.arraycopy(temp, 0, b, off, 16);
		}
	}
	
	
	@Override
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % 8 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		byte[] block = new byte[16];  // Column-major indexed
		byte[] temp = new byte[16];
		for (int end = off + len; off < end; off += 16) {  // For each block of 16 bytes
			System.arraycopy(b, off, block, 0, 16);
			decrypt(block, temp);
			System.arraycopy(temp, 0, b, off, 16);
		}
	}
	
	
	
	@Override
	protected void subBytes(byte[] block) {
		for (int i = 0; i < 16; i++)
			block[i] = SBOX[block[i] & 0xFF];
	}
	
	
	@Override
	protected void shiftRows(byte[] blockin, byte[] blockout) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++)
				blockout[i + j * 4] = blockin[i + (j + i) % 4 * 4];
		}
	}
	
	
	@Override
	protected void mixColumns(byte[] blockin, byte[] blockout) {
		for (int i = 0; i < 16; i += 4) {
			for (int j = 0; j < 4; j++)
				blockout[i + j] = (byte)(
						  mul02[blockin[i + (j + 0) % 4] & 0xFF]
						^ mul03[blockin[i + (j + 1) % 4] & 0xFF]
						^ blockin[i + (j + 2) % 4]
						^ blockin[i + (j + 3) % 4]);
		}
	}
	
	
	// Self-inverting
	@Override
	protected void addRoundKey(byte[] block, byte[] key) {
		for (int i = 0; i < 16; i++)
			block[i] ^= key[i];
	}
	
	
	@Override
	protected void subBytesInverse(byte[] block) {
		for (int i = 0; i < 16; i++)
			block[i] = SBOX_INVERSE[block[i] & 0xFF];
	}
	
	
	@Override
	protected void shiftRowsInverse(byte[] blockin, byte[] blockout) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++)
				blockout[i + j * 4] = blockin[i + (j - i + 4) % 4 * 4];
		}
	}
	
	
	@Override
	protected void mixColumnsInverse(byte[] blockin, byte[] blockout) {
		for (int i = 0; i < 16; i += 4) {
			for (int j = 0; j < 4; j++)
				blockout[i + j] = (byte)(
						  mul0E[blockin[i + (j + 0) % 4] & 0xFF]
						^ mul0B[blockin[i + (j + 1) % 4] & 0xFF]
						^ mul0D[blockin[i + (j + 2) % 4] & 0xFF]
						^ mul09[blockin[i + (j + 3) % 4] & 0xFF]);
		}
	}
	
}
