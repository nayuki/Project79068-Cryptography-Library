package p79068.datastruct;

import java.util.Iterator;


/**
 * An unordered set of objects, with no duplicates. <code>null</code>s must not be stored in a set.
 * @param <E> the type of object stored in this set
 */
public interface Set<E> {
	
	/**
	 * Returns the size of this set. It is illegal for a set's length to exceed <code>Integer.MAX_VALUE</code>.
	 * @return the number of objects in this set
	 */
	public int size();
	
	
	/**
	 * Tests whether this set contains the specified object.
	 * @param obj the object whose presence to test
	 * @return <code>true</code> if this set contains <code>obj</code>; <code>false</code> otherwise
	 * @throws NullPointerException if <code>obj</code> is <code>null</code>
	 */
	public boolean contains(Object obj);
	
	
	/**
	 * Tests whether this set contains all the objects in the specified set.
	 * @param set the other set
	 * @return whether this collection is a subset of <code>set</code>
	 * @throws NullPointerException if <code>set</code> is <code>null</code>
	 */
	public boolean containsAll(Set<?> set);
	
	
	/**
	 * Adds the specified object to this set.
	 * @param obj the object to add
	 * @return <code>true</code> if the object was added; <code>false</code> if the object was not added because it already exists in this set
	 * @throws NullPointerException if <code>obj</code> is <code>null</code>
	 */
	public boolean add(E obj);
	
	
	/**
	 * Adds all objects in the specified set to this set.
	 * @param set the collection whose objects to add
	 * @throws NullPointerException if <code>set</code> is <code>null</code>
	 */
	public void addAll(Set<? extends E> set);
	
	
	/**
	 * Adds all objects in the specified collection to this set.
	 * @param coll the collection whose objects to add
	 * @throws NullPointerException if <code>coll</code> is <code>null</code>
	 */
	public void addAll(Collection<? extends E> coll);
	
	
	/**
	 * Removes the object in this set that is equal to the specified object and returns <code>true</code> if such object exists, otherwise returns <code>false</code>.
	 * @param obj the object to remove
	 * @return <code>true</code> if there is an object in this set that is equal to <code>obj</code> and it was removed; <code>false</code> otherwise
	 * @throws NullPointerException if <code>obj</code> is <code>null</code>
	 */
	public boolean remove(Object obj);
	
	
	/**
	 * Removes objects in this set that are equal to objects in the specified set, and returns the number of objects removed. Objects that are in the specified set but not in this set have no effect on the removal process.
	 * @param set the set of objects to remove
	 * @return the number of objects removed
	 * @throws NullPointerException if <code>set</code> is <code>null</code>
	 */
	public int removeAll(Set<?> set);
	
	
	/**
	 * Removes all objects from this set.
	 */
	public void clear();
	
	
	/**
	 * Returns an iterator over the objects in this set, traversing in an arbitrary order.
	 * @return an iterator over the objects in this set, traversing in an arbitrary order
	 */
	public Iterator<E> iterator();
	
	
	/**
	 * Compares the specified object with this set for equality. Returns <code>true</code> if the specified object is a set and <code>this.containsAll(other) && other.containsAll(this)</code>. Otherwise, this method returns <code>false</code>.
	 * @param other the object to compare to
	 * @return whether the <code>other</code> is a set with the same contents as this
	 */
	public boolean equals(Object other);
	
	
	/**
	 * Returns the hash code for this set. It must be the sum of the hash codes of the objects in this set.
	 * @return the hash code for this set
	 */
	public int hashCode();
	
	
	/**
	 * Returns a string representation of this set. A suggested format is <code>Set [<var>object</var>, <var>object</var>, ...]</code>. This is subjected to change.
	 * @return a string representation of this set
	 */
	public String toString();
	
}