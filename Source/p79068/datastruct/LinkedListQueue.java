package p79068.datastruct;

import p79068.lang.NullChecker;


/**
 * A linked list-based queue. All operations are in <var>O</var>(1) time.
 * <p>Mutability: <em>Mutable</em><br>
 *  Thread safety: <em>Unsafe</em></p>
 */
public final class LinkedListQueue<E> extends Queue<E> {
	
	private LinkedListNode<E> head;
	private LinkedListNode<E> tail;
	
	
	
	/**
	 * Creates a linked list queue.
	 */
	public LinkedListQueue() {
		head = null;
		tail = null;
	}
	
	
	
	public void enqueue(E obj) {
		NullChecker.check(obj);
		if (head == null) {
			head = new LinkedListNode<E>(obj);
			tail = head;
		} else {
			tail.next = new LinkedListNode<E>(obj);
			tail = tail.next;
		}
	}
	
	
	public E dequeue() {
		if (isEmpty())
			throw new IllegalStateException("Empty queue");
		E result = head.value;
		head = head.next;
		if (head == null)
			tail = null;
		return result;
	}
	
	
	public E peek() {
		if (isEmpty())
			throw new IllegalStateException("Empty queue");
		return head.value;
	}
	
	
	public boolean isEmpty() {
		return head == null;
	}
	
	
	@SuppressWarnings("unchecked")
	public LinkedListQueue<E> clone() {
		LinkedListQueue<E> result = (LinkedListQueue<E>)super.clone();
		if (result.head != null) {
			result.head = result.head.clone();
			LinkedListNode<E> node = result.head;
			for (; node.next != null; node = node.next)
				node.next = node.next.clone();
			result.tail = node;
		}
		return result;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Queue [");
		for (LinkedListNode<E> node = head; node != null; node = node.next) {
			if (node != head)
				sb.append(", ");
			sb.append(node.value);
		}
		sb.append("]");
		return sb.toString();
	}
	
}