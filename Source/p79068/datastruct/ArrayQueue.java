package p79068.datastruct;

import p79068.lang.NullChecker;


/**
An array-based queue. Enqueues and dequeues are in amortized <var>O</var>(1) time.
<p>Mutability: <em>Mutable</em><br>
 Thread safety: <em>Unsafe</em></p>
*/
public final class ArrayQueue<E> extends Queue<E> {
	
	private E[] data;
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
	@SuppressWarnings("unchecked")
	public ArrayQueue(int initCapacity) {
		if (initCapacity < 2)
			throw new IllegalArgumentException("Initial capacity less than 2");
		data = (E[])new Object[initCapacity];
		head = 0;
		tail = 0;
	}
	
	
	public void enqueue(E obj) {
		NullChecker.check(obj);
		if (length() + 1 == data.length)
			resize(data.length * 2);
		data[tail] = obj;
		tail++;
		if (tail == data.length)
			tail = 0;
	}
	
	public E dequeue() {
		if (isEmpty())
			throw new IllegalStateException("Empty queue");
		E result = data[head];
		data[head] = null;
		head++;
		if (head == data.length)
			head = 0;
		if (length() <= data.length / 4 && data.length / 2 >= 2)
			resize(data.length / 2);
		return result;
	}
	
	public E peek() {
		if (isEmpty())
			throw new IllegalStateException("Empty queue");
		return data[head];
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
			return tail + data.length - head;
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayQueue<E> clone() {
		ArrayQueue<E> result = (ArrayQueue<E>)super.clone();
		result.data = data.clone();
		return result;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Queue [");
		for (int i = head; i != tail;) {
			if (i != head)
				sb.append(", ");
			sb.append(data[i]);
			i++;
			if (i == data.length)
				i = 0;
		}
		sb.append("]");
		return sb.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	private void resize(int newCapacity) {
		if (newCapacity < 2)
			throw new AssertionError();
		E[] newdata = (E[])new Object[data.length * 2];
		int j = 0;
		for (int i = head; i != tail; j++) {
			newdata[j] = data[i];
			i++;
			if (i == data.length)
				i = 0;
		}
		data = newdata;
		head = 0;
		tail = j;
	}
}