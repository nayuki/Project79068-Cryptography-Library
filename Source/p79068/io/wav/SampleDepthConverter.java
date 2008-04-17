package p79068.io.wav;

import java.io.*;


public abstract class SampleDepthConverter{


 protected SampleDepthConverter(){}


 public abstract void toInt8(byte[] b,int channel,byte[][] sample);
 public abstract void toInt16(byte[] b,int channel,short[][] sample);
 public abstract void toFloat32(byte[] b,int channel,float[][] sample);

 public abstract void fromInt8(byte[][] sample,byte[] b);
 public abstract void fromInt16(short[][] sample,byte[] b);
 public abstract void fromFloat32(float[][] sample,byte[] b);


 protected static byte toInt8(float f){
  if(f>=0){
   byte i=(byte)(f*128+128.5);
   if(f>1||i==0)return (byte)255;
   return i;}
  else{
   if(f<-1)f=-1;
   return (byte)(f*128+128.5);}}

 protected static short toInt16(float f){
  if(f>=0){
   short i=(short)(f*32768+0.5);
   if(f>1||i==-32768)return 32767;
   return i;}
  else{
   if(f<-1)f=-1;
   return (short)(f*32768-0.5);}}}