package p79068.datastruct;

import p79068.lang.NullChecker;


/**
 * A linked list-based queue. All operations are in <var>O</var>(1) time.
 * <p>Mutability: <em>Mutable</em><br>
 *  Thread safety: <em>Unsafe</em></p>
 */
public final class LinkedListQueue<E> implements Queue<E> {
	
	/**
	 * The linked list node that holds the head object of this queue.
	 * This node points to the node that holds the next object of this queue, and so on.
	 * This field is <code>null</code> if and only if this queue is empty.
	 * This field is equal to <code>tail</code> (and not <code>null</code>) if and only if this queue has 1 object.
	 */
	private LinkedListNode<E> head;
	
	/**
	 * The linked list node that holds the tail object of this queue.
	 * This node points to nothing.
	 * This field is <code>null</code> if and only if this queue is empty.
	 * This field is equal to <code>head</code> (and not <code>null</code>) if and only if this queue has 1 object.
	 */
	private LinkedListNode<E> tail;
	
	
	/**
	 * The length of this queue.
	 */
	private int length;
	
	
	
	/**
	 * Creates a linked list queue.
	 */
	public LinkedListQueue() {
		head = null;
		tail = null;
		length = 0;
	}
	
	
	
	/**
	 * Adds the specified object to the tail of this queue.
	 * @param obj the object to enqueue
	 * @throws IllegalStateException if this queue's length is <code>Integer.MAX_VALUE</code>
	 */
	public void enqueue(E obj) {
		NullChecker.check(obj);
		if (length == Integer.MAX_VALUE)
			throw new IllegalStateException("Maximum length reached");
		if (head == null) {
			head = new LinkedListNode<E>(obj);
			tail = head;
		} else {
			tail.next = new LinkedListNode<E>(obj);
			tail = tail.next;
		}
		length++;
	}
	
	
	/**
	 * Removes and returns the object at the head of this queue.
	 * @return the object at the head of this queue
	 * @throws IllegalStateException if this queue is empty
	 */
	public E dequeue() {
		if (isEmpty())
			throw new IllegalStateException("Empty queue");
		E result = head.object;
		head = head.next;
		if (head == null)
			tail = null;
		length--;
		return result;
	}
	
	
	/**
	 * Returns the object at head of this queue without removing it.
	 * @return the object at the head of this queue
	 * @throws IllegalStateException if this queue is empty
	 */
	public E peek() {
		if (isEmpty())
			throw new IllegalStateException("Empty queue");
		return head.object;
	}
	
	
	/**
	 * Returns the length of this queue.
	 * @return the number of objects in this queue
	 */
	public int length() {
		return length;
	}
	
	
	/**
	 * Tests whether this queue is empty.
	 * @return <code>true</true> if this queue has no objects; <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return head == null;
	}
	
	
	/**
	 * Creates and returns a copy of this queue. The queue's data structures are cloned, but the objects stored are not cloned.
	 * @return a copy of this queue
	 */
	@Override
	@SuppressWarnings("unchecked")
	public LinkedListQueue<E> clone() {
		LinkedListQueue<E> result;
		try {
			result = (LinkedListQueue<E>)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
		if (result.head != null) {
			result.head = result.head.clone();
			LinkedListNode<E> node = result.head;
			for (; node.next != null; node = node.next)
				node.next = node.next.clone();
			result.tail = node;
		}
		return result;
	}
	
	
	/**
	 * Returns a string representation of this queue. The format is <code>Queue [<var>head</var>, ..., <var>tail</var>]</code>. This is subjected to change.
	 * @return a string representation of this queue
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Queue [");
		for (LinkedListNode<E> node = head; node != null; node = node.next) {
			if (node != head)
				sb.append(", ");
			sb.append(node.object);
		}
		sb.append("]");
		return sb.toString();
	}
	
}