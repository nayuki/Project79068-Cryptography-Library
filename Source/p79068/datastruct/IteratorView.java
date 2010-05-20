package p79068.datastruct;

import java.util.Iterator;


public class IteratorView<E> implements Iterator<E> {
	
	private Iterator<E> iterator;
	
	
	
	public IteratorView(Iterator<E> iter) {
		this.iterator = iter;
	}
	
	
	
	public boolean hasNext() {
		return iterator.hasNext();
	}
	
	
	public E next() {
		return iterator.next();
	}
	
	
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}