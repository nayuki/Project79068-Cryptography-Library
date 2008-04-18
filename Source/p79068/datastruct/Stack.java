package p79068.datastruct;


/**
 * A stack, a last-in-first-out (LIFO) data structure.
 */
public interface Stack<E> extends Cloneable {
	
	/**
	 * Adds the specified object to the top of this stack.
	 * @param obj the object to add
	 */
	public void push(E obj);
	
	
	/**
	 * Removes and returns the object at the top of this stack.
	 * @return the object at the top of this stack
	 */
	public E pop();
	
	
	/**
	 * Returns the object at the top of this stack without removing it.
	 * @return the object at the top of this stack
	 */
	public E peek();
	
	
	/**
	 * Returns the height of this stack.
	 * @return the number of objects in this stack
	 */
	public int height();
	
	
	/**
	 * Tests whether this stack is empty.
	 * @return <code>true</true> if this stack has no objects; <code>false</code> otherwise
	 */
	public boolean isEmpty();
	
	
	/**
	 * Creates and returns a copy of this stack.
	 * @return a copy of this stack
	 */
	public Stack<E> clone();
	
	
	/**
	 * Returns a string representation of this stack. A suggested format is <code>Queue [<var>bottom</var>, ..., <var>top</var>]</code>. This is subjected to change.
	 * @return a string representation of this stack
	 */
	public String toString();
	
}