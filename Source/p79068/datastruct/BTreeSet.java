package p79068.datastruct;


public final class BTreeSet<E extends Comparable<? super E>> {
	
	private BTreeNode<E> root;
	
	private int size;
	
	private final int maxKeys;
	
	
	
	public BTreeSet(int maxKeys) {
		if (maxKeys < 2)
			throw new IllegalArgumentException("Max keys < 2");
		if (maxKeys % 2 != 0)
			throw new IllegalArgumentException("Max keys not even");
		this.maxKeys = maxKeys;
		root = new BTreeLeaf<E>(maxKeys);
		size = 0;
	}
	
	
	
	public int size() {
		return size;
	}
	
	
	public boolean contains(E key) {
		return root.contains(key);
	}
	
	
	public void insert(E key) {
		BTreeNodeSplit<E> split = root.insert(key);
		if (split != null) {
			BTreeInternalNode<E> temp = new BTreeInternalNode<E>(maxKeys);
			temp.keys[0] = split.key;
			temp.children[0] = split.left;
			temp.children[1] = split.right;
			temp.nKeys = 1;
			root = temp;
		}
	}
	
	
	
	void print() {
		root.print(0);
	}
	
	
}