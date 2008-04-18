package p79068.datastruct;

import java.util.Iterator;


/**
 * A read-only wrapper for a readable collection.
 */
public final class CollectionViewWrapper<E> extends CollectionView<E> {
	
	private CollectionView<E> collection;
	
	
	/**
	 * Creates a read-only wrapper over the specified collection.
	 */
	public CollectionViewWrapper(CollectionView<E> coll) {
		collection = coll;
	}
	
	
	public int size() {
		return collection.size();
	}
	
	public boolean contains(Object o) {
		return collection.contains(o);
	}
	
	public int count(Object o) {
		return collection.count(o);
	}
	
	public CollectionView<E> getAll(Object o) {
		return collection.getAll(o);
	}
	
	public Iterator<E> iterator() {
		return collection.iterator();
	}
}