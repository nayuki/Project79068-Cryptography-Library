package util;


import static org.junit.Assert.*;
import org.junit.Test;
import p79068.util.Interner;

public class InternerTest {
	
	@Test
	public void testIntern() {
		Integer obj0 = new Integer(7);
		Integer obj1 = new Integer(7);
		Integer obj2 = new Integer(7);
		assertNotSame(obj0, obj1);  // Guaranteed by the Java language
		assertNotSame(obj1, obj2);
		assertNotSame(obj2, obj0);
		
		Interner<Integer> interner = new Interner<Integer>();
		obj0 = interner.intern(obj0);
		obj1 = interner.intern(obj1);
		obj2 = interner.intern(obj2);
		
		assertSame(obj0, obj1);
		assertSame(obj1, obj2);
		assertSame(obj2, obj0);
		
		assertEquals(new Integer(7), obj0);
	}
	
}