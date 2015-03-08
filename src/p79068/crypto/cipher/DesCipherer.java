package p79068.crypto.cipher;

import p79068.Assert;
import p79068.crypto.Zeroizer;


final class DesCipherer extends AbstractCipherer {
	
	private long[] keySchedule;
	private long[] keyScheduleReverse;
	
	
	
	DesCipherer(Des cipher, byte[] key) {
		super(cipher, key);
		
		// Pack key bytes in big endian
		long tempKey = 0;
		for (byte b : key)
			tempKey = (tempKey << 8) | (b & 0xFF);
		
		// Add padding bits
		if (key.length == 7) {
			for (int i = 0; i < 8; i++) {
				long mask = (1L << (i * 8)) - 1;
				tempKey = ((tempKey & ~mask) << 1) | (tempKey & mask);
			}
		}
		
		// Expand key schedule
		keySchedule = new long[KEY_SCHEDULE_SHIFTS.length];
		keyScheduleReverse = new long[KEY_SCHEDULE_SHIFTS.length];
		long left  = permuteBits(tempKey, PERMUTED_CHOICE_1_LEFT );
		long right = permuteBits(tempKey, PERMUTED_CHOICE_1_RIGHT);
		for (int i = 0; i < KEY_SCHEDULE_SHIFTS.length; i++) {
			int s = KEY_SCHEDULE_SHIFTS[i];
			left  = ((left  << s) | (left  >>> (28 - s))) & HALF_KEY_MASK;
			right = ((right << s) | (right >>> (28 - s))) & HALF_KEY_MASK;
			keySchedule[i] = permuteBits((left << 28) | right, PERMUTED_CHOICE_2);
			keyScheduleReverse[keySchedule.length - 1 - i] = keySchedule[i];
		}
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		crypt(b, off, len, keySchedule);
	}
	
	
	@Override
	public void decrypt(byte[] b, int off, int len) {
		crypt(b, off, len, keyScheduleReverse);
	}
	
	
	private void crypt(byte[] b, int off, int len, long[] keySch) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
		if (len % 8 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		for (int i = off, end = off + len; i < end; i += 8) {
			// Pack and initial permutation
			long longBlock = ((b[i + 0] & 0xFFL) << 56)
			               | ((b[i + 1] & 0xFFL) << 48)
			               | ((b[i + 2] & 0xFFL) << 40)
			               | ((b[i + 3] & 0xFFL) << 32)
			               | ((b[i + 4] & 0xFFL) << 24)
			               | ((b[i + 5] & 0xFFL) << 16)
			               | ((b[i + 6] & 0xFFL) <<  8)
			               | ((b[i + 7] & 0xFFL) <<  0);
			longBlock = initialPermutation(longBlock);
			
			// Do 16 rounds
			int left = (int)(longBlock >>> 32);
			int right = (int)longBlock;
			for (long subkey : keySch) {
				int oldRight = right;
				long temp = feistelExpansion(right) ^ subkey;
				right = left ^ feistelPermutation(feistelSbox(temp));
				left = oldRight;
			}
			
			// Final permutation and unpack
			longBlock = finalPermutation(((long)right << 32) | (left & 0xFFFFFFFFL));
			b[i + 0] = (byte)(longBlock >>> 56);
			b[i + 1] = (byte)(longBlock >>> 48);
			b[i + 2] = (byte)(longBlock >>> 40);
			b[i + 3] = (byte)(longBlock >>> 32);
			b[i + 4] = (byte)(longBlock >>> 24);
			b[i + 5] = (byte)(longBlock >>> 16);
			b[i + 6] = (byte)(longBlock >>>  8);
			b[i + 7] = (byte)(longBlock >>>  0);
		}
	}
	
	
	@Override
	public DesCipherer clone() {
		DesCipherer result = (DesCipherer)super.clone();
		result.keySchedule = result.keySchedule.clone();
		result.keyScheduleReverse = result.keyScheduleReverse.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (cipher == null)
			return;
		keySchedule = Zeroizer.clear(keySchedule);
		keyScheduleReverse = Zeroizer.clear(keyScheduleReverse);
		super.zeroize();
	}
	
	
	/* Utility functions */
	
