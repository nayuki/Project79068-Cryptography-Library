package p79068.lang;

import static org.junit.Assert.fail;
import org.junit.Test;


public class NullCheckerTest {
	
	@Test
	public void testCheckNotNull() {
		try {
			NullChecker.check(new Object());
			NullChecker.check(new String("a"));
			NullChecker.check(new Integer(3));
		} catch (NullPointerException e) {
			fail();
		}
	}
	
	
	@Test(expected = NullPointerException.class)
	public void testCheckNull() {
		NullChecker.check((Object)null);
	}
	
	
	@Test
	public void testCheckVarargNotNull() {
		try {
			NullChecker.check(new Object(), new String("a"), new Integer(3));
		} catch (NullPointerException e) {
			fail();
		}
	}
	
	
	@Test(expected = NullPointerException.class)
	public void testCheckVarargNull() {
		NullChecker.check(new Object(), null);
	}
	
}