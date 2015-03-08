package p79068.crypto.cipher;

import p79068.Assert;
import p79068.crypto.Zeroizer;


final class SimonCipherer extends AbstractCipherer {
	
	private long[] keySch;
	
	
	
	SimonCipherer(Simon cipher, byte[] key) {
		super(cipher, key);
		
		// Get parameters
		long z = Z[cipher.zIndex];
		int n = cipher.getBlockLength() * 4;  // Word width in bits
		int m = cipher.getKeyLength() * 8 / n;  // Number of key words
		
		// Key schedule expansion
		keySch = new long[cipher.numRounds];
		int bpw = n / 8;  // Bytes per word
		for (int i = 0; i < cipher.getKeyLength(); i++)
			keySch[m - 1 - i / bpw] |= (key[i] & 0xFFL) << ((bpw - 1 - i % bpw) * 8);
		long mask = getMask(n);
		for (int i = m; i < keySch.length; i++) {
			long temp = shiftRight(keySch[i - 1], n, 3);
			if (m == 4)
				temp ^= keySch[i - 3];
			temp ^= shiftRight(temp, n, 1);
			keySch[i] = (keySch[i - m] ^ temp ^ ((z >>> ((i - m) % 62)) & 1) ^ ~3) & mask;
		}
	}
	
	
	
	public void encrypt(byte[] b, int off, int len) {
		crypt(b, off, len, true);
	}
	
	
	public void decrypt(byte[] b, int off, int len) {
		crypt(b, off, len, false);
	}
	
	
	private void crypt(byte[] b, int off, int len, boolean encrypt) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
		if (len % cipher.getBlockLength() != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		int width = cipher.getBlockLength() * 4;  // Word width
		int bpw = width / 8;  // Bytes per word
		long mask = getMask(width); 
		for (int i = off, end = off + len; i < end; i += bpw * 2) {
			// Pack bytes into two words each representing half a block
			long x = 0;
			long y = 0;
			for (int j = 0; j < bpw; j++) {
				x = (x << 8) | (b[i + j      ] & 0xFF);
				y = (y << 8) | (b[i + j + bpw] & 0xFF);
			}
			
			// Feistel cipher
			if (encrypt) {
				for (int j = 0; j < keySch.length; j++) {
					long temp = x;
					long s1 = ((x << 1) | (x >>> (width - 1))) & mask;
					long s2 = ((x << 2) | (x >>> (width - 2))) & mask;
					long s8 = ((x << 8) | (x >>> (width - 8))) & mask;
					x = y ^ (s1 & s8) ^ s2 ^ keySch[j];
					y = temp;
				}
			} else {
				for (int j = keySch.length - 1; j >= 0; j--) {
					long temp = y;
					long s1 = ((y << 1) | (y >>> (width - 1))) & mask;
					long s2 = ((y << 2) | (y >>> (width - 2))) & mask;
					long s8 = ((y << 8) | (y >>> (width - 8))) & mask;
					y = x ^ (s1 & s8) ^ s2 ^ keySch[j];
					x = temp;
				}
			}
			
			// Unpack two words into bytes
			for (int j = 0; j < bpw; j++) {
				int shift = (bpw - 1 - j) << 3;
				b[i + j      ] = (byte)(x >>> shift);
				b[i + j + bpw] = (byte)(y >>> shift);
			}
		}
	}
	
	
	public void zeroize() {
		if (cipher == null)
			return;
		keySch = Zeroizer.clear(keySch);
		super.zeroize();
	}
	
	
	private static long shiftRight(long x, int width, int shift) {
		long mask = getMask(width);
		return ((x << (width - shift)) | (x >>> shift)) & mask;
	}
	
	
	private static long getMask(int width) {
		if (width == 64)
			return ~0L;
		else if (width >= 1 && width < 64)
			return (1L << width) - 1;
		else
			throw new IllegalArgumentException();
	}
	
	
	// Round constants for key schedule expansion, 62-bit sequences packed in little endian
	private static final long[] Z = {
		0x19C3522FB386A45FL,
		0x16864FB8AD0C9F71L,
		0x3369F885192C0EF5L,
		0x3C2CE51207A635DBL,
		0x3DC94C3A046D678BL,
	};
	
}
