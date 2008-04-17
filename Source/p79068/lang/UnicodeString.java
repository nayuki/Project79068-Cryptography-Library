package p79068.lang;

import p79068.util.HashCoder;


/**
A string where each element can take on the full ~24 bits of Unicode's range.
<p>Mutability: <em>Immutable</em></p>
*/
public final class UnicodeString implements Comparable<UnicodeString> {
	
	private int[] chars;
	public final int length;
	
	
	public UnicodeString(String str) {
		chars = new int[str.length()];
		int j = 0;
		for (int i = 0; i < str.length(); j++) {
			char c = str.charAt(i);
			if (c < 0xD800 || c >= 0xE000) { // Not a surrogate
				chars[j] = c;
				i++;
			} else {
				if (c >= 0xDC00 && c < 0xE000)
					throw new IllegalArgumentException("Malformed string"); // Low surrogate
				int tp = c;
				i++;
				if (i == str.length())
					throw new IllegalArgumentException("Malformed string");
				c = str.charAt(i);
				if (c < 0xDC00 || c >= 0xE000)
					throw new IllegalArgumentException("Malformed string"); // Not a low surrogate
				chars[j] = ((tp - 0xD800) << 10 | (c - 0xDC00)) + 0x10000;
				i++;
			}
		}
		length = j;
		if (length != str.length()) {
			int[] temp = new int[length];
			System.arraycopy(chars, 0, temp, 0, length);
			chars = temp;
		}
	}
	
	/**
	Constructs blah...
	@throws NullPointerException if <code>char</code> is null
	@throws IllegalArgumentException if <code>char</code> contains values less than 0 or greater than 0x10FFFF.
	*/
	public UnicodeString(int[] chars) {
		this(chars, 0, chars.length);
	}
	
	public UnicodeString(int[] chars, int off, int len) {
		BoundsChecker.check(chars.length, off, len);
		length = len;
		this.chars = new int[len];
		for (int i = 0; i < len; i++) {
			int c = chars[off + i];
			if (c < 0 || c > 0x10FFFF)
				throw new IllegalArgumentException();
			this.chars[i] = c;
		}
	}
	
	
	public int charAt(int index) {
		BoundsChecker.check(chars.length, index);
		return chars[index];
	}
	
	public UnicodeString substrOffLen(int offset, int length) {
		return new UnicodeString(chars, offset, length);
	}
	
	public UnicodeString substrStartEnd(int start, int end) {
		return substrOffLen(start, end - start);
	}
	
	
	public int find(int ch) {
		return find(ch, 0);
	}
	
	public int find(int ch, int start) {
		BoundsChecker.check(length, start);
		if (ch < 0 || ch > 0x10FFFF)
			throw new IllegalArgumentException("Invalid Unicode code point");
		for (; start < chars.length; start++) {
			if (chars[start] == ch)
				return start;
		}
		return -1;
	}
	
	public int reverseFind(int ch) {
		return reverseFind(ch, chars.length);
	}
	
	public int reverseFind(int ch, int start) {
		BoundsChecker.check(length, start);
		if (ch < 0 || ch > 0x10FFFF)
			throw new IllegalArgumentException("Invalid Unicode code point");
		for (; start >= 0; start--) {
			if (chars[start] == ch)
				return start;
		}
		return -1;
	}
	
	
	public String toJavaString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			int c = chars[i];
			if (c < 0x10000)
				sb.append((char)c);
			else {
				c -= 0x10000;
				sb.append((char)(0xD800 | c >>> 10));
				sb.append((char)(0xDC00 | c & 0x3FF));
			}
		}
		return sb.toString();
	}
	
	public String toString() {
		return toJavaString();
	}
	
	
	public boolean equals(Object obj) {
		if (!(obj instanceof UnicodeString))
			return false;
		UnicodeString str = (UnicodeString)obj;
		if (length != str.length)
			return false;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != str.chars[i])
				return false;
		}
		return true;
	}
	
	public int hashCode() {
		HashCoder h = HashCoder.newInstance();
		for (int i = 0; i < chars.length; i++)
			h.add(chars[i]);
		return h.getHashCode();
	}
	
	public int compareTo(UnicodeString str) {
		for (int i = 0; i < chars.length && i < str.chars.length; i++) {
			if (chars[i] != str.chars[i])
				return chars[i] - str.chars[i];
		}
		return length - str.length;
	}
}