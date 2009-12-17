package p79068.datastruct;


public class LinkedListQueueTest extends QueueTest {
	
	protected <E> Queue<E> newQueue() {
		return new LinkedListQueue<E>();
	}
	
}