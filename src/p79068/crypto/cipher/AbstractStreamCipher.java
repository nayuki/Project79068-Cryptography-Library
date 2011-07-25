package p79068.crypto.cipher;


public abstract class AbstractStreamCipher extends AbstractCipher implements StreamCipher {
	
	protected AbstractStreamCipher(String name, int keyLength) {
		super(name, 1, keyLength);
	}
	
	
	public StreamCipherer newCipherer(byte[] key) {
		return (StreamCipherer)super.newCipherer(key);
	}
	
}
