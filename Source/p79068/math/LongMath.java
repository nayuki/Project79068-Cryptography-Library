package p79068.math;


/**
Contains methods for math functions that deal with long integers.
<p>Instantiability: <em>Not applicable</em></p>
*/
public final class LongMath{

 /** Returns <code>x</code> modulo <code>y</code>. The result either has the same sign as <code>y</code> or is zero. This is not exactly the same as the remainder operator (<code>%</code>) provided by the language. */
 public static long mod(long x,long y){
  return (x%y+y)%y;}

 public static long divideAndFloor(long x,long y){
  if(!((x>=0)^(y>=0)))return x/y; // If both have the same sign
  else{
   long z=x/y;
   if(z*y==x)return z;
   else return z-1;}}

 /**
 Tests whether the specified integer is a power of 2. The powers of 2 are 1, 2, 4, ..., 4611686018427387904.
 <p>Note that Long.MIN_VALUE (-9223372036854775808) is not a power of 2 because it is negative.</p>

 @param x the integer to test
 @return <samp>true</samp> if x is positive and is a power of 2
 */
 public static boolean isPowerOf2(long x){
  return x>0&&(x&(x-1))==0;}

 /**
 Compares two integers without overflowing.
 @return <samp>-1</samp> if <code>x &lt; y</code>, <samp>0</samp> if <code>x == y</code>, or <samp>1</samp> if <code>x &gt; y</code>
 */
 public static int compare(long x,long y){
  if(x<y)return -1;
  else if(x>y)return 1;
  else return 0;}

 /**
 Compares two unsigned integers without overflowing.
 @param x an operand, interpreted as an unsigned 64-bit integer
 @param y an operand, interpreted as an unsigned 64-bit integer
 @return <samp>-1</samp> if <code>x &lt; y</code>, <samp>0</samp> if <code>x == y</code>, or <samp>1</samp> if <code>x &gt; y</code>
 */
 public static int compareUnsigned(long x,long y){
  return compare(x^(1L<<63),y^(1L<<63));} // Flip top bits


 private LongMath(){}}