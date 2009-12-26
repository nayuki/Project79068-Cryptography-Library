package p79068.datastruct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;


public final class ArrayStackTest extends StackTest {
	
	protected <E> ArrayStack<E> newStack() {
		return new ArrayStack<E>();
	}
	
	
	@Test
	public void testInitialization() {
		try {
			new ArrayStack<String>();
			new ArrayStack<String>(1);
			new ArrayStack<String>(2);
			new ArrayStack<String>(5);
			new ArrayStack<String>(31);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
	
	@Test
	public void testInvalidInitialization() {
		try { new ArrayStack<String>(  0); fail(); }  catch (IllegalArgumentException e) {}
		try { new ArrayStack<String>(- 1); fail(); }  catch (IllegalArgumentException e) {}
		try { new ArrayStack<String>(-47); fail(); }  catch (IllegalArgumentException e) {}
		try { new ArrayStack<String>(Integer.MIN_VALUE); fail(); }  catch (IllegalArgumentException e) {}
	}
	
	
	@Test
	public void testArrayGrowth() {
		ArrayStack<String> stack = new ArrayStack<String>(1);
		stack.push("Alpha");
		stack.push("Bravo");
		stack.push("Charlie");
		stack.push("Delta");
		stack.push("Echo");
		stack.push("Foxtrot");
		stack.push("Golf");
		stack.push("Hotel");
		assertEquals("Hotel", stack.pop());
		assertEquals("Golf", stack.pop());
		assertEquals("Foxtrot", stack.pop());
		assertEquals("Echo", stack.pop());
		assertEquals("Delta", stack.pop());
		assertEquals("Charlie", stack.pop());
		assertEquals("Bravo", stack.pop());
		stack.push("India");
		stack.push("Juliet");
		assertEquals("Juliet", stack.pop());
		assertEquals("India", stack.pop());
		assertEquals("Alpha", stack.pop());
	}
	
}