package p79068.io;

import java.io.*;


public class FileIo {
	
	public static byte[] readAll(File file) throws IOException {
		if (file.length() > Integer.MAX_VALUE)
			throw new RuntimeException("File too large");
		int len = (int)file.length();
		byte[] b = new byte[len];
		InputStream in = new FileInputStream(file);
		int read = 0;
		while (read < len) {
			int tp = in.read(b, read, len - read);
			if (tp == -1)
				throw new RuntimeException("Unexpected end of file");
			read += tp;
		}
		in.close();
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
		out.write(b);
		out.close();
	}
}