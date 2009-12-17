package p79068.util;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import p79068.lang.NullChecker;


public final class KeyedIntener<K,V> {
	
	private Map<K,WeakReference<V>> cache;
	
	private ReferenceQueue<V> queue;
	
	private Map<WeakReference<V>,K> inverseMap;
	
	
	
	public KeyedIntener() {
		cache = new HashMap<K,WeakReference<V>>();
		queue = new ReferenceQueue<V>();
	}
	
	
	
	public V get(K key) {
		NullChecker.check(key);
		cleanup();
		if (cache.containsKey(key))
			return cache.get(key).get();
		else
			return null;
	}
	
	
	public void put(K key, V value) {
		NullChecker.check(key);
		NullChecker.check(value);
		if (!cache.containsKey(key) || cache.get(key).get() == null) {
			WeakReference<V> ref = new WeakReference<V>(value, queue);
			cache.put(key, ref);
			inverseMap.put(ref, key);
		}
	}
	
	
	private void cleanup() {
		Reference<? extends V> ref = queue.poll();
		if (ref != null) {
			if (!inverseMap.containsKey(ref))
				throw new AssertionError();
			K key = inverseMap.get(ref);
			if (!cache.containsKey(key))
				throw new AssertionError();
			inverseMap.remove(ref);
			cache.remove(key);
		}
	}
	
}