package p79068.datastruct;


public class BinaryTreeNode<E> extends GenericBinaryTreeNode<E, BinaryTreeNode<E>> {
	
	public BinaryTreeNode(E value) {
		super(value);
	}

	BinaryTreeNode(E value, BinaryTreeNode<E> left, BinaryTreeNode<E> right) {
		super(value, left, right);
	}
	
}