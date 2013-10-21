package p79068.crypto.cipher;


public final class Rc2Test extends CipherTest {
	
	protected Cipher[] getCiphersToTest() {
		return new Cipher[] {
			new Rc2(64, 8),
			new Rc2(128, 16),
			new Rc2(192, 24),
			new Rc2(256, 32),
			new Rc2(1024, 128),
		};
	}
	
}
