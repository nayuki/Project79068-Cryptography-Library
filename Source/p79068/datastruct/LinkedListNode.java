package p79068.datastruct;


public class LinkedListNode<E> implements Cloneable {
	
	public E value;
	public LinkedListNode<E> next;
	
	
	public LinkedListNode(E val) {
		this(val, null);
	}
	
	public LinkedListNode(E val, LinkedListNode<E> next) {
		this.value = val;
		this.next = next;
	}
	
	
	@SuppressWarnings("unchecked")
	public LinkedListNode<E> clone() {
		try {
			return (LinkedListNode<E>)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
	}
	
	public String toString() {
		return String.format("LinkedListNode: %s", value);
	}
}