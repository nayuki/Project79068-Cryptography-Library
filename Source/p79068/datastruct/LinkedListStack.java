package p79068.datastruct;

import p79068.lang.NullChecker;


/**
 * A linked list-based stack. All operations are in O(1) time.
 * <p>Mutability: <em>Mutable</em><br>
 *  Thread safety: <em>Unsafe</em></p>
 */
public final class LinkedListStack<E> implements Stack<E> {
	
	/**
	 * The linked list node that holds the top object of this stack.
	 * This node points to the node that holds the next topmost object of this stack, and so on.
	 * This field is <code>null</code> if and only if this stack is empty.
	 */
	private LinkedListNode<E> top;
	
	/**
	 * The height of this stack.
	 */
	private int height;
	
	
	
	/**
	 * Creates a linked list stack.
	 */
	public LinkedListStack() {
		top = null;
		height = 0;
	}
	
	
	
	/**
	 * Adds the specified object to the top of this stack.
	 * @param obj the object to add
	 * @throws IllegalStateException if this stack's height is <code>Integer.MAX_VALUE</code>
	 */
	public void push(E obj) {
		NullChecker.check(obj);
		if (height == Integer.MAX_VALUE)
			throw new IllegalStateException("Maximum height reached");
		top = new LinkedListNode<E>(obj, top);
		height++;
	}
	
	
	/**
	 * Removes and returns the object at the top of this stack.
	 * @return the object at the top of this stack
	 * @throws IllegalStateException if this stack is empty
	 */
	public E pop() {
		if (isEmpty())
			throw new IllegalStateException("Stack underflow");
		E result = top.object;
		top = top.next;
		height--;
		return result;
	}
	
	
	/**
	 * Returns the object at the top of this stack without removing it.
	 * @return the object at the top of this stack
	 * @throws IllegalStateException if this stack is empty
	 */
	public E peek() {
		if (isEmpty())
			throw new IllegalStateException("Stack underflow");
		return top.object;
	}
	
	
	/**
	 * Returns the height of this stack.
	 * @return the number of objects in this stack
	 */
	public int height() {
		return height;
	}
	
	
	/**
	 * Tests whether this stack is empty.
	 * @return <code>true</true> if this stack has no objects; <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return top == null;
	}
	
	
	/**
	 * Creates and returns a copy of this stack. The stack's data structures are cloned, but the objects stored are not cloned.
	 * @return a copy of this stack
	 */
	@Override
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
	
	
	/**
	 * Returns a string representation of this stack. The format is <code>Queue [<var>bottom</var>, ..., <var>top</var>]</code>. This is subjected to change.
	 * @return a string representation of this stack
	 */
	@Override
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