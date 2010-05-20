package p79068.util;


/**
 * A semaphore.
 */
public final class Semaphore {
	
	/**
	 * The count of this semaphore.
	 */
	private int count;
	
	
	
	/**
	 * Constructs a semaphore with an initial count of zero.
	 */
	public Semaphore() {
		this(0);
	}
	
	
	/**
	 * Constructs a semaphore with the specified initial count.
	 */
	public Semaphore(int initCount) {
		if (initCount < 0)
			throw new IllegalArgumentException("Initial count must be non-negative");
		count = initCount;
	}
	
	
	
	/**
	 * Returns the count of this semaphore.
	 * @return the count of this semaphore
	 */
	public synchronized int getCount() {
		return count;
	}
	
	
	/**
	 * Increases this semaphore's count by one.
	 */
	public synchronized void up() {
		count++;
		notifyAll();
	}
	
	
	/**
	 * Increases this semaphore's count by the specified amount. The amount cannot be negative. {@code up(0)} does not change the semaphore's state.
	 * @param n the amount to increase this semaphore's count by
	 * @throws IllegalArgumentException if {@code n} &lt; 0
	 */
	public synchronized void up(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n must be non-negative");
		count += n;
		notifyAll();
	}
	
	
	/**
	 * Decreases this semaphore's count by one, blocking until at least one is available.
	 */
	public synchronized void down() throws InterruptedException {
		while (count < 1)
			wait();
		count--;
	}
	
	
	/**
	 * Decreases this semaphore's count by the specified amount, blocking until at least that amount is available. The amount cannot be negative. {@code down(0)} does not change the semaphore's state, and returns immediately.
	 * @param n the amount to decrease this semaphore's count by
	 * @throws IllegalArgumentException if {@code n} &lt; 0
	 */
	public synchronized void down(int n) throws InterruptedException {
		while (count < n)
			wait();
		count -= n;
	}
	
}