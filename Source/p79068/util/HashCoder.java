package p79068.util;


public abstract class HashCoder {
	
	public static HashCoder newInstance() {
		return new SimpleHashCoder();
	}
	
	
	public abstract HashCoder add(byte x);
	
	public abstract HashCoder add(short x);
	
	public abstract HashCoder add(int x);
	
	public abstract HashCoder add(long x);
	
	public abstract HashCoder add(float x);
	
	public abstract HashCoder add(double x);
	
	public abstract HashCoder add(boolean x);
	
	public abstract HashCoder add(char x);
	
	public abstract HashCoder add(Object x);
	
	
	public abstract int getHashCode();
}