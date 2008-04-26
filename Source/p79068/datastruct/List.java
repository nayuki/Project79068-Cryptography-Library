package p79068.datastruct;

import java.util.Iterator;


/**
 * An list, which is a finite sequence of objects. Storing <code>null</code>s may or may not be allowed.
 * @param <E> the type of object stored in this list
 */
public interface List<E> extends Iterable<E> {
	
	/**
	 * Returns the length of this list. It is illegal for a list's length to exceed <code>Integer.MAX_VALUE</code>.
	 * @return the number of objects in this list (including <code>null</code>s)
	 */
	public int length();
	
	
	/**
	 * Returns the object at the specified index in this list.
	 * @param index the index; <code>0 &lt;= index &lt; length()</code>
	 * @return the object at <code>index</code> in this list
	 */
	public E getAt(int index);
	
	
	/**
	 * Sets the object at the specified index in this list to the specified object.
	 * @param index the index; <code>0 &lt;= index &lt; length()</code>
	 * @param obj the object to set to
	 */
	public void setAt(int index, E obj);
	
	
	/**
	 * Appends the specified object to the end of this list. Equivalent to <code>insert(length(), obj)</code>.
	 * @param obj the object to append
	 */
	public void append(E obj);
	
	
	/**
	 * Appends the specified list to the end of this list. Equivalent to <code>insertList(length(), list)</code>.
	 * @param list the list of objects to append
	 */
	public void appendList(List<? extends E> list);
	
	
	/**
	 * Inserts the specified object at the specified index in this list.
	 * @param obj the object to insert
	 * @param index the index where <code>obj</code> will be inserted at; <code>0 &lt;= index &lt;= length()</code>
	 */
	public void insertAt(int index, E obj);
	
	
	/**
	 * Inserts the specified list at the specified index in this list.
	 * @param list the list of objects to insert
	 * @param index the index where the objects in <code>list</code> will be inserted at; <code>0 &lt;= index &lt;= length()</code>
	 */
	public void insertList(int index, List<? extends E> list);
	
	
	/**
	 * Removes and returns the object at the specified index in this list.
	 * @param index the index of the object to remove; <code>0 &lt;= index &lt; length()</code>
	 * @return the object at <code>index</code> in this list
	 */
	public E removeAt(int index);
	
	
	/**
	 * Removes from this list the objects in the specified range.
	 * @param offset the starting index of the range, inclusive; <code>0 &lt;= offset &lt; length()</code>
	 * @param length the number of objects in the range; <code>0 &lt;= length &lt; length() - offset</code>
	 */
	public void removeRange(int offset, int length);
	
	
	/**
	 * Removes all objects from this list. Equivalent to <code>removeRange(0, length())</code>.
	 */
	public void clear();
	
	
	/**
	 * Returns a sublist of this list of the specified length, starting at the specified offset.
	 * @param offset the offset
	 * @param length the length
	 * @return a sublist of this list
	 */
	public List<E> sublist(int offset, int length);
	
	
	/**
	 * Returns an iterator over the objects in this list, traversing in the proper order.
	 * @return an iterator
	 */
	public Iterator<E> iterator();
	
	
	/**
	 * Returns this list viewed as a collection. The returned collection may be linked to this list, or it may have its own data storage structures. The collection may or may not support modification operations. The collection may or may not be valid after this list is modified.
	 * @return this list as a collection
	 */
	public Collection<E> asCollection();
	
	
	/**
	 * Compares the specified object with this list for equality. Returns <code>true</code> if the specified object is a list of the same length and all corresponding pairs of elements are equal. Two elements <code>x</code> and <code>y</code> are equal if and only if <code>(x == null && y == null) || (x != null && y != null && x.equals(y))</code>. Otherwise, this method returns false.
	 * @param other the object to compare to for equality
	 * @return <code>true</code> if <code>other</code> is a list with the same contents as this list; <code>false</code> otherwise
	 */
	public boolean equals(Object other);
	
	
	/**
	 * Returns the hash code for this list.
	 * @return the hash code for this list
	 */
	public int hashCode();
	
	
	/**
	 * Returns a string representation of this list. A suggested format is <code>List [<var>0<sup>th</sup> object</var>, <var>1<sup>st</sup> object</var>, ...]</code>. This is subjected to change.
	 * @return a string representation of this list
	 */
	public String toString();
	
}