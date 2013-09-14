package p79068.crypto.cipher;

import p79068.util.HashCoder;


/**
 * The RC2 block cipher. It is described in RFC 2268.
 * <p>Key lengths: 8 to 1024 bits at multiples of 8 bits</p>
 * <p>Block length: 64 bits (8 bytes)</p>
 */
public final class Rc2 extends AbstractCipher implements BlockCipher {
	
	private int effectiveKeyLength;  // In bits
	private int keyLength;  // In bytes
	
	
	
	public Rc2(int effectiveKeyLength, int keyLength) {
		super(String.format("RC2 (%d-bit effective key length, %d-bit key)", effectiveKeyLength, keyLength * 8), 8, keyLength);
		if (keyLength < 1 || keyLength > 128)
			throw new IllegalArgumentException();
		this.effectiveKeyLength = effectiveKeyLength;
		this.keyLength = keyLength;
	}
	
	
	
	@Override
	protected Cipherer newCiphererUnchecked(byte[] key) {
		return new Rc2Cipherer(this, key);
	}
	
	
	public int getEffectiveKeyLength() {
		return effectiveKeyLength;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Rc2))
			return false;
		Rc2 cipher = (Rc2)obj;
		return effectiveKeyLength == cipher.effectiveKeyLength && keyLength == cipher.keyLength;
	}
	
	
	@Override
	public int hashCode() {
		HashCoder h = HashCoder.newInstance();
		h.add(effectiveKeyLength);
		h.add(keyLength);
		return h.getHashCode();
	}
	
}
