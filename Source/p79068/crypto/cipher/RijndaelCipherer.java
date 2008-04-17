/*
 * Keep in mind that all values, except any values for indexing arrays, represent polynomials in a finite field modulo another polynomial.
 */


package p79068.crypto.cipher;

import p79068.lang.BoundsChecker;


class RijndaelCipherer extends RijndaelCiphererParent {
	
	protected byte[][] keySchedule; // Key schedule, containing the round keys. The number of rounds is equal to keySchedule.length-1.
	protected int blockLength;
	protected int c1, c2, c3; // Constants for ShiftRows
			

	RijndaelCipherer(Rijndael cipher, byte[] key) {
		super(cipher, key);
		blockLength = cipher.getBlockLength();
		switch (blockLength) {
			case 16:
				c1 = 1;
				c2 = 2;
				c3 = 3;
				break; // 128 bits
			case 20:
				c1 = 1;
				c2 = 2;
				c3 = 3;
				break; // 160 bits
			case 24:
				c1 = 1;
				c2 = 2;
				c3 = 3;
				break; // 192 bits
			case 28:
				c1 = 1;
				c2 = 2;
				c3 = 4;
				break; // 224 bits
			case 32:
				c1 = 1;
				c2 = 3;
				c3 = 4;
				break; // 256 bits
			default:
				throw new AssertionError();
		}
		setKey(key);
	}
	
	
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % blockLength != 0)
			throw new IllegalArgumentException("Invalid block length");
		byte[] block = new byte[blockLength]; // Column-major indexed
		byte[] temp = new byte[blockLength];
		for (len += off; off < len; off += blockLength) {
			System.arraycopy(b, off, block, 0, blockLength);
			encrypt(block, temp);
			System.arraycopy(temp, 0, b, off, blockLength);
		}
	}
	
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % blockLength != 0)
			throw new IllegalArgumentException("Invalid block length");
		byte[] block = new byte[blockLength]; // Column-major indexed
		byte[] temp = new byte[blockLength];
		for (len += off; off < len; off += blockLength) {
			System.arraycopy(b, off, block, 0, blockLength);
			decrypt(block, temp);
			System.arraycopy(temp, 0, b, off, blockLength);
		}
	}
	
	
	public void zeroize() {
		if (cipher == null)
			return;
		blockLength = 0;
		for (int i = 0; i < keySchedule.length; i++) {
			for (int j = 0; j < keySchedule[i].length; j++)
				keySchedule[i][j] = 0;
			keySchedule[i] = null;
		}
		keySchedule = null;
		super.zeroize();
	}
	
	
	protected void setKey(byte[] key) {
		int nk = key.length / 4; // Number of 32-bit blocks in the key
		int nb = blockLength / 4; // Number of 32-bit blocks in the state
		int rounds = Math.max(nk, nb) + 6;
		int[] w = expandKey(key, nb); // Key schedule
		keySchedule = new byte[rounds + 1][];
		for (int i = 0; i < keySchedule.length; i++)
			keySchedule[i] = toBytesBigEndian(w, i * nb, nb);
	}
	
	
	protected void encrypt(byte[] block, byte[] temp) { // The result is placed in temp.
		addRoundKey(block, keySchedule[0]);
		for (int i = 1; i < keySchedule.length - 1; i++) {
			subBytes(block);
			shiftRows(block, temp);
			mixColumns(temp, block);
			addRoundKey(block, keySchedule[i]);
		}
		subBytes(block);
		shiftRows(block, temp);
		addRoundKey(temp, keySchedule[keySchedule.length - 1]);
	}
	
	protected void decrypt(byte[] block, byte[] temp) { // The result is placed in temp.
		addRoundKey(block, keySchedule[keySchedule.length - 1]);
		shiftRowsInverse(block, temp);
		subBytesInverse(temp);
		for (int i = keySchedule.length - 2; i >= 1; i--) {
			addRoundKey(temp, keySchedule[i]);
			mixColumnsInverse(temp, block);
			shiftRowsInverse(block, temp);
			subBytesInverse(temp);
		}
		addRoundKey(temp, keySchedule[0]);
	}
	
	protected void subBytes(byte[] block) {
		for (int i = 0; i < blockLength; i++)
			block[i] = sub[block[i] & 0xFF];
	}
	
	protected void shiftRows(byte[] blockin, byte[] sout) {
		int nb = blockLength / 4; // Number of columns, i.e. the number of elements in a row
		for (int i = 0; i < nb; i++) {
			sout[i * 4 + 0] = blockin[(i) % nb * 4 + 0];
			sout[i * 4 + 1] = blockin[(i + c1) % nb * 4 + 1];
			sout[i * 4 + 2] = blockin[(i + c2) % nb * 4 + 2];
			sout[i * 4 + 3] = blockin[(i + c3) % nb * 4 + 3];
		}
	}
	
	protected void mixColumns(byte[] blockin, byte[] sout) {
		for (int i = 0; i < blockLength; i += 4) {
			for (int j = 0; j < 4; j++)
				sout[i + j] = (byte)(mul02[blockin[i + j] & 0xFF] ^ mul03[blockin[i + (j + 1) % 4] & 0xFF] ^ blockin[i + (j + 2) % 4] ^ blockin[i + (j + 3) % 4]);
		}
	}
	
	protected void addRoundKey(byte[] block, byte[] key) { // Self-inverting
		for (int i = 0; i < blockLength; i++)
			block[i] ^= key[i];
	}
	
	protected void subBytesInverse(byte[] block) {
		for (int i = 0; i < blockLength; i++)
			block[i] = subinv[block[i] & 0xFF];
	}
	
	protected void shiftRowsInverse(byte[] blockin, byte[] sout) {
		int nb = blockLength / 4; // Number of columns, i.e. the number of elements in a row
		for (int i = 0; i < nb; i++) {
			sout[i * 4 + 0] = blockin[(i + nb) % nb * 4 + 0];
			sout[i * 4 + 1] = blockin[(i - c1 + nb) % nb * 4 + 1];
			sout[i * 4 + 2] = blockin[(i - c2 + nb) % nb * 4 + 2];
			sout[i * 4 + 3] = blockin[(i - c3 + nb) % nb * 4 + 3];
		}
	}
	
	protected void mixColumnsInverse(byte[] blockin, byte[] sout) {
		for (int i = 0; i < blockLength; i += 4) {
			for (int j = 0; j < 4; j++)
				sout[i + j] = (byte)(mul0E[blockin[i + j] & 0xFF] ^ mul0B[blockin[i + (j + 1) % 4] & 0xFF] ^ mul0D[blockin[i + (j + 2) % 4] & 0xFF] ^ mul09[blockin[i + (j + 3) % 4] & 0xFF]);
		}
	}
	
	
	protected static byte[] mul02, mul03;
	protected static byte[] mul0E, mul0B, mul0D, mul09;
	
	private static byte[] toBytesBigEndian(int[] ain, int off, int len) {
		byte[] aout = new byte[len * 4];
		for (int i = 0; i < len; i++) {
			aout[i << 2 | 0] = (byte)(ain[off + i] >>> 24);
			aout[i << 2 | 1] = (byte)(ain[off + i] >>> 16);
			aout[i << 2 | 2] = (byte)(ain[off + i] >>> 8);
			aout[i << 2 | 3] = (byte)(ain[off + i] >>> 0);
		}
		return aout;
	}
	
	
	static {
		initMultiplyTable();
	}
	
	private static void initMultiplyTable() {
		mul02 = new byte[256];
		mul03 = new byte[256];
		mul0E = new byte[256];
		mul0B = new byte[256];
		mul0D = new byte[256];
		mul09 = new byte[256];
		for (int i = 0; i < 256; i++) {
			mul02[i] = (byte)multiply(i, 0x02);
			mul03[i] = (byte)multiply(i, 0x03);
			mul0E[i] = (byte)multiply(i, 0x0E);
			mul0B[i] = (byte)multiply(i, 0x0B);
			mul0D[i] = (byte)multiply(i, 0x0D);
			mul09[i] = (byte)multiply(i, 0x09);
		}
	}
}