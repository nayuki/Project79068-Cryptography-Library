package p79068.datastruct;

import p79068.lang.NullChecker;


/**
 * A linked list-based stack. All operations are in O(1) time.
 * <p>Mutability: <em>Mutable</em><br>
 *  Thread safety: <em>Unsafe</em></p>
 */
public final class LinkedListStack<E> implements Stack<E> {
	
	private LinkedListNode<E> top;
	private int height;
	
	
	
	/**
	 * Creates a linked list stack.
	 */
	public LinkedListStack() {
		top = null;
		height = 0;
	}
	
	
	
	public void push(E obj) {
		NullChecker.check(obj);
		if (height == Integer.MAX_VALUE)
			throw new IllegalStateException("Maximum height reached");
		top = new LinkedListNode<E>(obj, top);
		height++;
	}
	
	
	public E pop() {
		if (isEmpty())
			throw new IllegalStateException("Stack underflow");
		E result = top.object;
		top = top.next;
		height--;
		return result;
	}
	
	
	public E peek() {
		if (isEmpty())
			throw new IllegalStateException("Stack underflow");
		return top.object;
	}
	
	
	public int height() {
		return height;
	}
	
	
	public boolean isEmpty() {
		return top == null;
	}
	
	
	@SuppressWarnings("unchecked")
	public LinkedListStack<E> clone() {
		LinkedListStack<E> result;
		try {
			result = (LinkedListStack<E>)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
		if (result.top != null) {
			result.top = result.top.clone();
			for (LinkedListNode<E> node = result.top; node.next != null; node = node.next)
				node.next = node.next.clone();
		}
		return result;
	}
	
	
	public String toString() {
		List<E> contents = new ArrayList<E>();
		for (LinkedListNode<E> node = top; node != null; node = node.next)
			contents.append(node.object);
		StringBuilder sb = new StringBuilder();
		sb.append("Stack [");
		for (int i = contents.length() - 1; i >= 0; i--) {
			if (i != contents.length() - 1)
				sb.append(", ");
			sb.append(contents.getAt(i));
		}
		sb.append("]");
		return sb.toString();
	}
	
}