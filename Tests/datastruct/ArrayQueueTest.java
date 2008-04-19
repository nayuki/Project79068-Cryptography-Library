package datastruct;

import static org.junit.Assert.*;
import org.junit.Test;
import p79068.datastruct.ArrayQueue;


public class ArrayQueueTest {
	
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
	public void testEnqueueDequeue() {
		ArrayQueue<String> queue = new ArrayQueue<String>(10);
		queue.enqueue("2");
		queue.enqueue("3");
		assertEquals("2", queue.dequeue());
		assertEquals("3", queue.dequeue());
		queue.enqueue("5");
		queue.enqueue("7");
		queue.enqueue("11");
		assertEquals("5", queue.dequeue());
		queue.enqueue("13");
		queue.enqueue("17");
		assertEquals("7", queue.dequeue());
		assertEquals("11", queue.dequeue());
		queue.enqueue("19");
		queue.enqueue("23");
		queue.enqueue("29");
		assertEquals("13", queue.dequeue());
		assertEquals("17", queue.dequeue());
		assertEquals("19", queue.dequeue());
		assertEquals("23", queue.dequeue());
		assertEquals("29", queue.dequeue());
	}
	
	
	@Test
	public void testInvalidDequeue() {
		ArrayQueue<String> queue = new ArrayQueue<String>();
		try {
			queue.dequeue();
			fail();
		} catch (IllegalStateException e) {}  // Pass
	}
	
	
	@Test
	public void testPeek() {
		ArrayQueue<String> queue = new ArrayQueue<String>(4);
		queue.enqueue("zeroth");
		assertEquals("zeroth", queue.peek());
		queue.enqueue("first");
		assertEquals("zeroth", queue.peek());
	}
	
	
	@Test
	public void testInvalidPeek() {
		ArrayQueue<String> queue = new ArrayQueue<String>();
		try {
			queue.peek();
			fail();
		} catch (IllegalStateException e) {}  // Pass
	}
	
	
	@Test
	public void testLength() {
		ArrayQueue<String> queue = new ArrayQueue<String>(5);
		assertEquals(0, queue.length());
		queue.enqueue("alpha");
		queue.enqueue("beta");
		queue.enqueue("gamma");
		assertEquals(3, queue.length());
		queue.dequeue();
		queue.dequeue();
		queue.dequeue();
		assertEquals(0, queue.length());
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
	public void testIsEmpty() {
		ArrayQueue<String> queue = new ArrayQueue<String>(4);
		assertTrue(queue.isEmpty());
		queue.enqueue("zero");
		queue.enqueue("one");
		assertFalse(queue.isEmpty());
		queue.dequeue();
		queue.dequeue();
		assertTrue(queue.isEmpty());
	}
	
	
	@Test
	public void testClone() {
		ArrayQueue<String> queue0 = new ArrayQueue<String>(7);
		queue0.enqueue("qwerty");
		queue0.enqueue("uiop");
		ArrayQueue<String> queue1 = queue0.clone();
		assertNotSame(queue0, queue1);
		queue0.enqueue("asdf");
		queue0.enqueue("ghjkl");
		assertEquals("qwerty", queue1.dequeue());
		assertEquals("uiop", queue1.dequeue());
		assertTrue(queue1.isEmpty());
		assertEquals("qwerty", queue0.dequeue());
		assertEquals("uiop", queue0.dequeue());
		assertEquals("asdf", queue0.dequeue());
		assertEquals("ghjkl", queue0.dequeue());
		assertTrue(queue0.isEmpty());
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