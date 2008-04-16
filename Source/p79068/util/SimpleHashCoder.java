package p79068.util;


class SimpleHashCoder extends HashCoder{

 private int state;


 SimpleHashCoder(){
  state=0;}


 public HashCoder add(byte x){
  state=state*37+(x&0xFF);
  return this;}

 public HashCoder add(short x){
  state=state*37+(x&0xFFFF);
  return this;}

 public HashCoder add(int x){
  state=state*37+x;
  return this;}

 public HashCoder add(long x){
  state=state*37+(int)(x>>>32);
  state=state*37+(int)(x>>> 0);
  return this;}

 public HashCoder add(float x){
  state=state*37+Float.floatToRawIntBits(x);
  return this;}

 public HashCoder add(double x){
  return add(Double.doubleToRawLongBits(x));}

 public HashCoder add(boolean x){
  if(x)state=state*37+1;
  else state=state*37;
  return this;}

 public HashCoder add(char x){
  state=state*37+x;
  return this;}

 public HashCoder add(Object x){
  state=state*37+x.hashCode();
  return this;}


 public int getHashCode(){
  return state;}}