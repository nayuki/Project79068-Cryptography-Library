package p79068.datastruct;

import java.util.Iterator;
import java.util.NoSuchElementException;
import p79068.lang.*;
import p79068.util.HashCoder;
import p79068.util.Random;


/** A linked list. */
public final class LinkedList<E> extends List<E> {
	
	private LinkedListNode<E> head;
	private int length;
	
	
	/** Creates a new linked list. */
	public LinkedList() {
		head = new LinkedListNode<E>(null); // Dummy head node
		length = 0;
	}
	
	/** Creates a new linked list with the contents of the specified list. */
	public LinkedList(ListView<E> list) {
		this();
		append(list);
	}
	
	
	/** Returns the length of this list, in O(1) time. */
	public int length() {
		return length;
	}
	
	public boolean contains(Object o) {
		NullChecker.check(o);
		for (LinkedListNode<E> node = getNode(0); node != null; node = node.next) {
			if (o.equals(node.value))
				return true;
		}
		return false;
	}
	
	public int count(Object o) {
		NullChecker.check(o);
		int count = 0;
		for (LinkedListNode<E> node = getNode(0); node != null; node = node.next) {
			if (o.equals(node.value))
				count++;
		}
		return count;
	}
	
	
	public void append(E obj) {
		NullChecker.check(obj);
		if (length == Integer.MAX_VALUE)
			throw new IllegalStateException("List has reached maximum length (2147483647)");
		getNode(length - 1).next = new LinkedListNode<E>(obj);
		length++;
	}
	
	public void append(ListView<? extends E> list) {
		NullChecker.check(list);
		LinkedListNode<E> node = getNode(length - 1);
		for (E obj : list) {
			if (length == Integer.MAX_VALUE)
				throw new IllegalStateException("List has reached maximum length (2147483647)");
			node = node.next = new LinkedListNode<E>(obj);
			length++;
		}
	}
	
	public void insert(int index, E obj) {
		NullChecker.check(obj);
		if (index < 0 || index > length)
			throw new IndexOutOfBoundsException(String.format("Bounds = [0,%d), insertion index = %d", length, index));
		if (length == Integer.MAX_VALUE)
			throw new IllegalStateException("List has reached maximum length (2147483647)");
		LinkedListNode<E> node = getNode(index - 1);
		node.next = new LinkedListNode<E>(obj, node.next);
		length++;
	}
	
	public void insert(int index, ListView<E> list) {
		NullChecker.check(list);
		if (index < 0 || index > length)
			throw new IndexOutOfBoundsException(String.format("Bounds = [0,%d), insertion index = %d", length, index));
		LinkedListNode<E> node = getNode(index - 1);
		for (E obj : list) {
			if (length == Integer.MAX_VALUE)
				throw new IllegalStateException("List has reached maximum length (2147483647)");
			node = node.next = new LinkedListNode<E>(obj, node.next);
			length++;
		}
	}
	
	public E getAt(int index) {
		BoundsChecker.check(length, index);
		return getNode(index).value;
	}
	
	public void setAt(int index, E obj) {
		BoundsChecker.check(length, index);
		NullChecker.check(obj);
		getNode(index).value = obj;
	}
	
	public E removeAt(int index) {
		BoundsChecker.check(length, index);
		LinkedListNode<E> node = getNode(index - 1);
		E result = node.next.value;
		node.next = node.next.next;
		length--;
		return result;
	}
	
	public int removeAll(Object o) {
		NullChecker.check(o);
		int removed = 0;
		LinkedListNode<E> node = getNode(-1);
		while (node.next != null) {
			if (o.equals(node.next.value)) {
				node.next = node.next.next;
				length--;
				removed++;
			} else
				node = node.next;
		}
		return removed;
	}
	
	public void shuffle() {
		shuffle(Random.DEFAULT);
	}
	
	public void shuffle(Random rand) {
		for (int i = 0; i < length(); i++) {
			int j = i + rand.randomInt(length() - i);
			E temp = getAt(i);
			setAt(i, getAt(j));
			setAt(j, temp);
		}
	}
	
	public void clear() {
		getNode(-1).next = null;
	}
	
	public void clearTo(E obj) {
		for (LinkedListNode<E> node = getNode(0); node != null; node = node.next)
			node.value = obj;
	}
	
	
	public int findNext(Object o) {
		return findNext(o, 0);
	}
	
	public int findNext(Object o, int start) {
		NullChecker.check(o);
		if (start >= length)
			return -1;
		int index = Math.max(start, 0);
		LinkedListNode<E> node = getNode(index);
		for (; node != null; node = node.next, index++) {
			if (o.equals(node.value))
				return index;
		}
		return -1;
	}
	
	public int findPrevious(Object o) {
		return findPrevious(o, length() - 1);
	}
	
	public int findPrevious(Object o, int start) {
		NullChecker.check(o);
		int result = -1;
		LinkedListNode<E> node = getNode(0);
		int index = 0;
		for (; node != null && index <= start; node = node.next, index++) {
			if (o.equals(node.value))
				result = index;
		}
		return result;
	}
	
	public Iterator<E> iterator() {
		return new Itr();
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (!(obj instanceof ListView))
			return false;
		ListView<E> list = (ListView<E>)obj;
		if (length != list.length())
			return false;
		LinkedListNode<E> node = getNode(0);
		for (E listobj : list) {
			if (!node.value.equals(listobj))
				return false;
			node = node.next;
		}
		return true;
	}
	
	public int hashCode() {
		HashCoder h = HashCoder.newInstance();
		h.add(length);
		for (LinkedListNode<E> node = getNode(0); node != null; node = node.next)
			h.add(node.value.hashCode());
		return h.getHashCode();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LinkedList [");
		boolean initial = true;
		for (LinkedListNode<E> node = getNode(0); node != null; node = node.next) {
			if (!initial)
				sb.append(", ");
			else
				initial = false;
			sb.append(node.value);
		}
		sb.append("]");
		return sb.toString();
	}
	
	
	/* Returns the node at the specified index. Returns null if there is no such node. */
	private LinkedListNode<E> getNode(int index) {
		if (index < -1)
			return null;
		LinkedListNode<E> node = head;
		for (int i = -1; i < index && node != null; i++)
			node = node.next;
		return node;
	}
	
	

	private class Itr implements Iterator<E> {
		
		LinkedListNode<E> currentNode;
		
		
		Itr() {
			currentNode = getNode(0);
		}
		
		
		public boolean hasNext() {
			return currentNode != null;
		}
		
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			E result = currentNode.value;
			currentNode = currentNode.next;
			return result;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}