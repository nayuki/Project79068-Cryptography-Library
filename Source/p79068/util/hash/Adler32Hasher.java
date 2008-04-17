package p79068.util.hash;

import p79068.lang.BoundsChecker;
import p79068.math.IntegerBitMath;


final class Adler32Hasher extends Hasher {
	
	private int s1;  // Always in the range [0, 65521) after update
	private int s2;  // Always in the range [0, 65521) after update
	
	
	
	Adler32Hasher(Adler32 algor) {
		super(algor);
		s1 = 1;
		s2 = 0;
	}
	
	
	
	public void update(byte b) {
		s1 = (s1 + (b & 0xFF)) % 65521;
		s2 = (s2 + s1) % 65521;
	}
	
	
	public void update(byte[] b, int off, int len) {
		BoundsChecker.check(b.length, off, len);
		for (int i = off, j = 0, end = off + len; i < end; i++) {
			s1 += b[i] & 0xFF;
			s2 += s1;
			j++;
			if (j == 3854) {
				s1 %= 65521;
				s2 %= 65521;
				j = 0;
			}
		}
		s1 %= 65521;
		s2 %= 65521;
	}
	
	
	public HashValue getHash() {
		return createHash(IntegerBitMath.toBytesBigEndian(new int[]{s2 << 16 | s1}));
	}
	
}