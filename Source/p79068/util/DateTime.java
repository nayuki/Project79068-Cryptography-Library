package p79068.util;

import p79068.math.ArithmeticOverflowException;
import p79068.math.Int128;
import p79068.math.LongMath;


/**
An instance of this class represents a date and time using the Gregorian calendar and UTC time zone, with microsecond precision. The epoch of 2000-01-01 00:00:00 UTC is used.
<p>Mutability: <em>Immutable</em></p>
<p>See Date for the numerical representations of year, month, and day.</p>
<p>The earliest representable date and time is year &minus;290278, month 12, day 22, time 19:59:05.224192 UTC.<br>
 The latest representable date and time is year 294277, month 01, day 09, time 04:00:54.775807 UTC.</p>
<p>Leap seconds are not taken into account.</p>
<p>This implementation runs in O(1) time for all inputs.</p>
<p>Note that Date can represent dates beyond DateTime's range, both before and after.</p>
*/
public final class DateTime implements Comparable<DateTime>{

 /**
 Returns the number of microseconds after the epoch that the specified date and time represents.
 @throws ArithmeticOverflowException if the result does not fit in a <code>long</code>
 */
 public static long microsSinceEpoch(int year,int month,int day){
  return microsSinceEpoch(year,month,day,0,0,0);}

 /**
 Returns the number of microseconds after the epoch that the specified date and time represents.
 @throws ArithmeticOverflowException if the result does not fit in a <code>long</code>
 */
 public static long microsSinceEpoch(int year,int month,int day,int hour,int minute,int second){
  return microsSinceEpoch(year,month,day,hour,minute,second,0);}

 /**
 Returns the number of microseconds after the epoch that the specified date and time represents.
 @throws ArithmeticOverflowException if the result does not fit in a <code>long</code>
 */
 public static long microsSinceEpoch(int year,int month,int day,int hour,int minute,int second,int microsecond){
  Int128 temp=new Int128(Date.daysSinceEpoch(year,month,day)).multiply(new Int128(86400000000L));
  temp=temp.add(new Int128(hour*3600000000L+minute*60000000L+second*1000000L+microsecond)); // The inner calculation doesn't overflow, but it's rather close to doing so.
  if(temp.compareTo(new Int128(Long.MIN_VALUE))<0||temp.compareTo(new Int128(Long.MAX_VALUE))>0)throw new ArithmeticOverflowException();
  return temp.low;}


 private int year;
 private int month;
 private int day;
 private int dayOfWeek;

 private int hour;
 private int minute;
 private int second;
 private int microsecond;

 private long microsSinceEpoch;


 public DateTime(){
  this((System.currentTimeMillis()-946684800000L)*1000);}

 /** Constructs a date-time object representing midnight on the specified date. */
 public DateTime(Date date){
  this(microsSinceEpoch(date.getYear(),date.getMonth(),date.getDay()));}

 /** Constructs a date-time object representing midnight on the specified date. */
 public DateTime(int year,int month,int day){
  this(microsSinceEpoch(year,month,day));}

 /** Constructs a date-time object representing the specified date and time. */
 public DateTime(int year,int month,int day,int hour,int minute,int second){
  this(microsSinceEpoch(year,month,day,hour,minute,second));}

 /** Constructs a date-time object representing the specified date and time. */
 public DateTime(int year,int month,int day,int hour,int minute,int second,int microsecond){
  this(microsSinceEpoch(year,month,day,hour,minute,second,microsecond));}

 /** Constructs a date-time object representing the specified date and time. */
 public DateTime(long microsSinceEpoch){
  this.microsSinceEpoch=microsSinceEpoch;
  long temp=microsSinceEpoch;
  microsecond=(int)LongMath.mod(temp,1000000);
  temp=LongMath.divideAndFloor(temp,1000000);
  second=(int)LongMath.mod(temp,60);
  temp=LongMath.divideAndFloor(temp,60);
  minute=(int)LongMath.mod(temp,60);
  temp=LongMath.divideAndFloor(temp,60);
  hour=(int)LongMath.mod(temp,24);
  temp=LongMath.divideAndFloor(temp,24);
  Date tempdate=new Date((int)temp); // temp is now equal to the number of days since the epoch. It is in [106751992,106751992).
  year=tempdate.getYear();
  month=tempdate.getMonth();
  day=tempdate.getDay();
  dayOfWeek=tempdate.getDayOfWeek();}


 /** Returns the year of this date-time object. */
 public int getYear(){
  return year;}

 /** Returns the month of this date-time object. */
 public int getMonth(){
  return month;}

 /** Returns the day of month of this date-time object. */
 public int getDay(){
  return day;}

 /** Returns the day of week of this date-time object. */
 public int getDayOfWeek(){
  return dayOfWeek;}

 /** Returns the hour of this date-time object. */
 public int getHour(){
  return hour;}

 /** Returns the minute of this date-time object. */
 public int getMinute(){
  return minute;}

 /** Returns the second of this date-time object. */
 public int getSecond(){
  return second;}

 /** Returns the microsecond of this date-time object. */
 public int getMicrosecond(){
  return microsecond;}

 /** Returns the number of microseconds since the epoch, of this date-time object. */
 public long getMicrosSinceEpoch(){
  return microsSinceEpoch;}


 /** Tests for equality with the specified object. */
 public boolean equals(Object obj){
  return obj instanceof DateTime&&microsSinceEpoch==((DateTime)obj).microsSinceEpoch;}

 /** Comparse this date-time with the specified date-time. */
 public int compareTo(DateTime obj){
  return LongMath.compare(microsSinceEpoch,obj.microsSinceEpoch);}

 public int hashCode(){
  return HashCoder.newInstance().add(microsSinceEpoch).getHashCode();}


 /** Returns the date-time representing this date-time plus the specified number of microseconds. */
 public DateTime add(long microseconds){
  Int128 temp=new Int128(microsSinceEpoch).add(new Int128(microseconds));
  if(temp.compareTo(new Int128(Long.MIN_VALUE))<0||temp.compareTo(new Int128(Long.MAX_VALUE))>0)throw new ArithmeticOverflowException();
  return new DateTime(temp.low);}

 /** Returns the difference between this date-time and the specified date-time, in microseconds. */
 public long subtract(DateTime date){
  Int128 temp=new Int128(microsSinceEpoch).subtract(new Int128(date.microsSinceEpoch));
  if(temp.compareTo(new Int128(Long.MIN_VALUE))<0||temp.compareTo(new Int128(Long.MAX_VALUE))>0)throw new ArithmeticOverflowException();
  return temp.low;}

 /** Returns this date as a string: e.g., <code><var>yyyy</var>-<var>mm</var>-<var>dd</var> <var>HH</var>:<var>MM</var>:<var>SS</var>.<var>SSSSSS</var> UTC</code>. This format is subject to change in the future. */
 public String toString(){
  return String.format("%04d-%02d-%02d %02d:%02d:%02d.%06d UTC",year,month,day,hour,minute,second,microsecond);}

 /** Returns the date of this object as a Date object. */
 public Date toDate(){
  return new Date((int)LongMath.divideAndFloor(microsSinceEpoch,86400000000L));}}