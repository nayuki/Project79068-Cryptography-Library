package p79068.crypto.cipher;

import p79068.Assert;
import p79068.crypto.Zeroizer;


final class Chacha20Cipherer extends AbstractStreamCipherer {
	
	private int[] referenceBlock;
	private int[] workingBlock;
	
	private int byteIndex;
	
	
	
	Chacha20Cipherer(Chacha20 cipher, byte[] key) {
		super(cipher, key);
		
		referenceBlock = new int[16];
		referenceBlock[0] = 0x61707865;
		referenceBlock[1] = 0x3320646E;
		referenceBlock[2] = 0x79622D32;
		referenceBlock[3] = 0x6B206574;
		for (int i = 0; i < key.length; i++)
			referenceBlock[4 + i / 4] |= (key[i] & 0xFF) << (i % 4 * 8);
		referenceBlock[12]--;
		
		workingBlock = new int[16];
		byteIndex = 64;
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
		
		int j = byteIndex;
		for (int i = off, end = off + len; i < end; i++) {
			if (j >= 64) {
				referenceBlock[12]++;
				calcBlock();
				j = 0;
			}
			b[i] ^= workingBlock[j >>> 2] >>> ((j & 3) << 3);
			j++;
		}
		byteIndex = j;
	}
	
	
	@Override
	public void skip(int byteCount) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		if (byteCount < 0)
			throw new IllegalArgumentException("Negative skip");
		long temp = ((referenceBlock[12] & 0xFFFFFFFFL) << 6) + byteIndex + byteCount;
		referenceBlock[12] = (int)(temp >>> 6);
		byteIndex = (int)temp & 0x3F;
		calcBlock();
	}
	
	
	@Override
	public Chacha20Cipherer clone() {
		Chacha20Cipherer result = (Chacha20Cipherer)super.clone();
		result.referenceBlock = result.referenceBlock.clone();
		result.workingBlock = result.workingBlock.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (cipher == null)
			return;
		referenceBlock = Zeroizer.clear(referenceBlock);
		workingBlock = Zeroizer.clear(workingBlock);
		byteIndex = 0;
		super.zeroize();
	}
	
	
	private void calcBlock() {
		System.arraycopy(referenceBlock, 0, workingBlock, 0, referenceBlock.length);
		for (int i = 0; i < 10; i++) {  // Do 20 rounds
			// Column round
			calcQuarterRound(0, 4,  8, 12);
			calcQuarterRound(1, 5,  9, 13);
			calcQuarterRound(2, 6, 10, 14);
			calcQuarterRound(3, 7, 11, 15);
			// Diagonal round
			calcQuarterRound(0, 5, 10, 15);
			calcQuarterRound(1, 6, 11, 12);
			calcQuarterRound(2, 7,  8, 13);
			calcQuarterRound(3, 4,  9, 14);
		}
		for (int i = 0; i < workingBlock.length; i++)
			workingBlock[i] += referenceBlock[i];
	}
	
	
	private void calcQuarterRound(int i, int j, int k, int l) {
		int a = workingBlock[i];
		int b = workingBlock[j];
		int c = workingBlock[k];
		int d = workingBlock[l];
		a += b;
		d = Integer.rotateLeft(d ^ a, 16);
		c += d;
		b = Integer.rotateLeft(b ^ c, 12);
		a += b;
		d = Integer.rotateLeft(d ^ a,  8);
		c += d;
		b = Integer.rotateLeft(b ^ c,  7);
		workingBlock[i] = a;
		workingBlock[j] = b;
		workingBlock[k] = c;
		workingBlock[l] = d;
	}
	
}
