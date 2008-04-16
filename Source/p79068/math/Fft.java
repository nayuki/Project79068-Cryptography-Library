/*
Computes the discrete Fourier transform/inverse transform of a complex vector using the radix-2 decimation in time (O(n log n)) algorithm.

Multi-thread safe.
*/


package p79068.math;


final class Fft extends Dft{

 private int length;
 private int[] permutation; // Bit-reversed addressing
 private double[] cos,sin;


 Fft(int len){
  if(len<1)throw new IllegalArgumentException("Length must be positive");
  length=len;
  int levels=log2(length);
  if((1<<levels)!=length)throw new IllegalArgumentException("Length not power of 2");
  cos=new double[len/2];
  sin=new double[len/2];
  for(int i=0;i<len/2;i++){
   cos[i]=Math.cos(i*2*Math.PI/len);
   sin[i]=Math.sin(i*2*Math.PI/len);}
  permutation=new int[len];
  for(int i=0;i<len;i++)permutation[i]=IntegerBitMath.reverseBits(i)>>>(32-levels);}


 public void transform(double[] inre,double[] inim,double[] outre,double[] outim){
  for(int i=0;i<length;i++){
   outre[i]=inre[permutation[i]];
   outim[i]=inim[permutation[i]];}
  transformPrivate(outre,outim);}

 public void transform(double[] re,double[] im){
  for(int i=0;i<length;i++){
   if(permutation[i]>i){ // This is possible because the permutation is self-inverting.
    double tp;
    tp=re[i];
    re[i]=re[permutation[i]];
    re[permutation[i]]=tp;
    tp=im[i];
    im[i]=im[permutation[i]];
    im[permutation[i]]=tp;}}
  transformPrivate(re,im);}


 private void transformPrivate(double[] re,double[] im){
  if(length>=2){
   for(int i=0;i<length;i+=2){ // Perform multiply-less length-2 DFT.
    double tpre=re[i|1];
    double tpim=im[i|1];
    re[i|1]=re[i]-tpre;
    im[i|1]=im[i]-tpim;
    re[i]+=tpre;
    im[i]+=tpim;}}
  if(length>=4){
   for(int i=0;i<length;i+=4){ // Perform multiply-less length-4 DFT.
    double tpre;
    double tpim;
    tpre=re[i|2];
    tpim=im[i|2];
    re[i|2]=re[i  ]-tpre;
    im[i|2]=im[i  ]-tpim;
    re[i  ]+=tpre;
    im[i  ]+=tpim;
    tpre= im[i|3];
    tpim=-re[i|3];
    re[i|3]=re[i|1]-tpre;
    im[i|3]=im[i|1]-tpim;
    re[i|1]+=tpre;
    im[i|1]+=tpim;}}
  for(int i=4,j=length/8;i*2<=length;i*=2,j/=2){ // i*2 is the current DFT size.
   for(int k=0,l=0,end=i;;){
    if(k<end){
     double tpre= re[k|i]*cos[l]+im[k|i]*sin[l];
     double tpim=-re[k|i]*sin[l]+im[k|i]*cos[l];
     re[k|i]=re[k]-tpre;
     im[k|i]=im[k]-tpim;
     re[k]+=tpre;
     im[k]+=tpim;
     k++;
     l+=j;}
    else{
     k+=i;
     l=0;
     if(k==length)break;
     end+=i*2;}}}}


 private static int log2(int x){
  if(x<=0)throw new IllegalArgumentException("Argument must be positive");
  for(int i=0;i<32;i++){
   if((x>>>i)==1)return i;}
  return -1;}} // This is not possible