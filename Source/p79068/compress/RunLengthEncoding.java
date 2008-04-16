/*
Run-Length Encoding

The output consists of a concatenation of verbatim and run blocks.
The leading byte of a block tells its uncompressed length. The most significant bit (0x80), if zero, indicates a verbatim block, else if one, indicates a run block. The remaining bits represent the uncompressed length minus one. e.g. 1000 0101 is a run block with length 6.
In a verbatim block, the number of bytes indicated by the header follows the header.
In a run block, a single byte follows, which is the run byte.
For example, the coding of "ABCDDD" would be "V2 A B C R2 D" (where V=verbatim, R=run).
(Subtracting 1 from the length means that length 0 (a useless block) cannot be coded, but 128 can be coded.)
*/


package p79068.compress;

import p79068.util.*;


public class RunLengthEncoding{

 public static byte[] encode(byte[] bin){
  return encode(bin,0,bin.length);}

 public static byte[] encode(byte[] bin,int off,int len){
  ByteBuffer bout=new ByteBuffer();
  level0:
  while(off<len){
   int start=off; // Start of literal run
   int last=128; // Not a valid signed byte value
   int run=0; // Number of consecutive instances of last
   for(;;off++){
    if(off==len){
     appendVerbatim(bin,start,off-start,bout);
     break level0;}
    if(bin[off]!=last){
     last=bin[off];
     run=1;}
    else{
     run++;
     if(run==3){
      off++;
      break;}}}
   appendVerbatim(bin,start,off-run-start,bout);
   for(;off<len&&bin[off]==last;off++)run++;
   for(;run>=128;run-=128)bout.append(0x80|127).append(last);
   if(run>0)bout.append(0x80|(run-1)).append(last);}
  return bout.toByteArray();}


 public static byte[] decode(byte[] bin){
  return decode(bin,0,bin.length);}

 public static byte[] decode(byte[] bin,int off,int len){
  ByteBuffer bout=new ByteBuffer();
  byte[] tpb=new byte[128];
  for(len+=off;off<len;){
   int l=(bin[off]&0x7F)+1;
   if((bin[off]&0x80)==0){
    bout.append(bin,off+1,l);
    off+=l+1;}
   else{
    byte b=bin[off+1];
    for(int i=0;i<l;i++)tpb[i]=b;
    bout.append(tpb,0,l);
    off+=2;}}
  return bout.toByteArray();}


 private static void appendVerbatim(byte[] bin,int off,int len,ByteBuffer bout){
  for(;len>=128;off+=128,len-=128)bout.append(127).append(bin,off,128);
  if(len>0)bout.append(len-1).append(bin,off,len);}}