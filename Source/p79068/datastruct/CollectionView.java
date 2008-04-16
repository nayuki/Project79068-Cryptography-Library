package p79068.datastruct;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import p79068.lang.NullChecker;
import p79068.util.HashCoder;


/**
 * An abstract readable collection. Null values must not be stored in the collection. In general, invoking a method with a null argument will cause a <code>NullPointerException</code> to be thrown.
 */
public abstract class CollectionView<E> implements Iterable<E> {
	
	/**
	 * Creates a readable collection.
	 */
	protected CollectionView() { }
	
	
	/**
	 * Returns the number of objects in this collection.
	 * @return the number of objects in this collection
	 */
	@SuppressWarnings("unused")
	public int size() {
		int size = 0;
		for (E o : this)
			size++;
		return size;
	}
	
	/**
	 * Tests whether this collection contains the specified object.
	 * @param o the object whose presence to test
	 * @return whether <code>o</code> is in this collection
	 */
	public boolean contains(Object o) {
		NullChecker.check(o);
		for (E obj : this) {
			if (o.equals(obj))
				return true;
		}
		return false;
	}
	
	/**
	 * Returns the number of objects in this collection equal to the specified object.
	 * @param o the object whose presence to count
	 * @return the number of objects in this collection equal to <code>o</code>
	 */
	public int count(Object o) {
		NullChecker.check(o);
		int count = 0;
		for (E obj : this) {
			if (o.equals(obj))
				count++;
		}
		return count;
	}
	
	/**
	 * Returns the objects in this collection that are equal to the specified object. The returned collection is read-only.
	 * @param o the object to compare to
	 * @return a read-only collection of objects from this collection equal to the specified object
	 */
	public CollectionView<E> getAll(Object o) {
		NullChecker.check(o);
		List<E> result = new ArrayList<E>();
		for (E obj : this) {
			if (o.equals(obj))
				result.append(obj);
		}
		return null; // FIXME
	}
	
	public boolean isSubsetOf(CollectionView<?> coll) {
		for (E obj : this) {
			if (count(obj) > coll.count(obj))
				return false;
		}
		return true;
	}
	
	public boolean isSupersetOf(CollectionView<?> coll) {
		return coll.isSubsetOf(this);
	}
	
	/**
	 * Returns an iterator over the objects in this collection, traversing in an arbitrary order.
	 * @return an iterator over the objects in this collection, traversing in an arbitrary order
	 */
	public abstract Iterator<E> iterator();
	
	
	/**
	 * Tests for equality with the specified object. Two collections are equal if they contain the same number of each value. For example, the collection [1, 3] does not equal the collection [1, 3, 3].
	 * @param other the object to test equality with
	 * @return true if <code>other</code> is a collection with the same number of each value as in this collection
	 */
	public boolean equals(Object other) {
		if (other == this)
			return true;
		else if (!(other instanceof CollectionView))
			return false;
		else {
			CollectionView<?> coll = (CollectionView<?>)other;
			return size() == coll.size() && isSubsetOf(coll) && isSupersetOf(coll);
		}
	}
	
	public int hashCode() {
		int[] hashes = new int[size()];
		Iterator<E> iter = iterator();
		for (int i = 0; i < hashes.length; i++)
			hashes[i] = iter.next().hashCode();
		if (iter.hasNext())
			throw new ConcurrentModificationException();
		Arrays.sort(hashes);
		HashCoder hc = HashCoder.newInstance();
		hc.add(size());
		for (int hash : hashes)
			hc.add(hash);
		return hc.getHashCode();
	}
	
	/**
	 * Returns a string representation of this collection. Currently, it returns a list of its items in arbitrary order. This is subjected to change.
	 * @return a string representation of this collection
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Collection [");
		for (E obj : this)
			sb.append(obj).append(", ");
		if (sb.substring(Math.max(sb.length() - 2, 0), sb.length()).equals(", "))
			sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		return sb.toString();
	}
	
}