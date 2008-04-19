package datastruct;

import static org.junit.Assert.*;
import org.junit.Test;
import p79068.datastruct.ArrayStack;


public class ArrayStackTest {
	
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