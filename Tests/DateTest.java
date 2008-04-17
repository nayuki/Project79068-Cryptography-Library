/*
 * Does not test the corner cases, where overflow may be possible (due to bugs - by design, no overflow problems should occur).
 */


import static org.junit.Assert.assertEquals;
import org.junit.Test;
import p79068.util.Date;


public class DateTest {
	
	static int[] monthLength = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; // In a non-leap year
	
	static int monthLength(int y, int m) {
		if (Date.isLeapYear(y) && m == 2)
			return 29; // We first assume that Date.isLeapYear() is correct
		else
			return monthLength[m - 1];
	}
	
	
	@Test
	public void isLeapYear() {
		int y = -4000;
		int i = 0; // Internal year counter for calculating leap years
		while (y <= 6000) {
			assertEquals(Date.isLeapYear(y), (i % 4 == 0 && i % 100 != 0 || i % 400 == 0));
			y++;
			i = (i + 1) % 400;
		}
	}
	
	@Test
	public void dayOfWeek() {
		int y = -2000;
		int m = 1;
		int d = 1;
		int i = 6; // Internal day of week counter. Note that 2000-01-01 is a Saturday (6). This day plus or minus any multiple of 400 years has the same day of week (e.g. 1600-01-01).
		while (y <= 6000) {
			assertEquals(Date.dayOfWeek(y, m, d), i);
			d++;
			if (d > monthLength(y, m)) {
				m++;
				d = 1;
				if (m > 12) {
					y++;
					m = 1;
				}
			}
			i = (i + 1) % 7;
		}
	}
	
	@Test
	public void getDaysSinceEpochForwards() {
		int y = 2000;
		int m = 1;
		int d = 1;
		int i = 0; // Internal days since epoch counter. Remember that 2000-01-01 is the epoch, i.e. day 0.
		while (y <= 6000) {
			assertEquals(Date.daysSinceEpoch(y, m, d), i);
			d++;
			if (d > monthLength(y, m)) {
				m++;
				d = 1;
				if (m > 12) {
					y++;
					m = 1;
				}
			}
			i++;
		}
	}
	
	@Test
	public void getDaysSinceEpochBackwards() {
		int y = 2000;
		int m = 1;
		int d = 1;
		int i = 0; // Internal days since epoch counter
		while (y >= -2000) {
			assertEquals(Date.daysSinceEpoch(y, m, d), i);
			d--;
			if (d <= 0) {
				m--;
				if (m <= 0) {
					y--;
					m = 12;
				}
				d = monthLength(y, m);
			}
			i--;
		}
	}
	
	// Days since epoch to calendar date, forwards
	@Test
	public void toCalendarDateForwards() {
		int i = 0;
		int y = 2000; // Internal calendar date counters
		int m = 1;
		int d = 1;
		while (y <= 6000) {
			Date date = new Date(i);
			assertEquals(date.getYear(), y);
			assertEquals(date.getMonth(), m);
			assertEquals(date.getDay(), d);
			d++;
			if (d > monthLength(y, m)) {
				m++;
				d = 1;
				if (m > 12) {
					y++;
					m = 1;
				}
			}
			i++;
		}
	}
	
	// Days since epoch to calendar date, backwards
	@Test
	public void toCalendarDateBackwards() {
		int i = 0;
		int y = 2000; // Internal calendar date counters
		int m = 1;
		int d = 1;
		while (y >= -2000) {
			Date date = new Date(i);
			assertEquals(date.getYear(), y);
			assertEquals(date.getMonth(), m);
			assertEquals(date.getDay(), d);
			d--;
			if (d <= 0) {
				m--;
				if (m <= 0) {
					y--;
					m = 12;
				}
				d = monthLength(y, m);
			}
			i--;
		}
	}
	
	// new Date(y,m,d), because it requires a conversion to epoch days and then back to calendar date.
	@Test
	public void newDate() {
		int y = -2000;
		int m = 1;
		int d = 1;
		while (y <= 6000) {
			Date date = new Date(y, m, d);
			assertEquals(date.getYear(), y);
			assertEquals(date.getMonth(), m);
			assertEquals(date.getDay(), d);
			d++;
			if (d > monthLength(y, m)) {
				m++;
				d = 1;
				if (m > 12) {
					y++;
					m = 1;
				}
			}
		}
	}
	
	@Test
	public void edgeCases() {
		{
			Date date = new Date(-2147483648);
			assertEquals(date.getYear(), -5877611);
			assertEquals(date.getMonth(), 6);
			assertEquals(date.getDay(), 22);
		}
		{
			Date date = new Date(2147483647);
			assertEquals(date.getYear(), 5881610);
			assertEquals(date.getMonth(), 7);
			assertEquals(date.getDay(), 11);
		}
	}
	
	@Test
	public void lenientRepresentations() {
		{
			int tp = Date.daysSinceEpoch(2000, 1, 1);
			int y = 2000 + 1000;
			int m = 1 - 1000 * 12;
			while (y >= 2000 - 1000) {
				assertEquals(Date.daysSinceEpoch(y, m, 1), tp);
				y--;
				m += 12;
			}
		}
		{
			int tp = Date.daysSinceEpoch(2000, 1, 1);
			int d = 1 - 200000;
			while (d <= 1 + 200000) {
				assertEquals(d - 1, Date.daysSinceEpoch(2000, 1, d));
				tp++;
				d++;
			}
		}
	}
}