package p79068.datastruct;

import p79068.lang.NullChecker;


/**
An array-based stack. Pushes and pops are in amortized O(1) time.
<p>Mutability: <em>Mutable</em><br>
 Thread safety: <em>Unsafe</em></p>
*/
public final class ArrayStack<E> implements Stack<E> {
	
	private E[] data;
	private int top;
	
	
	
	/**
	 * Creates an array-based stack.
	 */
	public ArrayStack() {
		this(16);
	}
	
	
	/**
	 * Creates an array-based stack with the specified initial capacity.
	 */
	@SuppressWarnings("unchecked")
	public ArrayStack(int initCapacity) {
		if (initCapacity < 1)
			throw new IllegalArgumentException("Initial capacity less than 1");
		data = (E[])new Object[initCapacity];
		top = 0;
	}
	
	
	
	public void push(E obj) {
		NullChecker.check(obj);
		if (top == data.length)
			resize(data.length * 2);
		data[top] = obj;
		top++;
	}
	
	
	public E pop() {
		if (isEmpty())
			throw new IllegalStateException("Stack underflow");
		E result = data[top - 1];
		data[top - 1] = null;
		top--;
		if (top <= data.length / 4 && data.length / 2 >= 1)
			resize(data.length / 2);
		return result;
	}
	
	
	public E peek() {
		if (isEmpty())
			throw new IllegalStateException("Stack underflow");
		return data[top - 1];
	}
	
	
	public boolean isEmpty() {
		return top == 0;
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayStack<E> clone() {
		ArrayStack<E> result;
		try {
			result = (ArrayStack<E>)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
		result.data = data.clone();
		return result;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Stack [");
		for (int i = 0; i < top; i++) {
			if (i != 0)
				sb.append(", ");
			sb.append(data[i]);
		}
		sb.append("]");
		return sb.toString();
	}
	
	
	// In this implementation, the backing array is doubled when it's full and halved when it's quarter-full.
	@SuppressWarnings("unchecked")
	private void resize(int newCapacity) {
		if (newCapacity < top || newCapacity < 1)
			throw new AssertionError();
		E[] newdata = (E[])new Object[newCapacity];
		System.arraycopy(data, 0, newdata, 0, top);
		data = newdata;
	}
	
}