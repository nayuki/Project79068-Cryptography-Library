package p79068.io.wav;

import java.io.*;
import p79068.io.*;


public final class WAVInputStream extends AudioInputStream{

 private InputStream in;
 private int sampledepth; // In bits per sample
 private SampleDepthConverter converter;


 public WAVInputStream(InputStream in) throws IOException{
  this.in=in;
  byte[] b=readFull(in,20);
  if(toInt32BigEndian(b, 0)!=0x52494646)throw new RuntimeException("Invalid RIFF header"); // "RIFF"
  if(toInt32BigEndian(b, 8)!=0x57415645)throw new RuntimeException("Invalid RIFF format"); // "WAVE"
  if(toInt32BigEndian(b,12)!=0x666D7420)throw new RuntimeException("Format chunk expected"); // "fmt "
  b=readFull(in,toInt32LittleEndian(b,16)); // The rest of the fmt chunk
  int twocc=toInt16LittleEndian(b,0); // 0x0001 = integer "PCM";  0x0003 = IEEE 754 "float"
  if(twocc!=1&&twocc!=3)throw new RuntimeException("Codec not supported");
  channel=toInt16LittleEndian(b,2);
  samplerate=toInt32LittleEndian(b,4);
  sampledepth=toInt16LittleEndian(b,14);
  if(twocc==1&&sampledepth==8)converter=new Int8Converter();
  else if(twocc==1&&sampledepth==16)converter=new Int16Converter();
  else if(twocc==3&&sampledepth==32)converter=new Float32Converter();
  else throw new RuntimeException("Sample depth not supported");
  b=readFull(in,8);
  if(toInt32BigEndian(b,0)!=0x64617461)throw new RuntimeException("Data chunk expected"); // "data"
  length=toInt32LittleEndian(b,4)/(sampledepth/8)/channel;}


 public int read(short[][] sample,int off,int len) throws IOException{
  byte[] b=readFull(in,len*channel*(sampledepth/8));
  converter.toInt16(b,channel,sample);
  return len;}

 public int read(float[][] sample,int off,int len) throws IOException{
  byte[] b=readFull(in,len*channel*(sampledepth/8));
  converter.toFloat32(b,channel,sample);
  return len;}


 public int getSampleDepth(){
  return sampledepth;}}