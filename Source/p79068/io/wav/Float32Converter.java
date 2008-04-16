/*
The bytes are interpreted as [signed] little-endian 32-bit IEEE floating-point samples.
*/


package p79068.io.wav;


public final class Float32Converter extends SampleDepthConverter{

 public Float32Converter(){
  super();}


 public void toInt8(byte[] b,int channel,byte[][] sample){
  for(int i=0,j=0;i<b.length;j++){
   for(int k=0;k<channel;k++,i+=4)sample[k][j]=toInt8(Float.intBitsToFloat((b[i]&0xFF)|(b[i|1]&0xFF)<<8|(b[i|2]&0xFF)<<16|b[i|3]<<24));}}

 public void toInt16(byte[] b,int channel,short[][] sample){
  for(int i=0,j=0;i<b.length;j++){
   for(int k=0;k<channel;k++,i+=4)sample[k][j]=toInt16(Float.intBitsToFloat((b[i]&0xFF)|(b[i|1]&0xFF)<<8|(b[i|2]&0xFF)<<16|b[i|3]<<24));}}

 public void toFloat32(byte[] b,int channel,float[][] sample){
  for(int i=0,j=0;i<b.length;j++){
   for(int k=0;k<channel;k++,i+=4)sample[k][j]=Float.intBitsToFloat((b[i]&0xFF)|(b[i|1]&0xFF)<<8|(b[i|2]&0xFF)<<16|b[i|3]<<24);}}


 public void fromInt8(byte[][] sample,byte[] b){
  for(int i=0,j=0;j<sample[0].length;j++){
   for(int k=0;k<sample.length;k++,i+=4){
    int tp=Float.floatToRawIntBits(sample[k][j]/128F);
    b[i|0]=(byte)(tp>>> 0);
    b[i|1]=(byte)(tp>>> 8);
    b[i|2]=(byte)(tp>>>16);
    b[i|3]=(byte)(tp>>>24);}}}

 public void fromInt16(short[][] sample,byte[] b){
  for(int i=0,j=0;j<sample[0].length;j++){
   for(int k=0;k<sample.length;k++,i+=4){
    int tp=Float.floatToRawIntBits(sample[k][j]/32768F);
    b[i|0]=(byte)(tp>>> 0);
    b[i|1]=(byte)(tp>>> 8);
    b[i|2]=(byte)(tp>>>16);
    b[i|3]=(byte)(tp>>>24);}}}

 public void fromFloat32(float[][] sample,byte[] b){
  for(int i=0,j=0;j<sample[0].length;j++){
   for(int k=0;k<sample.length;k++,i+=4){
    int tp=Float.floatToRawIntBits(sample[k][j]);
    b[i|0]=(byte)(tp>>> 0);
    b[i|1]=(byte)(tp>>> 8);
    b[i|2]=(byte)(tp>>>16);
    b[i|3]=(byte)(tp>>>24);}}}}