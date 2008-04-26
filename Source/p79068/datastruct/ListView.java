package p79068.datastruct;

import java.util.Iterator;


public class ListView<E> implements List<E> {
	
	private List<E> list;
	
	
	
	public ListView(List<E> list) {
		this.list = list;
	}
	
	
	
	public int length() {
		return list.length();
	}
	
	
	public E getAt(int index) {
		return list.getAt(index);
	}
	
	
	public List<E> sublist(int offset, int length) {
		return new ListView<E>(list.sublist(offset, length));
	}
	
	
	public Iterator<E> iterator() {
		return new IteratorView<E>(list.iterator());
	}
	
	
	public Collection<E> asCollection() {
		return new ListCollectionAdapter<E>(this);
	}
	
	
	
	public void append(Object obj) {
		throw new UnsupportedOperationException();
	}
	
	
	public void appendList(List<? extends E> list) {
		throw new UnsupportedOperationException();
	}
	
	
	public void clear() {
		throw new UnsupportedOperationException();
	}
	
	
	public void insert(int index, Object obj) {
		throw new UnsupportedOperationException();
	}
	
	
	public void insertList(int index, List<? extends E> list) {
		throw new UnsupportedOperationException();
	}
	
	
	public E removeAt(int index) {
		throw new UnsupportedOperationException();
	}
	
	
	public void removeRange(int offset, int length) {
		throw new UnsupportedOperationException();
	}
	
	
	public void setAt(int index, Object obj) {
		throw new UnsupportedOperationException();
	}
	
}