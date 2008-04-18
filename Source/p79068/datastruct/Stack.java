package p79068.datastruct;


/**
 * A stack, a last-in-first-out (LIFO) data structure.
 */
public interface Stack<E> extends Cloneable {
	
	/**
	 * Adds the specified object to the top of this stack.
	 */
	public void push(E obj);
	
	
	/**
	 * Removes and returns the object at the top of this stack.
	 */
	public E pop();
	
	
	/**
	 * Returns the object at the top of this stack.
	 */
	public E peek();
	
	
	/**
	 * Tests whether this stack is empty.
	 */
	public boolean isEmpty();
	
	
	public Stack<E> clone();
	
	
	/**
	 * Returns a string representation of the stack. Currently, the contents of the stack are listed from bottom to top. This is subjected to change.
	 * @return a string representation of the stack
	 */
	public String toString();
	
}