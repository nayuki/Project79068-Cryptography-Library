package p79068.datastruct;


/** An abstract dictionary (a mapping from keys to values). */
public abstract class Dictionary<K, V> extends DictionaryView<K, V> {
	
	/**
	 * Creates a dictionary.
	 */
	protected Dictionary() {}
	
	
	/** Associates the specified key with the specified value, overwriting any previous association for the same key. */
	public abstract void set(K key, V value);
	
	/** Adds the specified dictionary to this one, overwriting associations for new keys that equal previous keys. */
	public abstract void set(DictionaryView<K, V> dict);
	
	/** Removes the specified key and its associated value and returns the value. */
	public abstract V remove(K key);
}