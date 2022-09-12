package p79068.crypto.cipher;

import org.junit.Test;


public final class Chacha20Test extends StreamCipherTest {
	
	@Override
	protected StreamCipher getCipher() {
		return Chacha20.CIPHER;
	}
	
	
	
	@Test public void testBasic() {
		String[][] testCases = {
			{"000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F01000000000000090000004A00000000", "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000", "10F1E7E4D13B5915500FDD1FA32071C4C7D1F4C733C068030422AA9AC3D46C4ED2826446079FAA0914C2D705D98B02A2B5129CD1DE164EB9CBD083E8A2503C4E"},
			{"000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F01000000000000000000004A00000000", "4C616469657320616E642047656E746C656D656E206F662074686520636C617373206F66202739393A204966204920636F756C64206F6666657220796F75206F6E6C79206F6E652074697020666F7220746865206675747572652C2073756E73637265656E20776F756C642062652069742E", "6E2E359A2568F98041BA0728DD0D6981E97E7AEC1D4360C20A27AFCCFD9FAE0BF91B65C5524733AB8F593DABCD62B3571639D624E65152AB8F530C359F0861D807CA0DBF500D6A6156A38E088A22B65E52BC514D16CCF806818CE91AB77937365AF90BBF74A35BE6B40B8EEDF2785E42874D"},
			{"000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000", "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000", "76B8E0ADA0F13D90405D6AE55386BD28BDD219B8A08DED1AA836EFCC8B770DC7DA41597C5157488D7724E03FB8D84A376A43B8F41518A11CC387B669B2EE6586"},
			{"000000000000000000000000000000000000000000000000000000000000000001000000000000000000000000000000", "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000", "9F07E7BE5551387A98BA977C732D080DCB0F29A048E3656912C6533E32EE7AED29B721769CE64E43D57133B074D839D531ED1F28510AFB45ACE10A1F4B794D6F"},
			{"000000000000000000000000000000000000000000000000000000000000000101000000000000000000000000000000", "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000", "3AEB5224ECF849929B9D828DB1CED4DD832025E8018B8160B82284F3C949AA5A8ECA00BBB4A73BDAD192B5C42F73F2FD4E273644C8B36125A64ADDEB006C13A0"},
			{"000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002", "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000", "C2C64D378CD536374AE204B9EF933FCD1A8B2288B3DFA49672AB765B54EE27C78A970E0E955C14F3A88E741B97C286F75F8FC299E8148362FA198A39531BED6D"},
			{"000000000000000000000000000000000000000000000000000000000000000101000000000000000000000000000002", "416E79207375626D697373696F6E20746F20746865204945544620696E74656E6465642062792074686520436F6E7472696275746F7220666F72207075626C69636174696F6E20617320616C6C206F722070617274206F6620616E204945544620496E7465726E65742D4472616674206F722052464320616E6420616E792073746174656D656E74206D6164652077697468696E2074686520636F6E74657874206F6620616E204945544620616374697669747920697320636F6E7369646572656420616E20224945544620436F6E747269627574696F6E222E20537563682073746174656D656E747320696E636C756465206F72616C2073746174656D656E747320696E20494554462073657373696F6E732C2061732077656C6C206173207772697474656E20616E6420656C656374726F6E696320636F6D6D756E69636174696F6E73206D61646520617420616E792074696D65206F7220706C6163652C207768696368206172652061646472657373656420746F", "A3FBF07DF3FA2FDE4F376CA23E82737041605D9F4F4F57BD8CFF2C1D4B7955EC2A97948BD3722915C8F3D337F7D370050E9E96D647B7C39F56E031CA5EB6250D4042E02785ECECFA4B4BB5E8EAD0440E20B6E8DB09D881A7C6132F420E52795042BDFA7773D8A9051447B3291CE1411C680465552AA6C405B7764D5E87BEA85AD00F8449ED8F72D0D662AB052691CA66424BC86D2DF80EA41F43ABF937D3259DC4B2D0DFB48A6C9139DDD7F76966E928E635553BA76C5C879D7B35D49EB2E62B0871CDAC638939E25E8A1E0EF9D5280FA8CA328B351C3C765989CBCF3DAA8B6CCC3AAF9F3979C92B3720FC88DC95ED84A1BE059C6499B9FDA236E7E818B04B0BC39C1E876B193BFE5569753F88128CC08AAA9B63D1A16F80EF2554D7189C411F5869CA52C5B83FA36FF216B9C1D30062BEBCFD2DC5BCE0911934FDA79A86F6E698CED759C3FF9B6477338F3DA4F9CD8514EA9982CCAFB341B2384DD902F3D1AB7AC61DD29C6F21BA5B862F3730E37CFDC4FD806C22F221"},
			{"1C9240A5EB55D38AF333888604F6B5F0473917C1402B80099DCA5CBC207075C02A000000000000000000000000000002", "2754776173206272696C6C69672C20616E642074686520736C6974687920746F7665730A446964206779726520616E642067696D626C6520696E2074686520776162653A0A416C6C206D696D737920776572652074686520626F726F676F7665732C0A416E6420746865206D6F6D65207261746873206F757467726162652E", "62E6347F95ED87A45FFAE7426F27A1DF5FB69110044C0D73118EFFA95B01E5CF166D3DF2D721CAF9B21E5FB14C616871FD84C54F9D65B283196C7FE4F60553EBF39C6402C42234E32A356B3E764312A61A5532055716EAD6962568F87D3F3F7704C6A8D1BCD1BF4D50D6154B6DA731B187B58DFD728AFA36757A797AC188D1"},
		};
		for (String[] tc : testCases)
			CipherTest.testCipher(Chacha20.CIPHER, tc[0], tc[1], tc[2]);
	}
	
}