package p79068.crypto.hash;

import org.junit.Test;


public final class WhirlpoolTest {
	
	private static final String msg0 = "";
	private static final String msg1 = "The quick brown fox jumps over the lazy dog";
	private static final String msg2 = "The quick brown fox jumps over the lazy eog";
	
	
	@Test
	public void testWhirlpool() {
		HashUtils.testAscii(Whirlpool.WHIRLPOOL_FUNCTION, msg0, "19FA61D75522A4669B44E39C1D2E1726C530232130D407F89AFEE0964997F7A73E83BE698B288FEBCF88E3E03C4F0757EA8964E59B63D93708B138CC42A66EB3");
		HashUtils.testAscii(Whirlpool.WHIRLPOOL_FUNCTION, msg1, "B97DE512E91E3828B40D2B0FDCE9CEB3C4A71F9BEA8D88E75C4FA854DF36725FD2B52EB6544EDCACD6F8BEDDFEA403CB55AE31F03AD62A5EF54E42EE82C3FB35");
		HashUtils.testAscii(Whirlpool.WHIRLPOOL_FUNCTION, msg2, "C27BA124205F72E6847F3E19834F925CC666D0974167AF915BB462420ED40CC50900D85A1F923219D832357750492D5C143011A76988344C2635E69D06F2D38C");
	}
	
	
	@Test
	public void testZeroize() {
		HashUtils.testZeroization(Whirlpool.WHIRLPOOL_FUNCTION);
	}
	
}
