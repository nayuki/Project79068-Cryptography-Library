package p79068.datastruct;


public class HashSet<E> {
	
	private LinkedListNode<E>[] table;
	
	private int mask;
	
	
	
	public HashSet() {
		this(16);
	}
	
	
	@SuppressWarnings("unchecked")
	public HashSet(int initTableSize) {
		table = new LinkedListNode[initTableSize];
		mask = table.length - 1;
	}
	
	
	
	public boolean add(E obj) {
		if (!contains(obj)) {
			table[getBucket(obj)] = new LinkedListNode<E>(obj, table[getBucket(obj)]);
			return true;
		} else
			return false;
	}
	
	
	public boolean contains(E obj) {
		LinkedListNode<E> node = table[getBucket(obj)];
		while (node != null) {
			if (node.object.equals(obj))
				return true;
		}
		return false;
	}
	
	
	public boolean remove(E obj) {
		int bucket = getBucket(obj);
		if (table[bucket] == null)
			return false;
		else if (obj.equals(table[bucket].object)) {
			table[bucket] = table[bucket].next;
			return true;
		} else {
			LinkedListNode<E> node = table[bucket];
			while (node.next != null) {
				if (obj.equals(node.next.object)) {
					node.next = node.next.next;
					return true;
				}
			}
			return false;
		}
	}
	
	
	
	private int getBucket(E obj) {
		return obj.hashCode() & mask;
	}
	
}