package p79068.datastruct;

import java.util.Iterator;

public class CollectionView<E> implements Collection<E> {
	
	private Collection<E> collection;
	
	
	public CollectionView(Collection<E> coll) {
		collection = coll;
	}
	
	
	
	public int size() {
		return collection.size();
	}
	
	
	public boolean contains(Object obj) {
		return collection.contains(obj);
	}
	
	
	public boolean containsAll(Collection<?> coll) {
		return collection.containsAll(coll);
	}
	
	
	public int count(Object obj) {
		return collection.count(obj);
	}
	
	
	public Iterator<E> iterator() {
		return new IteratorView<E>(collection.iterator());
	}
	
	
	
	public void add(E obj) {
		throw new UnsupportedOperationException();
	}
	
	
	public void addAll(Collection<? extends E> coll) {
		throw new UnsupportedOperationException();
	}
	
	
	public void clear() {
		throw new UnsupportedOperationException();
	}
	
	
	public boolean remove(Object obj) {
		throw new UnsupportedOperationException();
	}
	
	
	public int removeAll(Collection<?> coll) {
		throw new UnsupportedOperationException();
	}
	
	
	public int removeAllOf(Object obj) {
		throw new UnsupportedOperationException();
	}
	
}