package p79068.datastruct;

public class AvlTree<E extends Comparable<E>> extends GenericBinarySearchTree<E, AvlTreeNode<E>> {
	
	public AvlTree() { }
	
	
	public void add(E obj) {
		root = add(root, obj);
	}
	
	public boolean contains(E obj) {
		return get(obj) != null;
	}
	
	public E remove(E obj) {
		E result = get(obj);
		if (result != null)
			root = remove(root, obj);
		return result;
	}
	
	
	public AvlTreeNode<E> getRoot() {
		return root;
	}
	
	
	private AvlTreeNode<E> add(AvlTreeNode<E> node, E obj) {
		if (node == null)
			return new AvlTreeNode<E>(obj);
		else {
			if (obj.compareTo(node.value) < 0)
				node.left = add(node.left, obj);
			else if (obj.compareTo(node.value) > 0)
				node.right = add(node.right, obj);
			// Else do nothing
			node.recalcHeight();
			return balance(node);
		}
	}
	
	private AvlTreeNode<E> remove(AvlTreeNode<E> node, E obj) {
		if (node == null)
			return null;
		else if (obj.compareTo(node.value) < 0) {
			node.left = remove(node.left, obj);
			node.recalcHeight();
			return balance(node);
		} else if (obj.compareTo(node.value) > 0) {
			node.right = remove(node.right, obj);
			node.recalcHeight();
			return balance(node);
		} else {  // obj.compareTo(node.value) == 0
			if (node.left == null && node.right == null)
				return null;
			else if (node.left != null && node.right == null)
				return node.left;
			else if (node.left == null && node.right != null)
				return node.right;
			else {
				node.value = getSuccessor(node);
				node = remove(node, node.value);
				node.recalcHeight();
				return balance(node);
			}
		}
	}
	
	
	private E getSuccessor(AvlTreeNode<E> node) {
		node = node.right;
		while (node.left != null)
			node = node.left;
		return node.value;
	}
	
	private AvlTreeNode<E> balance(AvlTreeNode<E> node) {
		if (Math.abs(node.getBalance()) <= 1)
			return node;
		else if (node.getBalance() == -2)
			return rotateRight(node);
		else if (node.getBalance() == 2)
			return rotateLeft(node);
		else
			throw new AssertionError();
	}
	
	/*
	 *   B            D
	 *  / \          / \
	 * A   D   ->   B   E
	 *    / \      / \
	 *   C   E    A   C
	 */
	private AvlTreeNode<E> rotateLeft(AvlTreeNode<E> node) {
		if (node.right != null && node.right.getBalance() == -1)
			node.right = rotateRight(node.right);
		AvlTreeNode<E> root = node.right;
		node.right = root.left;
		root.left = node;
		root.left.recalcHeight();
		root.recalcHeight();
		return root;
	}
	
	/*
	 *     D        B
	 *    / \      / \
	 *   B   E -> A   D
	 *  / \          / \
	 * A   C        C   E
	 */
	private AvlTreeNode<E> rotateRight(AvlTreeNode<E> node) {
		if (node.left != null && node.left.getBalance() == 1)
			node.left = rotateLeft(node.left);
		AvlTreeNode<E> root = node.left;
		node.left = root.right;
		root.right = node;
		root.right.recalcHeight();
		root.recalcHeight();
		return root;
	}
	
}