	// 64 bits -> 64 bits
	private static long initialPermutation(long data) {
		return (((((data >>> 6) & INITIAL_PERMUTATION_MASK) * INIT_FINAL_PERMUTATION_MULTIPLIER) >>> 56) & 0xFF) << 56
		     | (((((data >>> 4) & INITIAL_PERMUTATION_MASK) * INIT_FINAL_PERMUTATION_MULTIPLIER) >>> 56) & 0xFF) << 48
		     | (((((data >>> 2) & INITIAL_PERMUTATION_MASK) * INIT_FINAL_PERMUTATION_MULTIPLIER) >>> 56) & 0xFF) << 40
		     | (((((data >>> 0) & INITIAL_PERMUTATION_MASK) * INIT_FINAL_PERMUTATION_MULTIPLIER) >>> 56) & 0xFF) << 32
		     | (((((data >>> 7) & INITIAL_PERMUTATION_MASK) * INIT_FINAL_PERMUTATION_MULTIPLIER) >>> 56) & 0xFF) << 24
		     | (((((data >>> 5) & INITIAL_PERMUTATION_MASK) * INIT_FINAL_PERMUTATION_MULTIPLIER) >>> 56) & 0xFF) << 16
		     | (((((data >>> 3) & INITIAL_PERMUTATION_MASK) * INIT_FINAL_PERMUTATION_MULTIPLIER) >>> 56) & 0xFF) <<  8
		     | (((((data >>> 1) & INITIAL_PERMUTATION_MASK) * INIT_FINAL_PERMUTATION_MULTIPLIER) >>> 56) & 0xFF) <<  0;
	}
	
	
	// 64 bits -> 64 bits
	private static long finalPermutation(long data) {
		return ((((data >>> 24) & 0xFF) * INIT_FINAL_PERMUTATION_MULTIPLIER) & FINAL_PERMUTATION_MASK) >>> 0
		     | ((((data >>> 56) & 0xFF) * INIT_FINAL_PERMUTATION_MULTIPLIER) & FINAL_PERMUTATION_MASK) >>> 1
		     | ((((data >>> 16) & 0xFF) * INIT_FINAL_PERMUTATION_MULTIPLIER) & FINAL_PERMUTATION_MASK) >>> 2
		     | ((((data >>> 48) & 0xFF) * INIT_FINAL_PERMUTATION_MULTIPLIER) & FINAL_PERMUTATION_MASK) >>> 3
		     | ((((data >>>  8) & 0xFF) * INIT_FINAL_PERMUTATION_MULTIPLIER) & FINAL_PERMUTATION_MASK) >>> 4
		     | ((((data >>> 40) & 0xFF) * INIT_FINAL_PERMUTATION_MULTIPLIER) & FINAL_PERMUTATION_MASK) >>> 5
		     | ((((data >>>  0) & 0xFF) * INIT_FINAL_PERMUTATION_MULTIPLIER) & FINAL_PERMUTATION_MASK) >>> 6
		     | ((((data >>> 32) & 0xFF) * INIT_FINAL_PERMUTATION_MULTIPLIER) & FINAL_PERMUTATION_MASK) >>> 7;
	}
	
	
	// 32 bits -> 48 bits
	private static long feistelExpansion(int data) {
		long temp = data;  // Masking unnecessary
		return (((long)Integer.rotateRight(data, 1) << 16) & 0xFC0000000000L)
		     | ((temp << 13) & 0x03F000000000L)
		     | ((temp << 11) & 0x000FC0000000L)
		     | ((temp <<  9) & 0x00003F000000L)
		     | ((temp <<  7) & 0x000000FC0000L)
		     | ((temp <<  5) & 0x00000003F000L)
		     | ((temp <<  3) & 0x000000000FC0L)
		     | (Integer.rotateLeft(data, 1) & 0x00000000003FL);
	}
	
	
	// 32 bits -> 32 bits
	private static int feistelPermutation(int data) {
		return Integer.rotateLeft(data,  3) & (1 <<  5)
		     | Integer.rotateLeft(data,  4) & (1 << 18)
		     | Integer.rotateLeft(data,  5) & (1 << 30 | 1 << 22 | 1 << 13 | 1 << 10 | 1 <<  1)
		     | Integer.rotateLeft(data,  6) & (1 << 26)
		     | Integer.rotateLeft(data,  9) & (1 << 24)
		     | Integer.rotateLeft(data, 10) & (1 <<  4)
		     | Integer.rotateLeft(data, 11) & (1 << 11)
		     | Integer.rotateLeft(data, 12) & (1 << 21 | 1 <<  9)
		     | Integer.rotateLeft(data, 13) & (1 <<  2)
		     | Integer.rotateLeft(data, 14) & (1 << 20)
		     | Integer.rotateLeft(data, 15) & (1 << 31)
		     | Integer.rotateLeft(data, 16) & (1 << 17)
		     | Integer.rotateLeft(data, 17) & (1 << 29 | 1 << 28 | 1 << 15 | 1 <<  8)
		     | Integer.rotateLeft(data, 19) & (1 <<  6)
		     | Integer.rotateLeft(data, 21) & (1 << 25)
		     | Integer.rotateLeft(data, 22) & (1 << 14)
		     | Integer.rotateLeft(data, 24) & (1 << 27 | 1 << 23 | 1 << 19)
		     | Integer.rotateLeft(data, 25) & (1 <<  3 | 1 <<  0)
		     | Integer.rotateLeft(data, 26) & (1 << 16 | 1 << 12 | 1 <<  7);
	}
	
	
	// 48 bits -> 32 bits
	private static int feistelSbox(long data) {
		return SBOXES[0x000 | ((int)(data >>>  0) & 0x3F)] <<  0
		     | SBOXES[0x040 | ((int)(data >>>  6) & 0x3F)] <<  4
		     | SBOXES[0x080 | ((int)(data >>> 12) & 0x3F)] <<  8
		     | SBOXES[0x0C0 | ((int)(data >>> 18) & 0x3F)] << 12
		     | SBOXES[0x100 | ((int)(data >>> 24) & 0x3F)] << 16
		     | SBOXES[0x140 | ((int)(data >>> 30) & 0x3F)] << 20
		     | SBOXES[0x180 | ((int)(data >>> 36) & 0x3F)] << 24
		     | SBOXES[0x1C0 | ((int)(data >>> 42) & 0x3F)] << 28;
	}
	
	
	// Slow but general bit-permutation/expansion function
	private static long permuteBits(long data, int[] perm) {
		long result = 0;
		for (int i : perm)
			result = (result << 1) | ((data >>> i) & 1);
		return result;
	}
	
	
	/* Tables of constants */
	
