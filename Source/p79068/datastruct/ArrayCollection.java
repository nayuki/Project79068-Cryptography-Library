package p79068.datastruct;

import java.util.Iterator;
import java.util.NoSuchElementException;
import p79068.lang.NullChecker;


/**
 * An array-based collection.
 */
public final class ArrayCollection<E> extends AbstractDynamicArray<E> implements Collection<E> {
	
	public ArrayCollection() {
		this(16);
	}
	
	
	public ArrayCollection(int initCapacity) {
		super(initCapacity, 2);
	}
	
	
	public ArrayCollection(Collection<? extends E> coll) {
		this(coll.size());
		addAll(coll);
	}
	
	
	
	public int size() {
		return length;
	}
	
	
	public boolean contains(Object obj) {
		NullChecker.check(obj);
		for (int i = 0; i < length; i++) {
			if (obj.equals(objects[i]))
				return true;
		}
		return false;
	}
	
	
	public boolean containsAll(Collection<?> coll) {
		NullChecker.check(coll);
		for (Object obj : coll) {
			if (count(obj) < coll.count(obj))
				return false;
		}
		return true;
	}
	
	
	public int count(Object obj) {
		NullChecker.check(obj);
		int count = 0;
		for (int i = 0; i < length; i++) {
			if (obj.equals(objects[i]))
				count++;
		}
		return count;
	}
	
	
	public void add(E obj) {
		NullChecker.check(obj);
		upsize(length + 1);
		objects[length] = obj;
		length++;
	}
	
	
	public void addAll(Collection<? extends E> coll) {
		NullChecker.check(coll);
		for (E obj : coll)
			add(obj);
	}
	
	
	public boolean remove(Object obj) {
		NullChecker.check(obj);
		for (int i = 0; i < length; i++) {
			if (obj.equals(objects[i])) {
				System.arraycopy(objects, i + 1, objects, i, length - (i + 1));
				objects[length - 1] = null;
				length--;
				downsize();
				return true;
			}
		}
		return false;
	}
	
	
	public int removeAll(Collection<?> coll) {
		NullChecker.check(coll);
		int count = 0;
		for (Object obj : coll) {
			if (remove(obj))
				count++;
		}
		return count;
	}
	
	
	public int removeAllOf(Object obj) {
		int count = 0;
		while (remove(obj))
			count++;
		return count;
	}
	
	
	public void clear() {
		for (int i = 0; i < length; i++)
			objects[i] = null;
		length = 0;
	}
	
	
	public Iterator<E> iterator() {
		return new Itr();
	}
	
	
	public boolean equals(Object other) {
		if (other == this)
			return true;
		else if (!(other instanceof Collection))
			return false;
		else {
			Collection<?> coll = (Collection<?>)other;
			return this.containsAll(coll) && coll.containsAll(this);
		}
	}
	
	
	public int hashCode() {
		int hash = 0;
		for (int i = 0; i < length; i++)
			hash += objects[i].hashCode();
		return hash;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Collection [");
		for (int i = 0; i < length; i++) {
			if (i != 0)
				sb.append(", ");
			sb.append(objects[i]);
		}
		sb.append("]");
		return sb.toString();
	}
	
	
	
	private class Itr implements Iterator<E> {
		
		private int index;
		
		
		Itr() {
			index = 0;
		}
		
		
		public boolean hasNext() {
			return index != length;
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