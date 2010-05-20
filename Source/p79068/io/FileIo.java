package p79068.io;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;


public class FileIo {
	
	public static byte[] readAll(File file) throws IOException {
		if (file.length() > Integer.MAX_VALUE)
			throw new RuntimeException("File too large");
		int length = (int)file.length();
		byte[] b = new byte[length];
		InputStream in = new FileInputStream(file);
		try {
			int position = 0;
			while (position < length) {
				int read = in.read(b, position, length - position);
				if (read == -1)
					throw new EOFException("Unexpected end of file");
				position += read;
			}
		} finally {
			in.close();
		}
		return b;
	}
	
	
	public static String readAll(File file, String charset) throws IOException {
		InputStream in0 = new FileInputStream(file);
		InputStreamReader in = new InputStreamReader(in0);
		StringBuffer sb = new StringBuffer();
		char[] c = new char[65536];
		while (true) {
			int tp = in.read(c, 0, c.length);
			if (tp == -1)
				break;
			sb.append(c, 0, tp);
		}
		in.close();
		in0.close();
		return sb.toString();
	}
	
	
	public static void write(File file, byte[] b) throws IOException {
		OutputStream out = new FileOutputStream(file);
		try {
			out.write(b);
		} finally {
			out.close();
		}
	}
	
}