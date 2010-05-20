package p79068.util;


public final class Semaphore {
	
	private int count;
	
	
	
	public Semaphore() {
		count = 0;
	}
	
	
	
	public synchronized int getCount() {
		return count;
	}
	
	
	public synchronized void up() {
		count++;
		notifyAll();
	}
	
	
	public synchronized void up(int n) {
		count += n;
		notifyAll();
	}
	
	
	public synchronized void down() throws InterruptedException {
		while (count < 1)
			wait();
		count--;
	}
	
	
	public synchronized void down(int n) throws InterruptedException {
		while (count < n)
			wait();
		count -= n;
	}
	
}