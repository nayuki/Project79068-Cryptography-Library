package p79068.util.hash;


final class NullHasher extends Hasher {
	
	NullHasher(NullFunction algor) {
		super(algor);
	}
	
	
	
	public void update(byte b) {}
	
	
	public void update(byte[] b, int off, int len) {}
	
	
	public HashValue getHash() {
		return createHash(new byte[1]);
	}
	
}