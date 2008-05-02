package p79068.util;

import p79068.math.ArithmeticOverflowException;
import p79068.math.IntegerMath;
import p79068.math.LongMath;


/**
 * An instance of this class represents a date on the Gregorian calendar. The epoch of 2000-01-01 is used.
 * <p>Mutability: <em>Immutable</em></p>
 * <p>Years in CE/AD are represented normally. Years in BCE/BC are count down from zero: <code>0</code> is year 1 BCE, <code>-1</code> is year 2 BCE, <code>-2</code> is year 3 BCE, and so on.</p>
 * <p>Months:</p>
 * <ul>
 *  <li><code>01</code>: January</li>
 *  <li><code>02</code>: February</li>
 *  <li><code>03</code>: March</li>
 *  <li><code>04</code>: April</li>
 *  <li><code>05</code>: May</li>
 *  <li><code>06</code>: June</li>
 *  <li><code>07</code>: July</li>
 *  <li><code>08</code>: August</li>
 *  <li><code>09</code>: September</li>
 *  <li><code>10</code>: October</li>
 *  <li><code>11</code>: November</li>
 *  <li><code>12</code>: December</li>
 * </ul>
 * <p>Days of the week:</p>
 * <ul>
 *  <li><code>0</code>: Sunday</li>
 *  <li><code>1</code>: Monday</li>
 *  <li><code>2</code>: Tuesday</li>
 *  <li><code>3</code>: Wednesday</li>
 *  <li><code>4</code>: Thursday</li>
 *  <li><code>5</code>: Friday</li>
 *  <li><code>6</code>: Saturday</li>
 * </ul>
 * <p>The earliest representable date is year &minus;5877611, month 06, day 22.<br>
 *  The latest representable date is year 5881610, month 07, day 11.</p>
 * <p>All methods run in <var>O</var>(1) time for all inputs.</p>
 */
public final class Date implements Comparable<Date> {
	
	/**
	 * Tests whether the specified year is a leap year.
	 * <p>A year divisible by 400 is a leap year. A year divisible by 4 but not by 100 is a leap year.</p>
	 * @param year the year to test
	 * @return <code>true</code> if and only if <code>year</code> is a leap year
	 */
	public static boolean isLeapYear(int year) {
		return (IntegerMath.mod(year, 4) == 0 && IntegerMath.mod(year, 100) != 0) || IntegerMath.mod(year, 400) == 0;
	}
	
	
	/**
	 * Returns the day of week of the specified date. This method returns the correct result for all arguments.
	 * @param year the year
	 * @param month the month
	 * @param day the day of month
	 * @return the day of week, with <code>0</code> = Sunday, <code>1</code> = Monday, ..., <code>6</code> = Saturday
	 */
	public static int dayOfWeek(int year, int month, int day) {  // See Zeller's congruence.
		year = IntegerMath.mod(year, 400);                 // Reduce year to [0,400).
		month = (int)LongMath.mod((long)month - 3, 4800);  // Convert month from 1 = January to 0 = March, and reduce to [0,4800). (i.e. 400 years)
		year += month / 12;                                // Reduce month into year. Year is in [0,799).
		month %= 12;                                       // Reduce month to [0,12).
		day = IntegerMath.mod(day, 7);                     // Reduce day to [0,7).
		return (year + year/4 - year/100 + year/400 + (13*month+2)/5 + day + 2) % 7;  // Note: The first argument of the remainder operator is in [2,1029).
	}
	
	
	/**
	 * Returns the day of week of the specified date. This method returns the correct result for all arguments.
	 * @param daysSinceEpoch the number of days since the epoch of 2000-01-01
	 * @return the day of week, with <code>0</code> = Sunday, <code>1</code> = Monday, ..., <code>6</code> = Saturday
	 */
	public static int dayOfWeek(int daysSinceEpoch) {
		return (int)LongMath.mod((long)daysSinceEpoch + 6, 7);  // 2000-01-01 is a Saturday.
	}
	
	
	/**
	 * Returns the number of days after the epoch that the specified date represents.
	 * @throws ArithmeticOverflowException if the result does not fit in an <code>int</code>
	 */
	public static int daysSinceEpoch(int year, int month, int day) {
		long y = year;
		long m = month;
		long d = day;
		m -= 3;                                         // Convert month from 1, January-based to 0, March-based. January and February are considered to belong to the "previous" year.
		y += LongMath.divideAndFloor(m, 12);            // Reduce month into year.
		m -= LongMath.divideAndFloor(m, 12) * 12;       // Reduce month to [0,12).
		d--;                                            // Convert from 1-based day to 0-based day and use d as the accumulator.
		d += LongMath.divideAndFloor(y, 400) * 146097;  // Reduce year into day. There are 146097 days in any contiguous 400 years.
		y -= LongMath.divideAndFloor(y, 400) * 400;     // Reduce year to [0,400).
		d += y * 365 + y / 4 - y / 100 + y / 400 + cumulativeDays[(int)m];  // Add the days in the years (taking leap years into account) and months.
		d -= 730425;                                    // Convert epoch from 0000-03-01 to 2000-01-01.
		if (d < Integer.MIN_VALUE || d > Integer.MAX_VALUE)
			throw new ArithmeticOverflowException();
		return (int)d;
	}
	
	
	private int year;
	private int month;
	private int day;
	private int dayOfWeek;
	private int daysSinceEpoch;
	
	
	/**
	 * Constructs a date at the specified year, month, and day.
	 * @param year the year
	 * @param month the month
	 * @param day the day of month
	 * @throws ArithmeticOverflowException if the date cannot be represented
	 */
	public Date(int year, int month, int day) {
		this(daysSinceEpoch(year, month, day));
	}
	
