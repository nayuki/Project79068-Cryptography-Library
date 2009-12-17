package p79068.util;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import p79068.lang.NullChecker;
import p79068.math.Fft;


public final class KeyedIntener<K,V> {
	
	private Map<K,WeakReference<V>> cache;
	
	private ReferenceQueue<V> queue;
	
	private Map<WeakReference<V>,K> inverseMap;
	
	
	
	public KeyedIntener() {
		cache = new HashMap<K,WeakReference<V>>();
		inverseMap = new HashMap<WeakReference<V>,K>();
		queue = new ReferenceQueue<V>();
	}
	
	boolean x;
	
	public synchronized V get(K key) {
		NullChecker.check(key);
		cleanup();
		if (cache.containsKey(key))
			return cache.get(key).get();
		else
			return null;
	}
	
	
	public synchronized void put(K key, V value) {
		cleanup();
		NullChecker.check(key);
		NullChecker.check(value);
		boolean put = false;
		if (!cache.containsKey(key))
			put = true;
		else {
			WeakReference<V> oldref = cache.get(key);
			if (oldref.get() == null) {
				inverseMap.remove(oldref);
				put = true;
			}
		}
		if (put) {
			WeakReference<V> ref = new WeakReference<V>(value, queue);
			cache.put(key, ref);
			inverseMap.put(ref, key);
			assert cache.size() == inverseMap.size();
		}
	}

	
	private void cleanup() {
		while (true) {
			Reference<? extends V> ref = queue.poll();
			if (ref == null)
				break;
			if (!inverseMap.containsKey(ref))
				continue;
			K key = inverseMap.get(ref);
			if (!cache.containsKey(key))
				throw new AssertionError();
			inverseMap.remove(ref);
			cache.remove(key);
			assert cache.size() == inverseMap.size();
		}
	}
	
}