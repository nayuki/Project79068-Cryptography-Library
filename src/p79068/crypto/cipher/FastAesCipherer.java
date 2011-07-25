package p79068.crypto.cipher;

import p79068.crypto.Zeroizer;
import p79068.lang.BoundsChecker;
import p79068.math.IntegerBitMath;


final class FastAesCipherer extends Cipherer {
	
	/**
	 * Encryption key schedule, containing the round keys (in the order of application).
	 */
	private int[] encKeySch;
	
	/**
	 * Decryption key schedule, containing the round keys (in the order of application).
	 */
	private int[] decKeySch;
	
	/**
	 * The number of rounds. Equal to {@code min(key.length/4, 4) + 6}, where {@code key.length} is in bytes.
	 */
	private final int roundCount;
	
	
	
	FastAesCipherer(Aes cipher, byte[] key) {
		super(cipher, key);
		if (key.length % 4 != 0 || key.length == 0)
			throw new IllegalArgumentException("Invalid key length");
		
		// Set the key
		int nk = key.length / 4;  // Number of 32-bit blocks in the key
		roundCount = Math.max(nk, 4) + 6;
		
		encKeySch = AesUtils.expandKey(key, 4);
		
		decKeySch = new int[encKeySch.length];
		for (int i = 0; i < roundCount + 1; i++) {
			if (i == 0 || i == roundCount)  // Copy directly from encryption key schedule
				System.arraycopy(encKeySch, (roundCount - i) * 4, decKeySch, i * 4, 4);
			else {
				for (int j = 0; j < 4; j++) {
					decKeySch[i * 4 + j] =
						  mulinv0[SBOX[encKeySch[(roundCount - i) * 4 + j] >>> 24 & 0xFF] & 0xFF]
						^ mulinv1[SBOX[encKeySch[(roundCount - i) * 4 + j] >>> 16 & 0xFF] & 0xFF]
						^ mulinv2[SBOX[encKeySch[(roundCount - i) * 4 + j] >>>  8 & 0xFF] & 0xFF]
						^ mulinv3[SBOX[encKeySch[(roundCount - i) * 4 + j] >>>  0 & 0xFF] & 0xFF];
				}
			}
		}
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % 16 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		// For each block of 16 bytes
		for (int end = off + len; off < end; off += 16) {
			
			// Pack bytes into columns. Each variable represents a column. Also, do AddRoundKey.
			int x0 = (b[off +  0] << 24 | (b[off +  1] & 0xFF) << 16 | (b[off +  2] & 0xFF) << 8 | (b[off +  3] & 0xFF)) ^ encKeySch[0];
			int x1 = (b[off +  4] << 24 | (b[off +  5] & 0xFF) << 16 | (b[off +  6] & 0xFF) << 8 | (b[off +  7] & 0xFF)) ^ encKeySch[1];
			int x2 = (b[off +  8] << 24 | (b[off +  9] & 0xFF) << 16 | (b[off + 10] & 0xFF) << 8 | (b[off + 11] & 0xFF)) ^ encKeySch[2];
			int x3 = (b[off + 12] << 24 | (b[off + 13] & 0xFF) << 16 | (b[off + 14] & 0xFF) << 8 | (b[off + 15] & 0xFF)) ^ encKeySch[3];
			
			for (int i = 1; i < roundCount; i++) {
				int y0 = mul0[x0 >>> 24] ^ mul1[x1 >>> 16 & 0xFF] ^ mul2[x2 >>> 8 & 0xFF] ^ mul3[x3 & 0xFF];
				int y1 = mul0[x1 >>> 24] ^ mul1[x2 >>> 16 & 0xFF] ^ mul2[x3 >>> 8 & 0xFF] ^ mul3[x0 & 0xFF];
				int y2 = mul0[x2 >>> 24] ^ mul1[x3 >>> 16 & 0xFF] ^ mul2[x0 >>> 8 & 0xFF] ^ mul3[x1 & 0xFF];
				int y3 = mul0[x3 >>> 24] ^ mul1[x0 >>> 16 & 0xFF] ^ mul2[x1 >>> 8 & 0xFF] ^ mul3[x2 & 0xFF];
				x0 = y0 ^ encKeySch[i << 2 | 0];
				x1 = y1 ^ encKeySch[i << 2 | 1];
				x2 = y2 ^ encKeySch[i << 2 | 2];
				x3 = y3 ^ encKeySch[i << 2 | 3];
			}
			
			// Final SubBytes, ShiftRows, AddRoundKey
			int y0 = (bigsub[(x0 & 0xFF000000 | x1 & 0x00FF0000) >>> 16] << 16 | bigsub[x2 & 0x0000FF00 | x3 & 0x000000FF]) ^ encKeySch[roundCount << 2 | 0];
			int y1 = (bigsub[(x1 & 0xFF000000 | x2 & 0x00FF0000) >>> 16] << 16 | bigsub[x3 & 0x0000FF00 | x0 & 0x000000FF]) ^ encKeySch[roundCount << 2 | 1];
			int y2 = (bigsub[(x2 & 0xFF000000 | x3 & 0x00FF0000) >>> 16] << 16 | bigsub[x0 & 0x0000FF00 | x1 & 0x000000FF]) ^ encKeySch[roundCount << 2 | 2];
			int y3 = (bigsub[(x3 & 0xFF000000 | x0 & 0x00FF0000) >>> 16] << 16 | bigsub[x1 & 0x0000FF00 | x2 & 0x000000FF]) ^ encKeySch[roundCount << 2 | 3];
			
			// Unpack columns into bytes
			b[off +  0] = (byte)(y0 >>> 24);
			b[off +  1] = (byte)(y0 >>> 16);
			b[off +  2] = (byte)(y0 >>>  8);
			b[off +  3] = (byte)(y0 >>>  0);
			b[off +  4] = (byte)(y1 >>> 24);
			b[off +  5] = (byte)(y1 >>> 16);
			b[off +  6] = (byte)(y1 >>>  8);
			b[off +  7] = (byte)(y1 >>>  0);
			b[off +  8] = (byte)(y2 >>> 24);
			b[off +  9] = (byte)(y2 >>> 16);
			b[off + 10] = (byte)(y2 >>>  8);
			b[off + 11] = (byte)(y2 >>>  0);
			b[off + 12] = (byte)(y3 >>> 24);
			b[off + 13] = (byte)(y3 >>> 16);
			b[off + 14] = (byte)(y3 >>>  8);
			b[off + 15] = (byte)(y3 >>>  0);
		}
	}
	
	
	@Override
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % 16 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		// For each block of 16 bytes
		for (int end = off + len; off < end; off += 16) {
			
			// Pack bytes into columns. Each variable represents a column. Also, do AddRoundKey.
			int x0 = (b[off +  0] << 24 | (b[off +  1] & 0xFF) << 16 | (b[off +  2] & 0xFF) << 8 | (b[off +  3] & 0xFF)) ^ decKeySch[0];
			int x1 = (b[off +  4] << 24 | (b[off +  5] & 0xFF) << 16 | (b[off +  6] & 0xFF) << 8 | (b[off +  7] & 0xFF)) ^ decKeySch[1];
			int x2 = (b[off +  8] << 24 | (b[off +  9] & 0xFF) << 16 | (b[off + 10] & 0xFF) << 8 | (b[off + 11] & 0xFF)) ^ decKeySch[2];
			int x3 = (b[off + 12] << 24 | (b[off + 13] & 0xFF) << 16 | (b[off + 14] & 0xFF) << 8 | (b[off + 15] & 0xFF)) ^ decKeySch[3];
			
			for (int i = 1; i < roundCount; i++) {
				int y0 = mulinv0[x0 >>> 24] ^ mulinv1[x3 >>> 16 & 0xFF] ^ mulinv2[x2 >>> 8 & 0xFF] ^ mulinv3[x1 & 0xFF];
				int y1 = mulinv0[x1 >>> 24] ^ mulinv1[x0 >>> 16 & 0xFF] ^ mulinv2[x3 >>> 8 & 0xFF] ^ mulinv3[x2 & 0xFF];
				int y2 = mulinv0[x2 >>> 24] ^ mulinv1[x1 >>> 16 & 0xFF] ^ mulinv2[x0 >>> 8 & 0xFF] ^ mulinv3[x3 & 0xFF];
				int y3 = mulinv0[x3 >>> 24] ^ mulinv1[x2 >>> 16 & 0xFF] ^ mulinv2[x1 >>> 8 & 0xFF] ^ mulinv3[x0 & 0xFF];
				x0 = y0 ^ decKeySch[i << 2 | 0];
				x1 = y1 ^ decKeySch[i << 2 | 1];
				x2 = y2 ^ decKeySch[i << 2 | 2];
				x3 = y3 ^ decKeySch[i << 2 | 3];
			}
			
			// Final SubBytes, ShiftRows, AddRoundKey
			int y0 = (bigsubinv[(x0 & 0xFF000000 | x3 & 0x00FF0000) >>> 16] << 16 | bigsubinv[x2 & 0x0000FF00 | x1 & 0x000000FF]) ^ decKeySch[roundCount << 2 | 0];
			int y1 = (bigsubinv[(x1 & 0xFF000000 | x0 & 0x00FF0000) >>> 16] << 16 | bigsubinv[x3 & 0x0000FF00 | x2 & 0x000000FF]) ^ decKeySch[roundCount << 2 | 1];
			int y2 = (bigsubinv[(x2 & 0xFF000000 | x1 & 0x00FF0000) >>> 16] << 16 | bigsubinv[x0 & 0x0000FF00 | x3 & 0x000000FF]) ^ decKeySch[roundCount << 2 | 2];
			int y3 = (bigsubinv[(x3 & 0xFF000000 | x2 & 0x00FF0000) >>> 16] << 16 | bigsubinv[x1 & 0x0000FF00 | x0 & 0x000000FF]) ^ decKeySch[roundCount << 2 | 3];
			
			// Unpack columns into bytes
			b[off +  0] = (byte)(y0 >>> 24);
			b[off +  1] = (byte)(y0 >>> 16);
			b[off +  2] = (byte)(y0 >>>  8);
			b[off +  3] = (byte)(y0 >>>  0);
			b[off +  4] = (byte)(y1 >>> 24);
			b[off +  5] = (byte)(y1 >>> 16);
			b[off +  6] = (byte)(y1 >>>  8);
			b[off +  7] = (byte)(y1 >>>  0);
			b[off +  8] = (byte)(y2 >>> 24);
			b[off +  9] = (byte)(y2 >>> 16);
			b[off + 10] = (byte)(y2 >>>  8);
			b[off + 11] = (byte)(y2 >>>  0);
			b[off + 12] = (byte)(y3 >>> 24);
			b[off + 13] = (byte)(y3 >>> 16);
			b[off + 14] = (byte)(y3 >>>  8);
			b[off + 15] = (byte)(y3 >>>  0);
		}
	}
	
	
	@Override
	public FastAesCipherer clone() {
		FastAesCipherer result = (FastAesCipherer)super.clone();
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
	
	
	
	private static byte[] SBOX = AesUtils.getSbox();
	
	private static int[] bigsub, bigsubinv;  // Substitutes 2 bytes in parallel.
	private static int[] mul0, mul1, mul2, mul3;
	private static int[] mulinv0, mulinv1, mulinv2, mulinv3;
	
	
	static {
		byte[] sbox = AesUtils.getSbox();
		byte[] sboxinv = AesUtils.getSboxInverse();
		initBigSBox(sbox, sboxinv);
		initMultiplyTable(sbox, sboxinv);
	}
	
	
	private static void initBigSBox(byte[] sbox, byte[] sboxinv) {
		bigsub = new int[65536];
		bigsubinv = new int[65536];
		for (int i = 0; i < 65536; i++) {
			bigsub[i] = (sbox[i >>> 8] & 0xFF) << 8 | (sbox[i & 0xFF] & 0xFF);
			bigsubinv[i] = (sboxinv[i >>> 8] & 0xFF) << 8 | (sboxinv[i & 0xFF] & 0xFF);
		}
	}
	
	
	private static void initMultiplyTable(byte[] sbox, byte[] sboxinv) {
		mul0 = new int[256];
		mul1 = new int[256];
		mul2 = new int[256];
		mul3 = new int[256];
		for (int i = 0; i < 256; i++) {
			int j = sbox[i] & 0xFF;
			int temp = AesUtils.multiply(j, 0x02) << 24
			         | AesUtils.multiply(j, 0x01) << 16
			         | AesUtils.multiply(j, 0x01) <<  8
			         | AesUtils.multiply(j, 0x03) <<  0;
			mul0[i] = IntegerBitMath.rotateRight(temp,  0);
			mul1[i] = IntegerBitMath.rotateRight(temp,  8);
			mul2[i] = IntegerBitMath.rotateRight(temp, 16);
			mul3[i] = IntegerBitMath.rotateRight(temp, 24);
		}
		
		mulinv0 = new int[256];
		mulinv1 = new int[256];
		mulinv2 = new int[256];
		mulinv3 = new int[256];
		for (int i = 0; i < 256; i++) {
			int j = sboxinv[i] & 0xFF;
			int temp = AesUtils.multiply(j, 0x0E) << 24
			         | AesUtils.multiply(j, 0x09) << 16
			         | AesUtils.multiply(j, 0x0D) <<  8
			         | AesUtils.multiply(j, 0x0B) <<  0;
			mulinv0[i] = IntegerBitMath.rotateRight(temp,  0);
			mulinv1[i] = IntegerBitMath.rotateRight(temp,  8);
			mulinv2[i] = IntegerBitMath.rotateRight(temp, 16);
			mulinv3[i] = IntegerBitMath.rotateRight(temp, 24);
		}
	}
	
}
