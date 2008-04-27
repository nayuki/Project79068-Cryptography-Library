package datastruct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import p79068.datastruct.ArrayStack;



public abstract class StackTest {
	
	@Test
	public void testPushPop() {
		ArrayStack<String> stack = new ArrayStack<String>(7);
		stack.push("2");
		stack.push("3");
		assertEquals("3", stack.pop());
		assertEquals("2", stack.pop());
		stack.push("5");
		stack.push("7");
		stack.push("11");
		assertEquals("11", stack.pop());
		stack.push("13");
		stack.push("17");
		assertEquals("17", stack.pop());
		assertEquals("13", stack.pop());
		stack.push("19");
		stack.push("23");
		stack.push("29");
		assertEquals("29", stack.pop());
		assertEquals("23", stack.pop());
		assertEquals("19", stack.pop());
		assertEquals("7", stack.pop());
		assertEquals("5", stack.pop());
	}
	
	@Test
	public void testInvalidPop() {
		ArrayStack<String> stack = new ArrayStack<String>(2);
		try {
			stack.pop();
			fail();
		} catch (IllegalStateException e) {}  // Pass
	}
	
	@Test
	public void testPeek() {
		ArrayStack<String> stack = new ArrayStack<String>(5);
		stack.push("zeroth");
		assertEquals("zeroth", stack.peek());
		stack.push("first");
		assertEquals("first", stack.peek());
		stack.push("second");
		assertEquals("second", stack.peek());
	}
	
	@Test
	public void testInvalidPeek() {
		ArrayStack<String> stack = new ArrayStack<String>(2);
		try {
			stack.peek();
			fail();
		} catch (IllegalStateException e) {}  // Pass
	}
	
	@Test
	public void testHeight() {
		ArrayStack<String> stack = new ArrayStack<String>(5);
		assertEquals(0, stack.height());
		stack.push("alpha");
		stack.push("beta");
		stack.push("gamma");
		assertEquals(3, stack.height());
		stack.pop();
		stack.pop();
		stack.pop();
		assertEquals(0, stack.height());
	}
	
	@Test
	public void testIsEmpty() {
		ArrayStack<String> stack = new ArrayStack<String>(3);
		assertTrue(stack.isEmpty());
		stack.push("zero");
		stack.push("one");
		assertFalse(stack.isEmpty());
		stack.pop();
		stack.pop();
		assertTrue(stack.isEmpty());
	}
	
	@Test
	public void testClone() {
		ArrayStack<String> stack0 = new ArrayStack<String>(6);
		stack0.push("qwerty");
		stack0.push("uiop");
		ArrayStack<String> stack1 = stack0.clone();
		assertNotSame(stack0, stack1);
		stack0.push("asdf");
		stack0.push("ghjkl");
		assertEquals("uiop", stack1.pop());
		assertEquals("qwerty", stack1.pop());
		assertTrue(stack1.isEmpty());
		assertEquals("ghjkl", stack0.pop());
		assertEquals("asdf", stack0.pop());
		assertEquals("uiop", stack0.pop());
		assertEquals("qwerty", stack0.pop());
		assertTrue(stack0.isEmpty());
	}
	
}