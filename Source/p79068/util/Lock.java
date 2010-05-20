package p79068.util;


public final class Lock {
	
	private boolean locked;
	
	
	
	public Lock() {
		locked = false;
	}
	
	
	
	public synchronized void lock() throws InterruptedException {
		while (locked)
			wait();
		locked = true;
	}
	
	
	public synchronized void unlock() {
		if (!locked)
			throw new IllegalStateException("Already unlocked");
		locked = false;
		notify();
	}
	
}