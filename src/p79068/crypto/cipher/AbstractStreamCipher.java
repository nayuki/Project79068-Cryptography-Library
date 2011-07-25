package p79068.crypto.cipher;


public abstract class AbstractStreamCipher extends AbstractCipher implements StreamCipher {
	
	protected AbstractStreamCipher(String name, int keyLength) {
		super(name, 1, keyLength);
	}
	
}
