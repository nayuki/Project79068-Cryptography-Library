package p79068.util;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;


public final class Interner<E> {
	
	private WeakHashMap<E,WeakReference<E>> weakHashMap;
	
	
	
	public Interner() {
		weakHashMap = new WeakHashMap<E,WeakReference<E>>();
	}
	
	
	
	public synchronized E intern(E obj) {
		if (obj == null)
			return null;
		else {
			WeakReference<E> temp = weakHashMap.get(obj);
			if (temp != null)
				return temp.get();
			else {
				weakHashMap.put(obj, new WeakReference<E>(obj));
				return obj;
			}
		}
	}
	
}