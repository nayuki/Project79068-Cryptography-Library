/*
Delta Transform

Except byte 0, each byte becomes {itself minus the previous byte}.
*/


package p79068.compress;


public class DeltaTransform{

 // Operates in-place.
 public static void transform(byte[] b){
  transform(b,0,b.length);}

 public static void transform(byte[] b,int off,int len){
  // Less confusing version: for(int i=off+len-1;i>=off+1;i--)b[i]-=b[i-1];
  for(len+=off-1,off++;len>=off;len--)b[len]-=b[len-1];} // len becomes end; count backwards using len as the counter.


 public static void transformInverse(byte[] b){
  transformInverse(b,0,b.length);}

 public static void transformInverse(byte[] b,int off,int len){
  for(len+=off,off++;off<len;off++)b[off]+=b[off-1];}}