package p79068.datastruct;

import p79068.lang.*;


/** A hash table-based dictionary. */
public final class HashDictionary<K, V> extends Dictionary<K, V> {
	
	private Set<DictionaryEntry<K, V>> set;
	
	
	public HashDictionary() {
		set = new HashSet<DictionaryEntry<K, V>>();
	}
	
	
	public V get(K key) {
		NullChecker.check(key);
		DictionaryEntry<K, V> temp = set.get(new DictionaryEntry<K, V>(key, null));
		if (temp != null)
			return temp.value;
		else
			return null;
	}
	
	public void set(K key, V value) {
		NullChecker.check(key);
		NullChecker.check(value);
		set.set(new DictionaryEntry<K, V>(key, value));
	}
	
	public void set(DictionaryView<K, V> dict) {
		if (dict == null)
			throw new NullPointerException();
		throw new AssertionError("Incomplete");
	}
	
	public V remove(K key) {
		NullChecker.check(key);
		DictionaryEntry<K, V> temp = set.remove(new DictionaryEntry<K, V>(key, null));
		if (temp != null)
			return temp.value;
		else
			return null;
	}
	
	public boolean contains(K key) {
		NullChecker.check(key);
		return set.contains(new DictionaryEntry<K, V>(key, null));
	}
	
	public int size() {
		return set.size();
	}
	
	public Set<K> getKeys() {
		Set<K> result = new HashSet<K>();
		for (DictionaryEntry<K, V> entry : set)
			result.add(entry.key);
		return result;
	}
	
	public Collection<V> getValues() {
		Collection<V> result = new ArrayCollection<V>();
		for (DictionaryEntry<K, V> entry : set)
			result.add(entry.value);
		return result;
	}
}