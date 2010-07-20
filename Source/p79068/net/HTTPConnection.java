/*
 * Allows reading and writing HTTP requests and responses.
 * Not multithread-safe.
 */


package p79068.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import p79068.io.LineInputStream;
import p79068.util.ByteBuffer;


public class HTTPConnection {
	
	private Socket socket;
	private InputStream in0;
	public LineInputStream in;
	public OutputStream out;
	
	
	
	public HTTPConnection(String host, int port) throws IOException {
		this(new Socket(host, port));
	}
	
	
	public HTTPConnection(Socket sock) throws IOException {
		socket = sock;
		in0 = sock.getInputStream();
		in = new LineInputStream(in0);
		out = sock.getOutputStream();
	}
	
	
	
	public Object[] readRequest() throws IOException {
		String[] tpstr = parseFirstLine(in);
		String method = tpstr[0];
		String uri = tpstr[1];
		String version = tpstr[2];
		Map<String,String> header = new HashMap<String,String>();
		parseHeader(in, header);
		ByteBuffer b = new ByteBuffer();
		
		return new Object[]{method, uri, version, header, b.toByteArray()};
	}
	
	
	public Object[] readResponse(boolean message) throws IOException {
		String[] tpstr = parseFirstLine(in);
		String version = tpstr[0];
		int status = Integer.parseInt(tpstr[1]);
		String reason = tpstr[2];
		Map<String,String> header = new HashMap<String,String>();
		parseHeader(in, header);
		ByteBuffer b = new ByteBuffer();
		if (status / 100 == 1 || status == 204 || status == 304 || !message)
			;
		else {
			byte[] buf = new byte[2048];
			if (header.containsKey("Content-Length")) {
				int len = Integer.parseInt(header.get("Content-Length"));
				while (len > 0) {
					int read = in.read(buf, 0, Math.min(len, buf.length));
					if (read == -1)
						break;
					b.append(buf, 0, read);
					len -= read;
				}
			} else if (header.containsKey("Transfer-Encoding") && header.get("Transfer-Encoding").equalsIgnoreCase("chunked")) {
				int read = 0;
				while (true) {
					byte[] line = in.readLine();
					int len = Integer.parseInt(toString(line), 16);
					if (len == 0)
						break;
					while (len > 0) {
						read = in.read(buf, 0, Math.min(len, buf.length));
						if (read == -1)
							break;
						b.append(buf, 0, read);
						len -= read;
					}
					if (read == -1)
						break;
					in.readLine();
				}
				if (read != -1)
					parseHeader(in, header);
			} else if (header.containsKey("Content-Type")) {
				while (true) {
					int read = in.read(buf);
					if (read == -1)
						break;
					b.append(buf, 0, read);
				}
			}
		}
		return new Object[]{version, new Integer(status), reason, header, b.toByteArray()};
	}
	
	
	public void writeRequest(String method, String uri, String version, Map<String,String> header, byte[] entity) throws IOException {
		ByteBuffer b = new ByteBuffer();
		b.append(toASCII(method + " " + uri + " " + version + "\r\n"));
		for (String key : header.keySet()) {
			String value = header.get(key);
			if (value != null)
				b.append(toASCII(key + ": " + value + "\r\n"));
			else
				b.append(toASCII(key + "\r\n"));
		}
		b.append(toASCII("\r\n"));
		if (entity == null)
			out.write(b.toByteArray());
		else {
			if (b.length() + entity.length < 1200) {
				b.append(entity);
				out.write(b.toByteArray());
			} else {
				out.write(b.toByteArray());
				out.write(entity);
			}
		}
	}
	
	
	public void writeResponse(String version, int status, String reason, Map<String,String> header, byte[] entity) throws IOException {
		ByteBuffer b = new ByteBuffer();
		b.append(toASCII(version + " " + status + " " + reason + "\r\n"));
		for (String key : header.keySet()) {
			String value = header.get(key);
			if (value != null)
				b.append(toASCII(key + ": " + value + "\r\n"));
			else
				b.append(toASCII(key + "\r\n"));
		}
		b.append(toASCII("\r\n"));
		if (entity == null)
			out.write(b.toByteArray());
		else {
			if (b.length() + entity.length < 1200) {
				b.append(entity);
				out.write(b.toByteArray());
			} else {
				out.write(b.toByteArray());
				out.write(entity);
			}
		}
	}
	
	
	public void close() throws IOException {
		in.close();
		socket.close();
	}
	
	
	private static String[] parseFirstLine(LineInputStream in) throws IOException {
		ByteBuffer b = new ByteBuffer();
		String[] str = new String[3];
		byte[] line = in.readLine();
		int i = 0, j = 0;
		for (; i < str.length - 1; i++) {
			for (; j < line.length && line[j] != ' ' && line[j] != '\t'; j++)
				b.append(line[j]);
			while (j < line.length && (line[j] == ' ' || line[j] == '\t'))
				j++;
			str[i] = toString(b.toByteArray());
			b.clear();
		}
		for (; j < line.length; j++)
			b.append(line[j]);
		str[i] = toString(b.toByteArray());
		return str;
	}
	
	
	private static void parseHeader(LineInputStream in, Map<String,String> header) throws IOException {
		ByteBuffer b = new ByteBuffer();
		while (true) {
			byte[] line = in.readLine();
			if (line.length == 0)
				break;
			int i = 0;
			for (; i < line.length && line[i] != ':'; i++)
				b.append(line[i]);
			String key = toString(eliminateLinearWhiteSpace(b.toByteArray()));
			String value = null;
			b.clear();
			i++;
			if (i != line.length) {
				for (; i < line.length; i++)
					b.append(line[i]);
				value = toString(eliminateLinearWhiteSpace(b.toByteArray()));
				b.clear();
			}
			header.put(key, value);
		}
	}
	
	
	private static byte[] eliminateLinearWhiteSpace(byte[] sin) {
		int start, end;
		for (start = 0; start < sin.length && (sin[start] == ' ' || sin[start] == '\t'); start++);
		for (end = sin.length - 1; end >= start && (sin[end] == ' ' || sin[end] == '\t'); end--);
		end++;
		byte[] sout = new byte[end - start];
		for (int i = 0; start < end; i++, start++)
			sout[i] = sin[start];
		return sout;
	}
	
	
	private static byte[] toASCII(String sin) {
		byte[] sout = new byte[sin.length()];
		for (int i = 0; i < sin.length(); i++) {
			char c = sin.charAt(i);
			if (c >= 0x80)
				c = '?';
			sout[i] = (byte)c;
		}
		return sout;
	}
	
	
	private static String toString(byte[] sin) {
		char[] sout = new char[sin.length];
		for (int i = 0; i < sout.length; i++)
			sout[i] = (char)(sin[i] & 0xFF);
		return new String(sout);
	}
	
}