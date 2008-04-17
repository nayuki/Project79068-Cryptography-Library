package p79068.util.hash;


final class XorHasher extends Hasher {
	
	private int xor;  // Note: The upper 24 bits are always ignored.
	
	
	
	XorHasher(Xor algor) {
		super(algor);
		xor = 0;
	}
	
	
	
	public void update(byte b) {
		xor ^= b;
	}
	
	
	public void update(byte[] b, int off, int len) {
		for (int i = off, end = off + len; i < end; i++)
			xor ^= b[i];
	}
	
	
	public HashValue getHash() {
		return createHash(new byte[]{(byte)xor});
	}
	
}