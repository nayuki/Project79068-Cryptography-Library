package datastruct;

import static org.junit.Assert.*;
import org.junit.Test;
import p79068.datastruct.ArrayQueue;


public class ArrayQueueTest extends QueueTest {
	
	protected <E> ArrayQueue<E> newQueue() {
		return new ArrayQueue<E>();
	}
	
	
	@Test
	public void testInitialization() {
		try {
			new ArrayQueue<String>();
			new ArrayQueue<String>(2);
			new ArrayQueue<String>(5);
			new ArrayQueue<String>(16);
			new ArrayQueue<String>(37);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
	
	@Test
	public void testInvalidInitialization() {
		try { new ArrayQueue<String>( 1); fail(); }  catch (IllegalArgumentException e) {}
		try { new ArrayQueue<String>( 0); fail(); }  catch (IllegalArgumentException e) {}
		try { new ArrayQueue<String>(-1); fail(); }  catch (IllegalArgumentException e) {}
		try { new ArrayQueue<String>(-7); fail(); }  catch (IllegalArgumentException e) {}
		try { new ArrayQueue<String>(Integer.MIN_VALUE); fail(); }  catch (IllegalArgumentException e) {}
	}
	
	
	@Test
	public void testLengthWithWraparound() {
		ArrayQueue<String> queue = new ArrayQueue<String>(5);
		queue.enqueue("alpha");
		queue.enqueue("beta");
		queue.enqueue("gamma");
		queue.enqueue("delta");
		queue.dequeue();
		queue.enqueue("epsilon");
		queue.dequeue();
		queue.enqueue("zeta");
		queue.dequeue();
		assertEquals(3, queue.length());
	}
	
	
	@Test
	public void testArrayGrowth() {
		ArrayQueue<String> queue = new ArrayQueue<String>(2);
		queue.enqueue("Alpha");
		queue.enqueue("Bravo");
		queue.enqueue("Charlie");
		queue.enqueue("Delta");
		queue.enqueue("Echo");
		queue.enqueue("Foxtrot");
		queue.enqueue("Golf");
		queue.enqueue("Hotel");
		assertEquals("Alpha", queue.dequeue());
		assertEquals("Bravo", queue.dequeue());
		assertEquals("Charlie", queue.dequeue());
		assertEquals("Delta", queue.dequeue());
		assertEquals("Echo", queue.dequeue());
		assertEquals("Foxtrot", queue.dequeue());
		assertEquals("Golf", queue.dequeue());
		queue.enqueue("India");
		queue.enqueue("Juliet");
		assertEquals("Hotel", queue.dequeue());
		assertEquals("India", queue.dequeue());
		assertEquals("Juliet", queue.dequeue());
	}
	
}