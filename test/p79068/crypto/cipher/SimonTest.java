package p79068.crypto.cipher;

import org.junit.Test;


public class SimonTest extends CipherTest {
	
	protected Cipher[] getCiphersToTest() {
		return new Cipher[] {
			Simon.SIMON32_64_CIPHER,
			Simon.SIMON48_72_CIPHER,
			Simon.SIMON48_96_CIPHER,
			Simon.SIMON64_96_CIPHER,
			Simon.SIMON64_128_CIPHER,
			Simon.SIMON96_96_CIPHER,
			Simon.SIMON96_144_CIPHER,
			Simon.SIMON128_128_CIPHER,
			Simon.SIMON128_192_CIPHER,
			Simon.SIMON128_256_CIPHER,
		};
	}
	
	
	@Test public void testBasic() {
		CipherTest.testCipher(Simon.SIMON32_64_CIPHER  , "1918111009080100"                                                , "65656877"                        , "C69BE9BB"                        );
		CipherTest.testCipher(Simon.SIMON48_72_CIPHER  , "1211100A0908020100"                                              , "6120676E696C"                    , "DAE5AC292CAC"                    );
		CipherTest.testCipher(Simon.SIMON48_96_CIPHER  , "1A19181211100A0908020100"                                        , "72696320646E"                    , "6E06A5ACF156"                    );
		CipherTest.testCipher(Simon.SIMON64_96_CIPHER  , "131211100B0A090803020100"                                        , "6F7220676E696C63"                , "5CA2E27F111A8FC8"                );
		CipherTest.testCipher(Simon.SIMON64_128_CIPHER , "1B1A1918131211100B0A090803020100"                                , "656B696C20646E75"                , "44C8FC20B9DFA07A"                );
		CipherTest.testCipher(Simon.SIMON96_96_CIPHER  , "0D0C0B0A0908050403020100"                                        , "2072616C6C69702065687420"        , "602807A462B469063D8FF082"        );
		CipherTest.testCipher(Simon.SIMON96_144_CIPHER , "1514131211100D0C0B0A0908050403020100"                            , "74616874207473756420666F"        , "ECAD1C6C451E3F59C5DB1AE9"        );
		CipherTest.testCipher(Simon.SIMON128_128_CIPHER, "0F0E0D0C0B0A09080706050403020100"                                , "63736564207372656C6C657661727420", "49681B1E1E54FE3F65AA832AF84E0BBC");
		CipherTest.testCipher(Simon.SIMON128_192_CIPHER, "17161514131211100F0E0D0C0B0A09080706050403020100"                , "206572656874206E6568772065626972", "C4AC61EFFCDC0D4F6C9C8D6E2597B85B");
		CipherTest.testCipher(Simon.SIMON128_256_CIPHER, "1F1E1D1C1B1A191817161514131211100F0E0D0C0B0A09080706050403020100", "74206E69206D6F6F6D69732061207369", "8D2B5579AFC8A3A03BF72A87EFE7B868");
	}
	
}
