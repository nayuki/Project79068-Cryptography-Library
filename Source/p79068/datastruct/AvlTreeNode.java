package p79068.datastruct;


public class AvlTreeNode<E> extends GenericBinaryTreeNode<E, AvlTreeNode<E>> {
	
	public int height;
	
	
	public AvlTreeNode(E value) {
		super(value);
		height = 1;
	}
	
	
	public void recalcHeight() {
		height = Math.max(getHeight(left), getHeight(right)) + 1;
	}
	
	public int getBalance() {
		return getHeight(right) - getHeight(left);
	}
	
	
	private static int getHeight(AvlTreeNode<?> node) {
		if (node == null)
			return 0;
		else
			return node.height;
	}
	
}
