package p79068.crypto.cipher;

import org.junit.Test;


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
	
	
	@Test
	public void testRc2() {
		testCipher(new Rc2( 63,  8), "0000000000000000"                                                  , "0000000000000000", "EBB773F993278EFF");
		testCipher(new Rc2( 64,  8), "FFFFFFFFFFFFFFFF"                                                  , "FFFFFFFFFFFFFFFF", "278B27E42E2F0D49");
		testCipher(new Rc2( 64,  8), "3000000000000000"                                                  , "1000000000000001", "30649EDF9BE7D2C2");
		testCipher(new Rc2( 64,  1), "88"                                                                , "0000000000000000", "61A8A244ADACCCF0");
		testCipher(new Rc2( 64,  7), "88BCA90E90875A"                                                    , "0000000000000000", "6CCF4308974C267F");
		testCipher(new Rc2( 64, 16), "88BCA90E90875A7F0F79C384627BAFB2"                                  , "0000000000000000", "1A807D272BBE5DB1");
		testCipher(new Rc2(128, 16), "88BCA90E90875A7F0F79C384627BAFB2"                                  , "0000000000000000", "2269552AB0F85CA6");
		testCipher(new Rc2(129, 33), "88BCA90E90875A7F0F79C384627BAFB216F80A6F85920584C42FCEB0BE255DAF1E", "0000000000000000", "5B78D3A43DFFF1F1");
	}
	
}
