package p79068.datastruct;

import p79068.lang.NullChecker;


public class BinaryHeap<E extends Comparable<E>> implements Cloneable {
	
	private Comparable<E>[] values;
	private int size;
	
	
	@SuppressWarnings("unchecked")
	public BinaryHeap() {
		values = new Comparable[1];
	}
	
	
	public void enqueue(E obj) {
		NullChecker.check(obj);
		if (size == values.length)
			resize(values.length * 2);
		values[size] = obj;
		size++;
		int node = size - 1;
		while (true) {
			int parent = (node - 1) / 2;
			if (compare(node, parent) >= 0)
				break;
			swap(node, parent);
			node = parent;
		}
	}
	
	@SuppressWarnings("unchecked")
	public E dequeue() {
		if (size == 0)
			return null;
		E result = (E)values[0];
		values[0] = values[size - 1];
		values[size - 1] = null;
		size--;
		int node = 0;
		while (true) {
			int child = node * 2 + 1;
			if (child >= size)
				break;
			if (child + 1 < size && compare(child + 1, child) < 0)
				child++;
			if (compare(child, node) >= 0)
				break;
			swap(child, node);
			node = child;
		}
		if (size * 4 <= values.length && values.length >= 2)
			resize(values.length / 2);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public E peek() {
		if (size == 0)
			return null;
		return (E)values[0];
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	@SuppressWarnings("unchecked")
	public BinaryHeap<E> clone() {
		try {
			BinaryHeap<E> result = (BinaryHeap<E>)super.clone();
			result.values = values.clone();
			return result;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
	}
	
	public String toString() {
		BinaryHeap<E> temp = clone();
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		while (!temp.isEmpty())
			sb.append(temp.dequeue()).append(", ");
		if (sb.substring(Math.max(sb.length() - 2, 0), sb.length()).equals(", "))
			sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		return sb.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	private int compare(int i, int j) {
		return ((E)values[i]).compareTo((E)values[j]);
	}
	
	@SuppressWarnings("unchecked")
	private void swap(int i, int j) {
		E temp = (E)values[i];
		values[i] = values[j];
		values[j] = temp;
	}
	
	
	@SuppressWarnings("unchecked")
	private void resize(int newCap) {
		if (newCap < size)
			throw new IllegalArgumentException();
		Comparable<E>[] newvalues = new Comparable[newCap];
		System.arraycopy(values, 0, newvalues, 0, size);
		values = newvalues;
	}
}