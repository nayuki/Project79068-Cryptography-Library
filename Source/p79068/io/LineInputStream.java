package p79068.io;

import java.io.*;
import p79068.util.*;


public final class LineInputStream extends InputStream {
	
	private InputStream in;
	private int discard; // If next byte equals this, then discard it. Set as -2 because -1 means end of stream.
	

	public LineInputStream(InputStream in) {
		this.in = in;
		discard = -2;
	}
	
	
	@Override
	public int read() throws IOException {
		int tp = in.read();
		if (discard != -2) {
			if (tp == discard)
				tp = in.read();
			discard = -2;
		}
		return tp;
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (len == 0)
			return 0;
		if (discard == -2)
			return in.read(b, off, len);
		int tp = in.read();
		if (tp != discard) {
			discard = -2;
			b[off] = (byte)tp;
			return 1 + in.read(b, off + 1, len - 1);
		}
		discard = -2;
		return in.read(b, off, len - 1);
	}
	
	public byte[] readLine() throws IOException {
		ByteBuffer b = new ByteBuffer();
		int tp = read();
		if (tp == -1)
			return null;
		else if (tp == '\r')
			discard = '\n';
		else if (tp == '\n')
			discard = '\r';
		else {
			b.append((byte)tp);
			while (true) {
				tp = in.read();
				if (tp == -1) {
					if (b.length() == 0)
						return null;
					break;
				}
				if (tp == '\r') {
					discard = '\n';
					break;
				}
				if (tp == '\n') {
					discard = '\r';
					break;
				}
				b.append((byte)tp);
			}
		}
		return b.toByteArray();
	}
	
	
	@Override
	public void close() throws IOException {
		super.close();
		in.close();
	}
}