package p79068.datastruct;


final class BTreeLeaf<E extends Comparable<? super E>> extends BTreeNode<E> {
	
	public Object[] data;
	
	public int nItems;
	
	
	
	public BTreeLeaf(int maxItems) {
		data = new Object[maxItems];
		nItems = 0;
	}
	
	
	
	public boolean contains(E key) {
		for (int i = 0; i < nItems; i++) {
			if (key.equals(data[i]))
				return true;
		}
		return false;
	}
	
	
	@SuppressWarnings("unchecked")
	public BTreeNodeSplit<E> insert(E key) {
		int i;
		for (i = 0; i < nItems; i++) {
			int comp = key.compareTo((E)data[i]);
			if (comp == 0)
				return null;
			else if (comp < 0)
				break;
		}
		
		if (nItems < data.length) {
			System.arraycopy(data, i, data, i + 1, nItems - i);
			data[i] = key;
			nItems++;
			return null;
		} else {
			Object[] temp = new Object[nItems + 1];
			System.arraycopy(data, 0, temp, 0, i);
			temp[i] = key;
			System.arraycopy(data, i, temp, i + 1, nItems - i);
			
			int halfMax = data.length / 2;
			BTreeLeaf<E> left = new BTreeLeaf<E>(data.length);
			BTreeLeaf<E> right = new BTreeLeaf<E>(data.length);
			System.arraycopy(temp, 0, left.data, 0, halfMax);
			System.arraycopy(temp, halfMax + 1, right.data, 0, halfMax);
			left.nItems = halfMax;
			right.nItems = halfMax;
			return new BTreeNodeSplit(left, (E)temp[halfMax], right);
		}
	}
	
	
	
	void print(int level) {
		for (int i = 0; i < nItems; i++) {
			for (int j = 0; j < level*5;j++)
				System.out.print(" ");
			System.out.println(data[i]);
		}
	}
	
}