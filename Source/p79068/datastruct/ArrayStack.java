package p79068.datastruct;

import p79068.lang.NullChecker;


/**
 * An array-based stack. Pushes and pops are in amortized O(1) time.
 * <p>Mutability: <em>Mutable</em><br>
 *  Thread safety: <em>Unsafe</em></p>
 */
public final class ArrayStack<E> implements Stack<E> {
	
	/**
	 * The array that holds the objects of this stack.
	 * The range [<code>0</code>, <code>top</code>) is used, with <code>0</code> being the bottom end of the stack and <code>top</code> being the top end of the stack.
	 */
	private Object[] objects;
	
	/**
	 * The index where the next object will be pushed.
	 */
	private int top;
	
	
	
	/**
	 * Creates an array-based stack.
	 */
	public ArrayStack() {
		this(16);
	}
	
	
	/**
	 * Creates an array-based stack with the specified initial capacity.
	 * @param initCapacity the initial capacity, which must be at least 1
	 * @throws IllegalArgumentException if <code>initCapacity &lt; 1</code>
	 */
	@SuppressWarnings("unchecked")
	public ArrayStack(int initCapacity) {
		if (initCapacity < 1)
			throw new IllegalArgumentException("Initial capacity less than 1");
		objects = new Object[initCapacity];
		top = 0;
	}
	
	
	
	/**
	 * Adds the specified object to the top of this stack.
	 * @param obj the object to add
	 */
	public void push(E obj) {
		NullChecker.check(obj);
		if (top == objects.length)
			resize(objects.length * 2);
		objects[top] = obj;
		top++;
	}
	
	
	/**
	 * Removes and returns the object at the top of this stack.
	 * @return the object at the top of this stack
	 * @throws IllegalStateException if this stack is empty
	 */
	@SuppressWarnings("unchecked")
	public E pop() {
		if (isEmpty())
			throw new IllegalStateException("Stack underflow");
		E result = (E)objects[top - 1];
		objects[top - 1] = null;
		top--;
		if (top <= objects.length / 4 && objects.length / 2 >= 1)
			resize(objects.length / 2);
		return result;
	}
	
	
	/**
	 * Returns the object at the top of this stack without removing it.
	 * @return the object at the top of this stack
	 * @throws IllegalStateException if this stack is empty
	 */
	@SuppressWarnings("unchecked")
	public E peek() {
		if (isEmpty())
			throw new IllegalStateException("Stack underflow");
		return (E)objects[top - 1];
	}
	
	
	/**
	 * Returns the height of this stack.
	 * @return the number of objects in this stack
	 */
	public int height() {
		return top;
	}
	
	
	/**
	 * Tests whether this stack is empty.
	 * @return <code>true</true> if this stack has no objects; <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return top == 0;
	}
	
	
	/**
	 * Creates and returns a copy of this stack.
	 * @return a copy of this stack
	 */
	@SuppressWarnings("unchecked")
	public ArrayStack<E> clone() {
		ArrayStack<E> result;
		try {
			result = (ArrayStack<E>)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
		result.objects = objects.clone();
		return result;
	}
	
	
	/**
	 * Returns a string representation of this stack. The format is <code>Stack [<var>bottom</var>, ..., <var>top</var>]</code>. This is subjected to change.
	 * @return a string representation of this stack
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Stack [");
		for (int i = 0; i < top; i++) {
			if (i != 0)
				sb.append(", ");
			sb.append(objects[i]);
		}
		sb.append("]");
		return sb.toString();
	}
	
	
	// In this implementation, the backing array is doubled when it's full and halved when it's quarter-full.
	private void resize(int newCapacity) {
		if (newCapacity < top || newCapacity < 1)
			throw new AssertionError();
		Object[] newdata = new Object[newCapacity];
		System.arraycopy(objects, 0, newdata, 0, top);
		objects = newdata;
	}
	
}