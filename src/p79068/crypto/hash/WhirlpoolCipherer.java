package p79068.crypto.hash;

import p79068.Assert;
import p79068.crypto.Zeroizer;
import p79068.crypto.cipher.AbstractCipherer;
import p79068.crypto.cipher.BlockCipher;


final class WhirlpoolCipherer extends AbstractCipherer {
	
	private byte[] key;
	
	private WhirlpoolCore hasher;
	
	
	
	public WhirlpoolCipherer(BlockCipher cipher, byte[] key, int rounds, int[] sbox, int[] c, int[] cInv) {
		super(cipher, key);
		hasher = new WhirlpoolCore(rounds, sbox, c, cInv);
		setKey(key);
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
		if (len % 64 != 0)
			throw new IllegalArgumentException();
		
		byte[] tempmsg = new byte[64];
		byte[] temp = new byte[64];
		for (int i = off, end = off + len; i < end; i += 64) {
			System.arraycopy(b, i, tempmsg, 0, 64);
			hasher.encrypt(tempmsg, key, temp);
			System.arraycopy(tempmsg, 0, b, i, 64);
		}
	}
	
	
	@Override
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		Assert.assertRangeInBounds(b.length, off, len);
		if (len % 64 != 0)
			throw new IllegalArgumentException();
		
		byte[] tempmsg = new byte[64];
		byte[] temp = new byte[64];
		for (int i = off, end = off + len; i < end; i += 64) {
			System.arraycopy(b, i, tempmsg, 0, 64);
			hasher.decrypt(tempmsg, key, temp);
			System.arraycopy(tempmsg, 0, b, i, 64);
		}
	}
	
	
	@Override
	public WhirlpoolCipherer clone() {
		WhirlpoolCipherer result = (WhirlpoolCipherer)super.clone();
		result.key = result.key.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (cipher == null)
			return;
		key = Zeroizer.clear(key);
		super.zeroize();
	}
	
	
	private void setKey(byte[] key) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		if (key.length != 64)
			throw new AssertionError();
		this.key = key.clone();
	}
	
}
