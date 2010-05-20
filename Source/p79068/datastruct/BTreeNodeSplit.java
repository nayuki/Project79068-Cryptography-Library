package p79068.datastruct;


final class BTreeNodeSplit<E extends Comparable<? super E>> {
	
	public BTreeNode<E> left;
	
	public E key;
	
	public BTreeNode<E> right;
	
	
	
	public BTreeNodeSplit(BTreeNode<E> left, E key, BTreeNode<E> right) {
		this.left = left;
		this.key = key;
		this.right = right;
	}
	
}