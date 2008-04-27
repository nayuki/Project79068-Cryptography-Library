package datastruct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import p79068.datastruct.ArrayQueue;
import p79068.util.Random;


public class QueueTest {
	
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
	
	
	@Test(expected = IllegalStateException.class)
	public void testInvalidDequeue() {
		ArrayQueue<String> queue = new ArrayQueue<String>();
		queue.dequeue();
	}
	
	
	@Test
	public void testPeek() {
		ArrayQueue<String> queue = new ArrayQueue<String>(4);
		queue.enqueue("zeroth");
		assertEquals("zeroth", queue.peek());
		queue.enqueue("first");
		assertEquals("zeroth", queue.peek());
	}
	
	
	@Test(expected = IllegalStateException.class)
	public void testInvalidPeek() {
		ArrayQueue<String> queue = new ArrayQueue<String>();
		queue.peek();
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
	public void testEnqueueDequeueRandomly() {
		ArrayQueue<Integer> queue = new ArrayQueue<Integer>();
		int enqueued = 0;
		int dequeued = 0;
		for (int i = 0; i < 10000; i++) {
			assertEquals(queue.length(), enqueued - dequeued);
			if (Random.DEFAULT.randomBoolean()) {
				queue.enqueue(enqueued);
				enqueued++;
			} else {
				if (!queue.isEmpty()) {
					assertEquals(dequeued, queue.dequeue());
					dequeued++;
				}
			}
		}
	}
	
}