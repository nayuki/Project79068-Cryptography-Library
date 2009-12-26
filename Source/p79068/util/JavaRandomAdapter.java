package p79068.util;


@SuppressWarnings("serial")
final class JavaRandomAdapter extends java.util.Random {
	
	private Random random;
	
	
	public JavaRandomAdapter(Random rand) {
		random = rand;
	}
	
	
	@Override
	protected int next(int bits) {
		return random.randomInt() >>> (32 - bits);
	}
	
	@Override
	public int nextInt() {
		return random.randomInt();
	}
	
	@Override
	public long nextLong() {
		return random.randomLong();
	}
	
	@Override
	public void setSeed(long seed) {
		throw new UnsupportedOperationException();
	}
	
}