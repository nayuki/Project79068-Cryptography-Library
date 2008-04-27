package datastruct;

import p79068.datastruct.LinkedListQueue;
import p79068.datastruct.Queue;


public class LinkedListQueueTest extends QueueTest {
	
	protected <E> Queue<E> newQueue() {
		return new LinkedListQueue<E>();
	}
	
}