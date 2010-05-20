package p79068.crypto.hash;

import p79068.crypto.Zeroizer;
import p79068.crypto.cipher.BlockCipher;
import p79068.crypto.cipher.Cipherer;
import p79068.lang.BoundsChecker;


final class WhirlpoolCipherer extends Cipherer {
	
	private byte[] key;
	
	private WhirlpoolHasher hasher;
	
	
	
	public WhirlpoolCipherer(BlockCipher cipher, byte[] key, WhirlpoolParameters params) {
		super(cipher, key);
		hasher = new WhirlpoolHasher(params);
		setKey(key);
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
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
		BoundsChecker.check(b.length, off, len);
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
		Zeroizer.clear(key);
		key = null;
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