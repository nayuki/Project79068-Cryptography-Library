/*
Arithmetic Coding

With lots of help from http://www.arturocampos.com/ac_arithmetic.html .

Symbols with zero frequency must not be encoded.
*/


package p79068.compress;

import java.io.*;
import p79068.io.*;
import p79068.util.*;


public class ArithmeticCoding{

 protected int[] cumfreq;
 protected int total;

 protected int statebits;


 // 2^statebits * total <= 2^63
 public ArithmeticCoding(int[] freq,int statebits){
  if(statebits<2)throw new IllegalArgumentException("State is too small");
  if(statebits>63)throw new IllegalArgumentException("State is too large");
  this.statebits=statebits;
  cumfreq=new int[257];
  setFrequency(freq);
  if(total>(1L<<statebits)/4+2)throw new IllegalArgumentException("Total is too large");}


 public void encode(InputStream in,BitOutputStream out) throws IOException{
  final long mask=(1L<<statebits)-1;
  long low=0;
  long high=(1L<<statebits)-1;
  int underflow=0;
  while(true){
   int symbol=in.read();
   if(symbol==-1)break;
   long range=high+1-low;
if(cumfreq[symbol]==cumfreq[symbol+1])throw new RuntimeException("Attempting to encode a symbol with zero frequency");
   if(cumfreq[symbol+1]<total){
    high=low+cumfreq[symbol+1]*range/total-1;
    low=low+cumfreq[symbol]*range/total;}
   else{ // Handles the unlikely case where range*cumfreq[symbol+1] = 2^63, which is a negative number and will be a negative number when divided by total
    high=low+range-1;
    low=low+cumfreq[symbol]*range/total;}
   while((low^high)>>>(statebits-1)==0){ // While the highest bits are equal
    int bit=(int)(low>>>(statebits-1));
    out.writeBit(bit);
    if(underflow>0){
     for(;underflow>0;underflow--)out.writeBit(bit^1);}
    low=(low<<1)&mask;
    high=((high<<1)&mask)|1;}
   while((((low&~high)>>>(statebits-2))&1)!=0){ // While the second highest bit of low is 1 and the second highest bit of high is 0
    underflow++;
    low=(low<<1)&(mask>>>1);
    high=((high<<1)&(mask>>>1))|(1L<<(statebits-1))|1;}}
  if(low>0)out.writeBit(1);
  while(!out.isAligned())out.writeBit(0);}


 public void decode(BitInputStream in,OutputStream out,int len) throws IOException{
  final long mask=(1L<<statebits)-1;
  long low=0;
  long high=(1L<<statebits)-1;
  long code=0;
  for(int i=0;i<statebits;i++){
   int tp=in.readBit();
   if(tp==-1)tp=0;
   code=(code<<1)|tp;}
  for(int i=0;i<len;i++){
   long range=high+1-low;
if(code<low||code>high)throw new RuntimeException();
   int symbol=-1;
   {long tp=code-low;
    for(int j=0;j<256;j++){
     if(cumfreq[j+1]<total&&tp<cumfreq[j+1]*range/total){
      symbol=j;
      break;}
     else if(cumfreq[j+1]==total&&tp<range){
      symbol=j;
      break;}}}
   out.write(symbol);
   if(cumfreq[symbol+1]<total){
    high=low+cumfreq[symbol+1]*range/total-1;
    low=low+cumfreq[symbol]*range/total;}
   else{
    high=low+range-1;
    low=low+cumfreq[symbol]*range/total;}
   while((low^high)>>>(statebits-1)==0){ // While the highest bits are equal
    low=(low<<1)&mask;
    high=((high<<1)&mask)|1;
    int tp=in.readBit();
    if(tp==-1)tp=0;
    code=((code<<1)&mask)|tp;}
   while((((low&~high)>>>(statebits-2))&1)!=0){
    low=(low<<1)&(mask>>>1);
    high=((high<<1)&(mask>>>1))|(1L<<(statebits-1))|1;
    int tp=in.readBit();
    if(tp==-1)tp=0;
    code=(code&(1L<<(statebits-1)))|((code<<1)&(mask>>>1))|tp;}}}


 public void setFrequency(int[] freq){
  if(freq.length!=256)throw new IllegalArgumentException();
  for(int i=0,sum=0;;i++){
   cumfreq[i]=sum;
   if(i==freq.length)break;
   sum+=freq[i];}
  total=cumfreq[cumfreq.length-1];}}