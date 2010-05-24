package p79068.util;


class SimpleHashCoder extends HashCoder {
	
	private int state;
	
	
	
	SimpleHashCoder() {
		state = 0;
	}
	
	
	
	@Override
	public HashCoder add(byte x) {
		state += x;
		hash();
		return this;
	}
	
	@Override
	public HashCoder add(short x) {
		state += x;
		hash();
		return this;
	}
	
	@Override
	public HashCoder add(int x) {
		state += x;
		hash();
		return this;
	}
	
	@Override
	public HashCoder add(long x) {
		state += (int)(x >>> 32);
		hash();
		state += (int)(x >>> 0);
		hash();
		return this;
	}
	
	@Override
	public HashCoder add(float x) {
		state += Float.floatToRawIntBits(x);
		hash();
		return this;
	}
	
	@Override
	public HashCoder add(double x) {
		return add(Double.doubleToRawLongBits(x));
	}
	
	@Override
	public HashCoder add(boolean x) {
		state += x ? 1 : 0;
		hash();
		return this;
	}
	
	@Override
	public HashCoder add(char x) {
		state += x;
		hash();
		return this;
	}
	
	@Override
	public HashCoder add(Object x) {
		state += x.hashCode();
		hash();
		return this;
	}
	
	
	@Override
	public int getHashCode() {
		return state;
	}
	
	
	
	private void hash() {
		for (int i = 0; i < 4; i++) {  // Arbitrary number of mixing rounds
			state *= 0x7C824F73;  // Arbitrary number, coprime with 2^32
			state += 0x5C12FE83;  // Arbitrary number
			state = state << 11 | state >>> 21;  // Rotation by arbitrary amount coprime with int size (32)
		}
	}
	
}