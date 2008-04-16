/*
Computes the discrete Fourier transform/inverse transform of a complex vector using Bluestein's/chirp-z algorithm.
The asymptotic time complexity is O(n log n). However, it uses more initialization time, execution time, and memory compared to the native radix-2 FFT. Also, it experiences slightly more numerical error, although it is nowhere near as bad as the naive transform.

Multi-thread safe.
*/


package p79068.math;


final class ChirpZFft extends Dft{

 private int length;
 private int convlen; // The smallest power of 2 such that convlen>=len*2-1 .
 private Dft fft;
 private double[] cos,sin;
 private double[] convre,convim;


 ChirpZFft(int len){
  if(len<1)throw new IllegalArgumentException("Length must be positive");
  length=len;
  for(convlen=1;convlen<length*2-1;convlen*=2);
  cos=new double[length];
  sin=new double[length];
  convre=new double[convlen];
  convim=new double[convlen];
  cos[0]=1;
  sin[0]=0;
  convre[0]=cos[0]/convlen;
  convim[0]=sin[0]/convlen;
  for(int i=1;i<length;i++){
   convre[i]=convre[convlen-i]=(cos[i]=Math.cos((long)i*i%(length*2)*Math.PI/length))/convlen;
   convim[i]=convim[convlen-i]=(sin[i]=Math.sin((long)i*i%(length*2)*Math.PI/length))/convlen;}
  fft=new Fft(convlen);
  fft.transform(convre,convim);}


 public void transform(double[] inre,double[] inim,double[] outre,double[] outim){
  double[] tpre=new double[convlen];
  double[] tpim=new double[convlen];
  for(int i=0;i<length;i++){
   tpre[i]= inre[i]*cos[i]+inim[i]*sin[i];
   tpim[i]=-inre[i]*sin[i]+inim[i]*cos[i];}
  fft.transform(tpre,tpim);
  for(int i=0;i<convlen;i++){
   double re=tpre[i]*convre[i]-tpim[i]*convim[i];
   tpim[i]  =tpre[i]*convim[i]+tpim[i]*convre[i];
   tpre[i]=re;}
  fft.transformInverse(tpre,tpim);
  for(int i=0;i<length;i++){
   outre[i]= tpre[i]*cos[i]+tpim[i]*sin[i];
   outim[i]=-tpre[i]*sin[i]+tpim[i]*cos[i];}}

 public void transform(double[] re,double[] im){
  transform(re,im,re,im);}}