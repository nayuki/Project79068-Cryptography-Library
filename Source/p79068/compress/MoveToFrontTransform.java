/*
Move To Front Transform

Start with the list {0, 1, ..., 254, 255}.
For each character encountered, output its index on the list and them move it to the front of the list.
e.g. If the list is {0,1,2,3} and "2" is input, then the index "2" is output and the list becomes {2,0,1,3}.

This transform produces values near zero when the input has clusters of a small subset of the alphabet.
*/


package p79068.compress;


public class MoveToFrontTransform{

 // Operates in-place.
 public static void transform(byte[] b){
  transform(b,0,b.length);}

 public static void transform(byte[] b,int off,int len){
  int[] val=new int[256]; // Maps the index of x to the byte of val[x].
  for(int i=0;i<val.length;i++)val[i]=i;
  for(len+=off;off<len;off++){
   int tp=b[off]&0xFF;
   int i;
   for(i=0;;i++){ // Guaranteed to terminate since there will always be a match.
    if(val[i]==tp){
     b[off]=(byte)i;
     break;}}
   for(;i>=1;i--)val[i]=val[i-1]; // Shift all preceeding entries to the right.
   val[0]=tp;}}


 public static void transformInverse(byte[] b){
  transformInverse(b,0,b.length);}

 public static void transformInverse(byte[] b,int off,int len){
  int[] val=new int[256];
  for(int i=0;i<val.length;i++)val[i]=i;
  for(len+=off;off<len;off++){
   int i=b[off]&0xFF;
   b[off]=(byte)val[i];
   for(;i>=1;i--)val[i]=val[i-1]; // Shift all preceeding entries to the right.
   val[0]=b[off]&0xFF;}}


 private MoveToFrontTransform(){}}