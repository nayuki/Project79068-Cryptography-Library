package p79068.datastruct;


public class DictionaryEntry<K,V> {
	
	public final K key;
	
	public final V value;
	
	
	
	public DictionaryEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
}