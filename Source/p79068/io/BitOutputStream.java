package p79068.io;

import java.io.*;
import p79068.util.*;


public final class BitOutputStream extends OutputStream{

 private OutputStream out;
 private int currentbyte;


 public BitOutputStream(OutputStream out){
  this.out=out;
  currentbyte=0x80000000;}

int w=0;
 public void writeBit(int b) throws IOException{
w++;
  currentbyte=b<<31|currentbyte>>>1;
  if((currentbyte&0x00800000)!=0){
   out.write(currentbyte>>>24);
   currentbyte=0x80000000;}}

 public boolean isAligned(){
  return currentbyte==0x80000000;}

 public void flush() throws IOException{
  if(currentbyte!=0x80000000){
   while((currentbyte&0x00800000)==0)currentbyte>>>=1;
   out.write(currentbyte>>>24);
   currentbyte=0x80000000;}}


 public void write(int b) throws IOException{
  flush();
  out.write(b);}

 public void write(byte[] b,int off,int len) throws IOException{
  flush();
  out.write(b,off,len);}


 public void close() throws IOException{
System.out.println("Bits written: "+w);
  flush();
  super.close();
  out.close();}}