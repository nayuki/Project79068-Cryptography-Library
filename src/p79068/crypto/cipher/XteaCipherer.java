/*
 * Operates natively on 32-bit integers. For bytes, big-endian serialization is assumed.
 */


package p79068.crypto.cipher;

import p79068.Assert;
import p79068.crypto.Zeroizer;


final class XteaCipherer extends AbstractCipherer {
	
	private int[] int32Key;
	
	
	
	XteaCipherer(Tea cipher, byte[] key) {
		super(cipher, key);
		int32Key = new int[4];
		for (int i = 0; i < int32Key.length; i++) {
			int32Key[i] = (key[i * 4 + 0] & 0xFF) << 24
			            | (key[i * 4 + 1] & 0xFF) << 16
			            | (key[i * 4 + 2] & 0xFF) <<  8
			            | (key[i * 4 + 3] & 0xFF) <<  0;
		}
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
		if (len % 8 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		// For each block of 8 bytes
		for (int end = off + len; off < end; off += 8) {
			
			// Pack bytes into int32s in big endian
			int y = (b[off + 0] & 0xFF) << 24
			      | (b[off + 1] & 0xFF) << 16
			      | (b[off + 2] & 0xFF) <<  8
			      | (b[off + 3] & 0xFF) <<  0;
			int z = (b[off + 4] & 0xFF) << 24
			      | (b[off + 5] & 0xFF) << 16
			      | (b[off + 6] & 0xFF) <<  8
			      | (b[off + 7] & 0xFF) <<  0;
			
			// The 32 rounds
			for (int i = 0, sum = 0; i < 32; i++) {
				y += (((z << 4) ^ (z >>> 5)) + z) ^ (sum + int32Key[sum & 3]);
				sum += 0x9E3779B9;
				z += (((y << 4) ^ (y >>> 5)) + y) ^ (sum + int32Key[(sum >>> 11) & 3]);
			}
			
			// Unpack int32s into bytes in big endian
			b[off + 0] = (byte)(y >>> 24);
			b[off + 1] = (byte)(y >>> 16);
			b[off + 2] = (byte)(y >>>  8);
			b[off + 3] = (byte)(y >>>  0);
			b[off + 4] = (byte)(z >>> 24);
			b[off + 5] = (byte)(z >>> 16);
			b[off + 6] = (byte)(z >>>  8);
			b[off + 7] = (byte)(z >>>  0);
		}
	}
	
	
	@Override
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
		if (len % 8 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		// For each block of 8 bytes
		for (int end = off + len; off < end; off += 8) {
			
			// Pack bytes into int32s in big endian
			int y = (b[off + 0] & 0xFF) << 24
			      | (b[off + 1] & 0xFF) << 16
			      | (b[off + 2] & 0xFF) <<  8
			      | (b[off + 3] & 0xFF) <<  0;
			int z = (b[off + 4] & 0xFF) << 24
			      | (b[off + 5] & 0xFF) << 16
			      | (b[off + 6] & 0xFF) <<  8
			      | (b[off + 7] & 0xFF) <<  0;
			
			// The 32 rounds
			for (int i = 0, sum = 0xC6EF3720; i < 32; i++) {
				z -= (((y << 4) ^ (y >>> 5)) + y) ^ (sum + int32Key[(sum >>> 11) & 3]);
				sum -= 0x9E3779B9;
				y -= (((z << 4) ^ (z >>> 5)) + z) ^ (sum + int32Key[sum & 3]);
			}
			
			// Unpack int32s into bytes in big endian
			b[off + 0] = (byte)(y >>> 24);
			b[off + 1] = (byte)(y >>> 16);
			b[off + 2] = (byte)(y >>>  8);
			b[off + 3] = (byte)(y >>>  0);
			b[off + 4] = (byte)(z >>> 24);
			b[off + 5] = (byte)(z >>> 16);
			b[off + 6] = (byte)(z >>>  8);
			b[off + 7] = (byte)(z >>>  0);
		}
	}
	
	
	@Override
	public XteaCipherer clone() {
		XteaCipherer result = (XteaCipherer)super.clone();
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
