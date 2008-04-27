package p79068.datastruct;

import java.util.Set;


class AvlTreeNode<E> {
	
	public E object;
	
	public int height;
	
	public AvlTreeNode<E> left;
	
	public AvlTreeNode<E> right;
	
	
	
	public AvlTreeNode(E obj) {
		object = obj;
		height = 1;
		left = null;
		right = null;
	}
	
	
	
	public int getBalance() {
		return getHeight(right) - getHeight(left);
	}
	
	
	public void recalculateHeight() {
		height = 1 + Math.max(getHeight(left), getHeight(right));
	}
	
	
	public void checkStructure(Set<AvlTreeNode<E>> visitedNodes) {
		if (visitedNodes.contains(this))
			throw new AssertionError("AVL tree structure violated: duplicate objects");
		visitedNodes.add(this);
		if (left != null)
			left.checkStructure(visitedNodes);
		if (right != null)
			right.checkStructure(visitedNodes);
		if (height != 1 + Math.max(getHeight(left), getHeight(right)))
			throw new AssertionError("AVL tree structure violated: incorrect cached height");
		if (Math.abs(getBalance()) > 1)
			throw new AssertionError("AVL tree structure violated: height imbalance");
	}
	
	
	
	private static <E> int getHeight(AvlTreeNode<E> node) {
		if (node == null)
			return 0;
		else
			return node.height;
	}
	
}