package p79068.datastruct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import p79068.lang.NullChecker;


public class AvlTreeSet<E extends Comparable<? super E>> {
	
	private AvlTreeNode<E> root;
	
	private int size;
	
	
	
	public AvlTreeSet() {
		root = null;
		size = 0;
	}
	
	
	
	public int size() {
		return size;
	}
	
	
	public boolean contains(E obj) {
		NullChecker.check(obj);
		return contains(root, obj);
	}
	
	
	public boolean add(E obj) {
		NullChecker.check(obj);
		if (!contains(obj)) {
			if (size == Integer.MAX_VALUE)
				throw new IllegalStateException("Maximum size reached");
			root = add(root, obj);
			size++;
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
	 * Checks the structure of this AVL tree for consistency. This method is intended to be invoked by testers, by using reflection to bypass the access protection.
	 * @throws AssertionError if this tree is not a well formed AVL tree
	 */
	@SuppressWarnings("unused")
	private void checkStructure() {
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
				node.left = rotateLeft(node.left);
			node = rotateRight(node);
		} else if (balance == +2) {
			if (node.right.getBalance() == -1)
				node.right = rotateRight(node.right);
			node = rotateLeft(node);
		}
		return node;
	}
	
	
	/*
	 *   B            D
	 *  / \          / \
	 * A   D   ->   B   E
	 *    / \      / \
	 *   C   E    A   C
	 */
	private static <E extends Comparable<? super E>> AvlTreeNode<E> rotateLeft(AvlTreeNode<E> node) {
		AvlTreeNode<E> root = node.right;
		node.right = root.left;
		root.left = node;
		root.left.recalculate();
		root.recalculate();
		return root;
	}
	
	
	/*
	 *     D        B
	 *    / \      / \
	 *   B   E -> A   D
	 *  / \          / \
	 * A   C        C   E
	 */
	private static <E extends Comparable<? super E>> AvlTreeNode<E> rotateRight(AvlTreeNode<E> node) {
		AvlTreeNode<E> root = node.left;
		node.left = root.right;
		root.right = node;
		root.right.recalculate();
		root.recalculate();
		return root;
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