package p79068.datastruct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import p79068.lang.NullChecker;


public class AvlTreeSet<E extends Comparable<? super E>> {
	
	private AvlTreeNode<E> root;
	
	
	
	public AvlTreeSet() {
		root = null;
	}
	
	
	
	public int size() {
		if (root == null)
			return 0;
		else
			return root.size;
	}
	
	
	public boolean contains(E obj) {
		NullChecker.check(obj);
		return contains(root, obj);
	}
	
	
	public boolean add(E obj) {
		NullChecker.check(obj);
		if (!contains(obj)) {
			if (size() == Integer.MAX_VALUE)
				throw new IllegalStateException("Maximum size reached");
			root = add(root, obj);
			return true;
		} else
			return false;
	}
	
	
	public boolean remove(E obj) {
		NullChecker.check(obj);
		if (contains(obj)) {
			root = remove(root, obj);
			return true;
		} else
			return false;
	}
	
	
	/**
	 * Checks the structure of this AVL tree for consistency. This method is intended to be invoked by tester classes in this package.
	 * @throws AssertionError if this tree is not a well formed AVL tree
	 */
	void checkStructure() {
		if (root != null)
			root.checkStructure(new HashSet<AvlTreeNode<E>>());
	}
	
	public List<E> dumpInOrder() {List<E> result = new ArrayList<E>();dumpInOrder(root, result);return result;}
	private static <E extends Comparable<? super E>> void dumpInOrder(AvlTreeNode<E> node, List<E> list)
	{if(node!=null){dumpInOrder(node.left,list);list.add(node.object);dumpInOrder(node.right,list);}}
	
	
	
	private static <E extends Comparable<? super E>> AvlTreeNode<E> add(AvlTreeNode<E> node, E obj) {
		if (node == null)
			return new AvlTreeNode<E>(obj);
		else {
			int comp = obj.compareTo(node.object);
			if      (comp < 0) node.left = add(node.left, obj);
			else if (comp > 0) node.right = add(node.right, obj);
			// Else object already exists at this node; do nothing
			node.recalculate();
			return balance(node);
		}
	}
	
	
	private static <E extends Comparable<? super E>> boolean contains(AvlTreeNode<E> node, E obj) {
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
	
	
	private static <E extends Comparable<? super E>> AvlTreeNode<E> remove(AvlTreeNode<E> node, E obj) {
		if (node == null)
			return null;
		else {
			int comp = obj.compareTo(node.object);
			if      (comp < 0) node.left  = remove(node.left , obj);
			else if (comp > 0) node.right = remove(node.right, obj);
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
					node.right = remove(node.right, neighbor);
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
	private static <E extends Comparable<? super E>> AvlTreeNode<E> balance(AvlTreeNode<E> node) {
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
	private static <E extends Comparable<? super E>> E getPredecessor(AvlTreeNode<E> node) {
		if (node == null || node.left == null)
			return null;
		node = node.left;
		while (node.right != null)
			node = node.right;
		return node.object;
	}
	
	
	private static <E extends Comparable<? super E>> E getSuccessor(AvlTreeNode<E> node) {
		if (node == null || node.right == null)
			return null;
		node = node.right;
		while (node.left != null)
			node = node.left;
		return node.object;
	}
	
}