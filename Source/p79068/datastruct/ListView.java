package p79068.datastruct;

import java.util.Iterator;
import java.util.NoSuchElementException;
import p79068.util.HashCoder;


/**
 * An abstract readable list.
 */
public abstract class ListView<E> implements Iterable<E>{
	
	/**
	 * Creates a readable list.
	 */
	protected ListView() { }
	
	
	/**
	 * Returns the object at the specified index.
	 */
	public abstract E getAt(int index);
	
	/**
	 * Returns the length of this list.
	 */
	@SuppressWarnings("unused")
	public abstract int length();
	
	/**
	 * Returns the index of the first occurrence of the specified object. Equivalent to <code>findNext(o, 0)</code>.
	 */
	public int findNext(Object o) {
		return findNext(o, 0);
	}
	
	/**
	 * Returns the index of the first occurrence of the specified object, starting at the specified index (inclusive).
	 */
	public abstract int findNext(Object o, int start);
	
	/**
	 * Returns the index of the first occurrence of the specified object, searching backwards. Equivalent to <code>findPrevious(o, length()-1)</code>.
	 */
	public int findPrevious(Object o) {
		return findPrevious(o, length() - 1);
	}
	
	/**
	 * Returns the index of the first occurrence of the specified object, searching backwards starting at the specified index (inclusive).
	 */
	public abstract int findPrevious(Object o, int start);
	
	
	/**
	 * Returns an iterator over the objects in this list, traversing in order.
	 */
	public Iterator<E> iterator() {
		return new Itr();
	}
	
	public CollectionView<E> toCollection() {
		return new ListCollectionView<E>(this);
	}
	
	
	/**
	 * Compares with the specified object for equality.
	 * @param other the object to compare to
	 * @return whether the <code>other</code> is a list with the same contents as this
	 */
	public boolean equals(Object other) {
		if (other == this)
			return true;
		else if (!(other instanceof ListView))
			return false;
		else {
			ListView<?> list = (ListView<?>)other;
			if (length() != list.length())
				return false;
			for (int i = 0; i < length(); i++) {
				if (!getAt(i).equals(list.getAt(i)))
					return false;
			}
			return true;
		}
	}
	
	public int hashCode() {
		HashCoder hc = HashCoder.newInstance();
		hc.add(length());
		for (E o : this)
			hc.add(o);
		return hc.getHashCode();
	}
	
	/**
	 * Returns a string representation of this list. Currently, it lists all its elements in order. This is subjected to change.
	 * @return a string representation of this list
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("List [");
		for (E obj : this)
			sb.append(obj).append(", ");
		if (sb.substring(Math.max(sb.length() - 2, 0), sb.length()).equals(", "))
			sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		return sb.toString();
	}
	
	
	
	private class Itr implements Iterator<E> {
		
		private int index;
		
		
		Itr() {
			index = 0;
		}
		
		
		public boolean hasNext() {
			return index != length();
		}
		
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			E result = getAt(index);
			index++;
			return result;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
}