package p79068.crypto.cipher;


final class WhirlpoolCipherer extends WhirlpoolCiphererParent {
	
	WhirlpoolCipherer(WhirlpoolCipher cipher, byte[] key) {
		super(cipher, key);
		sub = WHIRLPOOL_SUB;
		subinv = WHIRLPOOL_SUBINV;
		mul = WHIRLPOOL_MUL;
		mulinv = WHIRLPOOL_MULINV;
		rcon = WHIRLPOOL_RCON;
		setKey(key);
	}
	
	
	private static final byte[] WHIRLPOOL_SUB;
	private static final byte[] WHIRLPOOL_SUBINV;
	private static final byte[][] WHIRLPOOL_MUL;
	private static final byte[][] WHIRLPOOL_MULINV;
	private static final byte[][] WHIRLPOOL_RCON;
	
	static {
		{
			int[] e = {0x1, 0xB, 0x9, 0xC, 0xD, 0x6, 0xF, 0x3, 0xE, 0x8, 0x7, 0x4, 0xA, 0x2, 0x5, 0x0}; // The E mini-box
			int[] r = {0x7, 0xC, 0xB, 0xD, 0xE, 0x4, 0x9, 0xF, 0x6, 0x3, 0x8, 0xA, 0x2, 0x5, 0x1, 0x0}; // The R mini-box
			int[] einv = new int[16]; // The inverse of E
			for (int i = 0; i < e.length; i++)
				einv[e[i]] = i;
			WHIRLPOOL_SUB = new byte[256];
			WHIRLPOOL_SUBINV = new byte[256];
			for (int i = 0; i < WHIRLPOOL_SUB.length; i++) {
				int left = e[i >>> 4];
				int right = einv[i & 0xF];
				int temp = r[left ^ right];
				WHIRLPOOL_SUB[i] = (byte)(e[left ^ temp] << 4 | einv[right ^ temp]);
				WHIRLPOOL_SUBINV[WHIRLPOOL_SUB[i] & 0xFF] = (byte)i;
			}
		}
		
		{
			WHIRLPOOL_RCON = new byte[ROUNDS][64];
			for (int i = 0; i < WHIRLPOOL_RCON.length; i++) {
				int j = 0;
				for (; j < 8; j++)
					WHIRLPOOL_RCON[i][j] = WHIRLPOOL_SUB[8 * i + j];
				for (; j < 64; j++)
					WHIRLPOOL_RCON[i][j] = 0;
			}
		}
		
		{
			int[] c = {0x01, 0x09, 0x02, 0x05, 0x08, 0x01, 0x04, 0x01};
			int[] cinv = {0x04, 0x3E, 0xCB, 0xC2, 0xC2, 0xA4, 0x0E, 0xAF};
			WHIRLPOOL_MUL = new byte[8][256];
			WHIRLPOOL_MULINV = new byte[8][256];
			for (int i = 0; i < c.length; i++) {
				for (int j = 0; j < 256; j++) {
					WHIRLPOOL_MUL[i][j] = (byte)multiply(j, c[i]);
					WHIRLPOOL_MULINV[i][j] = (byte)multiply(j, cinv[i]);
				}
			}
		}
	}
}