	private static int[] SBOXES = {
		0xD, 0x1, 0x2, 0xF, 0x8, 0xD, 0x4, 0x8, 0x6, 0xA, 0xF, 0x3, 0xB, 0x7, 0x1, 0x4, 0xA, 0xC, 0x9, 0x5, 0x3, 0x6, 0xE, 0xB, 0x5, 0x0, 0x0, 0xE, 0xC, 0x9, 0x7, 0x2, 0x7, 0x2, 0xB, 0x1, 0x4, 0xE, 0x1, 0x7, 0x9, 0x4, 0xC, 0xA, 0xE, 0x8, 0x2, 0xD, 0x0, 0xF, 0x6, 0xC, 0xA, 0x9, 0xD, 0x0, 0xF, 0x3, 0x3, 0x5, 0x5, 0x6, 0x8, 0xB,
		0x4, 0xD, 0xB, 0x0, 0x2, 0xB, 0xE, 0x7, 0xF, 0x4, 0x0, 0x9, 0x8, 0x1, 0xD, 0xA, 0x3, 0xE, 0xC, 0x3, 0x9, 0x5, 0x7, 0xC, 0x5, 0x2, 0xA, 0xF, 0x6, 0x8, 0x1, 0x6, 0x1, 0x6, 0x4, 0xB, 0xB, 0xD, 0xD, 0x8, 0xC, 0x1, 0x3, 0x4, 0x7, 0xA, 0xE, 0x7, 0xA, 0x9, 0xF, 0x5, 0x6, 0x0, 0x8, 0xF, 0x0, 0xE, 0x5, 0x2, 0x9, 0x3, 0x2, 0xC,
		0xC, 0xA, 0x1, 0xF, 0xA, 0x4, 0xF, 0x2, 0x9, 0x7, 0x2, 0xC, 0x6, 0x9, 0x8, 0x5, 0x0, 0x6, 0xD, 0x1, 0x3, 0xD, 0x4, 0xE, 0xE, 0x0, 0x7, 0xB, 0x5, 0x3, 0xB, 0x8, 0x9, 0x4, 0xE, 0x3, 0xF, 0x2, 0x5, 0xC, 0x2, 0x9, 0x8, 0x5, 0xC, 0xF, 0x3, 0xA, 0x7, 0xB, 0x0, 0xE, 0x4, 0x1, 0xA, 0x7, 0x1, 0x6, 0xD, 0x0, 0xB, 0x8, 0x6, 0xD,
		0x2, 0xE, 0xC, 0xB, 0x4, 0x2, 0x1, 0xC, 0x7, 0x4, 0xA, 0x7, 0xB, 0xD, 0x6, 0x1, 0x8, 0x5, 0x5, 0x0, 0x3, 0xF, 0xF, 0xA, 0xD, 0x3, 0x0, 0x9, 0xE, 0x8, 0x9, 0x6, 0x4, 0xB, 0x2, 0x8, 0x1, 0xC, 0xB, 0x7, 0xA, 0x1, 0xD, 0xE, 0x7, 0x2, 0x8, 0xD, 0xF, 0x6, 0x9, 0xF, 0xC, 0x0, 0x5, 0x9, 0x6, 0xA, 0x3, 0x4, 0x0, 0x5, 0xE, 0x3,
		0x7, 0xD, 0xD, 0x8, 0xE, 0xB, 0x3, 0x5, 0x0, 0x6, 0x6, 0xF, 0x9, 0x0, 0xA, 0x3, 0x1, 0x4, 0x2, 0x7, 0x8, 0x2, 0x5, 0xC, 0xB, 0x1, 0xC, 0xA, 0x4, 0xE, 0xF, 0x9, 0xA, 0x3, 0x6, 0xF, 0x9, 0x0, 0x0, 0x6, 0xC, 0xA, 0xB, 0x1, 0x7, 0xD, 0xD, 0x8, 0xF, 0x9, 0x1, 0x4, 0x3, 0x5, 0xE, 0xB, 0x5, 0xC, 0x2, 0x7, 0x8, 0x2, 0x4, 0xE,
		0xA, 0xD, 0x0, 0x7, 0x9, 0x0, 0xE, 0x9, 0x6, 0x3, 0x3, 0x4, 0xF, 0x6, 0x5, 0xA, 0x1, 0x2, 0xD, 0x8, 0xC, 0x5, 0x7, 0xE, 0xB, 0xC, 0x4, 0xB, 0x2, 0xF, 0x8, 0x1, 0xD, 0x1, 0x6, 0xA, 0x4, 0xD, 0x9, 0x0, 0x8, 0x6, 0xF, 0x9, 0x3, 0x8, 0x0, 0x7, 0xB, 0x4, 0x1, 0xF, 0x2, 0xE, 0xC, 0x3, 0x5, 0xB, 0xA, 0x5, 0xE, 0x2, 0x7, 0xC,
		0xF, 0x3, 0x1, 0xD, 0x8, 0x4, 0xE, 0x7, 0x6, 0xF, 0xB, 0x2, 0x3, 0x8, 0x4, 0xE, 0x9, 0xC, 0x7, 0x0, 0x2, 0x1, 0xD, 0xA, 0xC, 0x6, 0x0, 0x9, 0x5, 0xB, 0xA, 0x5, 0x0, 0xD, 0xE, 0x8, 0x7, 0xA, 0xB, 0x1, 0xA, 0x3, 0x4, 0xF, 0xD, 0x4, 0x1, 0x2, 0x5, 0xB, 0x8, 0x6, 0xC, 0x7, 0x6, 0xC, 0x9, 0x0, 0x3, 0x5, 0x2, 0xE, 0xF, 0x9,
		0xE, 0x0, 0x4, 0xF, 0xD, 0x7, 0x1, 0x4, 0x2, 0xE, 0xF, 0x2, 0xB, 0xD, 0x8, 0x1, 0x3, 0xA, 0xA, 0x6, 0x6, 0xC, 0xC, 0xB, 0x5, 0x9, 0x9, 0x5, 0x0, 0x3, 0x7, 0x8, 0x4, 0xF, 0x1, 0xC, 0xE, 0x8, 0x8, 0x2, 0xD, 0x4, 0x6, 0x9, 0x2, 0x1, 0xB, 0x7, 0xF, 0x5, 0xC, 0xB, 0x9, 0x3, 0x7, 0xE, 0x3, 0xA, 0xA, 0x0, 0x5, 0x6, 0x0, 0xD,
	};
	
