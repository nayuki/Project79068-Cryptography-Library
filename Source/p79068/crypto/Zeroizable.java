package p79068.crypto;


/** Allows clearing sensitive information contained in objects implementing this interface. */
public interface Zeroizable{

 /**
 Clear all sensitive information associated with this object. This method should set all fields to zero and references to null. Array elements should be cleared in the same manner. This method has no effect after being called once.
 */
 public void zeroize();}