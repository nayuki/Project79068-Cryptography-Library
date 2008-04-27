package datastruct;

import p79068.datastruct.LinkedListStack;
import p79068.datastruct.Stack;


public class LinkedListStackTest extends StackTest {
	
	protected <E> Stack<E> newStack() {
		return new LinkedListStack<E>();
	}
	
}