package p79068.crypto.cipher;

import p79068.crypto.Zeroizer;
import p79068.lang.BoundsChecker;


final class WhirlpoolCiphererParent extends Cipherer {
	
	protected byte[] sub;
	protected byte[] subinv;
	
	protected byte[][] mul;
	protected byte[][] mulinv;
	
	protected byte[][] rcon;
	
	
	protected byte[][] keySchedule;
	
	
	
	WhirlpoolCiphererParent(Whirlpool0Cipher cipher, byte[] sub, byte[] subinv, byte[][] mul, byte[][] mulinv, byte[][] rcon, byte[] key) {
		this((Cipher)cipher, sub, subinv, mul, mulinv, rcon, key);
	}
	
	
	WhirlpoolCiphererParent(Whirlpool1Cipher cipher, byte[] sub, byte[] subinv, byte[][] mul, byte[][] mulinv, byte[][] rcon, byte[] key) {
		this((Cipher)cipher, sub, subinv, mul, mulinv, rcon, key);
	}
	
	
	WhirlpoolCiphererParent(WhirlpoolCipher cipher, byte[] sub, byte[] subinv, byte[][] mul, byte[][] mulinv, byte[][] rcon, byte[] key) {
		this((Cipher)cipher, sub, subinv, mul, mulinv, rcon, key);
	}
	
	
	private WhirlpoolCiphererParent(Cipher cipher, byte[] sub, byte[] subinv, byte[][] mul, byte[][] mulinv, byte[][] rcon, byte[] key) {
		super(cipher, key);
		this.sub = sub;
		this.subinv = subinv;
		this.mul = mul;
		this.mulinv = mulinv;
		this.rcon = rcon;
		setKey(key);
	}
	
	
	
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % 64 != 0)
			throw new IllegalArgumentException();
		
		byte[] tempmsg = new byte[64];
		byte[] temp = new byte[64];
		for (int end = off + len; off < end; off += 64) {
			System.arraycopy(b, off, tempmsg, 0, 64);
			w(tempmsg, temp);
			System.arraycopy(tempmsg, 0, b, off, 64);
		}
	}
	
	
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % 64 != 0)
			throw new IllegalArgumentException();
		
		byte[] tempmsg = new byte[64];
		byte[] temp = new byte[64];
		for (int end = off + len; off < end; off += 64) {
			System.arraycopy(b, off, tempmsg, 0, 64);
			wInverse(tempmsg, temp);
			System.arraycopy(tempmsg, 0, b, off, 64);
		}
	}
	
	
	public void zeroize() {
		if (cipher == null)
			return;
		for (int i = 0; i < keySchedule.length; i++) {
			Zeroizer.clear(keySchedule[i]);
			keySchedule[i] = null;
		}
		keySchedule = null;
		super.zeroize();
	}
	
	
	protected void setKey(byte[] key) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		if (key.length != 64)
			throw new AssertionError();
		keySchedule = new byte[rcon.length + 1][];
		keySchedule[0] = key.clone();
		byte[] temp = new byte[64];
		for (int i = 1; i < keySchedule.length; i++) {
			keySchedule[i] = keySchedule[i - 1].clone();
			rho(keySchedule[i], rcon[i - 1], temp);
		}
	}
	
	
	
	// The internal block cipher. Overwrites block and temp.
	private void w(byte[] block, byte[] temp) {
		sigma(block, keySchedule[0]);
		for (int i = 1; i < keySchedule.length; i++)
			rho(block, keySchedule[i], temp);
	}
	
	
	// The round function. Overwrites block and temp.
	private void rho(byte[] block, byte[] key, byte[] temp) {
		gamma(block);
		pi(block, temp);
		theta(temp, block);
		sigma(block, key);
	}
	
	
	private void wInverse(byte[] block, byte[] temp) {
		for (int i = keySchedule.length - 1; i >= 1; i--)
			rhoInverse(block, keySchedule[i], temp);
		sigma(block, keySchedule[0]);
	}
	
	
	private void rhoInverse(byte[] block, byte[] key, byte[] temp) {
		sigma(block, key);
		thetaInverse(block, temp);
		piInverse(temp, block);
		gammaInverse(block);
	}
	
	
	// The non-linear layer
	private void gamma(byte[] block) {
		for (int i = 0; i < 64; i++)
			block[i] = sub[block[i] & 0xFF];
	}
	
	
	// The cyclical permutation
	private void pi(byte[] blockin, byte[] blockout) {
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++)
				blockout[((i + j) & 7) << 3 | j] = blockin[i << 3 | j];
		}
	}
	
	
	// The linear diffusion layer
	private void theta(byte[] blockin, byte[] blockout) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int sum = 0;
				for (int k = 0; k < 8; k++)
					sum ^= mul[k][blockin[i << 3 | (j + k) & 7] & 0xFF];
				blockout[i << 3 | j] = (byte)sum;
			}
		}
	}
	
	
	private void sigma(byte[] block, byte[] key) { // The key addition. Self-inverting.
		for (int i = 0; i < 64; i++)
			block[i] ^= key[i];
	}
	
	
	private void gammaInverse(byte[] block) {
		for (int i = 0; i < 64; i++)
			block[i] = subinv[block[i] & 0xFF];
	}
	
	
	private void piInverse(byte[] blockin, byte[] blockout) {
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++)
				blockout[i << 3 | j] = blockin[((i + j) & 7) << 3 | j];
		}
	}
	
	
	private void thetaInverse(byte[] blockin, byte[] blockout) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int sum = 0;
				for (int k = 0; k < 8; k++)
					sum ^= mulinv[k][blockin[i << 3 | (j + k) & 7] & 0xFF];
				blockout[i << 3 | j] = (byte)sum;
			}
		}
	}
	
}