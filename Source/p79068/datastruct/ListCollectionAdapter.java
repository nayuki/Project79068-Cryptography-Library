package p79068.datastruct;

import java.util.Iterator;
import p79068.lang.NullChecker;


public final class ListCollectionAdapter<E> implements Collection<E> {
	
	private List<E> list;
	
	
	
	public ListCollectionAdapter(List<E> list) {
		this.list = list;
	}
	
	
	
	public int size() {
		int size = 0;
		for (E obj : list) {
			if (obj != null)
				size++;
		}
		return size;
	}
	
	
	public boolean contains(Object obj) {
		NullChecker.check(obj);
		for (int i = 0; i < list.length(); i++) {
			if (obj.equals(list.getAt(i)))
				return true;
		}
		return false;
	}
	
	
	public boolean containsAll(Collection<?> coll) {
		NullChecker.check(coll);
		for (Object obj : coll) {
			if (count(obj) < coll.count(obj))
				return false;
		}
		return true;
	}
	
	
	public int count(Object obj) {
		NullChecker.check(obj);
		int count = 0;
		NullChecker.check(obj);
		for (int i = 0; i < list.length(); i++) {
			if (obj.equals(list.getAt(i)))
				count++;
		}
		return count;
	}
	
	
	public void add(E obj) {
		NullChecker.check(obj);
		list.append(obj);
	}
	
	
	public void addAll(Collection<? extends E> coll) {
		NullChecker.check(coll);
		for (E obj : coll)
			list.append(obj);
	}
	
	
	public boolean remove(Object obj) {
		NullChecker.check(obj);
		for (int i = 0; i < list.length(); i++) {
			if (obj.equals(list.getAt(i))) {
				list.removeAt(i);
				return true;
			}
		}
		return false;
	}
	
	
	public int removeAllOf(Object obj) {
		NullChecker.check(obj);
		int count = 0;
		for (int i = 0; i < list.length(); i++) {
			if (obj.equals(list.getAt(i))) {
				list.removeAt(i);
				count++;
			}
		}
		return count;
	}
	
	
	public int removeAll(Collection<?> coll) {
		NullChecker.check(coll);
		int count = 0;
		for (Object obj : coll) {
			if (remove(obj))
				count++;
		}
		return count;
	}
	
	
	public void clear() {
		list.clear();
	}
	
	
	public Iterator<E> iterator() {
		return list.iterator();
	}
	
	
	@Override
	public boolean equals(Object other) {
		if (other == this)
			return true;
		else if (!(other instanceof Collection))
			return false;
		else {
			Collection<?> coll = (Collection<?>)other;
			return this.containsAll(coll) && coll.containsAll(this);
		}
	}
	
	
	@Override
	public int hashCode() {
		int hash = 0;
		for (int i = 0; i < list.length(); i++)
			hash += list.getAt(i).hashCode();
		return hash;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Collection [");
		for (int i = 0; i < list.length(); i++) {
			sb.append(list.getAt(i));
			if (i != 0)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
	
}