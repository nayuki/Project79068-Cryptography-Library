package p79068.io;

import java.io.*;


public abstract class AudioInputStream {
	
	protected int length; // Length, in samples (-1 if unknown)
	protected double samplerate; // Sample rate, in Hz
	protected int channel; // Number of channels
	

	protected AudioInputStream() {
		length = -1;
	}
	
	
	public int read(short[][] sample) throws IOException {
		return read(sample, 0, sample[0].length);
	}
	
	public int read(short[][] sample, int off, int len) throws IOException {
		float[][] tpsample = new float[channel][len];
		int read = read(tpsample);
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < channel; j++) {
				int tp;
				if (tpsample[j][i] >= 0) {
					tp = (int)(tpsample[j][i] * 32768 + 0.5);
					if (tp > 32767)
						tp = 32767;
				} else
					tp = (int)(tpsample[j][i] * 32768 - 0.5);
				sample[j][off + i] = (short)tp;
			}
		}
		return read;
	}
	
	
	public int read(float[][] sample) throws IOException {
		return read(sample, 0, sample[0].length);
	}
	
	public abstract int read(float[][] sample, int off, int len) throws IOException;
	
	
	public int getLength() {
		return length;
	}
	
	public int getChannel() {
		return channel;
	}
	
	public double getSampleRate() {
		return samplerate;
	}
	
	
	protected static int readFull(InputStream in, byte[] b) throws IOException {
		return readFull(in, b, 0, b.length);
	}
	
	protected static int readFull(InputStream in, byte[] b, int off, int len) throws IOException {
		int readtotal = 0;
		while (readtotal < len) {
			int read = in.read(b, off + readtotal, len - readtotal);
			if (read == -1)
				break;
			readtotal += read;
		}
		return readtotal;
	}
	
	protected static byte[] readFull(InputStream in, int len) throws IOException {
		byte[] b = new byte[len];
		int readtotal = 0;
		while (readtotal < len) {
			int read = in.read(b, readtotal, len - readtotal);
			if (read == -1)
				break;
			readtotal += read;
		}
		if (readtotal == len)
			return b;
		throw new RuntimeException("Premature end of file");
	}
	
	protected static int toInt16BigEndian(byte[] b, int off) {
		return b[off] << 8 | (b[off + 1] & 0xFF);
	}
	
	protected static int toInt16LittleEndian(byte[] b, int off) {
		return (b[off] & 0xFF) | b[off + 1] << 8;
	}
	
	protected static int toInt32BigEndian(byte[] b, int off) {
		return b[off] << 24 | (b[off + 1] & 0xFF) << 16 | (b[off + 2] & 0xFF) << 8 | (b[off + 3] & 0xFF);
	}
	
	protected static int toInt32LittleEndian(byte[] b, int off) {
		return (b[off] & 0xFF) | (b[off + 1] & 0xFF) << 8 | (b[off + 2] & 0xFF) << 16 | b[off + 3] << 24;
	}
}