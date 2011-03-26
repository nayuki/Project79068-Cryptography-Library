package p79068.crypto.hash;

import p79068.crypto.Zeroizable;
import p79068.crypto.Zeroizer;
import p79068.lang.BoundsChecker;
import p79068.lang.NullChecker;
import p79068.util.hash.AbstractHasher;
import p79068.util.hash.HashValue;


/**
 * A hasher that only applies the compression function after each block.
 * <p>The instance returned by a BlockHashFunction is not necessarily a BlockHasher.</p>
 */
public final class BlockHasher extends AbstractHasher implements Zeroizable {
	
	/**
	 * The data of the current block.
	 */
	private byte[] block;
	
	/**
	 * The number of bytes filled in the current block. It is in the range [{@code 0}, {@code block.length}) initially and after each {@code update()} operation.
	 */
	private int blockLength;
	
	/**
	 * The total length of the message, in bytes. Warning: It overflows silently.
	 */
	private long length;
	
	
	private BlockHasherCore core;
	
	
	
	/**
	 * Constructs a new instance with the specified hash algorithm and block length.
	 */
	public BlockHasher(BlockHashFunction func, BlockHasherCore core) {
		super(func);
		NullChecker.check(core);
		block = new byte[func.getBlockLength()];
		blockLength = 0;
		length = 0;
		this.core = core;
	}
	
	
	
	/**
	 * Updates the current hash with the specified byte.
	 * @throws IllegalStateException if this object has been zeroized
	 */
	@Override
	public void update(byte b) {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		block[blockLength] = b;
		blockLength++;
		if (blockLength == block.length) {
			core.compress(block);
			blockLength = 0;
		}
		length++;
	}
	
	
	/**
	 * Updates the current hash with the specified byte array.
	 * @throws NullPointerException if {@code b} is {@code null}
	 * @throws IndexOutOfBoundsException if {@code off} and {@code len} specify that indices out of array {@code b}'s range to be accessed
	 * @throws IllegalStateException if this object has been zeroized
	 */
	@Override
	public void update(byte[] b, int off, int len) {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		
		length += len;  // Update length now, before len changes
		if (blockLength > 0) {  // Try to fill up the current block
			int temp = Math.min(block.length - blockLength, len);
			System.arraycopy(b, off, block, blockLength, temp);
			off += temp;
			len -= temp;
			blockLength += temp;
			if (blockLength == block.length) {
				core.compress(block);
				blockLength = 0;
			}
		}
		
		// If the current block was not completely filled and cleared, then len is now 0; there are no more remaining bytes to process.
		int temp = len / block.length * block.length;  // 0 <= temp <= len, and temp is a multiple of block.length
		core.compress(b, off, temp);
		off += temp;
		len -= temp;
		
		System.arraycopy(b, off, block, 0, len);  // 0 <= len < block.length
		blockLength += len;
	}
	
	
	/**
	 * Returns the hash value.
	 * @throws IllegalStateException if this object has been zeroized
	 */
	@Override
	public HashValue getHash() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		return core.clone().getHashDestructively(block.clone(), blockLength, length);
	}
	
	
	/**
	 * Returns a new hasher with the same internal state as this one's. The returned object uses the same algorithm, but its type need not be the same as this one's.
	 * @return a clone of this object
	 * @throws IllegalStateException if this object has been zeroized
	 */
	@Override
	public BlockHasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		BlockHasher result = (BlockHasher)super.clone();
		result.block = result.block.clone();
		result.core = result.core.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		length = 0;
		blockLength = 0;
		block = Zeroizer.clear(block);
		hashFunction = null;
		core.zeroize();
		core = null;
	}
	
}