package p79068.math;

import java.util.Arrays;


/**
Contains methods for calculating summary statistics for samples, e.g. mean, standard deviation.
<p>Instantiability: <em>Not applicable</em></p>
*/
public final class Statistics{

 // Measures of central tendency

 /**
 Returns the arithmetic mean of a sample.
 <p>It is mathematically defined as:</p>
 <p><code>mean = (x[0] + x[1] + ... + x[x.length-1]) / x.length</code></p>
 */
 public static double mean(double[] sample){
  double x=0;
  for(int i=0;i<sample.length;i++)x+=sample[i];
  return x/sample.length;}

 /**
 Returns the root mean square (RMS) of a sample.
 <p>It is mathematically defined as:</p>
 <p><code>rms = sqrt((x[0]^2 + x[1]^2 + ... + x[x.length-1]^2) / x.length)</code>,</p>
 <p>where <code>^</code> denotes exponentiation.</p>
 */
 public static double rootMeanSquare(double[] sample){
  double s=0;
  for(int i=0;i<sample.length;i++)s+=sample[i]*sample[i];
  return Math.sqrt(s/sample.length);}

 /**
 Returns the geometric mean of a sample.
 <p>It is mathematically defined as:</p>
 <p><code>mean = (x[0] + x[1] + ... + x[x.length-1]) ^ (1/x.length)</code>,</p>
 <p>where <code>^</code> denotes exponentiation.</p>
 */
 public static double geometricMean(double[] sample){
  double p=1;
  for(int i=0;i<sample.length;i++)p*=sample[i];
  return Math.pow(p,1d/sample.length);}

 /**
 Returns the harmonic mean of a sample.
 <p>It is mathematically defined as:</p>
 <p><code>harmonic mean = x.length / (1/x[0] + 1/x[1] + ... + 1/x[x.length-1])</code></p>
 */
 public static double harmonicMean(double[] sample){
  double s=0;
  for(int i=0;i<sample.length;i++)s+=1/sample[i];
  return sample.length/s;}

 /**
 Returns the median of a sample.
 <p>If the number of samples is odd, it is defined as the middle element in the sorted list of samples.</p>
 <p>If the number of samples is even, it is defined as the mean of the two elements closest to the middle in the sorted list of samples.</p>
 <p>For example:</p>
  <ul><li><code>median({3,1,2}) = 2</code></li>
  <li><code>median({4,5,6,7}) = 5.5</code></li></ul>
 */
 public static double median(double[] sample){
  if(sample.length==0)return Double.NaN;
  double[] tp=sample.clone();
  Arrays.sort(tp);
  if(sample.length%2==1)return tp[sample.length/2];
  return (tp[sample.length/2-1]+tp[sample.length/2])/2;}


 // Measures of deviation from center

 /**
 Returns the standard deviation of a population.
 <p>It is mathematically defined as:</p>
 <p><code>population std dev = sqrt(population variance)<br>&nbsp;&nbsp;&nbsp;&nbsp;= sqrt(((x[0]-mean)^2 + (x[1]-mean)^2 + ... + (x[x.length-1]-mean)^2) / x.length)</code></p>
 */
 public static double populationStdDev(double[] population){
  double x=0,xx=0;
  for(int i=0;i<population.length;i++){
   x+=population[i];
   xx+=population[i]*population[i];}
  return Math.sqrt((xx-x*x/population.length)/population.length);}

 /**
 Returns the standard deviation of a sample.
 <p>It is mathematically defined as:</p>
 <p><code>sample std dev = sqrt(sample variance)<br>&nbsp;&nbsp;&nbsp;&nbsp;= sqrt(((x[0]-mean)^2 + (x[1]-mean)^2 + ... + (x[x.length-1]-mean)^2) / (x.length-1))</code></p>
 */
 public static double sampleStdDev(double[] sample){
  double x=0,xx=0;
  for(int i=0;i<sample.length;i++){
   x+=sample[i];
   xx+=sample[i]*sample[i];}
  return Math.sqrt((xx-x*x/sample.length)/(sample.length-1));}

 /**
 Returns the variance of a population.
 <p>It is mathematically defined as:</p>
 <p><code>population var = ((x[0]-mean)^2 + (x[1]-mean)^2 + ... + (x[x.length-1]-mean)^2) / x.length</code></p>
 */
 public static double populationVariance(double[] population){
  double x=0,xx=0;
  for(int i=0;i<population.length;i++){
   x+=population[i];
   xx+=population[i]*population[i];}
  return (xx-x*x/population.length)/population.length;}

 /**
 Returns the variance of a sample.
 <p>It is mathematically defined as:</p>
 <p><code>sample var = ((x[0]-mean)^2 + (x[1]-mean)^2 + ... + (x[x.length-1]-mean)^2) / (x.length-1)</code></p>
 */
 public static double sampleVariance(double[] sample){
  double x=0,xx=0;
  for(int i=0;i<sample.length;i++){
   x+=sample[i];
   xx+=sample[i]*sample[i];}
  return (xx-x*x/sample.length)/(sample.length-1);}


 /**
 Returns the average absolute deviation of a sample from its mean.
 <p>It is defined as:</p>
 <p><code>abs dev = (abs(x[0]-mean) + abs(x[1]-mean) + ... + abs(x[x.length-1]-mean)) / x.length</code>,</p>
 <p>where <code>abs</code> is the absolute value function.</p>
 */
 public static double absoluteDeviation(double[] sample){
  double m=median(sample);
  double s=0;
  for(int i=0;i<sample.length;i++)s+=Math.abs(m-sample[i]);
  return s/sample.length;}

 /** Returns the range of a sample. It is defined as the maximum value in the sample minus the minimum value in the sample. */
 public static double range(double[] sample){
  if(sample.length==0)return Double.NaN;
  double max=sample[0],min=sample[0];
  for(int i=1;i<sample.length;i++){
   if(sample[i]>max)max=sample[i];
   else if(sample[i]<min)min=sample[i];}
  return max-min;}


 private Statistics(){}}