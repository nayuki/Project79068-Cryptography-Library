package p79068.datastruct;


abstract class BTreeNode<E extends Comparable<? super E>> {
	
	public abstract boolean contains(E key);
	
	public abstract BTreeNodeSplit<E> insert(E key);
	
	
	abstract void print(int level);
	
}
