/*
The bytes are interpreted as unsigned 8-bit integer samples.
*/


package p79068.io.wav;


public final class Int8Converter extends SampleDepthConverter{

 public Int8Converter(){
  super();}


 public void toInt8(byte[] b,int channel,byte[][] sample){
  for(int i=0,j=0;i<b.length;j++){
   for(int k=0;k<channel;k++,i++)sample[k][j]=(byte)(b[i]^0x80);}}

 public void toInt16(byte[] b,int channel,short[][] sample){
  for(int i=0,j=0;i<b.length;j++){
   for(int k=0;k<channel;k++,i++)sample[k][j]=(short)((b[i]^80)<<8);}}

 public void toFloat32(byte[] b,int channel,float[][] sample){
  for(int i=0,j=0;i<b.length;j++){
   for(int k=0;k<channel;k++,i++)sample[k][j]=(b[i]^0xFFFFFF80)/128F;}}


 public void fromInt8(byte[][] sample,byte[] b){
  for(int i=0,j=0;j<sample[0].length;j++){
   for(int k=0;k<sample.length;k++,i++)b[i]=(byte)(sample[k][j]^0x80);}}

 public void fromInt16(short[][] sample,byte[] b){
  for(int i=0,j=0;j<sample[0].length;j++){
   for(int k=0;k<sample.length;k++,i++){
    float tp=sample[k][j]/32768F;
    if(tp>=0){
     b[i]=(byte)(tp*128+128.5);
     if(tp>1||b[i]==0)b[i]=(byte)255;}
    else{
     if(tp<-1)tp=-1;
     b[i]=(byte)(tp*128+128.5);}}}}

 public void fromFloat32(float[][] sample,byte[] b){
  for(int i=0,j=0;j<sample[0].length;j++){
   for(int k=0;k<sample.length;k++,i++){
    float tp=sample[k][j];
    if(tp>=0){
     b[i]=(byte)(tp*128+128.5);
     if(tp>1||b[i]==0)b[i]=(byte)255;}
    else{
     if(tp<-1)tp=-1;
     b[i]=(byte)(tp*128+128.5);}}}}}