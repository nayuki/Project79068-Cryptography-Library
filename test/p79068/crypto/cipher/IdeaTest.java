package p79068.crypto.cipher;

import org.junit.Test;


public final class IdeaTest extends CipherTest {
	
	protected Cipher[] getCiphersToTest() {
		return new Cipher[] {Idea.CIPHER};
	}
	
	
	@Test public void testBasic() {
		testCipher(Idea.CIPHER, "00010002000300040005000600070008", "0000000100020003", "11FBED2B01986DE5");
		testCipher(Idea.CIPHER, "000102030405060708090A0B0C0D0E0F", "DB2D4A92AA68273F", "0011223344556677");
		testCipher(Idea.CIPHER, "2BD6459F82C5B300952C49104881FF48", "F129A6601EF62A47", "EA024714AD5C4D84");
	}
	
}
