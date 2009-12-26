package p79068.datastruct;


public final class ArrayListTest extends ListTest {
	
	@Override
	protected <E> ArrayList<E> newList() {
		return new ArrayList<E>();
	}
	
}