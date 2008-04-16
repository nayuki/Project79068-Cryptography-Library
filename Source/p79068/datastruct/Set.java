package p79068.datastruct;


/**
 * An abstract set (a collection with no duplicates).
 */
public abstract class Set<E> extends SetView<E> {
	
	/**
	 * Creates a set.
	 */
	protected Set() { }
	
	
	/**
	 * Adds the specified object to this set if it does not already exist.
	 */
	public void add(E obj) {
		if (!contains(obj))
			set(obj);
	}
	
	/**
	 * Adds each object from the specified collection to this set if it does not already exist. Also known as set union. If there are multiple equal objects in the collection, one arbitrary one is attempted to be added to this set.
	 */
	public void add(CollectionView<? extends E> coll) {
		for (E obj : coll)
			add(obj);
	}
	
	/**
	 * Adds the specified object to this set or replaces the existing one.
	 */
	public abstract void set(E obj);
	
	/**
	 * Adds each object from the specified collection to this set, replacing existing ones. If there are multiple equal objects in the collection, one arbitrary one of these stays in this set.
	 */
	public void set(CollectionView<? extends E> coll) {
		for (E obj : coll)
			set(obj);
	}
	
	/**
	 * Removes and returns the specified object from this set. */
	public abstract E remove(Object obj);
	
	/**
	 * Removes each object in the specified collection from this set. Also known as set subtraction. If there are multiple equal objects in the collection, it has the same effect as if there were no duplicates.
	 */
	public void remove(CollectionView<?> coll) {
		for (Object obj : coll)
			remove(obj);
	}
	
	/**
	 * Intersects this set with the specified collection.
	 */
	public abstract void intersect(CollectionView<?> coll);
	
}