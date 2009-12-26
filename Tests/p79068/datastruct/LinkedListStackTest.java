package p79068.datastruct;


public final class LinkedListStackTest extends StackTest {
	
	@Override
	protected <E> Stack<E> newStack() {
		return new LinkedListStack<E>();
	}
	
}