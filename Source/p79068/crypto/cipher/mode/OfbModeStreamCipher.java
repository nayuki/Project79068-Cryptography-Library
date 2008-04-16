package p79068.crypto.cipher.mode;

import p79068.crypto.Zeroizable;
import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.StreamCipherer;
import p79068.crypto.cipher.StreamCipher;


/**
A stream cipher using a block cipher in OFB (output feedback) mode.
<p>Encryption algorithm:</p>
<p><code>key stream[-1] = initialization vector<br>
key stream[i] = encrypt(key stream[i-1])<br>
ciphertext[i] = key stream[i] XOR plaintext[i]</code></p>
<p>Decryption algorithm:</p>
<p><code>key stream[-1] = initialization vector<br>
key stream[i] = encrypt(key stream[i-1])<br>
plaintext[i] = key stream[i] XOR ciphertext[i]</code></p>
*/
public final class OfbModeStreamCipher extends StreamCipher implements Zeroizable{

 private BlockCipher blockCipher;
 private byte[] key;


 public OfbModeStreamCipher(BlockCipher cipher,byte[] key){
  if(key.length!=cipher.getKeyLength())throw new IllegalArgumentException();
  blockCipher=cipher;
  this.key=key.clone();}


 public StreamCipherer newCipherer(byte[] initVector){
  if(blockCipher==null)throw new IllegalStateException("Already zeroized");
  if(initVector.length!=blockCipher.getBlockLength())throw new IllegalArgumentException();
  return new OfbModeStreamCipherer(this,initVector,blockCipher,key);}


 public String getName(){
  if(blockCipher==null)throw new IllegalStateException("Already zeroized");
  return String.format("%s in CBC mode",blockCipher.getName());}

 public int getKeyLength(){
  if(blockCipher==null)throw new IllegalStateException("Already zeroized");
  return blockCipher.getBlockLength();}


 public void zeroize(){
  if(blockCipher==null)throw new IllegalStateException("Already zeroized");
  for(int i=0;i<key.length;i++)key[i]=0;
  key=null;
  if(blockCipher instanceof Zeroizable)((Zeroizable)blockCipher).zeroize();
  blockCipher=null;}


 public boolean equals(Object obj){
  return obj instanceof OfbModeStreamCipher&&blockCipher.equals(((OfbModeStreamCipher)obj).blockCipher);}}