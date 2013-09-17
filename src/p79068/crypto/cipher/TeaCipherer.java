package p79068.crypto.cipher;

import p79068.Assert;
import p79068.crypto.Zeroizer;


// The cipher's specification operates on 32-bit integers. For byte inputs, big-endian serialization is implemented.
final class TeaCipherer extends AbstractCipherer {
	
	private int[] int32Key;
	
	
	
	TeaCipherer(Tea cipher, byte[] key) {
		super(cipher, key);
		int32Key = new int[4];
		for (int i = 0; i < key.length; i++)
			int32Key[i / 4] |= (key[i] & 0xFF) << ((3 - i % 4) * 8);
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
		if (len % 8 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		int k0 = int32Key[0];
		int k1 = int32Key[1];
		int k2 = int32Key[2];
		int k3 = int32Key[3];
		
		// For each block of 8 bytes
		for (int end = off + len; off < end; off += 8) {
			
			// Pack bytes into int32s in big endian
			int y = b[off    ] << 24 | (b[off + 1] & 0xFF) << 16 | (b[off + 2] & 0xFF) << 8 | (b[off + 3] & 0xFF);
			int z = b[off + 4] << 24 | (b[off + 5] & 0xFF) << 16 | (b[off + 6] & 0xFF) << 8 | (b[off + 7] & 0xFF);
			
			// The 32 rounds
			for (int sum = 0x9E3779B9; sum != 0x6526B0D9; sum += 0x9E3779B9) {
				y += ((z << 4) + k0) ^ (z + sum) ^ ((z >>> 5) + k1);
				z += ((y << 4) + k2) ^ (y + sum) ^ ((y >>> 5) + k3);
			}
			
			// Unpack int32s into bytes in big endian
			b[off    ] = (byte)(y >>> 24);
			b[off + 1] = (byte)(y >>> 16);
			b[off + 2] = (byte)(y >>>  8);
			b[off + 3] = (byte)y;
			b[off + 4] = (byte)(z >>> 24);
			b[off + 5] = (byte)(z >>> 16);
			b[off + 6] = (byte)(z >>>  8);
			b[off + 7] = (byte)z;
		}
	}
	
	
	@Override
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
		if (len % 8 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		int k0 = int32Key[0];
		int k1 = int32Key[1];
		int k2 = int32Key[2];
		int k3 = int32Key[3];
		
		// For each block of 8 bytes
		for (int end = off + len; off < end; off += 8) {
			
			// Pack bytes into int32s in big endian
			int y = b[off    ] << 24 | (b[off + 1] & 0xFF) << 16 | (b[off + 2] & 0xFF) << 8 | (b[off + 3] & 0xFF);
			int z = b[off + 4] << 24 | (b[off + 5] & 0xFF) << 16 | (b[off + 6] & 0xFF) << 8 | (b[off + 7] & 0xFF);
			
			// The 32 rounds
			for (int sum = 0xC6EF3720; sum != 0; sum -= 0x9E3779B9) {
				z -= ((y << 4) + k2) ^ (y + sum) ^ ((y >>> 5) + k3);
				y -= ((z << 4) + k0) ^ (z + sum) ^ ((z >>> 5) + k1);
			}
			
			// Unpack int32s into bytes in big endian
			b[off    ] = (byte)(y >>> 24);
			b[off + 1] = (byte)(y >>> 16);
			b[off + 2] = (byte)(y >>>  8);
			b[off + 3] = (byte)y;
			b[off + 4] = (byte)(z >>> 24);
			b[off + 5] = (byte)(z >>> 16);
			b[off + 6] = (byte)(z >>>  8);
			b[off + 7] = (byte)z;
		}
	}
	
	
	@Override
	public TeaCipherer clone() {
		TeaCipherer result = (TeaCipherer)super.clone();
		result.int32Key = result.int32Key.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (cipher == null)
			return;
		int32Key = Zeroizer.clear(int32Key);
		super.zeroize();
	}
	
}
