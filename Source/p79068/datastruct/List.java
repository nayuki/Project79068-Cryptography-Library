package p79068.datastruct;

import p79068.lang.NullChecker;


/**
 * An abstract list (a sequence of objects).
 */
public abstract class List<E> extends Array<E> {
	
	/**
	 * Creates a list.
	 */
	protected List() { }
	
	
	/**
	 * Adds the specified object to the end of this list. Equivalent to <code>insert(length(), obj)</code>.
	 */
	public void append(E obj) {
		NullChecker.check(obj);
		insert(length(), obj);
	}
	
	/**
	 * Adds the specified list to the end of this list.
	 */
	public void append(ListView<? extends E> list) {
		NullChecker.check(list);
		for (E obj : list)
			append(obj);
	}
	
	/**
	 * Inserts the specified object at the specified index.
	 */
	public abstract void insert(int index, E obj);
	
	/**
	 * Inserts the specified list at the specified index.
	 */
	public void insert(int index, ListView<E> list) {
		for (E obj : list) {
			insert(index, obj);
			index++;
		}
	}
	

	/**
	 * Removes and returns the object at the specified index.
	 */
	public abstract E removeAt(int index);
	
	/**
	 * Removes all objects equal to the specified object from this list.
	 */
	public int removeAll(Object o) {
		NullChecker.check(o);
		int removed = 0;
		int index = -1;
		while (true) {
			index = findNext(o, index);
			if (index == -1)
				break;
			else {
				removeAt(index);
				removed++;
			}
		}
		return removed;
	}
	
	/**
	 * Sets all elements to the specified value. Does not change the length of the list.
	 */
	public void clearTo(E obj) {
		NullChecker.check(obj);
		super.clearTo(obj);
	}
	
	/**
	 * Removes all objects from this list.
	 */
	public void clear() {
		while (length() > 0)
			removeAt(0);
	}
	
}