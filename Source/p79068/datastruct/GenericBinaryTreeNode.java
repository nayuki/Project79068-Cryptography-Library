package p79068.datastruct;


public class GenericBinaryTreeNode<E, N extends GenericBinaryTreeNode<E,N>> {
	
	public E value;
	public N left;
	public N right;
	
	
	public GenericBinaryTreeNode(E value) {
		this(value, null, null);
	}
	
	public GenericBinaryTreeNode(E value, N left, N right) {
		this.value = value;
		this.left = left;
		this.right = right;
	}
	
	
	public String toString() {
		return value.toString();
	}
	
}