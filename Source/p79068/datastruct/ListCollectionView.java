package p79068.datastruct;

import java.util.Iterator;
import p79068.lang.NullChecker;


public class ListCollectionView<E> extends CollectionView<E> {
	
	private ListView<E> list;
	
	
	public ListCollectionView(ListView<E> list) {
		this.list = list;
	}
	
	
	public Iterator<E> iterator() {
		return list.iterator();
	}
	
	
	public int size() {
		return list.length();
	}
	
	public boolean contains(Object o) {
		NullChecker.check(o);
		for (int i = 0; i < list.length(); i++) {
			if (o.equals(list.getAt(i)))
				return true;
		}
		return false;
	}
	
	public int count(Object o) {
		NullChecker.check(o);
		int count = 0;
		NullChecker.check(o);
		for (int i = 0; i < list.length(); i++) {
			if (o.equals(list.getAt(i)))
				count++;
		}
		return count;
	}
	
}