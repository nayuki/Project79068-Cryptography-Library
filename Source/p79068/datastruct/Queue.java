package p79068.datastruct;


/**
 * A queue, a first-in-first-out (FIFO) data structure.
 */
public interface Queue<E> extends Cloneable {
	
	/**
	 * Adds the specified object to the tail of this queue.
	 * @param obj the object to enqueue
	 * @throws IllegalStateException if this queue's length is <code>Integer.MAX_VALUE</code>
	 */
	public void enqueue(E obj);
	
	
	/**
	 * Removes and returns the object at the head of this queue.
	 * @return the object at the head of this queue
	 * @throws IllegalStateException if this queue is empty
	 */
	public E dequeue();
	
	
	/**
	 * Returns the object at head of this queue without removing it.
	 * @return the object at the head of this queue
	 * @throws IllegalStateException if this queue is empty
	 */
	public E peek();
	
	
	/**
	 * Returns the length of this queue. It is illegal for a queue's length to exceed <code>Integer.MAX_VALUE</code>.
	 * @return the number of objects in this queue
	 */
	public int length();
	
	
	/**
	 * Tests whether this queue is empty.
	 * @return <code>true</true> if this queue has no objects; <code>false</code> otherwise
	 */
	public boolean isEmpty();
	
	
	/**
	 * Creates and returns a copy of this queue.
	 * @return a copy of this queue
	 */
	public Queue<E> clone();
	
	
	/**
	 * Returns a string representation of this queue. A suggested format is <code>Queue [<var>head</var>, ..., <var>tail</var>]</code>. This is subjected to change.
	 * @return a string representation of this queue
	 */
	public String toString();
	
}