package p79068.datastruct;

import java.util.Iterator;
import java.util.NoSuchElementException;
import p79068.lang.*;
import p79068.util.Random;


/**
 * An array-based list.
 * <p>Mutability: <em>Mutable</em><br>
 *  Thread safety: <em>Unsafe</em></p>
 */
public final class ArrayList<E> implements List<E> {
	
	private Object[] objects;
	
	private int length;
	
	
	/**
	 * Creates an array-based list. The initial capacity is arbitrary.
	 */
	public ArrayList() {
		this(16);
	}
	
	
	/**
	 * Creates an array-based list with the specified initial capacity.
	 * @param initCapacity the initial capacity
	 */
	@SuppressWarnings("unchecked")
	public ArrayList(int initCapacity) {
		if (initCapacity < 1)
			throw new IllegalArgumentException("Initial capacity less than 1");
		objects = new Object[initCapacity];
		length = 0;
	}
	
	
	/**
	 * Creates an array-based list with the contents of the specified list.
	 */
	public ArrayList(List<? extends E> list) {
		this(list.length());
		appendList(list);
	}
	
	
	public int length() {
		return length;
	}
	
	
	@SuppressWarnings("unchecked")
	public E getAt(int index) {
		BoundsChecker.check(length, index);
		return (E)objects[index];
	}
	
	
	public void setAt(int index, E obj) {
		BoundsChecker.check(length, index);
		objects[index] = obj;
	}
	
	
	public void append(E obj) {
		if (length == objects.length)
			setCapacity(objects.length * 2);
		objects[length] = obj;
		length++;
	}
	
	public void appendList(List<? extends E> list) {
		NullChecker.check(list);
		for (E obj : list)
			append(obj);
	}
	
	public void insert(int index, E obj) {
		if (index < 0 || index > length)
			throw new IndexOutOfBoundsException(String.format("Bounds = [0,%d), insertion index = %d", length, index));
		if (length == objects.length)
			setCapacity(objects.length * 2);
		int i;
		for (i = length; i > index; i--)
			objects[i] = objects[i - 1];
		objects[i] = obj;
		length++;
	}
	
	
	public void insertList(int index, List<? extends E> list) {
		NullChecker.check(list);
		for (E obj : list) {
			insert(index, obj);
			index++;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public E removeAt(int index) {
		BoundsChecker.check(length, index);
		E result = (E)objects[index];
		System.arraycopy(objects, index + 1, objects, index, length - (index + 1));
		objects[length - 1] = null;
		length--;
		compactify();
		return result;
	}
	
	
	public void removeRange(int offset, int length) {
		for (int i = 0; i < length; i++)
			removeAt(offset);
	}
	
	
	public void clear() {
		for (int i = 0; i < length; i++)
			objects[i] = null;
		length = 0;
	}
	
	
	public Iterator<E> iterator() {
		return new Itr();
	}
	
	
	public Collection<E> asCollection() {
		return new ListCollectionAdapter<E>(this);
	}
	
	
	public void shuffle(Random r) {
		for (int i = 0; i < length; i++) {
			int j = i + r.randomInt(length - i);
			Object temp = objects[i];
			objects[i] = objects[j];
			objects[j] = temp;
		}
	}
	
	
	/**
	 * Returns the capacity of the array storing this list. The array is not reallocated until the number of object stored exceeds the capacity.
	 * @return the capacity of the array storing this list
	 */
	public int getCapacity() {
		return objects.length;
	}
	
	
	/**
	 * Sets the capacity of the array storing this list to the specified value. It must be greater than or equal to the length of the list.
	 * @param newCap the new capacity to set to
	 * @throws IllegalArgumentException if the new capacity is less than 1 or less than the length of the list
	 */
	@SuppressWarnings("unchecked")
	public void setCapacity(int newCap) {
		if (newCap < length || newCap < 1)
			throw new IllegalArgumentException("New capacity too small");
		E[] newdata = (E[])new Object[newCap];
		System.arraycopy(objects, 0, newdata, 0, length);
		objects = newdata;
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object other) {
		if (other == this)
			return true;
		else if (!(other instanceof List))
			return false;
		else if (other instanceof ArrayList) {
			ArrayList<?> list = (ArrayList<?>)other;
			if (length != list.length)
				return false;
			for (int i = 0; i < length; i++) {
				if (!equals(objects[i], list.objects[i]))
					return false;
			}
			return true;
		} else {
			List<?> list = (List<?>)other;
			int i = 0;
			for (Object obj : list) {
				if (!equals(objects[i], obj))
					return false;
				i++;
			}
			return i == length;
		}
	}
	
	
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("List [");
		for (int i = 0; i < length; i++) {
			if (i != 0)
				sb.append(", ");
			sb.append(objects[i]);
		}
		sb.append("]");
		return sb.toString();
	}
	
	
	private void compactify() {
		if (length <= objects.length / 4 && objects.length / 2 >= 1)
			setCapacity(objects.length / 2);
	}
	
	
	// This is equivalent to ((x == null && y == null) || (x != null && y != null && x.equals(y))), as long as when x is not null, x.equals(null) returns false.
	private static boolean equals(Object x, Object y) {
		if (x == null)
			return y == null;
		else
			return x.equals(y);
	}
	
	
	
	private class Itr implements Iterator<E> {
		
		private int index;
		private boolean removed;
		
		
		
		Itr() {
			index = -1;
			removed = false;
		}
		
		
		
		public boolean hasNext() {
			return index + 1 != length;
		}
		
		
		@SuppressWarnings("unchecked")
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			removed = false;
			index++;
			return (E)objects[index];
		}
		
		
		public void remove() {
			if (index == -1)
				throw new IllegalStateException("next() not called yet");
			else if (removed)
				throw new IllegalStateException("Element already removed");
			else {
				removeAt(index);
				index--;
				removed = true;
			}
		}
		
	}
	
}