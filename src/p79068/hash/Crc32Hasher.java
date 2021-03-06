/* 
 * The polynomial: x^32 + x^26 + x^23 + x^22 + x^16 + x^12 + x^11 + x^10 + x^8 + x^7 + x^5 + x^4 + x^2 + x^1 + x^0
 * This implementation is right-shift-based, implicitly reversing bits in the input byte and reversing the final CRC.
 */

package p79068.hash;

import p79068.Assert;
import p79068.math.IntegerBitMath;


final class Crc32Hasher extends AbstractHasher {
	
	private int register;
	
	
	
	Crc32Hasher(Crc hashFunc) {
		super(hashFunc);
		register = 0xFFFFFFFF;
	}
	
	
	
	@Override
	public void update(byte b) {
		register = (register >>> 8) ^ XOR_TABLE[(register ^ b) & 0xFF];
	}
	
	
	@Override
	public void update(byte[] b, int off, int len) {
		Assert.assertRangeInBounds(b.length, off, len);
		int reg = register;
		for (int i = off, end = off + len; i < end; i++)
			reg = (reg >>> 8) ^ XOR_TABLE[(reg ^ b[i]) & 0xFF];
		register = reg;
	}
	
	
	@Override
	public HashValue getHash() {
		return new HashValue(IntegerBitMath.toBytesBigEndian(new int[]{~register}));
	}
	
	
	
	private static final int[] XOR_TABLE;
	
	static {
		XOR_TABLE = new int[256];
		final int POLYNOMIAL = 0xEDB88320;
		for (int i = 0; i < 256; i++) {
			int reg = i;
			for (int j = 0; j < 8; j++)
				reg = (reg >>> 1) ^ ((reg & 1) * POLYNOMIAL);
			XOR_TABLE[i] = reg;
		}
	}
	
}
