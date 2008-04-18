package p79068.datastruct;

import p79068.lang.NullChecker;


/**
An array-based queue. Enqueues and dequeues are in amortized <var>O</var>(1) time.
<p>Mutability: <em>Mutable</em><br>
 Thread safety: <em>Unsafe</em></p>
*/
public final class ArrayQueue<E> implements Queue<E> {
	
	private Object[] objects;
	private int head;
	private int tail;
	
	
	
	/**
	 * Creates an array-based queue.
	 */
	public ArrayQueue() {
		this(16);
	}
	
	
	/**
	 * Creates an array-based queue with the specified initial capacity. The capacity must be at least 2.
	 */
	public ArrayQueue(int initCapacity) {
		if (initCapacity < 2)
			throw new IllegalArgumentException("Initial capacity less than 2");
		objects = new Object[initCapacity];
		head = 0;
		tail = 0;
	}
	
	
	
	public void enqueue(E obj) {
		NullChecker.check(obj);
		if (length() + 1 == objects.length)
			resize(objects.length * 2);
		objects[tail] = obj;
		tail++;
		if (tail == objects.length)
			tail = 0;
	}
	
	
	@SuppressWarnings("unchecked")
	public E dequeue() {
		if (isEmpty())
			throw new IllegalStateException("Empty queue");
		E result = (E)objects[head];
		objects[head] = null;
		head++;
		if (head == objects.length)
			head = 0;
		if (length() <= objects.length / 4 && objects.length / 2 >= 2)
			resize(objects.length / 2);
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public E peek() {
		if (isEmpty())
			throw new IllegalStateException("Empty queue");
		return (E)objects[head];
	}
	
	
	/**
	 * Tests whether this queue is empty.
	 */
	public boolean isEmpty() {
		return head == tail;
	}
	
	
	/**
	 * Returns the length of this queue.
	 */
	public int length() {
		if (head <= tail)
			return tail - head;
		else
			return tail + objects.length - head;
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayQueue<E> clone() {
		ArrayQueue<E> result;
		try {
			result = (ArrayQueue<E>)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
		result.objects = objects.clone();
		return result;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Queue [");
		for (int i = head; i != tail;) {
			if (i != head)
				sb.append(", ");
			sb.append(objects[i]);
			i++;
			if (i == objects.length)
				i = 0;
		}
		sb.append("]");
		return sb.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	private void resize(int newCapacity) {
		if (newCapacity < 2)
			throw new AssertionError();
		Object[] newdata = new Object[objects.length * 2];
		int j = 0;
		for (int i = head; i != tail; j++) {
			newdata[j] = objects[i];
			i++;
			if (i == objects.length)
				i = 0;
		}
		objects = newdata;
		head = 0;
		tail = j;
	}
	
}