package p79068.util;

import java.util.Comparator;


public class NaturalComparator<T extends Comparable<? super T>> implements Comparator<T> {
	
	public NaturalComparator() {}
	
	
	public int compare(T obj0, T obj1) {
		return obj0.compareTo(obj1);
	}
	
}