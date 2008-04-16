package p79068.datastruct;

import p79068.lang.BoundsChecker;


public class DynamicArray<E> {
	
	private Object[] values;
	private int length;
	
	private double resizeFactor;
	
	
	public DynamicArray() {
		this(1, 2);
	}
	
	
	public DynamicArray(int initCapacity, double resizeFactor) {
		values = new Object[initCapacity];
		this.resizeFactor = resizeFactor;
	}
	
	
	public int length() {
		return length;
	}
	
	
	@SuppressWarnings("unchecked")
	public E get(int index) {
		BoundsChecker.check(length, index);
		return (E)values[index];
	}
	
	
	@SuppressWarnings("unchecked")
	public void set(int index, E value) {
		BoundsChecker.check(length, index);
		values[index] = value;
	}
	
	
	public void append(E value) {
		if (length == values.length) {
			long newcap = Math.max(Math.round(length * resizeFactor), length + 1);
			newcap = Math.min(newcap, Integer.MAX_VALUE);
			resize((int)newcap);
		}
		values[length] = value;
		length++;
	}
	
	
	@SuppressWarnings("unchecked")
	public E removeTail() {
		if (length == 0)
			throw new IllegalStateException("Array is empty");
		E result = (E)values[length - 1];
		length--;
		return result;
	}
	
	
	public void resize(int newCapacity) {
		if (newCapacity < length)
			throw new IllegalArgumentException("New capacity less than length");
		Object[] newvalues = new Object[newCapacity];
		System.arraycopy(values, 0, newvalues, 0, length);
		values = newvalues;
	}
	
}