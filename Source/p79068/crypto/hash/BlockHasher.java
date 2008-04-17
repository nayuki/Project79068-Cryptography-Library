package p79068.crypto.hash;

import p79068.crypto.Zeroizable;
import p79068.lang.*;
import p79068.util.hash.Hasher;
import p79068.util.hash.HashValue;


/**
A hasher that only applies the compression function after each block.
<p>The instance returned by a BlockHashFunction is not necessarily a BlockHasher.</p>
*/
public abstract class BlockHasher extends Hasher implements Zeroizable{

 /** The total length of the message, in bytes. */
 protected long length;

 /** The data of the current block. */
 protected byte[] block;

 /** The number of bytes filled in the current block. */
 protected int blockLength;


 /** Constructs a new instance with the specified hash algorithm and block length. */
 protected BlockHasher(BlockHashFunction algor,int blockLength){
  super(algor);
  length=0;
  block=new byte[blockLength];
  this.blockLength=0;}


 /**
 Updates the current hash with the specified byte.
 @throws IllegalStateException if this object has been zeroized
 */
 public final void update(byte b){
  if(hashFunction==null)throw new IllegalStateException("Already zeroized");
  block[blockLength]=b;
  blockLength++;
  if(blockLength==block.length){
   compress();
   blockLength=0;}
  length++;}

 /**
 Updates the current hash with the specified byte array.
 @throws NullPointerException if <code>b</code> is <code>null</code>
 @throws IndexOutOfBoundsException if <code>off</code> and <code>len</code> specify that indices out of array <code>b</code>'s range to be accessed
 @throws IllegalStateException if this object has been zeroized
 */
 public final void update(byte[] b,int off,int len){
  if(hashFunction==null)throw new IllegalStateException("Already zeroized");
  BoundsChecker.check(b.length,off,len);
  length+=len;
  if(blockLength>0){
   int tp=Math.min(block.length-blockLength,len);
   System.arraycopy(b,off,block,blockLength,tp);
   off+=tp;
   len-=tp;
   blockLength+=tp;
   if(blockLength<block.length)return;
   compress();
   blockLength=0;}
  int tp=len/block.length*block.length;
  compress(b,off,tp);
  System.arraycopy(b,off+tp,block,0,len-tp); // Less than block.length bytes remain
  blockLength+=len-tp;}

 /**
 Returns the hash value.
 @throws IllegalStateException if this object has been zeroized
 */
 public HashValue getHash(){
  if(hashFunction==null)throw new IllegalStateException("Already zeroized");
  return clone().getHashDestructively();}


 /**
 Returns a new hasher with the same internal state as this one's. The returned object uses the same algorithm, but its type need not be the same as this one's.
 @return a clone of this object
 @throws IllegalStateException if this object has been zeroized
 */
 public BlockHasher clone(){
  if(hashFunction==null)throw new IllegalStateException("Already zeroized");
  BlockHasher result=(BlockHasher)super.clone();
  result.block=block.clone();
  return result;}

 public void zeroize(){
  if(hashFunction==null)throw new IllegalStateException("Already zeroized");
  length=0;
  blockLength=0;
  for(int i=0;i<block.length;i++)block[i]=0;
  block=null;
  hashFunction=null;}


 /**
 Applies the compression function to combine the current message block into the hasher's internal state. This calls <code>compress(this.block, 0, this.block.length)</code>.
 */
 protected void compress(){
  compress(block,0,block.length);}

 /**
 Applies the compression function to combine the specified message blocks into the hasher's internal state.
 @param message the byte array to compress
 @param off the offset into array <code>message</code>
 @param len the number of bytes to process, which is always a multiple of the block size
 */
 protected abstract void compress(byte[] message,int off,int len);

 /**
 Returns the hash value, possibly changing this hasher's internal state.
 @return the hash value
 */
 protected abstract HashValue getHashDestructively();}