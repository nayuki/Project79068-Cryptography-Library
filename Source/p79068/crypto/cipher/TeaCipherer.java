/*
Operates natively on 32-bit integers. For bytes, big-endian serialization is assumed.
*/


package p79068.crypto.cipher;

import p79068.lang.*;


final class TeaCipherer extends Cipherer {
	
	private int[] int32Key;
	
	
	
	TeaCipherer(Tea cipher, byte[] key) {
		super(cipher, key);
		int32Key = new int[4];
		for (int i = 0; i < int32Key.length; i++)
			int32Key[i] = key[i * 4] << 24 | (key[i * 4 + 1] & 0xFF) << 16 | (key[i * 4 + 2] & 0xFF) << 8 | (key[i * 4 + 3] & 0xFF);
	}
	
	
	
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if ((len & 7) != 0)
			throw new IllegalArgumentException("Invalid block length");
		int k0 = int32Key[0];
		int k1 = int32Key[1];
		int k2 = int32Key[2];
		int k3 = int32Key[3];
		for (len += off; off < len; off += 8) {
			int y = b[off + 0] << 24 | (b[off + 1] & 0xFF) << 16 | (b[off + 2] & 0xFF) << 8 | (b[off + 3] & 0xFF);
			int z = b[off + 4] << 24 | (b[off + 5] & 0xFF) << 16 | (b[off + 6] & 0xFF) << 8 | (b[off + 7] & 0xFF);
			for (int i = 0, sum = 0x9E3779B9; i < 32; i++, sum += 0x9E3779B9) {
				y += ((z << 4) + k0) ^ (z + sum) ^ ((z >>> 5) + k1);
				z += ((y << 4) + k2) ^ (y + sum) ^ ((y >>> 5) + k3);
			}
			b[off + 0] = (byte)(y >>> 24);
			b[off + 1] = (byte)(y >>> 16);
			b[off + 2] = (byte)(y >>> 8);
			b[off + 3] = (byte)(y >>> 0);
			b[off + 4] = (byte)(z >>> 24);
			b[off + 5] = (byte)(z >>> 16);
			b[off + 6] = (byte)(z >>> 8);
			b[off + 7] = (byte)(z >>> 0);
		}
	}
	
	
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if ((len & 7) != 0)
			throw new IllegalArgumentException("Invalid block length");
		int k0 = int32Key[0];
		int k1 = int32Key[1];
		int k2 = int32Key[2];
		int k3 = int32Key[3];
		for (len += off; off < len; off += 8) {
			int y = b[off + 0] << 24 | (b[off + 1] & 0xFF) << 16 | (b[off + 2] & 0xFF) << 8 | (b[off + 3] & 0xFF);
			int z = b[off + 4] << 24 | (b[off + 5] & 0xFF) << 16 | (b[off + 6] & 0xFF) << 8 | (b[off + 7] & 0xFF);
			for (int i = 0, sum = 0xC6EF3720; i < 32; i++, sum -= 0x9E3779B9) {
				z -= ((y << 4) + k2) ^ (y + sum) ^ ((y >>> 5) + k3);
				y -= ((z << 4) + k0) ^ (z + sum) ^ ((z >>> 5) + k1);
			}
			b[off + 0] = (byte)(y >>> 24);
			b[off + 1] = (byte)(y >>> 16);
			b[off + 2] = (byte)(y >>> 8);
			b[off + 3] = (byte)(y >>> 0);
			b[off + 4] = (byte)(z >>> 24);
			b[off + 5] = (byte)(z >>> 16);
			b[off + 6] = (byte)(z >>> 8);
			b[off + 7] = (byte)(z >>> 0);
		}
	}
	
	
	public void zeroize() {
		if (cipher == null)
			return;
		for (int i = 0; i < int32Key.length; i++)
			int32Key[i] = 0;
		int32Key = null;
		super.zeroize();
	}
	
}