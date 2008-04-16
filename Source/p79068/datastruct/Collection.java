package p79068.datastruct;


/**
 * An abstract collection.
 */
public abstract class Collection<E> extends CollectionView<E> {
	
	/**
	 * Creates a collection.
	 */
	protected Collection() { }
	
	
	/**
	 * Adds the specified object to this collection.
	 * @param obj the object to add
	 */
	public abstract void add(E obj);
	
	/**
	 * Adds all objects in the specified collection to this collection.
	 * @param coll the collection whose objects to add
	 */
	public void add(CollectionView<? extends E> coll) {
		for (E obj : coll)
			add(obj);
	}
	
	/**
	 * Removes and returns an object equal to the specified object. If no object in the collection equals the specified object, then <samp>null</samp> is returned.
	 * @param o the object to compare equality with
	 * @return an arbitrary object in this collection equal to <code>o</code>, or <samp>null</samp> if there is none
	 */
	public abstract E remove(Object o);
	
	/**
	 * Removes objects in this collection equal to objects in the specified collection and returns the number of objects removed. Objects that are in the specified collection but not in this collection have no effect.
	 * @param coll the collection
	 * @return the number of objects removed
	 */
	public int removeAll(CollectionView<?> coll) {
		int removed = 0;
		for (Object obj : coll)
			removed += removeAll(obj);
		return removed;
	}
	
	/**
	 * Removes all objects equal to the specified object from this collection and returns the number of objects removed.
	 * @param obj the object to compare equality with
	 * @return the number of objects removed
	 */
	public int removeAll(Object o) {
		int removed = 0;
		while (remove(o) != null)
			removed++;
		return removed;
	}
	
	/**
	 * Removes all objects from this collection.
	 */
	public abstract void clear();
	
}