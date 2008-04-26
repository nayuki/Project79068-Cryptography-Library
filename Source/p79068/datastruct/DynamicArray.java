package p79068.datastruct;

public abstract class DynamicArray<E> {
	
	protected Object[] objects;
	
	protected double ratio;
	
	protected int length;
	
	
	
	protected DynamicArray(int initCapacity) {
		this(initCapacity, 2);
	}
	
	
	protected DynamicArray(int initCapacity, double ratio) {
		if (initCapacity < 1)
			throw new IllegalArgumentException();
		if (ratio < 1 || ratio > Integer.MAX_VALUE || Double.isNaN(ratio))
			throw new IllegalArgumentException();
		objects = new Object[initCapacity];
		this.ratio = ratio;
		length = 0;
	}
	
	
	
	protected void upsize(int minCapacity) {
		if (minCapacity < length)
			throw new IllegalArgumentException();
		int newcapacity = objects.length;
		while (newcapacity < minCapacity) {
			long temp = Math.max(Math.round(newcapacity * ratio), newcapacity + 1);  // Increase the capacity by at least 1
			newcapacity = (int)Math.min(temp, Integer.MAX_VALUE);  // But don't let it exceed Integer.MAX_VALUE
		}
		resize(newcapacity);
	}
	
	
	protected void downsize() {
		int newcapacity = objects.length;
		while (newcapacity > 1 && (double)length / newcapacity <= ratio) {
			int temp = Math.min((int)Math.round(newcapacity / ratio), newcapacity - 1);  // Decrease the capacity by at least 1
			newcapacity = Math.max(temp, 1);  // But don't let the capacity drop below 1
		}
	}
	
	
	protected void resize(int newCapacity) {
		if (newCapacity == objects.length)
			return;  // No action required
		if (newCapacity < length)
			throw new IllegalArgumentException();
		Object[] newobjects = new Object[newCapacity];
		System.arraycopy(objects, 0, newobjects, 0, length);
		objects = newobjects;
	}
	
}