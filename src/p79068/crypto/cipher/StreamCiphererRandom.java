package p79068.crypto.cipher;

import java.util.Arrays;

import p79068.util.random.AbstractRandom;


public final class StreamCiphererRandom extends AbstractRandom {
	
	private StreamCipherer cipherer;
	
	
	
	public StreamCiphererRandom(StreamCipherer cipherer) {
		if (cipherer == null)
			throw new AssertionError();
		this.cipherer = cipherer;
	}
	
	
	
	@Override
	public int uniformInt() {
		byte[] b = new byte[4];
		cipherer.encrypt(b, 0, b.length);
		return b[0] << 24
		    | (b[1] & 0xFF) << 16
		    | (b[2] & 0xFF) <<  8
		    | (b[3] & 0xFF);
	}
	
	
	@Override
	public long uniformLong() {
		byte[] b = new byte[8];
		cipherer.encrypt(b, 0, b.length);
		return (long)b[0] << 56
		    | (b[1] & 0xFFL) << 48
		    | (b[2] & 0xFFL) << 40
		    | (b[3] & 0xFFL) << 32
		    | (b[4] & 0xFFL) << 24
		    | (b[5] & 0xFFL) << 16
		    | (b[6] & 0xFFL) <<  8
		    | (b[7] & 0xFFL);
	}
	
	
	@Override
	public void uniformBytes(byte[] b, int off, int len) {
		Arrays.fill(b, off, off + len, (byte)0);  // This is not strictly necessary
		cipherer.encrypt(b, off, len);
	}
	
}
