package p79068.datastruct;

import p79068.lang.NullChecker;


/**
 * An array-based stack. Pushes and pops are in amortized O(1) time.
 * <p>Mutability: <em>Mutable</em><br>
 *  Thread safety: <em>Unsafe</em></p>
 */
public final class ArrayStack<E> extends AbstractDynamicArray<E> implements Stack<E> {
	
	/*
	 * For the field "protected Object[] objects" defined in the superclass:
	 *   The array that holds the objects of this stack.
	 *   The range [<code>0</code>, <code>top</code>) is used, with <code>0</code> being the bottom end of the stack and <code>top</code> being the top end of the stack.
	 */
	
	/*
	 * For the field "protected int length" defined in the superclass:
	 *   The index where the next object will be pushed. (This is the top of the stack.)
	 */
	
	
	
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
	public ArrayStack(int initCapacity) {
		super(initCapacity, 2);
	}
	
	
	
	/**
	 * Adds the specified object to the top of this stack.
	 * @param obj the object to add
	 */
	public void push(E obj) {
		NullChecker.check(obj);
		upsize(length + 1);
		objects[length] = obj;
		length++;
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
		E result = (E)objects[length - 1];
		objects[length - 1] = null;
		length--;
		downsize();
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
		return (E)objects[length - 1];
	}
	
	
	/**
	 * Returns the height of this stack.
	 * @return the number of objects in this stack
	 */
	public int height() {
		return length;
	}
	
	
	/**
	 * Tests whether this stack is empty.
	 * @return <code>true</true> if this stack has no objects; <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return length == 0;
	}
	
	
	/**
	 * Creates and returns a copy of this stack. The stack's data structures are cloned, but the objects stored are not cloned.
	 * @return a copy of this stack
	 */
	@Override
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
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Stack [");
		for (int i = 0; i < length; i++) {
			if (i != 0)
				sb.append(", ");
			sb.append(objects[i]);
		}
		sb.append("]");
		return sb.toString();
	}
	
}