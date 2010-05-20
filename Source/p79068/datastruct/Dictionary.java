package p79068.datastruct;

import java.util.Iterator;


/**
 * A dictionary, which is a mapping from keys to values. Keys must not be <code>null</code>. Values may or may not be <code>null</code>.
 */
public interface Dictionary<K,V> extends Iterable<DictionaryEntry<K,V>> {
	
	/**
	 * Returns the number of entries (or keys) in this dictionary. It is illegal for a dictionary's size to exceed <code>Integer.MAX_VALUE</code>.
	 */
	public int size();
	
	
	/**
	 * Returns the value associated with the specified key.
	 */
	public V getValue(K key);
	
	
	/**
	 * Tests whether this dictionary contains a mapping with the specified key.
	 */
	public boolean containsKey(K key);
	
	
	/**
	 * Associates the specified key with the specified value, not overwriting a previous mapping for the same key if it exists.
	 */
	public void add(K key, V value);
	
	
	/**
	 * Adds the mappings in the specified dictionary to this dictionary, not overwriting mappings for keys that already exist.
	 */
	public void addAll(Dictionary<K,V> dict);
	
	
	/**
	 * Associates the specified key with the specified value, overwriting a previous mapping for the same key if it exists.
	 */
	public void set(K key, V value);
	
	
	/**
	 * Adds the mappings in the specified dictionary to this dictionary, overwriting mappings for keys that already exist.
	 */
	public void setAll(Dictionary<K,V> dict);
	
	
	/**
	 * Removes the specified key and its associated value and returns the value.
	 */
	public V remove(K key);
	
	
	/**
	 * Removes the mappings with keys in the specified set of keys.
	 */
	public void removeAll(Set<K> keys);
	
	
	/**
	 * Returns the set of keys in this dictionary.
	 */
	public abstract Set<K> getKeys();
	
	
	/**
	 * Returns the collection of values in this dictionary.
	 */
	public abstract Collection<V> getValues();
	
	
	/**
	 * Returns an iterator over the mappings of this dictionary, in arbitrary order.
	 */
	public Iterator<DictionaryEntry<K,V>> iterator();
	
}