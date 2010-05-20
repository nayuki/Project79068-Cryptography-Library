package p79068.lang;

import static org.junit.Assert.fail;
import org.junit.Test;


public final class BoundsCheckerTest {
	
	@Test
	public void testCheckIndexValid() {
		try {
			BoundsChecker.check(1, 0);
			BoundsChecker.check(3, 0);
			BoundsChecker.check(3, 1);
			BoundsChecker.check(3, 2);
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
	}
	
	
	@Test
	public void testCheckIndexInvalid() {
		int[][] cases = {
				{0, 0},
				{3, 3},
				{4, 5},
				{4, 11},
				{719, 846},
				{Integer.MAX_VALUE - 1, Integer.MAX_VALUE}
		};
		
		for (int[] thecase : cases) {
			try {
				BoundsChecker.check(thecase[0], thecase[1]);
				fail(String.format("%d %d", thecase[0], thecase[1]));
			} catch (IndexOutOfBoundsException e) {}
		}
	}
	
	
	@Test
	public void testCheckIndexNegativeIndex() {
		int[][] cases = {
				{0, -1},
				{0, Integer.MIN_VALUE},
				{100, Integer.MIN_VALUE},
				{Integer.MAX_VALUE, Integer.MIN_VALUE}
		};
		
		for (int[] thecase : cases) {
			try {
				BoundsChecker.check(thecase[0], thecase[1]);
				fail(String.format("%d %d", thecase[0], thecase[1]));
			} catch (IndexOutOfBoundsException e) {}
		}
	}
	
	
	@Test
	public void testCheckIndexNegativeArrayLength() {
		int[][] cases = {
				{-1, 0},
				{Integer.MIN_VALUE, 23},
				{Integer.MIN_VALUE + 1, -45},
				{Integer.MIN_VALUE / 2, 0}
		};
		
		for (int[] thecase : cases) {
			try {
				BoundsChecker.check(thecase[0], thecase[1]);
				fail(String.format("%d %d", thecase[0], thecase[1]));
			} catch (IllegalArgumentException e) {}
		}
	}
	
	
	
	@Test
	public void testCheckRangeValid() {
		try {
			BoundsChecker.check(0, 0, 0);
			BoundsChecker.check(1, 0, 0);
			BoundsChecker.check(1, 0, 1);
			BoundsChecker.check(1, 1, 0);
			BoundsChecker.check(17, 5, 6);
			BoundsChecker.check(17, 5, 12);
			BoundsChecker.check(17, 0, 17);
			BoundsChecker.check(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
			BoundsChecker.check(Integer.MAX_VALUE, 31, Integer.MAX_VALUE - 31);
			BoundsChecker.check(Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
	}
	
	
	@Test
	public void testCheckRangeInvalid() {
		int[][] cases = {
				{0, 1, 0},
				{0, 1, 2},
				{5, 3, 3},
				{5, 5, 4},
				{5, 8, 0},
				{5, -2, 7},
				{5, -3, 4},
				{5, 2, -1},
				{0, Integer.MAX_VALUE, Integer.MAX_VALUE},
				{Integer.MAX_VALUE, 1, Integer.MAX_VALUE},
				{Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE},
				{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}
		};
		
		for (int[] thecase : cases) {
			try {
				BoundsChecker.check(thecase[0], thecase[1], thecase[2]);
				fail(String.format("%d %d %d", thecase[0], thecase[1], thecase[2]));
			} catch (IndexOutOfBoundsException e) {}
		}
	}
	
	
	@Test
	public void testCheckRangeNegativeArrayLength() {
		int[][] cases = {
				{-1, 0, 0},
				{-3, 0, -2},
				{-3, -1, 2},
				{Integer.MIN_VALUE, 0, Integer.MAX_VALUE},
				{Integer.MIN_VALUE + 1, 0, Integer.MAX_VALUE / 2},
				{Integer.MIN_VALUE / 3, Integer.MIN_VALUE / 4, 100}
		};
		
		for (int[] thecase : cases) {
			try {
				BoundsChecker.check(thecase[0], thecase[1], thecase[2]);
				fail(String.format("%d %d %d", thecase[0], thecase[1], thecase[2]));
			} catch (IllegalArgumentException e) {}
		}
	}
	
}
