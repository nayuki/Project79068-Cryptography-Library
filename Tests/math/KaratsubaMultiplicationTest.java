package math;

import static org.junit.Assert.assertEquals;
import java.math.BigInteger;
import java.util.Random;
import org.junit.Test;
import p79068.math.KaratsubaMultiplication;


public class KaratsubaMultiplicationTest {
	
	private static Random random = new Random();
	
	
	@Test
	public void testBig() {
		BigInteger x = new BigInteger(30000, random);
		BigInteger y = new BigInteger(30000, random);
		assertEquals(x.multiply(y), KaratsubaMultiplication.multiply(x, y));
	}
	
}