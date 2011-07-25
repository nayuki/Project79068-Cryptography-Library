package p79068.crypto.hash;

import java.util.Arrays;

import p79068.crypto.Zeroizer;
import p79068.hash.HashValue;
import p79068.math.IntegerBitMath;


public abstract class AbstractSha1Core extends BlockHasherCore {
	
	protected int[] state;
	
	
	
	protected AbstractSha1Core() {
		state = new int[]{0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0};
	}
	
	
	
	@Override
	public AbstractSha1Core clone() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		AbstractSha1Core result = (AbstractSha1Core)super.clone();
		result.state = result.state.clone();
		return result;
	}
	
	
	@Override
	public void zeroize() {
		if (state == null)
			throw new IllegalStateException("Already zeroized");
		state = Zeroizer.clear(state);
	}
	
	
	@Override
	public HashValue getHashDestructively(byte[] block, int blockLength, long length) {
		block[blockLength] = (byte)0x80;
		blockLength++;
		Arrays.fill(block, blockLength, block.length, (byte)0);
		if (blockLength + 8 > block.length) {
			compress(block);
			Arrays.fill(block, (byte)0);
		}
		for (int i = 0; i < 8; i++)
			block[block.length - 1 - i] = (byte)((length * 8) >>> (i * 8));
		compress(block);
		return new HashValue(IntegerBitMath.toBytesBigEndian(state));
	}
	
}
