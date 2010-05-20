package p79068.net;

import java.io.*;
import java.util.*;


public class HttpWriter {
	
	protected OutputStream out;
	
	
	
	public HttpWriter(OutputStream out) {
		this.out = out;
	}
	
	
	
	public void writeRequest(HttpRequest request) throws IOException {
		out.write(toBytes(String.format("%s %s %s\r\n", request.method, request.uri, request.httpVersion)));
		for (String key : request.headers.keySet()) {
			List<String> values = request.headers.get(key);
			if (values.size() == 0)
				out.write(toBytes(String.format("%s\r\n", key)));
			else {
				for (String value : values)
					out.write(toBytes(String.format("%s: %s\r\n", key, value)));
			}
		}
		out.write(toBytes("\r\n"));
		out.flush();
	}
	
	
	public void writeResponse(HttpResponse response) throws IOException {
		out.write(toBytes(String.format("%s %03d %s\r\n", response.httpVersion, response.statusCode, response.reason)));
		for (String key : response.headers.keySet()) {
			List<String> values = response.headers.get(key);
			if (values.size() == 0)
				out.write(toBytes(String.format("%s\r\n", key)));
			else {
				for (String value : values)
					out.write(toBytes(String.format("%s: %s\r\n", key, value)));
			}
		}
		out.write(toBytes("\r\n"));
		out.flush();
	}
	
	
	protected static byte[] toBytes(String str) {
		byte[] b = new byte[str.length()];
		for (int i = 0; i < b.length; i++) {
			char c = str.charAt(i);
			if (c < 0x80)
				b[i] = (byte)c;
			else
				b[i] = '?';
		}
		return b;
	}
	
}