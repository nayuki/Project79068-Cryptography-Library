package p79068.datastruct;

import p79068.lang.NullChecker;


/**
 * An abstract readable set.
 */
public abstract class SetView<E> extends CollectionView<E> {
	
	/**
	 * Creates a readable set.
	 */
	protected SetView() { }
	
	
	/**
	 * Returns the number of instances of the specified object in this set, which is either <samp>0</samp> or <samp>1</samp>.
	 */
	public int count(Object o) {
		if (contains(o))
			return 1;
		else
			return 0;
	}
	
	/**
	 * Returns the object in this set that is equal to the specified object, or null if there is none.
	 */
	public E get(Object o) {
		NullChecker.check(o);
		for (E obj : this) {
			if (o.equals(obj))
				return obj;
		}
		return null;
	}
	
	/**
	 * Returns the objects in this set that are equal to the specified object. The returned collection is read-only, and contains either 0 or 1 items.
	 * @param obj the object to compare to
	 * @return a read-only collection of objects from this set equal to the specified object
	 */
	public CollectionView<E> getAll(Object o) {
		List<E> result = new ArrayList<E>(1);
		E temp = get(o);
		if (temp != null)
			result.append(temp);
		return null; // FIXME
	}
	
	
	/**
	 * Returns a string representation of this set. Currently, it returns a list of its items in arbitrary order. This is subjected to change.
	 * @return a string representation of this set
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Set [");
		for (E obj : this)
			sb.append(obj).append(", ");
		if (sb.substring(Math.max(sb.length() - 2, 0), sb.length()).equals(", "))
			sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		return sb.toString();
	}
	
}