/*
Unsynchronized.
*/


package p79068.util;


public final class ByteBuffer {
	
	private byte[] buffer;
	private int length;
	
	
	
	public ByteBuffer() {
		buffer = new byte[1];
		length = 0;
	}
	
	
	
	public ByteBuffer append(byte b) {
		if (length == buffer.length)
			buffer = resize(buffer, length * 2);
		buffer[length] = b;
		length++;
		return this;
	}
	
	
	public ByteBuffer append(int b) {
		if (length == buffer.length)
			buffer = resize(buffer, length * 2);
		buffer[length] = (byte)b;
		length++;
		return this;
	}
	
	
	public ByteBuffer append(byte[] b) {
		return append(b, 0, b.length);
	}
	
	
	public ByteBuffer append(byte[] b, int off, int len) {
		if (length + len > buffer.length) {
			int tp = buffer.length;
			while (tp < length + len)
				tp *= 2;
			buffer = resize(buffer, tp);
		}
		System.arraycopy(b, off, buffer, length, len);
		length += len;
		return this;
	}
	
	
	public int length() {
		return length;
	}
	
	
	public byte[] toByteArray() {
		byte[] b = new byte[length];
		System.arraycopy(buffer, 0, b, 0, length);
		return b;
	}
	
	
	public void clear() {
		length = 0;
	}
	
	
	private static byte[] resize(byte[] ain, int len) {
		byte[] aout = new byte[len];
		System.arraycopy(ain, 0, aout, 0, Math.min(len, ain.length));
		return aout;
	}
	
}