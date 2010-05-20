package p79068.util;


public final class ReentrantLock {
	
	private Thread owner;
	
	private int count;
	
	
	
	public ReentrantLock() {
		owner = null;
		count = 0;
	}
	
	
	
	public synchronized void lock() throws InterruptedException {
		if (Thread.currentThread() == owner) {
			if (count == Integer.MAX_VALUE)
				throw new IllegalStateException("Maximum count reached");
			count++;
		} else {
			while (owner != null)
				wait();
			owner = Thread.currentThread();
			count = 1;
		}
	}
	
	
	public synchronized void unlock() {
		if (Thread.currentThread() != owner)
			throw new IllegalArgumentException("Thread does not own lock");
		count--;
		if (count == 0) {
			owner = null;
			notify();
		}
	}
	
}