package p79068.crypto.hash;

import p79068.hash.Hasher;


public final class Whirlpool extends AbstractBlockHashFunction {
	
	/**
	 * The Whirlpool hash function. {@code name = "Whirlpool"}, {@code hashLength = 64}, {@code blockLength = 64}. This is based on Whirlpool-T with the linear mixing changed.
	 */
	public static final Whirlpool WHIRLPOOL_FUNCTION = new Whirlpool("Whirlpool", 10, getWhirlpoolSbox(), getWhirlpoolC(), getWhirlpoolCInverse());
	
	
	
	private int rounds;
	
	private int[] sbox;
	
	private int[] c;
	
	private int[] cInverse;
	
	
	
	private Whirlpool(String name, int rounds, int[] sbox, int[] c, int[] cInv) {
		super(name, 64, 64);
		this.rounds = rounds;
		this.sbox = sbox;
		this.c = c;
		this.cInverse = cInv;
	}
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new WhirlpoolCore(rounds, sbox, c, cInverse));
	}
	
	
	
	private static int[] getWhirlpoolSbox() {
		int[] e = {0x1, 0xB, 0x9, 0xC, 0xD, 0x6, 0xF, 0x3, 0xE, 0x8, 0x7, 0x4, 0xA, 0x2, 0x5, 0x0};  // The E mini-box
		int[] r = {0x7, 0xC, 0xB, 0xD, 0xE, 0x4, 0x9, 0xF, 0x6, 0x3, 0x8, 0xA, 0x2, 0x5, 0x1, 0x0};  // The R mini-box
		int[] einv = new int[16];  // The inverse of E
		for (int i = 0; i < e.length; i++)
			einv[e[i]] = i;

		int[] sub = new int[256];
		for (int i = 0; i < sub.length; i++) {
			int left = e[i >>> 4];
			int right = einv[i & 0xF];
			int temp = r[left ^ right];
			sub[i] = (e[left ^ temp] << 4 | einv[right ^ temp]);
		}
		return sub;
	}
	
	
	private static int[] getWhirlpoolC() {
		return new int[]{0x01, 0x09, 0x02, 0x05, 0x08, 0x01, 0x04, 0x01};
	}
	
	private static int[] getWhirlpoolCInverse() {
		return new int[]{0x04, 0x3E, 0xCB, 0xC2, 0xC2, 0xA4, 0x0E, 0xAF};
	}
	
}
