package p79068.crypto.hash;

import java.util.Arrays;
import org.junit.Test;
import p79068.hash.HashFunction;


public final class TigerTest extends CryptoHashFunctionTest {
	
	protected HashFunction[] getHashFunctionsToTest() {
		return new HashFunction[] {
			Tiger.TIGER_FUNCTION,
			Tiger.TIGER2_FUNCTION,
		};
	}
	
	
	private static String msg0 = "";
	private static String msg1 = "a";
	private static String msg2 = "abc";
	private static String msg3 = "message digest";
	private static String msg4 = "abcdefghijklmnopqrstuvwxyz";
	private static String msg5 = "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq";
	private static String msg6 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static String msg7 = "12345678901234567890123456789012345678901234567890123456789012345678901234567890";
	private static String msg8;  // One million a's
	
	static {
		char[] c = new char[1000000];
		Arrays.fill(c, 'a');
		msg8 = new String(c);
	}
	
	
	
	@Test
	public void testTiger() {
		testAscii(Tiger.TIGER_FUNCTION, new String[][] {
			{msg0, "3293AC630C13F0245F92BBB1766E16167A4E58492DDE73F3"},
			{msg1, "77BEFBEF2E7EF8AB2EC8F93BF587A7FC613E247F5F247809"},
			{msg2, "2AAB1484E8C158F2BFB8C5FF41B57A525129131C957B5F93"},
			{msg3, "D981F8CB78201A950DCF3048751E441C517FCA1AA55A29F6"},
			{msg4, "1714A472EEE57D30040412BFCC55032A0B11602FF37BEEE9"},
			{msg5, "0F7BF9A19B9C58F2B7610DF7E84F0AC3A71C631E7B53F78E"},
			{msg6, "8DCEA680A17583EE502BA38A3C368651890FFBCCDC49A8CC"},
			{msg7, "1C14795529FD9F207A958F84C52F11E887FA0CABDFD91BFD"},
			{msg8, "6DB0E2729CBEAD93D715C6A7D36302E9B3CEE0D2BC314B41"},
		});
	}
	
	
	@Test
	public void testTiger2() {
		testAscii(Tiger.TIGER2_FUNCTION, new String[][] {
			{msg0, "4441BE75F6018773C206C22745374B924AA8313FEF919F41"},
			{msg1, "67E6AE8E9E968999F70A23E72AEAA9251CBC7C78A7916636"},
			{msg2, "F68D7BC5AF4B43A06E048D7829560D4A9415658BB0B1F3BF"},
			{msg3, "E29419A1B5FA259DE8005E7DE75078EA81A542EF2552462D"},
			{msg4, "F5B6B6A78C405C8547E91CD8624CB8BE83FC804A474488FD"},
			{msg5, "A6737F3997E8FBB63D20D2DF88F86376B5FE2D5CE36646A9"},
			{msg6, "EA9AB6228CEE7B51B77544FCA6066C8CBB5BBAE6319505CD"},
			{msg7, "D85278115329EBAA0EEC85ECDC5396FDA8AA3A5820942FFF"},
			{msg8, "E068281F060F551628CC5715B9D0226796914D45F7717CF4"},
		});
	}
	
}
