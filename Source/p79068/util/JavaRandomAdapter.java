package p79068.util;


final class JavaRandomAdapter extends java.util.Random{

 Random random;


 JavaRandomAdapter(Random r){
  random=r;}


 protected int next(int bits){
  return random.randomInt()>>>(32-bits);}

 public int nextInt(){
  return random.randomInt();}

 public long nextLong(){
  return random.randomLong();}

 public void setSeed(long seed){
  throw new UnsupportedOperationException();}}