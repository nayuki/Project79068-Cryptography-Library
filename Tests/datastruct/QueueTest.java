package datastruct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import p79068.datastruct.Queue;
import p79068.util.Random;


public abstract class QueueTest {
	
	protected abstract <E> Queue<E> newQueue();
	
	
	@Test
	public void testEnqueueDequeue() {
		Queue<String> queue = newQueue();
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
	public void testInvalidDequeue0() {
		Queue<String> queue = newQueue();
		queue.dequeue();
	}
	
	
	@Test(expected = IllegalStateException.class)
	public void testInvalidDequeue1() {
		Queue<String> queue;
		try {
			queue = newQueue();
			queue.enqueue("hello");
			queue.enqueue("world");
			queue.dequeue();
			queue.dequeue();
		} catch (IllegalStateException e) {
			fail();
			return;
		}
		queue.dequeue();
	}
	
	
	@Test
	public void testPeek() {
		Queue<String> queue = newQueue();
		queue.enqueue("zeroth");
		assertEquals("zeroth", queue.peek());
		queue.enqueue("first");
		assertEquals("zeroth", queue.peek());
	}
	
	
	@Test(expected = IllegalStateException.class)
	public void testInvalidPeek0() {
		Queue<String> queue = newQueue();
		queue.peek();
	}
	
	
	@Test(expected = IllegalStateException.class)
	public void testInvalidPeek1() {
		Queue<String> queue;
		try {
			queue = newQueue();
			queue.enqueue("hello");
			queue.enqueue("world");
			queue.dequeue();
			queue.dequeue();
		} catch(IllegalStateException e) {
			fail();
			return;
		}
		queue.peek();
	}
	
	
	@Test
	public void testLength() {
		Queue<String> queue = newQueue();
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
		Queue<String> queue = newQueue();
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
		Queue<String> queue0 = newQueue();
		queue0.enqueue("qwerty");
		queue0.enqueue("uiop");
		Queue<String> queue1 = queue0.clone();
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
		Queue<Integer> queue = newQueue();
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