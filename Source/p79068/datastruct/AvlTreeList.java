package p79068.datastruct;

import p79068.lang.NullChecker;


public class AvlTreeList<E> {
	
	private AvlTreeNode<E> root;
	
	
	
	public AvlTreeList() {
		root = null;
	}
	
	
	
	public int size() {
		if (root == null)
			return 0;
		else
			return root.size;
	}
	
	
	public void insertAt(int index, E obj) {
		NullChecker.check(obj);
		if (size() == Integer.MAX_VALUE)
			throw new IllegalStateException("Maximum size reached");
		root = insertAt(root, index, obj);
	}
	
	
	public void removeAt(int index) {
		root = removeAt(root, index);
	}
	
	
	/**
	 * Checks the structure of this AVL tree for consistency. This method is intended to be invoked by testers, by using reflection to bypass the access protection.
	 * @throws AssertionError if this tree is not a well formed AVL tree
	 */
	@SuppressWarnings("unused")
	private void checkStructure() {
		if (root != null)
			root.checkStructure(new java.util.HashSet<AvlTreeNode<E>>());
	}
	
	
	
	private static <E> AvlTreeNode<E> insertAt(AvlTreeNode<E> node, int index, E obj) {
		if (node == null)
			return new AvlTreeNode<E>(obj);
		else {
			int comp = obj.compareTo(node.object);
			if      (comp < 0) node.left = insertAt(node.left, obj);
			else if (comp > 0) node.right = insertAt(node.right, obj);
			// Else object already exists at this node; do nothing
			node.recalculate();
			return balance(node);
		}
	}
	
	
	private static <E> boolean getNodeAt(AvlTreeNode<E> node, int index) {
		while (true) {
			if (node == null)
				return false;
			else {
				int comp = obj.compareTo(node.object);
				if      (comp < 0) node = node.left;
				else if (comp > 0) node = node.right;
				else return true;  // Found at this node
			}
		}
	}
	
	
	private static <E> AvlTreeNode<E> removeAt(AvlTreeNode<E> node, int index) {
		if (node == null)
			return null;
		else {
			int comp = obj.compareTo(node.object);
			if      (comp < 0) node.left  = removeAt(node.left , obj);
			else if (comp > 0) node.right = removeAt(node.right, obj);
			else {
				if (node.left == null && node.right == null)
					return null;
				else if (node.left != null && node.right == null)
					node = node.left;
				else if (node.left == null && node.right == null)
					node = node.right;
				else {
					// Using the predecessor is an equally good choice.
					// In that case, remove that value from node.left .
					E neighbor = getSuccessor(node);
					node.object = neighbor;
					node.right = removeAt(node.right, neighbor);
				}
			}
			node.recalculate();
			return balance(node);
		}
	}
	
	
	/*
	 * Balances the subtree rooted at the specified node and returns the new root.
	 * Assumes that the specified node has a balance in [-2,+2], and all subtrees are have balances in [-1,+1].
	 * Also assumes that the specified node is not null (the 0-height tree).
	 */
	private static <E> AvlTreeNode<E> balance(AvlTreeNode<E> node) {
		int balance = node.getBalance();
		if (balance == -2) {
			if (node.left.getBalance() == +1)
				node.left = node.left.rotateLeft();
			node = node.rotateRight();
		} else if (balance == +2) {
			if (node.right.getBalance() == -1)
				node.right = node.right.rotateRight();
			node = node.rotateLeft();
		}
		return node;
	}
	
	
	@SuppressWarnings("unused")
	private static <E> E getPredecessor(AvlTreeNode<E> node) {
		if (node == null || node.left == null)
			return null;
		node = node.left;
		while (node.right != null)
			node = node.right;
		return node.object;
	}
	
	
	private static <E> E getSuccessor(AvlTreeNode<E> node) {
		if (node == null || node.right == null)
			return null;
		node = node.right;
		while (node.left != null)
			node = node.left;
		return node.object;
	}
	
}