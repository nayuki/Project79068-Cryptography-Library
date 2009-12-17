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
		NullChecker.check(null);
	}
	
}