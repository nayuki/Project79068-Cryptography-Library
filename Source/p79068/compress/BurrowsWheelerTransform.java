/*
Burrows-Wheeler Transform
*/


package p79068.compress;


public class BurrowsWheelerTransform{

 public static int transform(byte[] b){
  return transform(b,0,b.length);}

 public static int transform(byte[] b,int off,int len){
  if(isSingleSymbol(b,off,len))return 0;
  byte[] tpb=new byte[len*2]; // Contains 2 copies of the input sequence
  int[] ind=new int[len]; // String rotation start indices
  for(int i=0;i<len;i++){
   tpb[i]=tpb[i+len]=(byte)(b[off+i]^0x80);
   ind[i]=i;}
  encodeSort(tpb,ind);
  int start=-1;
  for(int i=0;i<len;i++){
   b[off+i]=(byte)(tpb[ind[i]+len-1]^0x80); // Index is same as (ind[i]-1) mod len
   if(ind[i]==1)start=i;}
  return start;}


 public static void transformInverse(byte[] b,int start){
  transformInverse(b,0,b.length,start);}

 public static void transformInverse(byte[] b,int off,int len,int start){
  if(len==0||len==1||isSingleSymbol(b,off,len))return;
  byte[] tpb=new byte[len]; // To be sorted
  int[] gt=new int[len]; // Go-to indices
  for(int i=0;i<len;i++){
   tpb[i]=(byte)(b[off+i]^0x80);
   gt[i]=i;}
  decodeSort(tpb,gt);
  b[off]=b[off+start];
  for(int i=1;i<len;i++){
   b[off+i]=(byte)(tpb[start]^0x80);
   start=gt[start];}}


 private static void encodeSort(byte[] b,int[] ind){
  int len=ind.length;
  for(int i=len/2-1;i>=0;i--)encodeSortSift(b,ind,i,len);
  for(int i=len-1;i>=1;i--){
   int tp=ind[0];
   ind[0]=ind[i];
   ind[i]=tp;
   encodeSortSift(b,ind,0,i);}}

 private static void encodeSortSift(byte[] b,int[] ind,int i,int end){
  while(true){
   int child=i*2+1;
   if(child>=end)break;
   if(child+1<end&&encodeCompare(b,ind[child+1],ind[child])>0)child++;
   if(encodeCompare(b,ind[child],ind[i])>0){
    int tp=ind[i];
    ind[i]=ind[child];
    ind[child]=tp;}
   else break;
   i=child;}}


 private static void decodeSort(byte[] b,int[] ind){
  int len=ind.length;
  for(int i=len/2-1;i>=0;i--)decodeSortSift(b,ind,i,len);
  for(int i=len-1;i>=1;i--){
    byte tpb=b[0];
    b[0]=b[i];
    b[i]=tpb;
    int tpi=ind[0];
    ind[0]=ind[i];
    ind[i]=tpi;
   decodeSortSift(b,ind,0,i);}}

 private static void decodeSortSift(byte[] b,int[] ind,int i,int end){
  while(true){
   int child=i*2+1;
   if(child>=end)break;
   if(child+1<end&&(b[child+1]>b[child]||b[child+1]==b[child]&&ind[child+1]>ind[child]))child++;
   if(b[child]>b[i]||b[child]==b[i]&&ind[child]>ind[i]){
    byte tpb=b[i];
    b[i]=b[child];
    b[child]=tpb;
    int tpi=ind[i];
    ind[i]=ind[child];
    ind[child]=tpi;}
   else break;
   i=child;}}


 private static boolean isSingleSymbol(byte[] b,int off,int len){
  if(len==0||len==1)return true;
  byte b0=b[off];
  for(len+=off,off++;off<len&&b[off]==b0;off++); // len becomes end, off becomes i
  return off==len;}

 private static int encodeCompare(byte[] b,int i,int j){ // Guarantees no out-of-bounds if i<len, j<len, b.length<len*2, and b does not consist of just a single symbol
  while(b[i]==b[j]){
   i++;
   j++;}
  return b[i]-b[j];}}