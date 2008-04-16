package p79068.io;

import java.io.*;
import p79068.util.*;


public final class BitInputStream extends InputStream{

 private InputStream in;
 private int currentbyte;


 public BitInputStream(InputStream in){
  this.in=in;
  currentbyte=1;}


 public int readBit() throws IOException{
  if(currentbyte==1)currentbyte=in.read()|0x100;
  if(currentbyte==-1)return -1;
  int ret=currentbyte&1;
  currentbyte>>>=1;
  return ret;}

 public boolean isAligned(){
  return currentbyte==1;}


 public int read() throws IOException{
  currentbyte=1;
  return in.read();}

 public int read(byte[] b,int off,int len) throws IOException{
  currentbyte=1;
  return in.read(b,off,len);}


 public void close() throws IOException{
  super.close();
  in.close();}}