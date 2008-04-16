package p79068.util.hash;


/**
 * The XOR checksum function. The hash value is the XOR of all the bytes.
 * <p>Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public class Xor extends HashFunction{

 /** The singleton instance of the XOR hash function. */
 public final static Xor FUNCTION=new Xor();

 /** Returns the name of this hash function: <samp>XOR</samp>. */
 public String getName(){
  return "XOR";}

 /** Returns the length of hash values produced by this hash function: <samp>1</samp> byte. */
 public int getHashLength(){
  return 1;}

 public Hasher newHasher(){
  return new XorHasher(this);}}
