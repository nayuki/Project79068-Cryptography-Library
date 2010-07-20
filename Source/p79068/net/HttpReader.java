package p79068.net;

import java.io.IOException;
import java.io.InputStream;


public final class HttpReader {
	
	private InputStream in;
	
	
	
	public HttpReader(InputStream in) {
		this.in = in;
	}
	
	
	
	public HttpRequest readRequest() throws IOException {
		HttpRequest request = new HttpRequest();
		{
			String[] temp = readLine().split(" ", 3);
			if (temp.length != 3)
				throw new RuntimeException();
			request.setMethod(temp[0]);
			request.setUri(temp[1]);
			request.setHttpVersion(temp[2]);
		}
		
		while (true) {
			String line = readLine();
			if (line.equals(""))
				break;
			String[] temp = line.split(":", 2);
			if (temp.length == 1)
				request.addHeader(temp[0], null);
			else
				request.addHeader(temp[0], temp[1]);
		}
		
		// TODO: Read message body
		
		return request;
	}
	
	
	public HttpResponse readResponse() throws IOException {
		return readResponse(true);
	}
	
	
	public HttpResponse readHeadResponse() throws IOException {
		return readResponse(false);
	}
	
	
	private HttpResponse readResponse(boolean allowEntity) throws IOException {
		HttpResponse response = new HttpResponse();
		{
			String[] temp = readLine().split(" ", 3);
			if (temp.length != 3)
				throw new RuntimeException();
			response.setHttpVersion(temp[0]);
			response.setStatusCode(Integer.parseInt(temp[1]));
			response.setReasonPhrase(temp[2]);
		}
		
		while (true) {
			String line = readLine();
			if (line.equals(""))
				break;
			String[] temp = line.split(":", 2);
			if (temp.length == 1)
				response.addHeader(temp[0], null);
			else
				response.addHeader(temp[0], temp[1]);
		}
		/* Read message body */
		return response;
	}
	
	
	private String readLine() throws IOException {
		StringBuilder sb = new StringBuilder();
		while (true) {
			int b = in.read();
			if (b == -1)
				throw new RuntimeException();
			else if (b == '\n')
				throw new RuntimeException("Premature end of stream");
			else if (b == '\r') {
				if (in.read() != '\n')
					throw new RuntimeException();
				else
					break;
			} else
				sb.append((char)b);
		}
		return sb.toString();
	}
	
}