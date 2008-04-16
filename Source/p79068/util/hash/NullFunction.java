package p79068.util.hash;


/**
 * The null hash function, always returning 8 bits of zeros.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public class NullFunction extends HashFunction{

 /** The singleton instance of the null hash function. */
 public final static NullFunction FUNCTION=new NullFunction();


 public Hasher newHasher(){
  return new NullHasher(this);}


 /** Returns the name of this hash function: <samp>Null</samp>. */
 public String getName(){
  return "Null";}

 /** Returns the length of hash values produced by this hash function: <samp>1</samp> byte. */
 public int getHashLength(){
  return 1;}


 private NullFunction(){}}