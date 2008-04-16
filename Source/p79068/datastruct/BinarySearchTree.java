package p79068.datastruct;


public class BinarySearchTree<E extends Comparable<E>> extends GenericBinarySearchTree<E, BinaryTreeNode<E>> {
	
	public BinarySearchTree() { }
	
	
	public void add(E obj) {
		root = add(root, obj);
	}
	
	public void remove(E obj) {
		root = remove(root, obj);
	}
	
	
	/* The following methods should be static, but their type parameters would run long. */
	
	private BinaryTreeNode<E> add(BinaryTreeNode<E> node, E obj) {
		if (node == null)
			return new BinaryTreeNode<E>(obj);
		else {
			if (obj.compareTo(node.value) < 0)
				node.left = add(node.left, obj);
			else if (obj.compareTo(node.value) > 0)
				node.right = add(node.right, obj);
			return node;
		}
	}
		
	private BinaryTreeNode<E> remove(BinaryTreeNode<E> node, E obj) {
		if (node == null)
			return null;
		else if (obj.compareTo(node.value) < 0) {
			node.left = remove(node.left, obj);
			return node;
		} else if (obj.compareTo(node.value) > 0) {
			node.right = remove(node.right, obj);
			return node;
		} else {
			if (node.left == null && node.right == null)
				return null;
			else if (node.left != null && node.right == null)
				return node.left;
			else if (node.left == null && node.right != null)
				return node.right;
			else {
				E successor = getLeftmostValue(node.right);
				node.value = successor;
				node.right = remove(node.right, successor);
				return node;
			}
		}
	}
	
	private E getLeftmostValue(BinaryTreeNode<E> node) {
		if (node.left == null)
			return node.value;
		else
			return getLeftmostValue(node.left);
	}
	
	private E getRightmostValue(BinaryTreeNode<E> node) {
		if (node.left == null)
			return node.value;
		else
			return getRightmostValue(node.left);
	}
}