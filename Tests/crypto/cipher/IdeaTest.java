package crypto.cipher;

import org.junit.Test;
import crypto.CryptoUtils;
import p79068.crypto.cipher.Idea;


public class IdeaTest {
	
	@Test
	public void testBasic() {
		CryptoUtils.testCipher(Idea.CIPHER, "00010002000300040005000600070008", "0000000100020003", "11fbed2b01986de5");
	}
	
}