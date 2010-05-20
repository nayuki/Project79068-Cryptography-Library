package p79068.datastruct;

import java.util.Iterator;

import p79068.lang.NullChecker;
import p79068.math.IntegerMath;


public class HashSet<E> implements Set<E> {
	
	private LinkedListNode<E>[] table;
	
	private int mask;
	
	private double loadFactor;
	
	private int size;
	
	
	
	public HashSet() {
		this(16, 0.8);
	}
	
	
	@SuppressWarnings("unchecked")
	public HashSet(int initTableSize, double loadFactor) {
		if (!IntegerMath.isPowerOf2(initTableSize))
			throw new IllegalArgumentException("Initial table size is not a power of 2");
		if (loadFactor <= 0 || loadFactor > Integer.MAX_VALUE || Double.isNaN(loadFactor))
			throw new IllegalArgumentException("Load factor out of range");
		
		table = new LinkedListNode[initTableSize];
		mask = table.length - 1;
		this.loadFactor = loadFactor;
		size = 0;
	}
	
	
	
	public int size() {
		return size;
	}
	
	
	public boolean add(E obj) {
		NullChecker.check(obj);
		if (size == Integer.MAX_VALUE)
			throw new IllegalStateException("Maximum size reached");
		int bucket = getBucket(obj);
		LinkedListNode<E> node = table[bucket];
		while (node != null) {
			if (node.object.equals(obj))
				return false;
		}
		table[bucket] = new LinkedListNode<E>(obj, table[bucket]);
		if ((double)size / table.length > loadFactor)
			resize(table.length * 2);
		return true;
	}
	
	
	public void addAll(Set<? extends E> set) {
		NullChecker.check(set);
		for (E obj : set)
			add(obj);
	}
	
	
	public void addAll(Collection<? extends E> coll) {
		NullChecker.check(coll);
		for (E obj : coll)
			add(obj);
	}
	
	
	public boolean contains(Object obj) {
		NullChecker.check(obj);
		LinkedListNode<E> node = table[getBucket(obj)];
		while (node != null) {
			if (node.object.equals(obj))
				return true;
		}
		return false;
	}
	
	
	public boolean containsAll(Set<?> set) {
		NullChecker.check(set);
		for (Object obj : set) {
			if (!contains(obj))
				return false;
		}
		return true;
	}
	
	
	public boolean remove(Object obj) {
		NullChecker.check(obj);
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
	
	
	public int removeAll(Set<?> set) {
		NullChecker.check(set);
		int count = 0;
		for (Object obj : set) {
			if (remove(obj))
				count++;
		}
		return count;
	}
	
	
	public void clear() {
		for (int i = 0; i < table.length; i++)
			table[i] = null;
	}
	
	
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	private int getBucket(Object obj) {
		return obj.hashCode() & mask;
	}
	
	
	@SuppressWarnings("unchecked")
	private void resize(int newSize) {
		if (!IntegerMath.isPowerOf2(newSize))
			throw new IllegalArgumentException("New table size is not a power of 2");
		LinkedListNode<E>[] newtable = new LinkedListNode[newSize];
		mask = newtable.length - 1;
		for (LinkedListNode<E> node : table) {
			while (node != null) {
				LinkedListNode<E> next = node.next;
				int newbucket = getBucket(node.object);
				node.next = newtable[newbucket];
				newtable[newbucket] = node;
				node = next;
			}
		}
		table = newtable;
	}
	
}