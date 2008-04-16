package p79068.crypto.hash;

import p79068.util.hash.Hasher;
import p79068.util.hash.HashFunction;


/**
The SHA-384 hash function.
<p>Mutability: <em>Immutable</em><br>
 Instantiability: <em>Singleton</em></p>
@see HashFunction
@see Sha512
*/
public class Sha384 extends BlockHashFunction{

 /** The singleton instance of the SHA-384 hash function. */
 public final static Sha384 FUNCTION=new Sha384();


 public Hasher newHasher(){
  return new Sha512Hasher(this);}


 /** Returns the name of this hash function: <samp>SHA-384</samp>. */
 public String getName(){
  return "SHA-384";}

 /** Returns the length of hash values produced by this hash function: <samp>48</samp> bytes (384 bits). */
 public int getHashLength(){
  return 48;}

 /** Returns the block length of this hash function: <samp>128</samp> bytes (1024 bits). */
 public int getBlockLength(){
  return 128;}


 private Sha384(){}}