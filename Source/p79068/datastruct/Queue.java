package p79068.datastruct;


/**
 * A queue, a first-in-first-out (FIFO) data structure.
 */
public interface Queue<E> extends Cloneable {
	
	/**
	 * Adds the specified object to the tail of this queue.
	 */
	public void enqueue(E obj);
	
	
	/**
	 * Removes and returns the head of this queue.
	 */
	public E dequeue();
	
	
	/**
	 * Returns the head of this queue.
	 */
	public E peek();
	
	
	/**
	 * Tests whether this queue is empty.
	 */
	public boolean isEmpty();
	
	
	public Queue<E> clone();
	
	
	/**
	 * Returns a string representation of the queue. Currently, the contents of the queue are listed from head to tail. This is subjected to change.
	 * @return a string representation of the queue
	 */
	public String toString();
	
}