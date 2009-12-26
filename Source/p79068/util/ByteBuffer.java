package p79068.util;

import java.util.Arrays;


public final class ByteBuffer {
	
	private byte[] buffer;
	
	private int length;
	
	
	
	public ByteBuffer() {
		buffer = new byte[1];
		length = 0;
	}
	
	
	
	public ByteBuffer append(byte b) {
		if (length == buffer.length)
			resize(length * 2);
		buffer[length] = b;
		length++;
		return this;
	}
	
	
	public ByteBuffer append(int b) {
		if (length == buffer.length)
			resize(length * 2);
		buffer[length] = (byte)b;
		length++;
		return this;
	}
	
	
	public ByteBuffer append(byte[] b) {
		return append(b, 0, b.length);
	}
	
	
	public ByteBuffer append(byte[] b, int off, int len) {
		if (length + len > buffer.length) {
			int newLength = buffer.length;
			while (newLength < length + len)
				newLength *= 2;
			resize(newLength);
		}
		System.arraycopy(b, off, buffer, length, len);
		length += len;
		return this;
	}
	
	
	public int length() {
		return length;
	}
	
	
	public byte[] toByteArray() {
		return Arrays.copyOf(buffer, length);
	}
	
	
	public void clear() {
		length = 0;
	}
	
	
	private void resize(int newCapacity) {
		if (newCapacity < length || newCapacity < 1)
			throw new IllegalArgumentException();
		buffer = Arrays.copyOf(buffer, newCapacity);
	}
	
}