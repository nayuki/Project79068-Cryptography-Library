/*
The bytes are interpreted as signed little-endian 16-bit integer samples.
*/


package p79068.io.wav;


public final class Int16Converter extends SampleDepthConverter{

 public Int16Converter(){
  super();}


 public void toInt8(byte[] b,int channel,byte[][] sample){
  for(int i=0,j=0;i<b.length;j++){
   for(int k=0;k<channel;k++,i+=2)sample[k][j]=toInt8((b[i]&0xFF|b[i|1]<<8)/32768F);}}

 public void toInt16(byte[] b,int channel,short[][] sample){
  for(int i=0,j=0;i<b.length;j++){
   for(int k=0;k<channel;k++,i+=2)sample[k][j]=(short)(b[i]&0xFF|b[i|1]<<8);}}

 public void toFloat32(byte[] b,int channel,float[][] sample){
  for(int i=0,j=0;i<b.length;j++){
   for(int k=0;k<channel;k++,i+=2)sample[k][j]=(b[i]&0xFF|b[i|1]<<8)/32768F;}} // Note: For the byte with offset 1, the sign bit must be extended


 public void fromInt8(byte[][] sample,byte[] b){
  for(int i=0,j=0;j<sample[0].length;j++){
   for(int k=0;k<sample.length;k++,i+=2){
    int tp=toInt8(sample[k][j]/32768F);
    b[i|0]=(byte)(tp>>>0);
    b[i|1]=(byte)(tp>>>8);}}}

 public void fromInt16(short[][] sample,byte[] b){
  for(int i=0,j=0;j<sample[0].length;j++){
   for(int k=0;k<sample.length;k++,i+=2){
    b[i|0]=(byte)(sample[k][j]>>>0);
    b[i|1]=(byte)(sample[k][j]>>>8);}}}

 public void fromFloat32(float[][] sample,byte[] b){
  for(int i=0,j=0;j<sample[0].length;j++){
   for(int k=0;k<sample.length;k++,i++){
    float tp0=sample[k][j];
    int tp1;
    if(tp0>=0){
     if(tp0>1)tp0=1;
     tp1=(int)(tp0*32768+0.5);
     if(tp1==32768)tp1=32767;}
    else{
     if(tp0<-1)tp0=-1;
     tp1=(int)(tp0*32768-0.5);}
    b[i|0]=(byte)(tp1>>>0);
    b[i|1]=(byte)(tp1>>>8);}}}}