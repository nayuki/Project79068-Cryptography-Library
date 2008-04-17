/*
 * TODO: This class doesn't work!
 */

package p79068.util;


public class Channel<E> {
	
	class Putter extends Thread {
		E value;
		
		Putter(E v) throws Exception {
			System.out.println("Putter(" + v + ")");
			value = v;
			start();
			Thread.sleep(200);
		}
		
		public void run() {
			put(value);
			System.out.println("Put: " + value);
		}
	}
	
	class Getter extends Thread {
		Getter() throws Exception {
			System.out.println("Getter()");
			start();
			Thread.sleep(200);
		}
		
		public void run() {
			System.out.println("Get: " + get());
		}
	}
	
	
	public static void main(String[] args) throws Throwable {
		Channel<Integer> c = new Channel<Integer>();
		c.new Putter(1);
		c.new Getter();
		Thread.sleep(1000);
		c.new Putter(2);
		c.new Putter(3);
		c.new Putter(4);
		c.new Getter();
		c.new Getter();
		c.new Getter();
		Thread.sleep(1000);
	}
	
	

	protected int putters;
	protected int getters;
	protected Object transferLock;
	protected E object;
	
	
	public Channel() {
		putters = 0;
		getters = 0;
		transferLock = new Object();
		object = null;
	}
	
	
	public synchronized void put(E o) {
		if (getters > 0) {
			object = o;
			notify(); // Notify a getter
			goodWait(transferLock); // Wait for transfer to complete
			object = null;
		} else {
			putters++;
			goodWait(this);
			object = o;
			notify();
			putters--;
		}
	}
	
	public synchronized E get() {
		if (putters > 0) {
			notify(); // Notify a putter
			goodWait(transferLock);
			E result = object;
			object = null;
			return result;
		} else {
			getters++;
			goodWait(this);
			E result = object;
			goodNotify(transferLock);
			getters--;
			return result;
		}
	}
	
	private static void goodWait(Object o) {
		synchronized (o) {
			while (true) {
				try {
					o.wait();
					break;
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	private static void goodNotify(Object o) {
		synchronized (o) {
			o.notify();
		}
	}
}