package p79068.datastruct;

import java.util.Iterator;
import java.util.NoSuchElementException;
import p79068.lang.NullChecker;


/**
 * An array-based collection.
 */
public final class ArrayCollection<E> implements Collection<E> {
	
	private Object[] objects;
	
	private int size;
	
	
	
	public ArrayCollection() {
		this(16);
	}
	
	
	public ArrayCollection(int initCapacity) {
		objects = new Object[initCapacity];
		size = 0;
	}
	
	
	public ArrayCollection(Collection<? extends E> coll) {
		this(coll.size());
		addAll(coll);
	}
	
	
	
	public int size() {
		return size;
	}
	
	
	public boolean contains(Object obj) {
		NullChecker.check(obj);
		for (int i = 0; i < size; i++) {
			if (obj.equals(objects[i]))
				return true;
		}
		return false;
	}
	
	
	public int count(Object obj) {
		NullChecker.check(obj);
		int count = 0;
		for (int i = 0; i < size; i++) {
			if (obj.equals(objects[i]))
				count++;
		}
		return count;
	}
	
	
	public void add(E obj) {
		NullChecker.check(obj);
		if (size == objects.length)
			resize(objects.length * 2);
		objects[size] = obj;
		size++;
	}
	
	
	public void addAll(Collection<? extends E> coll) {
		NullChecker.check(coll);
		for (E obj : coll)
			add(obj);
	}
	
	
	@SuppressWarnings("unchecked")
	public E remove(Object obj) {
		NullChecker.check(obj);
		for (int i = 0; i < size; i++) {
			if (obj.equals(objects[i])) {
				E result = (E)objects[i];
				System.arraycopy(objects, i + 1, objects, i, size - (i + 1));
				objects[size - 1] = null;
				size--;
				if (size <= objects.length / 4 && objects.length / 2 >= 1)
					resize(objects.length / 2);
				return result;
			}
		}
		return null;
	}
	
	
	public int removeAll(Collection<?> coll) {
		NullChecker.check(coll);
		int count = 0;
		for (Object obj : coll) {
			if (remove(obj) != null)
				count++;
		}
		return count;
	}
	
	
	public int removeAllOf(Object obj) {
		int count = 0;
		while (remove(obj) != null)
			count++;
		return count;
	}
	
	
	public void clear() {
		for (int i = 0; i < size; i++)
			objects[i] = null;
		size = 0;
	}
	
	
	public Iterator<E> iterator() {
		return new Itr();
	}
	
	
	public boolean isSubsetOf(Collection<?> coll) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean isSupersetOf(Collection<?> coll) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public boolean equals(Object other) {
		if (other == this)
			return true;
		else if (!(other instanceof Collection))
			return false;
		else {
			Collection<?> coll = (Collection<?>)other;
			return isSubsetOf(coll) && isSupersetOf(coll);
		}
	}
	
	
	public int hashCode() {
		int hash = 0;
		for (int i = 0; i < size; i++)
			hash += objects[i].hashCode();
		return hash;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Collection [");
		for (int i = 0; i < size; i++) {
			sb.append(objects[i]);
			if (i != 0)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	
	
	private void resize(int newCap) {
		if (newCap < size)
			throw new AssertionError();
		Object[] newobjects = new Object[newCap];
		System.arraycopy(objects, 0, newobjects, 0, size);
		objects = newobjects;
	}
	
	
	
	private class Itr implements Iterator<E> {
		
		private int index;
		
		
		Itr() {
			index = 0;
		}
		
		
		public boolean hasNext() {
			return index != size;
		}
		
		@SuppressWarnings("unchecked")
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			E result = (E)objects[index];
			index++;
			return result;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
}