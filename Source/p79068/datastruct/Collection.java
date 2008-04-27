package p79068.datastruct;

import java.util.Iterator;


/**
 * An unordered collection of objects, with duplicates allowed. <code>null</code>s must not be stored in a collection.
 * @param <E> the type of object stored in this collection
 */
public interface Collection<E> extends Iterable<E> {
	
	/**
	 * Returns the size of this collection. It is illegal for a collection's length to exceed <code>Integer.MAX_VALUE</code>.
	 * @return the number of objects in this collection
	 */
	public int size();
	
	
	/**
	 * Tests whether this collection contains the specified object.
	 * @param obj the object whose presence to test
	 * @return <code>true</code> if this collection contains <code>obj</code>; <code>false</code> otherwise
	 * @throws NullPointerException if <code>obj</code> is <code>null</code>
	 */
	public boolean contains(Object obj);
	
	
	/**
	 * Tests whether this collection contains all the objects in the specified collection. The number of instances of each object is important. For example, [3, 3, 4, 4] is a superset of [3, 4, 4], but [3, 4, 4] is not a subset of [3, 3, 4].
	 * @param coll the other collection
	 * @return whether this collection is a superset of <code>coll</code>
	 * @throws NullPointerException if <code>coll</code> is <code>null</code>
	 */
	public boolean containsAll(Collection<?> coll);
	
	
	/**
	 * Returns the number of objects in this collection that are equal to the specified object.
	 * @param obj the object whose presence to count
	 * @return the number of objects that are equal to <code>obj</code>
	 * @throws NullPointerException if <code>obj</code> is <code>null</code>
	 */
	public int count(Object obj);
	
	
	/**
	 * Adds the specified object to this collection.
	 * @param obj the object to add
	 * @throws NullPointerException if <code>obj</code> is <code>null</code>
	 */
	public void add(E obj);
	
	
	/**
	 * Adds all objects in the specified collection to this collection.
	 * @param coll the collection whose objects to add
	 * @throws NullPointerException if <code>coll</code> is <code>null</code>
	 */
	public void addAll(Collection<? extends E> coll);
	
	
	/**
	 * Removes an arbitrary object in this collection that is equal to the specified object and returns <code>true</code> if such object exists, otherwise returns <code>false</code>.
	 * @param obj the object to remove
	 * @return <code>true</code> if there is an object in this set that is equal to <code>obj</code> and it was removed; <code>false</code> otherwise
	 * @throws NullPointerException if <code>obj</code> is <code>null</code>
	 */
	public boolean remove(Object obj);
	
	
	/**
	 * Removes all objects in this collection that are equal to the specified object, and returns the number of objects removed.
	 * @param obj the object to remove
	 * @return the number of objects removed
	 * @throws NullPointerException if <code>obj</code> is <code>null</code>
	 */
	public int removeAllOf(Object obj);
	
	
	/**
	 * Removes objects in this collection that are equal to objects in the specified collection, and returns the number of objects removed. Objects that are in the specified collection but not in this collection have no effect.
	 * @param coll the collection of objects to remove
	 * @return the number of objects removed
	 * @throws NullPointerException if <code>coll</code> is <code>null</code>
	 */
	public int removeAll(Collection<?> coll);
	
	
	/**
	 * Removes all objects from this collection.
	 */
	public void clear();
	
	
	/**
	 * Returns an iterator over the objects in this collection, traversing in an arbitrary order.
	 * @return an iterator over the objects in this collection, traversing in an arbitrary order
	 */
	public Iterator<E> iterator();
	
	
	/**
	 * Compares the specified object with this collection for equality. Returns <code>true</code> if the specified object is a collection, and each element of each collection has the same number of instances in each collection. This is equivalent to <code>isSubsetOf(other) && isSupersetOf(other)</code>. Otherwise, this method returns <code>false</code>.
	 * @param other the object to compare to
	 * @return whether the <code>other</code> is a collection with the same contents as this
	 */
	public boolean equals(Object other);
	
	
	/**
	 * Returns the hash code for this collection. It must be the sum of the hash codes of the objects in this collection.
	 * @return the hash code for this collection
	 */
	public int hashCode();
	
	
	/**
	 * Returns a string representation of this collection. A suggested format is <code>Collection [<var>object</var>, <var>object</var>, ...]</code>. This is subjected to change.
	 * @return a string representation of this collection
	 */
	public String toString();
	
}