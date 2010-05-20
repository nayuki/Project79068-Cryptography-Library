package p79068.datastruct;


final class BTreeInternalNode<E extends Comparable<? super E>> extends BTreeNode<E> {
	
	public Object[] keys;
	
	public BTreeNode<E>[] children;
	
	public int nKeys;
	
	
	
	@SuppressWarnings("unchecked")
	public BTreeInternalNode(int maxKeys) {
		keys = new Object[maxKeys];
		children = new BTreeNode[maxKeys + 1];
		nKeys = 0;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public boolean contains(E key) {
		int i;
		for (i = 0; i < nKeys; i++) {
			int comp = key.compareTo((E)keys[i]);
			if (comp == 0)
				return true;
			else if (comp < 0)
				break;
		}
		return children[i].contains(key);
	}
	
	
	@SuppressWarnings("unchecked")
	public BTreeNodeSplit<E> insert(E key) {
		int i;
		for (i = 0; i < nKeys; i++) {
			int comp = key.compareTo((E)keys[i]);
			if (comp == 0)
				return null;
			else if (comp < 0)
				break;
		}
		
		BTreeNodeSplit<E> split = children[i].insert(key);
		if (split == null)
			return null;
		else {
			if (nKeys < keys.length) {
				System.arraycopy(keys, i, keys, i + 1, nKeys - i);
				keys[i] = split.key;
				System.arraycopy(children, i + 1, children, i + 2, nKeys - i);
				children[i] = split.left;
				children[i + 1] = split.right;
				nKeys++;
				return null;
			} else {
				Object[] tempkeys = new Object[nKeys + 1];
				System.arraycopy(keys, 0, tempkeys, 0, i);
				tempkeys[i] = split.key;
				System.arraycopy(keys, i, tempkeys, i + 1, nKeys - i);
				
				BTreeNode[] tempchildren = new BTreeNode[nKeys + 2];
				System.arraycopy(children, 0, tempchildren, 0, i);
				tempchildren[i] = split.left;
				tempchildren[i + 1] = split.right;
				System.arraycopy(children, i + 1, tempchildren, i + 2, nKeys - i);
				
				int halfKeys = keys.length / 2;
				BTreeInternalNode<E> left = new BTreeInternalNode<E>(keys.length);
				BTreeInternalNode<E> right = new BTreeInternalNode<E>(keys.length);
				System.arraycopy(tempkeys, 0, left.keys, 0, halfKeys);
				System.arraycopy(tempkeys, halfKeys + 1, right.keys, 0, halfKeys);
				System.arraycopy(tempchildren, 0, left.children, 0, halfKeys + 1);
				System.arraycopy(tempchildren, halfKeys + 1, right.children, 0, halfKeys + 1);
				left.nKeys = halfKeys;
				right.nKeys = halfKeys;
				return new BTreeNodeSplit<E>(left, (E)tempkeys[halfKeys], right);
			}
		}
	}
	
	
	
	void print(int level) {
		for (int i = 0; i < nKeys; i++) {
			children[i].print(level+1);
			for (int j = 0; j < level*5;j++)
				System.out.print(" ");
			System.out.println(keys[i]);
		}
		children[nKeys].print(level+1);
	}
	
}