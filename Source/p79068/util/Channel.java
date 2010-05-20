package p79068.util;

import p79068.lang.NullChecker;


public final class Channel<E> {
	
	private E object;
	
	
	
	public Channel() {
		object = null;
	}
	
	
	
	public synchronized void put(E obj) throws InterruptedException {
		NullChecker.check(obj);
		while (object != null)
			wait();
		object = obj;
		notifyAll();
	}
	
	
	public synchronized E get() throws InterruptedException {
		while (object == null)
			wait();
		E result = object;
		object = null;
		notifyAll();
		return result;
	}
	
}