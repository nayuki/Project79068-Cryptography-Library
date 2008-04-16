package p79068.datastruct;

import p79068.util.Random;
import p79068.lang.NullChecker;


/**
 * An abstract array (fixed-length list).
 */
public abstract class Array<E> extends ListView<E> {
	
	/**
	 * Creates an array.
	 */
	protected Array() { }
	
	
	/**
	 * Sets the specified index to the specified object.
	 */
	public abstract void setAt(int index, E obj);
	
	/**
	 * Randomly permutes this list with some default random number generator.
	 */
	public void shuffle() {
		shuffle(Random.DEFAULT);
	}
	
	/**
	 * Randomly permutes this list. All permutations occur with equal probability if the random number generator is unbiased.
	 * @param r the random number generator to use
	 */
	public void shuffle(Random r) {
		for (int i = 0; i < length(); i++) {
			int j = i + r.randomInt(length() - i);
			E temp = getAt(i);
			setAt(i, getAt(j));
			setAt(j, temp);
		}
	}
	
	/**
	 * Sets all elements to the specified value.
	 * @param obj the object to set all elements to
	 */
	public void clearTo(E obj) {
		NullChecker.check(obj);
		for (int i = 0; i < length(); i++)
			setAt(i, obj);
	}
	
}