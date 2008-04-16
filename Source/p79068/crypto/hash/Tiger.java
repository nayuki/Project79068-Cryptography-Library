package p79068.crypto.hash;

import p79068.util.hash.Hasher;
import p79068.util.hash.HashFunction;


/** 
The Tiger hash function.
<p>Mutability: <em>Immutable</em><br>
 Instantiability: <em>Singleton</em></p>
@see HashFunction
*/
public class Tiger extends BlockHashFunction{

 /** The singleton instance of the Tiger hash function. */
 public final static Tiger FUNCTION=new Tiger();


 public Hasher newHasher(){
  return new TigerHasher(this);}


 /** Returns the name of this hash function: <samp>Tiger</samp>. */
 public String getName(){
  return "Tiger";}

 /** Returns the length of hash values produced by this hash function: <samp>24</samp> bytes (192 bits). */
 public int getHashLength(){
  return 24;}

 /** Returns the block length of this hash function: <samp>64</samp> bytes (512 bits). */
 public int getBlockLength(){
  return 64;}


 private Tiger(){}}