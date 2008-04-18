package p79068.datastruct;


/**
 * A singly-linked list node.
 * @param <E> the type of object stored in this node
 */
public class LinkedListNode<E> implements Cloneable {
	
	/**
	 * The object stored in this node.
	 */
	public E object;
	
	/**
	 * The reference to the next node.
	 */
	public LinkedListNode<E> next;
	
	
	
	/**
	 * Creates a linked list node storing the specified object.
	 * @param val the object to store
	 */
	public LinkedListNode(E val) {
		this(val, null);
	}
	
	
	/**
	 * Creates a linked list node that stores the specified object and points to the specified next node.
	 * @param val the object to store
	 * @param next the next node to point to
	 */
	public LinkedListNode(E obj, LinkedListNode<E> next) {
		this.object = obj;
		this.next = next;
	}
	
	
	
	/**
	 * Creates and returns a shallow copy of this node. The object stored and the next node are not cloned.
	 * @return a copy of this node
	 */
	@SuppressWarnings("unchecked")
	public LinkedListNode<E> clone() {
		try {
			return (LinkedListNode<E>)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
	}
	
	
	/**
	 * Returns a string representation of this node.
	 * @return a string representation of this node
	 */
	public String toString() {
		return String.format("LinkedListNode: %s", object);
	}
	
}