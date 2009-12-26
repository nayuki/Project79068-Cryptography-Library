package p79068.datastruct;


public final class LinkedListQueueTest extends QueueTest {
	
	protected <E> Queue<E> newQueue() {
		return new LinkedListQueue<E>();
	}
	
}