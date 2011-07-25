package p79068.crypto.hash;

import java.util.Arrays;
import p79068.crypto.Zeroizer;
import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.Shacal2;
import p79068.lang.BoundsChecker;
import p79068.math.IntegerBitMath;


final class Shacal2Cipherer extends Cipherer {
	
	private int[] keySchedule;
	
	
	
	public Shacal2Cipherer(Shacal2 cipher, byte[] key) {
		super(cipher, key);
		setKey(key);
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % 32 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		// For each block of 32 bytes
		for (int end = off + len; off < end; off += 32) {
			
			// Pack bytes into int32s in big endian
			int[] message = new int[8];
			for (int i = 0; i < 32; i++)
				message[i >>> 2] |= (b[off + i] & 0xFF) << ((3 ^ i) << 3);
			
			Sha256Core.encrypt(keySchedule, message);
			
			// Unpack int32s into bytes in big endian
			for (int i = 0; i < 32; i++)
				b[off + i] = (byte)(message[i >>> 2] >>> ((3 ^ i) << 3));
		}
	}
	
	
	@Override
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % 32 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		// For each block of 32 bytes
		for (int end = off + len; off < end; off += 32) {
			
			// Pack bytes into int32s in big endian
			int[] message = new int[8];
			for (int i = 0; i < 32; i++)
				message[i >>> 2] |= (b[off + i] & 0xFF) << ((3 ^ i) << 3);
			
			Sha256Core.decrypt(keySchedule, message);
			
			// Unpack int32s into bytes in big endian
			for (int i = 0; i < 32; i++)
				b[off + i] = (byte)(message[i >>> 2] >>> ((3 ^ i) << 3));
		}
	}
	
	
	@Override
	public Shacal2Cipherer clone() {
		Shacal2Cipherer result = (Shacal2Cipherer)super.clone();
		result.keySchedule = result.keySchedule.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (cipher == null)
			return;
		keySchedule = Zeroizer.clear(keySchedule);
		super.zeroize();
	}
	
	
	
	private void setKey(byte[] key) {
		key = Arrays.copyOf(key, 64);  // Truncates or zero-pads the key to 64 bytes
		keySchedule = IntegerBitMath.fromBytesBigEndian(key);
		keySchedule = Arrays.copyOf(keySchedule, 64);  // Expand to 64 entries
		
		// Expand the key schedule
		for (int i = 16; i < 64; i++) {
			int s0 = (keySchedule[i-15] << 25 | keySchedule[i-15] >>> 7) ^ (keySchedule[i-15] << 14 | keySchedule[i-15] >>> 18) ^ (keySchedule[i-15] >>> 3);
			int s1 = (keySchedule[i-2] << 15 | keySchedule[i-2] >>> 17) ^ (keySchedule[i-2] << 13 | keySchedule[i-2] >>> 19) ^ (keySchedule[i-2] >>> 10);
			keySchedule[i] = keySchedule[i - 16] + keySchedule[i - 7] + s0 + s1;
		}
	}
	
}