	private static long INITIAL_PERMUTATION_MASK          = 0x0101010101010101L;
	private static long FINAL_PERMUTATION_MASK            = 0x8080808080808080L;
	private static long INIT_FINAL_PERMUTATION_MULTIPLIER = 0x8040201008040201L;
	
	private static int[] PERMUTED_CHOICE_1_LEFT = {7, 15, 23, 31, 39, 47, 55, 63, 6, 14, 22, 30, 38, 46, 54, 62, 5, 13, 21, 29, 37, 45, 53, 61, 4, 12, 20, 28};
	private static int[] PERMUTED_CHOICE_1_RIGHT = {1, 9, 17, 25, 33, 41, 49, 57, 2, 10, 18, 26, 34, 42, 50, 58, 3, 11, 19, 27, 35, 43, 51, 59, 36, 44, 52, 60};
	private static int[] PERMUTED_CHOICE_2 = {42, 39, 45, 32, 55, 51, 53, 28, 41, 50, 35, 46, 33, 37, 44, 52, 30, 48, 40, 49, 29, 36, 43, 54, 15, 4, 25, 19, 9, 1, 26, 16, 5, 11, 23, 8, 12, 7, 17, 0, 22, 3, 10, 14, 6, 20, 27, 24};
	
	private static int HALF_KEY_MASK = (1 << 28) - 1;
	
	private static int[] KEY_SCHEDULE_SHIFTS = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
	
}
