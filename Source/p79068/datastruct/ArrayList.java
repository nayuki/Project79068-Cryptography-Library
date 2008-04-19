package p79068.datastruct;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import p79068.lang.BoundsChecker;
import p79068.lang.NullChecker;
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
		ensureCapacity(length + 1);
		objects[length] = obj;
		length++;
	}
	
	
	public void appendList(List<? extends E> list) {
		NullChecker.check(list);
		ensureCapacity(length + list.length());
		for (E obj : list) {
			objects[length] = obj;
			length++;
		}
	}
	
	
	public void insert(int index, E obj) {
		if (index < 0 || index > length)
			throw new IndexOutOfBoundsException(String.format("Bounds = [0,%d), insertion index = %d", length, index));
		ensureCapacity(length + 1);
		System.arraycopy(objects, index, objects, index + 1, length - index);
		objects[index] = obj;
		length++;
	}
	
	
	public void insertList(int index, List<? extends E> list) {
		NullChecker.check(list);
		if (index < 0 || index > length)
			throw new IndexOutOfBoundsException(String.format("Bounds = [0,%d), insertion index = %d", length, index));
		int listlen = list.length();
		ensureCapacity(length + listlen);
		System.arraycopy(objects, index, objects, index + listlen, length - index);
		length += listlen;
		for (E obj : list) {
			if (listlen == 0)
				throw new ConcurrentModificationException();
			objects[index] = obj;
			index++;
			length++;
			listlen--;
		}
		if (listlen != 0)
			throw new ConcurrentModificationException();
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
		BoundsChecker.check(this.length, offset, length);
		System.arraycopy(objects, offset + length, objects, offset, this.length - length);
		for (int i = this.length - length; i < this.length; i++)
			objects[i] = null;
		this.length -= length;
	}
	
	
	public void clear() {
		for (int i = 0; i < length; i++)
			objects[i] = null;
		length = 0;
	}
	
	
	public List<E> sublist(int offset, int length) {
		BoundsChecker.check(this.length, offset, length);
		return new Sublist(offset, length);
	}
	
	
	public Iterator<E> iterator() {
		return new Itr();
	}
	
	
	public Collection<E> asCollection() {
		return new ListCollectionAdapter<E>(this);
	}
	
	
	public void shuffle(Random rand) {
		for (int i = 0; i < length; i++) {
			int j = i + rand.randomInt(length - i);
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
	 * @param newCapacity the new capacity to set to
	 * @throws IllegalArgumentException if the new capacity is less than 1 or less than the length of the list
	 */
	public void setCapacity(int newCapacity) {
		if (newCapacity < length || newCapacity < 1)
			throw new IllegalArgumentException("New capacity too small");
		else if (newCapacity != objects.length) {  // Only do something if the capacity changed
			Object[] newdata = new Object[newCapacity];
			System.arraycopy(objects, 0, newdata, 0, length);
			objects = newdata;
		}
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
	
	
	private void ensureCapacity(int capacity) {
		int newcapacity = objects.length;
		while (newcapacity < capacity)
			newcapacity *= 2;
		setCapacity(newcapacity);
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
		private int end;
		private boolean canRemove;
		
		
		
		public Itr() {
			this(0, length);
		}
		
		
		public Itr(int offset, int length) {
			index = offset;
			end = offset + length;
			canRemove = false;
		}
		
		
		
		public boolean hasNext() {
			return index != end;
		}
		
		
		@SuppressWarnings("unchecked")
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			canRemove = true;
			E result = (E)objects[index];
			index++;
			return result;
		}
		
		
		public void remove() {
			if (canRemove) {
				removeAt(index - 1);
				index--;
				end--;
				canRemove = false;
			} else
				throw new IllegalStateException("Element already removed or next() not called yet");
		}
		
	}
	
	
	
	private class Sublist implements List<E> {
		
		private int offset;
		
		private int length;
		
		
		
		public Sublist(int offset, int length) {
			this.offset = offset;
			this.length = length;
		}
		
		
		
		public int length() {
			return length;
		}
		
		
		@SuppressWarnings("unchecked")
		public E getAt(int index) {
			BoundsChecker.check(length, index);
			return (E)objects[offset + index];
		}
		
		
		public List<E> sublist(int offset, int length) {
			BoundsChecker.check(this.length, offset, length);
			return new Sublist(this.offset + offset, length);
		}
		
		
		public Iterator<E> iterator() {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		public Collection<E> asCollection() {
			return new ListCollectionAdapter<E>(this);
		}
		
		
		
		public void append(E obj) {
			throw new UnsupportedOperationException();
		}
		
		
		public void appendList(List<? extends E> list) {
			throw new UnsupportedOperationException();
		}
		
		
		public void clear() {
			throw new UnsupportedOperationException();
		}
		
		
		public void insert(int index, E obj) {
			throw new UnsupportedOperationException();
		}
		
		
		public void insertList(int index, List<? extends E> list) {
			throw new UnsupportedOperationException();
		}
		
		
		public E removeAt(int index) {
			throw new UnsupportedOperationException();
		}
		
		
		public void removeRange(int offset, int length) {
			throw new UnsupportedOperationException();
		}
		
		
		public void setAt(int index, E obj) {
			throw new UnsupportedOperationException();
		}
		
	}
	
}