package p79068.datastruct;

import java.util.Iterator;
import p79068.lang.BoundsChecker;
import p79068.lang.NullChecker;


/**
 * An circular array-based queue. Enqueues and dequeues are in amortized <var>O</var>(1) time.
 * <p>Mutability: <em>Mutable</em><br>
 *  Thread safety: <em>Unsafe</em></p>
 */
public final class ArrayQueue<E> implements Queue<E> {
	
	/**
	 * The array that holds the objects of this queue.
	 * This array is used in a circular fashion; that is:
	 *  - If <code>head &lt;= tail</code>, then the objects in this queue are stored in the range [<code>head</code>, <code>tail</code>).
	 *  - Otherwise, the objects in this queue are stored in the range [<code>head</code>, <code>objects.length</code>) concatenated with the range [<code>0</code>, <code>tail</code>).
	 * Note that <code>head == tail</code> represents the queue being empty. There is no way to represent the queue being full, so the maximum usage of the queue is <code>objects.length - 1</code>.
	 */
	private Object[] objects;
	
	/**
	 * The index where the next object will be dequeued.
	 */
	private int head;
	
	/**
	 * The index where the next object will be enqueued.
	 */
	private int tail;
	
	
	
	/**
	 * Creates a circular array-based queue. An arbitrary default initial capacity is used.
	 */
	public ArrayQueue() {
		this(16);
	}
	
	
	/**
	 * Creates an circular array-based queue with the specified initial capacity.
	 * @param initCapacity the initial capacity, which must be at least 2
	 * @throws IllegalArgumentException if <code>initCapacity &lt; 2</code>
	 */
	public ArrayQueue(int initCapacity) {
		if (initCapacity < 2)
			throw new IllegalArgumentException("Initial capacity less than 2");
		objects = new Object[initCapacity];
		head = 0;
		tail = 0;
	}
	
	
	
	/**
	 * Adds the specified object to the tail of this queue.
	 * @param obj the object to enqueue
	 */
	public void enqueue(E obj) {
		NullChecker.check(obj);
		if (length() + 1 == objects.length)
			resize(objects.length * 2);
		objects[tail] = obj;
		tail++;
		if (tail == objects.length)  // Wrap around
			tail = 0;
	}
	
	
	/**
	 * Removes and returns the object at the head of this queue.
	 * @return the object at the head of this queue
	 * @throws IllegalStateException if this queue is empty
	 */
	@SuppressWarnings("unchecked")
	public E dequeue() {
		if (isEmpty())
			throw new IllegalStateException("Empty queue");
		E result = (E)objects[head];
		objects[head] = null;
		head++;
		if (head == objects.length)  // Wrap around
			head = 0;
		if (length() <= objects.length / 4 && objects.length / 2 >= 2)
			resize(objects.length / 2);
		return result;
	}
	
	
	/**
	 * Returns the object at head of this queue without removing it.
	 * @return the object at the head of this queue
	 * @throws IllegalStateException if this queue is empty
	 */
	@SuppressWarnings("unchecked")
	public E peek() {
		if (isEmpty())
			throw new IllegalStateException("Empty queue");
		return (E)objects[head];
	}
	
	
	/**
	 * Returns the length of this queue.
	 * @return the number of objects in this queue
	 */
	public int length() {
		if (head <= tail)  // No wrap-around
			return tail - head;
		else  // Has wrap-around
			return tail - head + objects.length;
	}
	
	
	/**
	 * Tests whether this queue is empty.
	 * @return <code>true</true> if this queue has no objects; <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return head == tail;
	}
	
	
	/**
	 * Creates and returns a copy of this queue. The queue's data structures are cloned, but the objects stored are not cloned.
	 * @return a copy of this queue
	 */
	@Override
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
	
	
	public List<E> asList() {
		return new Lst<E>(this);
	}
	
	
	/**
	 * Returns a string representation of this queue. The format is <code>Queue [<var>head</var>, ..., <var>tail</var>]</code>. This is subjected to change.
	 * @return a string representation of this queue
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Queue [");
		for (int i = head; i != tail;) {
			if (i != head)
				sb.append(", ");
			sb.append(objects[i]);
			i++;
			if (i == objects.length)  // Wrap around
				i = 0;
		}
		sb.append("]");
		return sb.toString();
	}
	
	
	private void resize(int newCapacity) {
		if (newCapacity < 2)
			throw new AssertionError();
		Object[] newobject = new Object[newCapacity];
		if (head <= tail)  // No wrap-around
			System.arraycopy(objects, head, newobject, 0, tail - head);
		else {  // Has wrap-around
			System.arraycopy(objects, head, newobject, 0, objects.length - head);
			System.arraycopy(objects, 0, newobject, objects.length - head, tail);
		}
		int len = length();
		objects = newobject;
		head = 0;
		tail = len;
	}
	
	
	
	private static class Lst<E> implements List<E> {
		
		private final ArrayQueue<E> queue;
		
		
		public Lst(ArrayQueue<E> queue) {
			NullChecker.check(queue);
			this.queue = queue;
		}
		
		
		public int length() {
			return queue.length();
		}
		
		
		@SuppressWarnings("unchecked")
		public E getAt(int index) {
			BoundsChecker.check(length(), index);
			if (index < queue.objects.length - queue.head)  // No wrap-around
				return (E)queue.objects[queue.head + index];
			else  // Wrap-around
				return (E)queue.objects[queue.head - queue.objects.length + index];
		}
		
		
		public Iterator<E> iterator() {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		public List<E> sublist(int offset, int length) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		public Collection<E> asCollection() {
			// TODO Auto-generated method stub
			return null;
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
		
		
		public void insertAt(int index, Object obj) {
			throw new UnsupportedOperationException();
		}
		
		
		public void insertListAt(int index, List<? extends E> list) {
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
	
}