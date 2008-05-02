package lang;

import static org.junit.Assert.*;
import org.junit.Test;
import p79068.lang.NullChecker;


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
		NullChecker.check(null);
	}
	
}