package p79068.crypto.cipher;


public final class WhirlpoolCipherTest extends CipherTest {
	
	protected Cipher[] getCiphersToTest() {
		return new Cipher[] {
			WhirlpoolCipher.WHIRLPOOL_CIPHER,
			WhirlpoolCipher.WHIRLPOOL_T_CIPHER,
			WhirlpoolCipher.WHIRLPOOL0_CIPHER,
		};
	}
	
}
