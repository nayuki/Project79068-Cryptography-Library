package p79068.datastruct;

import java.util.Iterator;
import java.util.NoSuchElementException;
import p79068.lang.NullChecker;


/**
 * An list-based collection.
 */
public final class ArrayCollection<E> extends Collection<E> {
	
	private Object[] objects;
	private int size;
	
	
	public ArrayCollection() {
		this(16);
	}
	
	public ArrayCollection(int initCapacity) {
		objects = new Object[initCapacity];
		size = 0;
	}
	
	
	public int size() {
		return size;
	}
	
	public boolean contains(Object o) {
		NullChecker.check(o);
		for (int i = 0; i < size; i++) {
			if (o.equals(objects[i]))
				return true;
		}
		return false;
	}
	
	public int count(Object o) {
		NullChecker.check(o);
		int count = 0;
		for (int i = 0; i < size; i++) {
			if (o.equals(objects[i]))
				count++;
		}
		return count;
	}
	
	public CollectionView<E> getAll(Object o) {
		NullChecker.check(o);
		return null; // FIXME
	}
	
	public Iterator<E> iterator() {
		return new Itr();
	}
	
	
	public void add(E obj) {
		NullChecker.check(obj);
		if (size == objects.length)
			resize(objects.length * 2);
		objects[size] = obj;
		size++;
	}
	
	@SuppressWarnings("unchecked")
	public E remove(Object o) {
		NullChecker.check(o);
		E result = null;
		for (int i = 0; i < size; i++) {
			if (o.equals(objects[i])) {
				result = (E)objects[i];
				System.arraycopy(objects, i + 1, objects, i, size - 1 - i);
				objects[size - 1] = null;
				size--;
				if (size <= objects.length / 4 && objects.length / 2 >= 1)
					resize(objects.length / 2);
			}
		}
		return result;
	}
	
	public void clear() {
		for (int i = 0; i < size; i++)
			objects[i] = null;
		size = 0;
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