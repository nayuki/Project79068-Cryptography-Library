package p79068.compress;

import java.io.*;
import p79068.io.*;


public final class FibonacciCoding{

 private static int[] fibonacci; // 1, 2, 3, 5, ..., 701408733, 1134903170, 1836311903

 static{
  fibonacci=new int[45];
  int x=1;
  int y=1;
  for(int i=0;i<fibonacci.length;i++){
   fibonacci[i]=y;
   int z=x+y;
   x=y;
   y=z;}}


 public static void encode(int[] val,BitOutputStream out) throws IOException{
  for(int i=0;i<val.length;i++){
   if(val[i]<=0)throw new IllegalArgumentException();
   int x=val[i];
   if(x<=3524578){
    int fib=0;
    for(int j=31;j>=0;j--){
     if(fibonacci[j]<=x){
      fib|=1<<j;
      x-=fibonacci[j];}}
    for(;fib!=0;fib>>>=1)out.writeBit(fib&1);
    out.writeBit(1);}
   else{
    long fib=0;
    for(int j=44;j>=0;j--){
     if(fibonacci[j]<=x){
      fib|=1L<<j;
      x-=fibonacci[j];}}
    for(;fib!=0;fib>>>=1)out.writeBit((int)fib&1);
    out.writeBit(1);}}}


 public static int[] decode(BitInputStream in) throws IOException{
  int[] val=new int[1];
  int vallen=0;
  int currval=0;
  int i=0; // Current Fibonacci sequence index
  int last=-1; // The last bit
  while(true){
   int tp=in.readBit();
   if(tp==-1)break;
   if(tp==1&&last==1){
    val[vallen]=currval;
    vallen++;
    if(vallen==val.length)val=resize(val,vallen*2);
    currval=0;
    i=0;
    last=-1;}
   else{
    currval+=tp*fibonacci[i];
    i++;
    last=tp;}}
  return resize(val,vallen);}


 private static int[] resize(int[] ain,int newlen){
  int[] aout=new int[newlen];
  System.arraycopy(ain,0,aout,0,Math.min(newlen,ain.length));
  return aout;}

}