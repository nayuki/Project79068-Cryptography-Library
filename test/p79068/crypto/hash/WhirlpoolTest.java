package p79068.crypto.hash;

import org.junit.Test;
import p79068.hash.HashFunction;


public final class WhirlpoolTest extends CryptoHashFunctionTest {
	
	protected HashFunction[] getHashFunctionsToTest() {
		return new HashFunction[] {
			Whirlpool.WHIRLPOOL0_FUNCTION,
			Whirlpool.WHIRLPOOL_T_FUNCTION,
			Whirlpool.WHIRLPOOL_FUNCTION,
		};
	}
	
	
	private static final String msg0 = "";
	private static final String msg1 = "The quick brown fox jumps over the lazy dog";
	private static final String msg2 = "The quick brown fox jumps over the lazy eog";
	
	
	
	@Test public void testWhirlpool0() {
		testAscii(Whirlpool.WHIRLPOOL0_FUNCTION, new String[][] {
			{msg0, "B3E1AB6EAF640A34F784593F2074416ACCD3B8E62C620175FCA0997B1BA2347339AA0D79E754C308209EA36811DFA40C1C32F1A2B9004725D987D3635165D3C8"},
			{msg1, "4F8F5CB531E3D49A61CF417CD133792CCFA501FD8DA53EE368FED20E5FE0248C3A0B64F98A6533CEE1DA614C3A8DDEC791FF05FEE6D971D57C1348320F4EB42D"},
			{msg2, "228FBF76B2A93469D4B25929836A12B7D7F2A0803E43DABA0C7FC38BC11C8F2A9416BBCF8AB8392EB2AB7BCB565A64AC50C26179164B26084A253CAF2E012676"},
		});
	}
	
	
	@Test public void testWhirlpoolT() {
		testAscii(Whirlpool.WHIRLPOOL_T_FUNCTION, new String[][] {
			{msg0, "470F0409ABAA446E49667D4EBE12A14387CEDBD10DD17B8243CAD550A089DC0FEEA7AA40F6C2AAAB71C6EBD076E43C7CFCA0AD32567897DCB5969861049A0F5A"},
			{msg1, "3CCF8252D8BBB258460D9AA999C06EE38E67CB546CFFCF48E91F700F6FC7C183AC8CC3D3096DD30A35B01F4620A1E3A20D79CD5168544D9E1B7CDF49970E87F1"},
			{msg2, "C8C15D2A0E0DE6E6885E8A7D9B8A9139746DA299AD50158F5FA9EECDDEF744F91B8B83C617080D77CB4247B1E964C2959C507AB2DB0F1F3BF3E3B299CA00CAE3"},
		});
	}
	
	
	@Test public void testWhirlpool() {
		testAscii(Whirlpool.WHIRLPOOL_FUNCTION, new String[][] {
			{msg0, "19FA61D75522A4669B44E39C1D2E1726C530232130D407F89AFEE0964997F7A73E83BE698B288FEBCF88E3E03C4F0757EA8964E59B63D93708B138CC42A66EB3"},
			{msg1, "B97DE512E91E3828B40D2B0FDCE9CEB3C4A71F9BEA8D88E75C4FA854DF36725FD2B52EB6544EDCACD6F8BEDDFEA403CB55AE31F03AD62A5EF54E42EE82C3FB35"},
			{msg2, "C27BA124205F72E6847F3E19834F925CC666D0974167AF915BB462420ED40CC50900D85A1F923219D832357750492D5C143011A76988344C2635E69D06F2D38C"},
		});
	}
	
}
