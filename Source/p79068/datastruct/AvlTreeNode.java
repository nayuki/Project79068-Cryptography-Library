package p79068.datastruct;

import java.util.Set;


class AvlTreeNode<E> {
	
	/**
	 * The object stored at this node. This should not be <code>null</code>.
	 */
	public E object;
	
	/**
	 * The height of the tree rooted at this node. Interpreting a <code>null</code> subtree as having height <code>0</code>, this height is equal to <code>Math.max(left.height, right.height) + 1</code>.
	 */
	public int height;
	
	/**
	 * The number of nodes in the subtree rooted at this node, including this node. Interpreting a <code>null</code> subtree as having size <code>0</code>, this size is equal to <code>left.size + right.size + 1</code>.
	 */
	public int size;
	
	/**
	 * The root node of the left subtree.
	 */
	public AvlTreeNode<E> left;
	
	/**
	 * The root node of the right subtree.
	 */
	public AvlTreeNode<E> right;
	
	
	
	/**
	 * Creates an AVL tree node with the specified object. The height of the tree rooted at this node is <code>1</code>. The size of the tree rooted at this node is <code>1</code>. The left and right subtrees are set to <code>null</code>.
	 * @param obj the object stored in the node
	 */
	public AvlTreeNode(E obj) {
		object = obj;
		height = 1;
		size = 1;
		left = null;
		right = null;
	}
	
	
	
	/**
	 * Returns the balance of the tree rooted at this node, which is the height of the right subtree minus the height of the left subtree. Negative values indicate that this tree is "left-heavy"; positive values indicate that this tree is "right-heavy"; zero indicates that this tree has height-balanced subtrees.
	 * @return the balance of the tree rooted at this node
	 */
	public int getBalance() {
		return getHeight(right) - getHeight(left);
	}
	
	
	/**
	 * Recalculates the height and size of the subtree rooted at this node, assuming that the left and right subtrees' heights and sizes are correctly calculated.
	 */
	public void recalculate() {
		height = Math.max(getHeight(left), getHeight(right)) + 1;
		size = getSize(left) + getSize(right) + 1;
	}
	
	
	/*
	 *   B            D
	 *  / \          / \
	 * A   D   ->   B   E
	 *    / \      / \
	 *   C   E    A   C
	 */
	public AvlTreeNode<E> rotateLeft() {
		if (right == null)
			throw new IllegalStateException();
		AvlTreeNode<E> root = this.right;
		this.right = root.left;
		root.left = this;
		this.recalculate();
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
	public AvlTreeNode<E> rotateRight() {
		if (left == null)
			throw new IllegalStateException();
		AvlTreeNode<E> root = this.left;
		this.left = root.right;
		root.right = this;
		this.recalculate();
		root.recalculate();
		return root;
	}
	
	
	
	/**
	 * Recursively checks the structure of the AVL tree rooted at this node for consistency.
	 * <p>Currently, these properties are checked:</p>
	 * <ul>
	 *  <li>This node has not been visited before. (Otherwise, some node would have at least 2 parents, which disqualifies this structure from being a tree.)</li>
	 *  <li>The left and right subtrees have all of these properties.</li>
	 *  <li>The height of the tree rooted at this node is equal to 1 plus the height of of the tallest subtree.</li>
	 *  <li>The size of the tree rooted at this node is equal to 1 plus the size of each subtree.</li>
	 *  <li>The balance of this node is <code>-1</code>, <code>0</code>, or <code>+1</code>.</li>
	 * </ul>
	 * @throws AssertionError if the tree rooted at this node is not a well formed AVL tree
	 */
	void checkStructure(Set<AvlTreeNode<E>> visitedNodes) {
		if (visitedNodes.contains(this))
			throw new AssertionError("AVL tree structure violated: not a tree");
		visitedNodes.add(this);
		if (left != null)
			left.checkStructure(visitedNodes);
		if (right != null)
			right.checkStructure(visitedNodes);
		if (height != Math.max(getHeight(left), getHeight(right)) + 1)
			throw new AssertionError("AVL tree structure violated: incorrect cached height");
		if (size != getSize(left) + getSize(right) + 1)
			throw new AssertionError("AVL tree structure violated: incorrect cached size");
		if (Math.abs(getBalance()) > 1)
			throw new AssertionError("AVL tree structure violated: height imbalance");
	}
	
	
	
	/**
	 * Returns the height of the tree rooted at the specified node. Returns <code>0</code> if <code>node</code> is <code>null</code>; otherwise returns <code>node.height</code>.
	 * @param node the root node of the tree
	 * @return <code>0</code> if <code>node</code> is <code>null</code>; otherwise <code>node.height</code>
	 */
	private static int getHeight(AvlTreeNode<?> node) {
		if (node == null)
			return 0;
		else
			return node.height;
	}
	
	
	/**
	 * Returns the size of the tree rooted at the specified node. Returns <code>0</code> if <code>node</code> is <code>null</code>; otherwise returns <code>node.size</code>.
	 * @param node the root node of the tree
	 * @return <code>0</code> if <code>node</code> is <code>null</code>; otherwise <code>node.size</code>
	 */
	private static int getSize(AvlTreeNode<?> node) {
		if (node == null)
			return 0;
		else
			return node.height;
	}
	
}