	/**
	 * Constructs a date at the specified number of days after the epoch (2000-01-01). The epoch (January 1, 2000) is day 0.
	 * <p>Note: Epochs used in other systems, represented in this system:</p>
	 * <ul>
	 *  <li>1601-01-01 = day <code>-145731</code></li>
	 *  <li>1900-01-01 = day <code>-36524</code></li>
	 *  <li>1970-01-01 = day <code>-10957</code></li>
	 *  <li>1980-01-01 = day <code>-7305</code></li>
	 * </ul>
	 */
	public Date(int daysSinceEpoch) {
		this.daysSinceEpoch = daysSinceEpoch;
		dayOfWeek = dayOfWeek(daysSinceEpoch);
		long d = (long)daysSinceEpoch + 730425;                // Convert epoch from 2000-01-01 to 0000-03-01.
		year = (int)LongMath.divideAndFloor(d, 146097) * 400;  // Reduce d into year.
		d -= LongMath.divideAndFloor(d, 146097) * 146097;      // Reduce d to [0,146097).
		day = (int)d;
		// Calculate the year, taking leap years into account. Valid only for d in [0,146097).
		year += Math.min(day / 36524, 3) * 100;                // There are *usually* 36524 days in 100 years.
		day -= Math.min(day / 36524, 3) * 36524;               // Reduce d to *approximately* [0, 100 years).
		year += day / 1461 * 4;                                // There are *usually* 1461 days in 4 years.
		day -= day / 1461 * 1461;                              // Reduce d to *approximately* [0, 4 years).
		year += Math.min(day / 365, 3);                        // There are *usually* 365 days in 1 year.
		day -= Math.min(day / 365, 3) * 365;                   // Reduce d to [0,366).
		for (month = 0; month < 11 && cumulativeDays[month + 1] <= day; month++);  // Find the month that the day belongs in.
		day = day - cumulativeDays[month] + 1;                 // Convert to day of month, 1-based.
		month += 3;                                            // Convert from 0, March-based to 1, January-based.
		if (month > 12) {                                      // Reduce month to [1,13).
			year++;
			month -= 12;
		}
	}
	
	
	/**
	 * Returns the year of this date.
	 * @return the year of this date
	 */
	public int getYear() {
		return year;
	}
	
	
	/**
	 * Returns the month of this date.
	 * @return the month of this date
	 */
	public int getMonth() {
		return month;
	}
	
	
	/**
	 * Returns the day of month of this date.
	 * @return the day of month of this date
	 */
	public int getDay() {
		return day;
	}
	
	
	/**
	 * Returns the day of week of this date.
	 * @return the day of week of this date
	 */
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	
	
	/**
	 * Returns the number of days since the epoch, of this date.
	 * @return the number of days since the epoch, of this date
	 */
	public int getDaysSinceEpoch() {
		return daysSinceEpoch;
	}
	
	
	
	/**
	 * Returns a date representing this date plus the specified number of days. Addition and subtraction are related by the equation <code>this.add(x).subtract(this) == x</code> (for <code>int x</code>), assuming no overflow.
	 * @param days the number of days to add
	 * @return a date representing <code>days</code> days after this date
	 * @throws ArithmeticOverflowException if the resulting cannot be represented
	 */
	public Date add(int days) {
		long temp = (long)daysSinceEpoch + days;
		if (temp < Integer.MIN_VALUE || temp > Integer.MAX_VALUE)
			throw new ArithmeticOverflowException();
		return new Date((int)temp);
	}
	
	
	/**
	 * Returns the signed difference between this date and the specified date, in days. Addition and subtraction are related by the equation <code>this.add(x).subtract(this) == x</code> (for <code>int x</code>), assuming no overflow.
	 * @param date the date to subtract
	 * @return the number of days to add to <code>date</code> in order to get <code>this</code>
	 * @throws ArithmeticOverflowException if the resulting difference cannot be represented
	 */
	public int subtract(Date date) {
		long temp = (long)daysSinceEpoch - date.daysSinceEpoch;
		if (temp < Integer.MIN_VALUE || temp > Integer.MAX_VALUE)
			throw new ArithmeticOverflowException();
		return (int)temp;
	}
	
	
	
	/**
	 * Tests for equality with the specified object.
	 */
	public boolean equals(Object other) {
		if (other == this)
			return true;
		else if (!(other instanceof Date))
			return false;
		else {
			Date date = (Date)other;
			return daysSinceEpoch == date.daysSinceEpoch;
			// With 'daysSinceEpoch' being equal, all 4 other fields (year, month, day, dayOfWeek) are equal too.
		}
	}
	
	
	/**
	 * Compares this date with the specified date.
	 */
	public int compareTo(Date other) {
		return IntegerMath.compare(daysSinceEpoch, other.daysSinceEpoch);
	}
	
	
	/**
	 * Returns the hash code for this date.
	 */
	public int hashCode() {
		return daysSinceEpoch;
	}
	
	
	/**
	 * Returns this date as a string: e.g., <code><var>yyyy</var>-<var>mm</var>-<var>dd</var></code>. This format is subject to change.
	 */
	public String toString() {
		return String.format("%04d-%02d-%02d", year, month, day);
	}
	
	
	
	// The cumulative number of days since March 1st.
	// 0 = March 1st, 1 = April 1st, ..., 11 = February 1st of next year
	private static final int[] cumulativeDays = {0, 31, 61, 92, 122, 153, 184, 214, 245, 275, 306, 337};
	
}