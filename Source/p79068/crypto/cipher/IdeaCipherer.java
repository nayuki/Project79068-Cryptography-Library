/*
 * Addition takes place in the group of integers modulo 65536.
 * Multiplication takes place in the group of non-zero integers modulo 65537, with element 65536 being represented by the bit pattern 0x0000.
 */

package p79068.crypto.cipher;

import p79068.crypto.Zeroizer;
import p79068.lang.BoundsChecker;


final class IdeaCipherer extends Cipherer {
	
	/**
	 * The encryption key schedule.
	 */
	private int[] encKeySch;
	
	/**
	 * The decryption key schedule.
	 */
	private int[] decKeySch;
	
	
	
	public IdeaCipherer(Idea cipher, byte[] key) {
		super(cipher, key);
		setKey(key);
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % 8 != 0)
			throw new IllegalArgumentException("Invalid block length");
		crypt(b, off, len, encKeySch);
	}
	
	
	@Override
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % 8 != 0)
			throw new IllegalArgumentException("Invalid block length");
		crypt(b, off, len, decKeySch);
	}
	
	
	@Override
	public IdeaCipherer clone() {
		IdeaCipherer result = (IdeaCipherer)super.clone();
		result.encKeySch = result.encKeySch.clone();
		result.decKeySch = result.decKeySch.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (cipher == null)
			return;
		encKeySch = Zeroizer.clear(encKeySch);
		decKeySch = Zeroizer.clear(decKeySch);
		super.zeroize();
	}
	
	
	
	private void setKey(byte[] key) {
		encKeySch = new int[52];
		for (int i = 0; i < encKeySch.length; i++)
			encKeySch[i] = getInt16(key, i * 16 + i / 8 * 25);
		
		decKeySch = new int[52];
		decKeySch[0] = reciprocal(encKeySch[48]);
		decKeySch[1] = negate(encKeySch[49]);
		decKeySch[2] = negate(encKeySch[50]);
		decKeySch[3] = reciprocal(encKeySch[51]);
		decKeySch[4] = (encKeySch[46]);
		decKeySch[5] = (encKeySch[47]);
		for (int i = 6; i < 48; i += 6) {
			decKeySch[i + 0] = reciprocal(encKeySch[48 - i + 0]);
			decKeySch[i + 1] = negate(encKeySch[48 - i + 2]);
			decKeySch[i + 2] = negate(encKeySch[48 - i + 1]);
			decKeySch[i + 3] = reciprocal(encKeySch[48 - i + 3]);
			decKeySch[i + 4] = (encKeySch[42 - i + 4]);
			decKeySch[i + 5] = (encKeySch[42 - i + 5]);
		}
		decKeySch[48] = reciprocal(encKeySch[0]);
		decKeySch[49] = negate(encKeySch[1]);
		decKeySch[50] = negate(encKeySch[2]);
		decKeySch[51] = reciprocal(encKeySch[3]);
	}
	
	
	private static void crypt(byte[] b, int off, int len, int[] keySch) {
		// For each block of 8 bytes
		for (int end = off + len; off < end; off += 8) {
			
			// Pack bytes into uint16s in big endian
			int w = (b[off + 0] & 0xFF) << 8 | (b[off + 1] & 0xFF);
			int x = (b[off + 2] & 0xFF) << 8 | (b[off + 3] & 0xFF);
			int y = (b[off + 4] & 0xFF) << 8 | (b[off + 5] & 0xFF);
			int z = (b[off + 6] & 0xFF) << 8 | (b[off + 7] & 0xFF);
			
			// 8 rounds. i represents the offset in the key schedule.
			for (int i = 0; i < 48; i += 6) {
				w = multiply(w, keySch[i + 0]);
				x = (x + keySch[i + 1]) & 0xFFFF;
				y = (y + keySch[i + 2]) & 0xFFFF;
				z = multiply(z, keySch[i + 3]);
				int u = multiply(w ^ y, keySch[i + 4]);
				int v = multiply(((x ^ z) + u) & 0xFFFF, keySch[i + 5]);
				u = (u + v) & 0xFFFF;
				w ^= v;
				x ^= u;
				y ^= v;
				z ^= u;
				int tp = x;
				x = y;
				y = tp;
			}
			w = multiply(w, keySch[48]);
			int tp = (x + keySch[50]) & 0xFFFF;
			x = (y + keySch[49]) & 0xFFFF;
			y = tp;
			z = multiply(z, keySch[51]);
			
			// Unpack uint16s into bytes in big endian
			b[off + 0] = (byte)(w >>> 8);
			b[off + 1] = (byte)(w >>> 0);
			b[off + 2] = (byte)(x >>> 8);
			b[off + 3] = (byte)(x >>> 0);
			b[off + 4] = (byte)(y >>> 8);
			b[off + 5] = (byte)(y >>> 0);
			b[off + 6] = (byte)(z >>> 8);
			b[off + 7] = (byte)(z >>> 0);
		}
	}
	
	
	
	// This has been verified by brute force. But it can be proven mathematically too.
	private static int multiply(int x, int y) {
		if ((x & 0xFFFF) != x || (y & 0xFFFF) != y)
			throw new IllegalArgumentException();
		
		if (x == 0)
			return (0x10001 - y) & 0xFFFF;
		else if (y == 0)
			return (0x10001 - x) & 0xFFFF;
		else {
			int z = x * y;
			z = (z & 0xFFFF) - (z >>> 16);
			if (z < 0)
				z += 0x10001;
			return z & 0xFFFF;
		}
	}
	
	
	private static int negate(int x) {
		if ((x & 0xFFFF) != x)
			throw new IllegalArgumentException();
		return (0x10000 - x) & 0xFFFF;
	}
	
	
	/**
	 * Returns the reciprocal (multiplicative inverse) of the specified element modulo 65537.
	 * @return the reciprocal of the specified element
	 */
	private static int reciprocal(int y) {
		if ((y & 0xFFFF) != y)
			throw new IllegalArgumentException();
		
		if (y == 0)
			return 0;
		int x = 0x10001;
		int a = 0;
		int b = 1;
		while (y != 0) {
			int z = x % y;
			int c = a - x / y * b;
			x = y;
			y = z;
			a = b;
			b = c;
		}
		if (a >= 0)
			return a;
		else
			return a + 0x10001;
	}
	
	
	// Returns 16 consecutive bits from b starting at bitOffset (measured in bits), which is modulo the number of bits in b.
	private static int getInt16(byte[] b, int bitOffset) {
		if (bitOffset < 0)
			throw new IllegalArgumentException();
		int headByte = bitOffset / 8;
		int result = ((b[(headByte + 0) % b.length] & 0xFF) << (8 + bitOffset % 8))
				   | ((b[(headByte + 1) % b.length] & 0xFF) << (bitOffset % 8))
				   | ((b[(headByte + 2) % b.length] & 0xFF) >>> (8 - bitOffset % 8));
		return result & 0xFFFF;
	}
	
}