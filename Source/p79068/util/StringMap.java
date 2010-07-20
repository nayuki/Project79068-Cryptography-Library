package p79068.util;


public class StringMap {
	
	protected String[] key;
	protected String[] value;
	protected int length;
	
	
	public StringMap() {
		key = new String[1];
		value = new String[1];
		length = 0;
	}
	
	
	public String[] get(int index) {
		return new String[]{key[index], value[index]};
	}
	
	public String[] getCaseInsensitive(String key) {
		String[] ret = new String[1];
		int j = 0;
		for (int i = 0; i < length; i++) {
			if (this.key[i].equalsIgnoreCase(key)) {
				if (j == ret.length)
					ret = resize(ret, j * 2);
				ret[j] = value[i];
				j++;
			}
		}
		return resize(ret, j);
	}
	
	
	public void put(String key, String value) {
		if (this.key.length == length) {
			this.key = resize(this.key, length * 2);
			this.value = resize(this.value, length * 2);
		}
		this.key[length] = key;
		this.value[length] = value;
		length++;
	}
	
	
	public int length() {
		return length;
	}
	
	
	private static String[] resize(String[] ain, int len) {
		if (ain.length == len)
			return ain;
		String[] aout = new String[len];
		if (ain.length < len)
			len = ain.length;
		for (int i = 0; i < len; i++)
			aout[i] = ain[i];
		return aout;
	}
}