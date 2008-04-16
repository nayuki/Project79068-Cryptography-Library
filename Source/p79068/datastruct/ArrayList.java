package p79068.datastruct;

import java.util.Iterator;
import java.util.NoSuchElementException;
import p79068.lang.*;
import p79068.util.Random;


/**
An array-based list.
<p>Mutability: <em>Mutable</em><br>
 Thread safety: <em>Unsafe</em></p>
*/
public final class ArrayList<E> extends List<E> implements Cloneable {
	
	private E[] data;
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
			throw new IllegalArgumentException("Non-positive initial capacity");
		data = (E[])new Object[initCapacity];
		length = 0;
	}
	
	/**
	 * Creates an array-based list with the contents of the specified list.
	 */
	public ArrayList(ListView<E> list) {
		this(list.length());
		append(list);
	}
	
	
	public int length() {
		return length;
	}
	
	/**
	 * Returns the capacity of the array storing this list. The array is not reallocated until the number of object stored exceeds the capacity.
	 * @return the capacity of the array storing this list
	 */
	public int getCapacity() {
		return data.length;
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
		System.arraycopy(data, 0, newdata, 0, length);
		data = newdata;
	}
	
	public boolean contains(Object o) {
		NullChecker.check(o);
		for (E obj : data) {
			if (o.equals(obj))
				return true;
		}
		return false;
	}
	
	public int count(Object o) {
		NullChecker.check(o);
		int count = 0;
		for (E obj : data) {
			if (o.equals(obj))
				count++;
		}
		return count;
	}
	
	public E getAt(int index) {
		BoundsChecker.check(length, index);
		return data[index];
	}
	
	
	public void append(E obj) {
		NullChecker.check(obj);
		if (length == data.length)
			setCapacity(data.length * 2);
		data[length] = obj;
		length++;
	}
	
	public void append(ListView<? extends E> list) {
		NullChecker.check(list);
		for (E obj : list)
			append(obj);
	}
	
	public void insert(int index, E obj) {
		if (index < 0 || index > length)
			throw new IndexOutOfBoundsException(String.format("Bounds = [0,%d), insertion index = %d", length, index));
		NullChecker.check(obj);
		if (length == data.length)
			setCapacity(data.length * 2);
		int i;
		for (i = length; i > index; i--)
			data[i] = data[i - 1];
		data[i] = obj;
		length++;
	}
	
	public void setAt(int index, E obj) {
		BoundsChecker.check(length, index);
		NullChecker.check(obj);
		data[index] = obj;
	}
	
	public E removeAt(int index) {
		BoundsChecker.check(length, index);
		E result = data[index];
		length--;
		int i;
		for (i = index; i < length; i++)
			data[i] = data[i + 1];
		data[length] = null;
		compactify();
		return result;
	}
	
	public int removeAll(Object o) {
		NullChecker.check(o);
		int removed = 0;
		int oldlength = length;
		for (int i = 0, j = 0; i < length; j++) {
			if (i != j)
				data[i] = data[j];
			if (o.equals(data[i])) {
				length--;
				removed++;
			} else
				i++;
		}
		for (int i = length; i < oldlength; i++)
			data[i] = null;
		compactify();
		return removed;
	}
	
	public void shuffle(Random r) {
		for (int i = 0; i < length; i++) {
			int j = i + r.randomInt(length - i);
			E temp = data[i];
			data[i] = data[j];
			data[j] = temp;
		}
	}
	
	public void clear() {
		for (int i = 0; i < length; i++)
			data[i] = null;
		length = 0;
	}
	
	public void clearTo(E obj) {
		NullChecker.check(obj);
		for (int i = 0; i < length; i++)
			data[i] = obj;
	}
	
	
	public int findNext(Object o) {
		return findNext(o, 0);
	}
	
	public int findNext(Object o, int start) {
		NullChecker.check(o);
		for (int i = start; i < length; i++) {
			if (o.equals(data[i]))
				return i;
		}
		return -1;
	}
	
	public int findPrevious(Object o) {
		return findPrevious(o, length - 1);
	}
	
	public int findPrevious(Object o, int start) {
		NullChecker.check(o);
		for (int i = start; i >= 0; i--) {
			if (o.equals(data[i]))
				return i;
		}
		return -1;
	}
	
	public Iterator<E> iterator() {
		return new Itr();
	}
	
	private void compactify() {
		if (length <= data.length / 4 && data.length / 2 >= 1)
			setCapacity(data.length / 2);
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
		
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			removed = false;
			index++;
			return data[index];
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