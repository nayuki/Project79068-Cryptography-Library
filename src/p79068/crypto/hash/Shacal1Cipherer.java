package p79068.crypto.hash;

import java.util.Arrays;
import p79068.crypto.Zeroizer;
import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.Shacal1;
import p79068.lang.BoundsChecker;
import p79068.math.IntegerBitMath;


final class Shacal1Cipherer extends Cipherer {
	
	private int[] keySchedule;
	
	
	
	public Shacal1Cipherer(Shacal1 cipher, byte[] key) {
		super(cipher, key);
		setKey(key);
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % 20 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		// For each block of 20 bytes
		for (int end = off + len; off < end; off += 20) {
			
			// Pack bytes into int32s in big endian
			int[] message = new int[5];
			for (int i = 0; i < 20; i++)
				message[i >>> 2] |= (b[off + i] & 0xFF) << ((3 ^ i) << 3);
			
			Sha1Core.encrypt(keySchedule, message);
			
			// Unpack int32s into bytes in big endian
			for (int i = 0; i < 20; i++)
				b[off + i] = (byte)(message[i >>> 2] >>> ((3 ^ i) << 3));
		}
	}
	
	
	@Override
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if (len % 20 != 0)
			throw new IllegalArgumentException("Invalid block length");
		
		// For each block of 20 bytes
		for (int end = off + len; off < end; off += 20) {
			
			// Pack bytes into int32s in big endian
			int[] message = new int[5];
			for (int i = 0; i < 20; i++)
				message[i >>> 2] |= (b[off + i] & 0xFF) << ((3 ^ i) << 3);
			
			Sha1Core.decrypt(keySchedule, message);
			
			// Unpack int32s into bytes in big endian
			for (int i = 0; i < 20; i++)
				b[off + i] = (byte)(message[i >>> 2] >>> ((3 ^ i) << 3));
		}
	}
	
	
	@Override
	public Shacal1Cipherer clone() {
		Shacal1Cipherer result = (Shacal1Cipherer)super.clone();
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
		keySchedule = Arrays.copyOf(keySchedule, 80);  // Expand to 80 entries
		
		// Expand the key schedule
		for (int i = 16; i < 80; i++) {
			int temp = keySchedule[i - 3] ^ keySchedule[i - 8] ^ keySchedule[i - 14] ^ keySchedule[i - 16];
			keySchedule[i] = temp << 1 | temp >>> 31;
		}
	}
	
}