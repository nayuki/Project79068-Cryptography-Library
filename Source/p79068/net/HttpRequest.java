package p79068.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpRequest {
	
	protected String method;
	protected String uri;
	protected String httpVersion;
	protected Map<String, List<String>> headers;
	protected byte[] message;
	
	
	
	HttpRequest() {
		method = null;
		uri = null;
		httpVersion = null;
		headers = new HashMap<String, List<String>>();
		message = null;
	}
	
	
	public HttpRequest(String method, String uri) {
		this(method, uri, null);
	}
	
	
	public HttpRequest(String method, String uri, byte[] message) {
		setMethod(method);
		setUri(uri);
		setHttpVersion("HTTP/1.1");
		setMessage(message);
		headers = new HashMap<String, List<String>>();
	}
	
	
	
	public String getMethod() {
		return method;
	}
	
	
	public String getUri() {
		return uri;
	}
	
	
	public String getHttpVersion() {
		return httpVersion;
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, List<String>> getHeaders() {
		return (Map<String, List<String>>)((HashMap)headers).clone();
	}
	
	
	public byte[] getMessage() {
		return message.clone();
	}
	
	
	public void setMethod(String method) {
		if (!isToken(method))
			throw new IllegalArgumentException();
		this.method = method;
	}
	
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
	public void setHttpVersion(String version) {
		httpVersion = version;
	}
	
	
	public void addHeader(String name, String value) {
		if (!isToken(name))
			throw new IllegalArgumentException();
		if (!headers.containsKey(name))
			headers.put(name, new ArrayList<String>());
		if (value != null)
			headers.get(name).add(value);
	}
	
	
	public void setMessage(byte[] message) {
		if (message != null)
			this.message = message.clone();
		else
			this.message = null;
	}
	
	
	protected static boolean isToken(String str) {
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c < 32 || c == ' ' || c == '(' || c == ')' || c == '<' || c == '>' || c == '@' || c == ',' || c == ';' || c == ':' || c == '\\' || c == '"' || c == '/' || c == '[' || c == ']' || c == '?' || c == '=' || c == '{' || c == '}')
				return false;
		}
		return true;
	}
	
}