package p79068.datastruct;

import java.util.Arrays;
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
public final class ArrayList<E> extends AbstractDynamicArray<E> implements List<E> {
	
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
	public ArrayList(int initCapacity) {
		super(initCapacity, 2);
	}
	
	
	/**
	 * Creates an array-based list with the contents of the specified list.
	 * @param list TODO
	 */
	public ArrayList(List<? extends E> list) {
		this(list.length());
		appendList(list);
	}
	
	
	/**
	 * Creates an array-based list with the contents of the specified argument list or array.
	 * @param objs TODO
	 */
	public ArrayList(E... objs) {
		this(objs.length);
		for (E obj : objs)
			append(obj);
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
		upsize(length + 1);
		objects[length] = obj;
		length++;
	}
	
	
	public void appendList(List<? extends E> list) {
		NullChecker.check(list);
		upsize(length + list.length());
		for (E obj : list) {
			objects[length] = obj;
			length++;
		}
	}
	
	
	public void insertAt(int index, E obj) {
		if (index < 0 || index > length)
			throw new IndexOutOfBoundsException(String.format("Bounds = [0,%d), insertion index = %d", length, index));
		upsize(length + 1);
		System.arraycopy(objects, index, objects, index + 1, length - index);
		objects[index] = obj;
		length++;
	}
	
	
	public void insertListAt(int index, List<? extends E> list) {
		NullChecker.check(list);
		if (index < 0 || index > length)
			throw new IndexOutOfBoundsException(String.format("Bounds = [0,%d), insertion index = %d", length, index));
		int listlen = list.length();
		upsize(length + listlen);
		System.arraycopy(objects, index, objects, index + listlen, length - index);
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
		downsize();
		return result;
	}
	
	
	public void removeRange(int offset, int length) {
		BoundsChecker.check(this.length, offset, length);
		System.arraycopy(objects, offset + length, objects, offset, this.length - length);
		for (int i = this.length - length; i < this.length; i++)
			objects[i] = null;
		this.length -= length;
		downsize();
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
		else if (newCapacity != objects.length)  // Only do something if the capacity changed
			objects = Arrays.copyOf(objects, newCapacity);
	}
	
	
	@Override
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
	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	@Override
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
		
		
		public void insertAt(int index, E obj) {
			throw new UnsupportedOperationException();
		}
		
		
		public void insertListAt(int index, List<? extends E> list) {
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